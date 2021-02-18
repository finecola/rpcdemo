package com.choco.transport;

/**
 * 1.启动, 监听端口
 * 2.接收数据,实际上是一个输入流,返回执行结果
 * 3.关闭监听
 */
public interface TransportServer {
    /**
     * 启动, 监听端口
     *
     * @param port
     * @param handler
     */
    void init(Integer port, RequestHandler handler);

    /**
     * 接收数据,实际上是一个输入流,返回执行结果
     */
    void start();

    /**
     * 关闭监听
     */
    void stop();
}
