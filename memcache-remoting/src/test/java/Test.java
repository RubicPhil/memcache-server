import com.rubic.memcache.cache.CommandHandler;
import com.rubic.memcache.cache.CommandHandlerFactory;
import com.rubic.memcache.cache.impl.DeleteCommandHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/3.
 */
public class Test {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("delete");
        list.add("key");
        CommandHandler handler = new DeleteCommandHandler(list);
        handler.handleCommand();
        CommandHandlerFactory factory = CommandHandlerFactory.getInstance();
        factory.getHandler(list, null);
        handler.handleCommand();
    }
}
