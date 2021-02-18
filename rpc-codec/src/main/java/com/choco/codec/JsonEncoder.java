package com.choco.codec;

import com.alibaba.fastjson.JSON;

/**
 * 基于json的序列化
 */
public class JsonEncoder implements Encoder{
    @Override
    public byte[] encode(Object obj) {
        return JSON.toJSONBytes(obj);
    }
}
