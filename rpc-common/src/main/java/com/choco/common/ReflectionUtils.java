package com.choco.common;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * Created by choco on 2021/2/16 13:39
 */
public class ReflectionUtils {
    /**
     * 根据class创建对象的实例
     * @param clazz
     * @param <T>
     */
    public static <T> T newInstance(Class<T> clazz){
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 获取类的public方法
     * @param clazz
     * @return
     */
    public static Method[] getPublicMethods(Class clazz){
        Method[] methods = clazz.getDeclaredMethods();
        ArrayList<Method> list = new ArrayList<>();

        for(Method m: methods){
            //过滤出public方法
            if(Modifier.isPublic(m.getModifiers())){
                list.add(m);
            }
        }
        return list.toArray(new Method[0]);
    }

    /**
     * 反射调用对象的指定方法,对于static方法,没有对象,为null
     * @param obj 被代理对象的实例, 就是一个类实例 obj.getClass()
     * @param method
     * @param args
     * @return 返回方法名字
     */
    public static Object invoke(Object obj, Method method, Object... args){
        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
