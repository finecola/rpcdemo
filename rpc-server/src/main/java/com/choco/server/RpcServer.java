package com.choco.server;

import com.choco.codec.Decoder;
import com.choco.codec.Encoder;
import com.choco.common.ReflectionUtils;
import com.choco.proto.Request;
import com.choco.proto.Response;
import com.choco.transport.RequestHandler;
import com.choco.transport.TransportServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by choco on 2021/2/16 19:50
 */
@Slf4j
public class RpcServer {
    private final RpcServerConfig config;
    private final TransportServer net;
    private final Encoder encoder;
    private final Decoder decoder;
    private final ServiceManager serviceManager;
    private final ServiceInvoke serviceInvoke;

    public RpcServer(){
        this(new RpcServerConfig());
    }

    public RpcServer(RpcServerConfig config) {
        this.config = config;
        this.net = ReflectionUtils.newInstance(config.getTransportClass());
        this.net.init(config.getPort(), this.handler);

        this.encoder = ReflectionUtils.newInstance(config.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(config.getDecoderClass());

        this.serviceInvoke = new ServiceInvoke();
        this.serviceManager = new ServiceManager();

    }

    public <T> void register(Class<T> interfaceClass, T bean) {
        serviceManager.register(interfaceClass, bean);
    }

    public <T> void start() {
        this.net.start();
    }

    public <T> void stop() {
        this.net.stop();
    }

    private final RequestHandler handler = new RequestHandler() {
        final Response response = new Response();

        @Override
        public void onRequest(InputStream receive, OutputStream reply) {
            try {
                byte[] inBytes = IOUtils.readFully(receive, receive.available());
                //把输入流 反序列化成 请求
                Request request = decoder.decode(inBytes, Request.class);
                log.info("get request: {}", request);

                ServiceInstance instance = serviceManager.lookup(request);
                Object res = serviceInvoke.invoke(instance, request);
                response.setData(res);
                response.setCode(0);
                response.setMessage("我的测试, ok");
            } catch (Exception e) {
                log.warn(e.getMessage(), e);
                //没有采用枚举的方法, 我们人为规定非0都表示失败
                response.setCode(2);
                response.setMessage("RpcServer get error:: " + e.getClass().getName() + ":" + e.getMessage());
            } finally {
                try {
                    //onRequest最终把response序列化后写回客户端
                    byte[] out = encoder.encode(response);
                    reply.write(out);
                    log.info("response client");
                } catch (Exception e) {
                    log.warn(e.getMessage(),e);
                }
            }
        }
    };

}
