package com.choco.client;

import com.choco.codec.Decoder;
import com.choco.codec.Encoder;
import com.choco.proto.Request;
import com.choco.proto.Response;
import com.choco.proto.ServiceDescriptor;
import com.choco.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * jdk动态代理的InvocationHandler类
 */
@Slf4j
public class RemoteInvoke implements InvocationHandler {
    private final Class clazz;
    private final Encoder encoder;
    private final Decoder decoder;
    private final TransportSelector selector;

    public RemoteInvoke(Class clazz, Encoder encoder, Decoder decoder, TransportSelector selector) {
        this.clazz = clazz;
        this.encoder = encoder;
        this.decoder = decoder;
        this.selector = selector;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Request request = new Request();
        request.setService(ServiceDescriptor.from(clazz, method));
        request.setParameters(args);

        //把请求进行网络传输,执行,获取执行结果
        Response resp = invokeRemote(request);

        if (resp == null || resp.getCode() != 0) {
            throw new IllegalStateException("fail to invoke remote::" + resp);
        }
        return resp.getData();
    }

    private Response invokeRemote(Request request) {
        TransportClient client = null;
        Response resp = null;
        try {
            client = selector.select();
            byte[] outBytes = encoder.encode(request);
            //write方法: ByteArrayInputStream:byte->is并write
            InputStream receive = client.write(new ByteArrayInputStream(outBytes));

            byte[] inbytes = IOUtils.readFully(receive, receive.available());
            //转换为Response这是自定义的, 包含code, message, data
            resp = decoder.decode(inbytes, Response.class);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            resp.setCode(1);
            resp.setMessage("rpcClient get error:" + e.getClass() + ":" + e.getMessage());
        } finally {
            if (client != null) {
                selector.release(client);
            }
        }
        return resp;
    }
}
