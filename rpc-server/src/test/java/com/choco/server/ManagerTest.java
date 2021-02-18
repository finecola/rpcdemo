package com.choco.server;

import com.choco.common.ReflectionUtils;
import com.choco.proto.Request;
import com.choco.proto.ServiceDescriptor;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertNotNull;

/**
 * Created by choco on 2021/2/16 18:44
 */
public class ManagerTest {
    ServiceManager sm;

    /**
     * 添加了Before, 这里先执行
     */
    @Before
    public void init(){
        sm = new ServiceManager();
        TestInterface bean = new TestClass();
        sm.register(TestInterface.class, bean);
    }

    @Test
    public void register(){
        TestInterface bean = new TestClass();
        sm.register(TestInterface.class, bean);
    }

    @Test
    public void lookup(){
        Method method = ReflectionUtils.getPublicMethods(TestInterface.class)[0];
        ServiceDescriptor from = ServiceDescriptor.from(TestInterface.class, method);

        Request request = new Request();
        request.setService(from);
        ServiceInstance instance = sm.lookup(request);

        assertNotNull(instance);
    }
}
