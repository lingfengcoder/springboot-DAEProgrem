import com.travel.common.util.RegexUtil;

/**
 * @Auther: wz
 * @Date: 2018/12/24 12:16
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
        String username = "";
        String password = "";
        new RegexUtil()
                .matches(RegexUtil.username_REGEX, username)
                .matches(RegexUtil.password_REGEX, password);
    }

}
