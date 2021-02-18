package com.choco.client;

import com.choco.proto.Peer;
import com.choco.transport.TransportClient;

import java.util.List;

/**
 * 表示选择哪个server去连接
 */
public interface TransportSelector {
    /**
     * 初始化selector
     * @param peers server信息集合
     * @param count client与server已经建立连接的数量
     * @param clazz TransportClient的实现类
     */
    void init(List<Peer> peers, Integer count, Class<? extends TransportClient> clazz);

    /**
     * 选择一个transportClient与server做交互.
     */
    TransportClient select();

    void release(TransportClient client);

    void close();
}
