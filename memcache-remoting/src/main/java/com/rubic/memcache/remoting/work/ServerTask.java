package com.rubic.memcache.remoting.work;

import com.rubic.memcache.cache.CommandHandler;
import com.rubic.memcache.cache.CommandHandlerFactory;
import com.rubic.memcache.common.Constants;
import com.rubic.memcache.common.utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/**
 * 每个连接都由线程执行
 * Created by Administrator on 2018/4/3.
 */
public class ServerTask implements Runnable {

    private Socket acceptSocket;

    public ServerTask(Socket socket) {
        acceptSocket = socket;

    }

    public void run() {

        try {
            CommandHandlerFactory factory = CommandHandlerFactory.getInstance();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(acceptSocket.getInputStream()));
            PrintWriter printWriter = new PrintWriter(acceptSocket.getOutputStream(),true);

            String line = "";
            String response = "";
            StringBuilder messageBuilder = new StringBuilder("");

            while (true) {
                line = bufferedReader.readLine();

                if (line.equals("quit")) {
                    break;
                }

                List<String> commandList = StringUtils.getWords(line);
                if (commandList.size() == 0) { // 命令为空
                    printWriter.write(Constants.NORMAL_ERROR + "\r\n");
                    printWriter.flush();
                    continue;
                }

                CommandHandler commandHandler = factory.getHandler(commandList, bufferedReader);
                response = commandHandler.handleCommand();

                printWriter.write(response);
                printWriter.flush();
            }

            printWriter.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                acceptSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
