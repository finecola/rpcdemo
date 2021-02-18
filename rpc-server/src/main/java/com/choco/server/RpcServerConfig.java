package com.choco.server;

import com.choco.codec.Decoder;
import com.choco.codec.Encoder;
import com.choco.codec.JsonDecoder;
import com.choco.codec.JsonEncoder;
import com.choco.transport.HttpTransportServer;
import com.choco.transport.TransportServer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by choco on 2021/2/16 15:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("all")
public class RpcServerConfig {
    //服务端的网络传输模块
    private Class<? extends TransportServer> transportClass = HttpTransportServer.class;
    //序列化
    private Class<? extends Encoder> encoderClass = JsonEncoder.class;
    private Class<? extends Decoder> decoderClass = JsonDecoder.class;
    private int port = 3000;
}
