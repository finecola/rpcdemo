package com.choco.codec;

import com.alibaba.fastjson.JSON;

/**
 * 基于json的反序列化,并转化成具体的类.
 */
public class JsonDecoder implements Decoder{
    @Override
    public <T> T decode(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }
}
