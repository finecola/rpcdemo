package com.choco.server;

import org.junit.Test;

/**
 * Created by choco on 2021/2/16 18:43
 */
public class TestClass implements TestInterface{
    @Override
    public void hello() {
    }

    @Test
    public void s(){
        System.out.println(TestInterface.class.getName());
    }
}
