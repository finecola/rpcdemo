package com.choco.test;

import com.choco.common.ReflectionUtils;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by choco on 2021/2/16 14:02
 */
public class ReflectTest {
    @Test
    public void newInstance(){
        TestClass testClass = ReflectionUtils.newInstance(TestClass.class);
        assertNotNull(testClass);
    }

    @Test
    public void getPublicMethods(){
        Method[] methods = ReflectionUtils.getPublicMethods(TestClass.class);
        assertEquals(1, methods.length);

        assertEquals("a", methods[0].getName());
    }

    @Test
    public void invoke(){
        Method[] methods = ReflectionUtils.getPublicMethods(TestClass.class);
        Method method = methods[0];

        TestClass testClass = new TestClass();

        Object a = ReflectionUtils.invoke(testClass, method);

        assertEquals("a", a);
    }
}
