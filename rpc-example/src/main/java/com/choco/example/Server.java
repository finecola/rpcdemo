package com.choco.example;

import com.choco.server.RpcServer;

/**
 * Created by choco on 2021/2/16 21:38
 */
public class Server {
    public static void main(String[] args) {
        RpcServer server = new RpcServer();

        server.register(CalcService.class, new CalcServiceImpl());
        server.start();
    }
}
