package com.rubic.memcache.cache.impl;

import com.rubic.memcache.cache.Cache;
import com.rubic.memcache.cache.CommandHandler;
import com.rubic.memcache.common.Constants;
import com.rubic.memcache.common.store.pojo.Item;

import java.util.List;

/**
 * Created by Administrator on 2018/4/3.
 */
public class DeleteCommandHandler implements CommandHandler {

    private List<String> commandList;

    public DeleteCommandHandler() {}

    public DeleteCommandHandler(List<String> commandList) {
        this.commandList = commandList;
    }

    public String handleCommand() {
        String message = new DeleteCommandHandler(this.commandList).checkCommand();
        if (!message.equals(Constants.NORMAL_FLAG)) {
            return  message;
        }

        System.out.println(commandList.get(0) + " " + commandList.get(1));
        int size = commandList.size();
        String key = commandList.get(1);

        if (Cache.CACHE.containsKey(key)) {
            Item item = Cache.CACHE.get(key);
            long now = System.currentTimeMillis();
            long diff = now - item.getLastUsedTime();

            if ((item.getExpireTime() != 0 && diff >= item.getExpireTime())) { // 判断过期时间
                return Constants.NORMAL_NONDELETED + "\r\n";
            } else {
                Cache.CACHE.remove(key);
                if (size == 3 && commandList.get(2).equals("noreply")) {
                    return "";
                } else {
                    return Constants.NORMAL_DELETED + "\r\n";
                }
            }
        } else {
            return Constants.NORMAL_NONDELETED + "\r\n";
        }
    }

    public String checkCommand() {
        int size = commandList.size();
        if (commandList == null || size < 2) {
            return Constants.NORMAL_ERROR + "\r\n";
        }

        if (size > 3) {
            return Constants.CLIENT_ERROR + " " + Constants.FORMAT_ERROR + "\r\n";
        }

        return Constants.NORMAL_FLAG;
    }
}
