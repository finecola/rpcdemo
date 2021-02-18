package com.choco.server;

import com.choco.common.ReflectionUtils;
import com.choco.proto.Request;
import com.choco.proto.ServiceDescriptor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ServiceManager {
    private final Map<ServiceDescriptor, ServiceInstance> services;

    public ServiceManager() {
        this.services = new ConcurrentHashMap<>();
    }

    /**
     * bean是 interfaceClass的实现类实例
     * 这里是把接口所有的public方法都注册为服务
     */
    public <T> void register(Class<T> interfaceClass, T bean) {
        //获取接口的所有public方法, 并注册到services里面
        Method[] methods = ReflectionUtils.getPublicMethods(interfaceClass);

        for (Method method : methods) {
            ServiceInstance instance = new ServiceInstance(bean, method);
            ServiceDescriptor sd = ServiceDescriptor.from(interfaceClass, method);
            services.put(sd, instance);

            log.info("register success: {} {}", sd.getClazz(), sd.getMethod());
        }
    }

    /**
     * 根据request,获取service, 然后进行服务查找
     * @param request
     * @return
     */
    public ServiceInstance lookup(Request request){
        ServiceDescriptor sd = request.getService();
        //在map里面,根据sd查找服务实例
        return services.get(sd);
    }
}
