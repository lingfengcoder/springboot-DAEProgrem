package com.travel.common.annotation;

//import com.travel.common.entity.user.User;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * spring el表达式解析
 */
public class SpelParser {

    private static ExpressionParser parser=new SpelExpressionParser();

    /**
     *
     * @param key el表达式
     * @param paramNames 方法的形参
     * @param args 方法的实参
     * @return 解析后的字符串
     */
    public static String getKey(String key,String[] paramNames,Object[]args){
        //将key的字符串解析为el表达式
        if(key==null||key.equals(""))return key;
        Expression exp=parser.parseExpression(key);
        //初始化赋值上下文
        EvaluationContext context=new StandardEvaluationContext();
        if(args.length<=0){
            return null;
        }
        //将形参和形参值 配对并赋值到上下文中
        for(int i=0;i<args.length;i++){
            context.setVariable(paramNames[i],args[i]);
        }
        return exp.getValue(context,String.class);
    }

//    public static void main(String[] args) {
//        //表达式
//        String key="#user.id+'666'+#username";
//
//        User user=new User();
//        user.setId(110);
//
//        String username="wz";
//        //形参名字
//        String paramName[]=new String[]{"user","username"};
//        //形参数值(实参)
//        Object [] _args=new Object[2];
//        _args[0]=user;
//        _args[1]=username;
//        System.out.println(SpelParser.getKey(key,paramName,_args));
//
//    }
}
