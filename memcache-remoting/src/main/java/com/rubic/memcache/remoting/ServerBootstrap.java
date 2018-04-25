package com.rubic.memcache.remoting;

import com.rubic.memcache.remoting.server.TCPServer;

/**
 * Created by Administrator on 2018/4/3.
 */
public class ServerBootstrap {
    public static void main(String[] args) {
        System.out.println("######### Server is Running #########");
        new TCPServer(args.length == 0 ? null : args[0]).exec();
    }
}
