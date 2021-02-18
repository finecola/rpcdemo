package com.choco.example;

import com.choco.client.RpcClient;

/**
 * Created by choco on 2021/2/16 21:38
 */
public class Client {
    public static void main(String[] args) {
        RpcClient client = new RpcClient();
        CalcService instance = client.getProxyInstance(CalcService.class);

        int sum = instance.add(5, 7);
        int res = instance.minus(1, 8);

        System.out.println(sum);
        System.out.println(res);
    }
}
