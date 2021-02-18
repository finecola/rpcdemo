package com.choco.server;

import com.choco.common.ReflectionUtils;
import com.choco.proto.Request;

/**
 * 调用具体的服务
 */
public class ServiceInvoke {
    public Object invoke(ServiceInstance instance, Request request){
        /**
         * 调用类的具体实例
         */
        return ReflectionUtils.invoke(instance.getTarget(), instance.getMethod(), request.getParameters());
    }
}
