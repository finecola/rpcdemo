package com.choco.codec;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by choco on 2021/2/16 14:28
 */
public class TestEncoder {
    @Test
    public void encoder(){
        TestBean bean = new TestBean("choco", 12, new String[]{"12", "21"});
        JsonEncoder jsonEncoder = new JsonEncoder();
        byte[] bytes = jsonEncoder.encode(bean);

        assertNotNull(bytes);
    }

    @Test
    public void decoder(){
        TestBean bean = new TestBean("choco", 12, new String[]{"12", "21"});
        JsonEncoder jsonEncoder = new JsonEncoder();
        byte[] bytes = jsonEncoder.encode(bean);

        JsonDecoder jsonDecoder = new JsonDecoder();
        TestBean bean1 = jsonDecoder.decode(bytes, TestBean.class);
        assertEquals(bean.getName(), bean1.getName());
    }

}
