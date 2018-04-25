package com.rubic.memcache.cache.impl;

import com.rubic.memcache.cache.Cache;
import com.rubic.memcache.cache.CommandHandler;
import com.rubic.memcache.common.Constants;
import com.rubic.memcache.common.store.pojo.Item;
import com.rubic.memcache.common.utils.StringUtils;
import com.rubic.memcache.common.utils.TimeUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2018/4/3.
 */
public class SetCommandHandler implements CommandHandler {

    private BufferedReader bufferedReader;

    private List<String> commandList;

    public SetCommandHandler() {}

    public SetCommandHandler(List<String> commandList) {
        this.commandList = commandList;
    }

    public SetCommandHandler(List<String> commandList, BufferedReader bufferedReader) {
        this.commandList = commandList;
        this.bufferedReader = bufferedReader;
    }



    public String handleCommand() {
        String message = new SetCommandHandler(this.commandList).checkCommand();
        if (!message.equals(Constants.NORMAL_FLAG)) {
            return message;
        }

        int read = 0;
        int cur = 0;
        int bytes = Integer.valueOf(commandList.get(4));
        try {
            StringBuilder stringBuilder = new StringBuilder("");
            while (read < bytes) {
                String temp = bufferedReader.readLine();
                read += temp.length() + 2 * cur;
                cur++;

                stringBuilder.append(temp);
                stringBuilder.append("\r\n");
            }
            if (read > bytes) {
                return Constants.CLIENT_ERROR + " " + Constants.DATA_ERROR + "\r\n";
            }

            int flags = Integer.valueOf(commandList.get(2));
            long expire = Long.valueOf(commandList.get(3));
            String key = commandList.get(1);


            Item item = new Item();
            item.setBytes(bytes);
            item.setFlags(flags);
            item.setExpireTime(TimeUtils.getUnixTimeStamp(expire));
            item.setLastUsedTime(0L);
            item.setValue(stringBuilder.toString());

            Cache.CACHE.put(key, item);

            if (commandList.size() == 6 && commandList.get(5).equals("noreply")) {
                return "\r\n";
            } else {
                return Constants.NORMAL_STRORED + "\r\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String checkCommand() {
        if (commandList == null || commandList.size() > 6 || commandList.size() < 5) {
            return Constants.NORMAL_ERROR + "\r\n";
        }

        String flag = commandList.get(2);
        String expire = commandList.get(3);
        String bytes = commandList.get(4);
        if (StringUtils.isPureDigital(flag) && StringUtils.isDigital(expire)
                && StringUtils.isPureDigital(bytes)) {
            return Constants.NORMAL_FLAG;
        } else {
            return Constants.CLIENT_ERROR + " " + Constants.FORMAT_ERROR + "\r\n";
        }
    }
}
