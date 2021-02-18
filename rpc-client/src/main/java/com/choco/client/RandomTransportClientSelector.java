package com.choco.client;

import com.choco.common.ReflectionUtils;
import com.choco.proto.Peer;
import com.choco.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by choco on 2021/2/16 20:38
 */
@Slf4j
public class RandomTransportClientSelector implements TransportSelector{
    /**
     * 表示连接好的client
     */
    private final List<TransportClient> clients;

    public RandomTransportClientSelector(){
        clients = new ArrayList<>();
    }

    /**
     * count: 自定义初始化client数量
     * @param peers server信息集合
     * @param count client与server已经建立连接的数量
     * @param clazz TransportClient的实现类
     */
    @Override
    public synchronized void init(List<Peer> peers, Integer count, Class<? extends TransportClient> clazz) {
        //count表示需要创建的client数量
        count=Math.max(count, 1);

        for(Peer peer: peers){
            //每个client都去连接server
            for (int i = 0; i < count; i++) {
                TransportClient client = ReflectionUtils.newInstance(clazz);
                client.connect(peer);
                clients.add(client);
            }
            log.info("connect server:{}", peer);
        }
    }

    /**
     * 从clients集合里面移除client,并随机选择一个client返回
     */
    @Override
    public synchronized TransportClient select() {
        int i = new Random().nextInt(clients.size());
        return clients.remove(i);
    }

    /**
     * 把client加入到池子里
     * @param client
     */
    @Override
    public synchronized void release(TransportClient client) {
        clients.add(client);
    }

    /**
     * 关闭所有client连接
     */
    @Override
    public synchronized void close() {
        for (TransportClient client: clients){
            client.close();
        }
        clients.clear();
    }
}
