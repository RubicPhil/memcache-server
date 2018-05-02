import com.rubic.memcache.common.utils.StringUtils;

/**
 * Created by Administrator on 2018/4/4.
 */
public class Test {
    public static void main(String[] args) {
        String s = "-0.12";
        System.out.println(StringUtils.isDigital(s));
    }
}
