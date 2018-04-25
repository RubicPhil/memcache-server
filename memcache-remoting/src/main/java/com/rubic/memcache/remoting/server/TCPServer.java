package com.rubic.memcache.remoting.server;

import com.rubic.memcache.common.utils.TimeUtils;
import com.rubic.memcache.remoting.work.ServerTask;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 通过线程池支持多线程连接
 * Created by Administrator on 2018/4/3.
 */
public class TCPServer {

    private ServerSocket serverSocket = null;

    //io密集型推荐2 * Ncpu
    private final ExecutorService excutorService = Executors.newFixedThreadPool(2 * Runtime.getRuntime().availableProcessors());

    private final int SERVER_PORT;

    public TCPServer(String port) {
        SERVER_PORT = port != null ? Integer.valueOf(port) : 0;
    }

    public void exec() {
        try {
            serverSocket = new ServerSocket(SERVER_PORT == 0 ? 8083 : SERVER_PORT);
            TimeUtils.SERVER_STARTED = System.currentTimeMillis();

            while (true) {
                Socket acceptSocket = serverSocket.accept();
                excutorService.execute(new ServerTask(acceptSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            excutorService.shutdown();

            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {

    }

}
