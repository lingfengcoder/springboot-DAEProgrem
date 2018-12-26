package com.travel.common.annotation.DAEAspect;

import com.travel.common.annotation.AspectInterface;
import com.travel.common.annotation.SpelParser;
import com.travel.common.exception.FileException;
import com.travel.common.superHttpCenter.DAEService.DAEServiceImpl;
import com.travel.common.superHttpCenter.controller.httpBean.*;
import com.travel.common.superHttpCenter.controller.httpResponseBean.Http500;
import com.travel.common.superHttpCenter.mockHttpRequest.MockHttpRequest;
import com.travel.common.util.DAE.bean.AESBean;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static com.travel.common.util.FileOption.getFileSuffixName;
import static com.travel.common.util.FileType.*;


@Aspect
@Component
public class DAEAnnAspect implements AspectInterface {

    private final static String AES_BEAN = "AES_BEAN";
    private final static String ARGS = "ARGS";


    @Autowired
    DAEServiceImpl DAEServiceImpl;
    //Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(com.travel.common.annotation.DAEAspect.DAEAnnotation)")
    public void DAEHandler() {
        System.out.println("into DAEHandler");
    }

    @Before("DAEHandler()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        StringBuffer stringBuffer = new StringBuffer();
        // 记录下请求内容
        stringBuffer.append("\r\nURL:" + request.getRequestURL().toString() + "\r\n");
        stringBuffer.append("HTTP_METHOD:" + request.getMethod() + "\r\n");
        stringBuffer.append("IP:" + request.getRemoteAddr() + "\r\n");
        stringBuffer.append("CLASS_METHOD:" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "\r\n");
        stringBuffer.append("ARGS:"+Arrays.asList(joinPoint.getArgs())+"\r\n");
        logger.info(stringBuffer.toString());
//        //通过aop切点获取方法
//        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
//        //通过方法获取方法的形参
//        String[] parameterNames = new LocalVariableTableParameterNameDiscoverer().getParameterNames(method);
//        System.out.println(parameterNames);
//        //方法实参
//        Object obj = joinPoint.getArgs();
//        System.out.println(obj);
    }

    @AfterReturning(returning = "ret", pointcut = "DAEHandler()")
    public void doAfterReturning(Object ret){
        // 处理完请求，返回内容
         //System.out.println("方法的返回值 : " + ret);

    }

    //后置异常通知
    @AfterThrowing("DAEHandler()")
    public void handlerException(JoinPoint jp) {
        logger.error("方法异常时执行.....");

    }

    //后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
    @After("DAEHandler()")
    public void after(JoinPoint jp) {
        //System.out.println("方法最后执行.....");
    }

    //环绕
    @Around("@annotation(DAE)")
    public Object doAround(ProceedingJoinPoint pjp, DAEAnnotation DAE)throws Throwable {
        String DAEType = (String) getRealKey(DAE.DAEType(), pjp);
        boolean needEncode = DAE.needEncrypt();
        boolean needDecode = DAE.needDecrypt();
        boolean needLog = DAE.needLog();
        String fileType = DAE.allowFileType();
        String[] fileTypes = DAE.allowFileTypes();
        //调用方法本身
        Object res = null;
        try {
            //前置处理
            Object[] args = pjp.getArgs();
            Map<String, Object> argMap = null;
            //文件过滤
            try {
                uploadFileFilter(args, fileType, fileTypes);
            } catch (FileException fe) {
                return new Http500("不支持的文件格式:" + fe.getExceptionMsg());
            }

            //解密处理
            if (needDecode) {
                argMap = handlerArgs(args);
                args = argMap == null ? null : (Object[]) argMap.get(ARGS);
            }
            //源方法执行
            if (args != null) {
                res = pjp.proceed(args);
            } else {
                logger.error("解密失败");
                return new Http500("解密失败");
            }

            //处理返回
            if (needEncode) {//需要加密
                if (argMap != null) {
                    res = handlerRet(res, (AESBean) argMap.get(AES_BEAN));
                } else {
                    return new Http500("解密失败");
                }
            }
        } catch (Throwable throwable) {
            //抛出异常 交给统一异常处理器处理
            throw throwable;
            //return new Http500();
        }
        /* //拦截的类路径
            String classpath = pjp.getSourceLocation().getWithinType().toString();
            //拦截的方法名
            String fncname = pjp.getSignature().getName();
            */
        return res;
    }

    private boolean uploadFileFilter(Object[] srcArgs, Object... ruleSwitch) throws FileException {
        String[] rules = {};
        String suffix="";
        for (Object o : ruleSwitch) {
            if (o.getClass().equals(String[].class)) {
                //默认值 选取另外
                if (Arrays.equals((String[]) o, NO_ALLOWS)) continue;
                rules = (String[]) o;
                break;
            }
            if (o.getClass().equals(String.class)) {
                //默认值 选取另外
                if (o.equals(NO_ALLOW)) continue;
                rules = queryAllowFileType((String) o);
            }
        }
        for (Object o : srcArgs) {
            if (o instanceof HttpServletRequest) {
                HttpServletRequest req = (HttpServletRequest) o;
                //没有上传文件
                if(!(req instanceof StandardMultipartHttpServletRequest)){
                    return true;
                }
                if (!req.getClass().equals(StandardMultipartHttpServletRequest.class)
                        || ((StandardMultipartHttpServletRequest) req).getMultiFileMap().isEmpty())
                    return true;
                Map<?, ?> mapTemp = ((StandardMultipartHttpServletRequest) req).getMultiFileMap();
                for (Map.Entry<?, ?> item : mapTemp.entrySet()) {
                    //Object o1= item.getKey();
                    LinkedList<MultipartFile> fileList = (LinkedList) item.getValue();
                    //对文件类型过滤
                    for (MultipartFile file : fileList) {
                        suffix=getFileSuffixName(file.getOriginalFilename());
                        if (!fileTypeRulesCheck(rules,suffix)) {
                            throw new FileException(suffix);
                            //return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * 处理请求参数
     *
     * @param
     * @return
     */
    private Map<String, Object> handlerArgs(Object[] srcArgs) {
        //返回值初始化
        Map<String, Object> resMap = new HashMap<>();
        resMap.put(AES_BEAN, null);
        resMap.put(ARGS, null);
        if (srcArgs == null) return null;
        if (srcArgs.length == 1) {
            // 利用多态 get /post...统一处理
            HttpServletRequest req = (HttpServletRequest) srcArgs[0];
            Map<String, String[]> params = req.getParameterMap();
            //检查脏数据
            if(!checkRequestParams(params)){
                logger.error("接口存在脏数据，不予解密");
                return null;
            }
            //处理解密数据
            RequestDecryptBean requestDecryptBean = DAEServiceImpl.decryptData(processParams(params));
            //利用多态 重写getParameter()方法 返回解密后的参数
            MockHttpRequest mockHttpRequest = new MockHttpRequest(req, requestDecryptBean);
            if (requestDecryptBean != null) {
                resMap.put(AES_BEAN, requestDecryptBean.getAesBean());
                resMap.put(ARGS, new Object[]{mockHttpRequest});
                return resMap;
            } else {
                //解密失败
                return null;
            }
            //多参数情况
        } else {
            //GET
            if (srcArgs.length == 2) {
                HttpServletRequest req = (HttpServletRequest) srcArgs[0];
                Map<String, String[]> params = req.getParameterMap();
                //检查脏数据
                if(!checkRequestParams(params)){
                    logger.error("接口存在脏数据，不予解密");
                    return null;
                }
                //处理解密数据
                RequestDecryptBean requestDecryptBean = DAEServiceImpl.decryptData(processParams(params));
                if (requestDecryptBean != null) {
                    //设置map参数
                    srcArgs[1] = requestDecryptBean.getData();
                    resMap.put(AES_BEAN, requestDecryptBean.getAesBean());
                    resMap.put(ARGS, srcArgs);
                } else {
                    //解密失败
                    return null;
                }
                return resMap;
            }
        }
        return null;
    }

    /**
     * 统一接口数据格式
     * @param params
     * @return
     */
    private RequestEncryptBean processParams(Map<String, String[]> params) {
        RequestEncryptBean requestEncryptBean = new RequestEncryptBean();
        requestEncryptBean.setAesBeanStr(params.get(RequestDAEEnum.AESBean.name())[0]);
        requestEncryptBean.setTimeStamp(params.get(RequestDAEEnum.timeStamp.name())[0]);
        requestEncryptBean.setSignature(params.get(RequestDAEEnum.signature.name())[0]);
        requestEncryptBean.setData(params.get(RequestDAEEnum.data.name())[0]);
        return requestEncryptBean;
    }

    /**
     * 接口数据格式脏检查
     * @param params
     * @return
     */
    private boolean  checkRequestParams(Map<String, String[]> params){

        if(params.get(RequestDAEEnum.AESBean.name())==null){
            return false;
        }
        if(params.get(RequestDAEEnum.timeStamp.name())==null){
            return false;
        }
        if(params.get(RequestDAEEnum.signature.name())==null){
            return false;
        }
        if(params.get(RequestDAEEnum.data.name())==null){
            return false;
        }
        return true;
    }

    /**
     * 处理返回数据
     *
     * @param srcRet
     * @return
     */
    private Object handlerRet(Object srcRet, AESBean aesBean) {
        ResponseEncryptBean responseEncryptBean;
        try {
            responseEncryptBean = DAEServiceImpl.encryptData(aesBean, srcRet);
        } catch (Exception e) {
            e.printStackTrace();
            return new Http500("加密失败");
        }
        return responseEncryptBean;
    }

    /**
     * 获取注解 数据 (el表达式方式)
     *
     * @param str
     * @param pjp
     * @return
     */
    private Object getRealKey(Object str, ProceedingJoinPoint pjp) {
        if (str.getClass().equals(String.class)) {
            //通过aop切点获取方法
            Method method = ((MethodSignature) pjp.getSignature()).getMethod();
            //通过方法获取方法的形参
            String[] parameterNames = new LocalVariableTableParameterNameDiscoverer().getParameterNames(method);
            return SpelParser.getKey((String) str, parameterNames, pjp.getArgs());
        } else {
            return str;
        }
    }


}
