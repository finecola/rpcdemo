package com.choco.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;

/**
 * Created by choco on 2021/2/16 15:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("all")
public class ServiceInstance {
    /**
     * 根据target生成具体的实例
     */
    private Object target;
    /**
     * target的哪个方法暴露出来成为服务
     */
    private Method method;
}
