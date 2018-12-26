import com.travel.app.entity.user.User;
import com.travel.common.annotation.regexAspect.RegexFilter;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * @Auther: wz
 * @Date: 2018/12/24 12:39
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
        boolean hasAnnotation = User.class.isAnnotationPresent(RegexFilter.class);

        if ( hasAnnotation ) {
            RegexFilter regexFilter = User.class.getAnnotation(RegexFilter.class);

            System.out.println("id:"+regexFilter.regex());
        }
        User user=new User();
        user.setPhone("188451212601");

       // System.out.println( Pattern.matches(User.phone_REGEX,));

        try {
            System.out.println(temp1(user));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean temp1(Object obj){
        Class<?> regexFilterClass = obj.getClass();
        Map<String, String> map = new ConcurrentHashMap<>();
        Field[] fields = regexFilterClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(RegexFilter.class)) {
                //获取private权限
                field.setAccessible(true);
                RegexFilter regexFilter = field.getDeclaredAnnotation(RegexFilter.class);
                //获取属性
               // String name = field.getName();
                //获取属性值
                Object value = null;
                try {
                    value = field.get(obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();

                    return false;
                }
                //Class type=regexFilter.type();
               if(!Pattern.matches(regexFilter.regex(),String.valueOf(value))) return false;
            }
        }
        return true;
    }
}
