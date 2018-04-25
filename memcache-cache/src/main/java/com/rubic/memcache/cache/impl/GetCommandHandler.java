package com.rubic.memcache.cache.impl;

import com.rubic.memcache.cache.Cache;
import com.rubic.memcache.cache.CommandHandler;
import com.rubic.memcache.common.Constants;
import com.rubic.memcache.common.store.pojo.Item;

import java.util.List;

/**
 * Created by Administrator on 2018/4/3.
 */
public class GetCommandHandler implements CommandHandler {

    private List<String> commandList;

    public GetCommandHandler() {}

    public GetCommandHandler(List<String> commandList) {
        this.commandList = commandList;
    }


    public String handleCommand() {
        String message = new GetCommandHandler(this.commandList).checkCommand();
        if (!message.equals(Constants.NORMAL_FLAG)) {
            return  message;
        }

        int length = commandList.size();
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 1; i < length; i++) {
            String key = commandList.get(i);
            System.out.println("get key: " + key);
            Item item = Cache.CACHE.get(key);


            if (item == null ) {
                stringBuilder.append("");
            } else {
                long now = System.currentTimeMillis();
                long diff = now - item.getLastUsedTime();
                if (item.getExpireTime() != 0 && diff >= item.getExpireTime()) { //判断过期时间
                    stringBuilder.append("");
                } else {
                    stringBuilder.append(Constants.NORMAL_VALUE);
                    stringBuilder.append(" ");
                    stringBuilder.append(key);
                    stringBuilder.append(" ");
                    stringBuilder.append(item.getFlags());
                    stringBuilder.append(" ");
                    stringBuilder.append(item.getBytes());
                    stringBuilder.append("\r\n");
                    stringBuilder.append(item.getValue());
                }
            }
        }
        stringBuilder.append(Constants.NORMAL_END);
        stringBuilder.append("\r\n");

        return stringBuilder.toString();
    }

    public String checkCommand() {
        if (commandList == null || commandList.size() < 2) {
            return Constants.NORMAL_ERROR + "\r\n";
        }

        return Constants.NORMAL_FLAG;
    }
}
