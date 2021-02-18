package com.choco.proto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表示一个RPC请求
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Request {
    /**
     * 里面只是描述了参数类型, 返回值类型
     */
    private ServiceDescriptor service;
    /**
     * 方法的参数值
     */
    private Object[] parameters;
}
