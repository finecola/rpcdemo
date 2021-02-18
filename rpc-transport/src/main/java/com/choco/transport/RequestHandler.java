package com.choco.transport;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 处理网络请求
 */
public interface RequestHandler {
    /**
     * 获取输入流, 返回输出流
     *
     * @param receive
     * @param reply
     */
    void onRequest(InputStream receive, OutputStream reply);
}
