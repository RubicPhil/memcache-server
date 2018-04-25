package com.rubic.memcache.cache.impl;

import com.rubic.memcache.cache.CommandHandler;
import com.rubic.memcache.common.Constants;

/**
 * Created by Administrator on 2018/4/3.
 */
public class NoNameCommandHandler implements CommandHandler {

    public String handleCommand() {
        StringBuilder messageBuilder = new StringBuilder("");

        messageBuilder.append(Constants.NORMAL_ERROR);
        messageBuilder.append("\r\n");
        return messageBuilder.toString();
    }

    public String checkCommand() {
        return null;
    }
}
