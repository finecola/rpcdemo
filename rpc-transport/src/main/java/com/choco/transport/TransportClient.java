package com.choco.transport;

import com.choco.proto.Peer;

import java.io.InputStream;

/**
 * 1.建立连接
 * 2.发送数据,并等待响应
 * 3.关闭连接
 */
@SuppressWarnings("all")
public interface TransportClient {
    /**
     * 建立连接
     *
     * @param peer
     */
    void connect(Peer peer);

    /**
     * 发送数据, 并等待响应
     *
     * @param data
     * @return
     */
    InputStream write(InputStream data);

    /**
     * 关闭连接
     */
    void close();
}
