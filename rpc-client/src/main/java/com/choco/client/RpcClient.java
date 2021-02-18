package com.choco.client;

import com.choco.codec.Decoder;
import com.choco.codec.Encoder;
import com.choco.common.ReflectionUtils;

import java.lang.reflect.Proxy;

/**
 * Created by choco on 2021/2/16 20:59
 */
public class RpcClient {
    private final RpcClientConfig config;
    private final Encoder encoder;
    private final Decoder decoder;
    private final TransportSelector selector;

    public RpcClient(){
        this(new RpcClientConfig());
    }

    public RpcClient(RpcClientConfig config){
        this.config = config;
        this.decoder = ReflectionUtils.newInstance(this.config.getDecoderClass());
        this.encoder = ReflectionUtils.newInstance(this.config.getEncoderClass());
        this.selector = ReflectionUtils.newInstance(this.config.getSelectorClass());

        this.selector.init(
                this.config.getServers(),
                this.config.getConnectCount(),
                this.config.getTransportClass()
        );
    }

    /**
     * JDK动态代理, 生成代理类
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getProxyInstance(Class<T> clazz){
        return (T) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{clazz},
                new RemoteInvoke(clazz, encoder, decoder, selector)
        );
    }
}
