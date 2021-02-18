package com.choco.proto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 表示socket传输的内容
 * 方法所属的类, 方法名称, 参数, 返回值
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ServiceDescriptor {
    private String clazz;
    private String method;
    private String returnType;
    private String[] parameterTypes;

    /**
     * 仅根据方法, 方法所属类构建传输内容
     * @param clazz
     * @param method
     * @return
     */
    public static ServiceDescriptor from(Class clazz, Method method){
        ServiceDescriptor sd = new ServiceDescriptor();
        sd.setClazz(clazz.getName());
        sd.setMethod(method.getName());
        sd.setReturnType(method.getReturnType().getName());

        Class[] parameterClass = method.getParameterTypes();
        String[] p = new String[parameterClass.length];

        for (int i = 0; i < parameterClass.length; i++) {
            p[i] = parameterClass[i].getName();
        }
        sd.setParameterTypes(p);

        return sd;
    }

    @Override
    public String toString() {
        return "ServiceDescriptor{" +
                "clazz='" + clazz + '\'' +
                ", method='" + method + '\'' +
                ", returnType='" + returnType + '\'' +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                '}';
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ServiceDescriptor sd = (ServiceDescriptor) obj;
        return sd.toString().equals(this.toString());
    }
}
