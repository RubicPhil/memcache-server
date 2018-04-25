package com.rubic.memcache.cache;

import com.rubic.memcache.cache.impl.DeleteCommandHandler;
import com.rubic.memcache.cache.impl.GetCommandHandler;
import com.rubic.memcache.cache.impl.NoNameCommandHandler;
import com.rubic.memcache.cache.impl.SetCommandHandler;

import java.io.BufferedReader;
import java.util.List;

/**
 * 简单工厂模式 + 单例模式完成命令处理类的实例化
 *
 * Created by Administrator on 2018/4/3.
 */
public class CommandHandlerFactory {

    private CommandHandlerFactory() {}

    private static class LazyHolder {
        public static CommandHandlerFactory commandHandlerFactory = new CommandHandlerFactory();
    }

    public static CommandHandlerFactory getInstance() { return LazyHolder.commandHandlerFactory; }

    /**
     * 返回具体命令执行类
     * @param commandList
     * @param bufferedReader
     * @return
     */
    public CommandHandler getHandler(final List<String> commandList, final BufferedReader bufferedReader) {
        String commandType = commandList.get(0);

        if (commandType.equals("set")) {
            return new SetCommandHandler(commandList, bufferedReader);
        } else if (commandType.equals("get")) {
            return new GetCommandHandler(commandList);
        } else if (commandType.equals("delete")) {
            return new DeleteCommandHandler(commandList);
        } else {
            return new NoNameCommandHandler();
        }
    }

}
