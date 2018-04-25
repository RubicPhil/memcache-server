package com.rubic.memcache.cache;

/**
 * Created by Administrator on 2018/4/3.
 */
public interface CommandHandler {

    String handleCommand();

    String checkCommand();
}
