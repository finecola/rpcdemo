package com.choco.client;

import com.choco.codec.Decoder;
import com.choco.codec.Encoder;
import com.choco.codec.JsonDecoder;
import com.choco.codec.JsonEncoder;
import com.choco.proto.Peer;
import com.choco.transport.HttpTransportClient;
import com.choco.transport.TransportClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RpcClientConfig {
    /**
     * 左边都是extends接口, 右边都是其实现类
     */
    private Class<? extends TransportClient> transportClass = HttpTransportClient.class;
    private Class<? extends Encoder> encoderClass = JsonEncoder.class;
    private Class<? extends Decoder> decoderClass = JsonDecoder.class;
    private Class<? extends TransportSelector> selectorClass = RandomTransportClientSelector.class;

    private Integer connectCount = 1;
    private List<Peer> servers = Arrays.asList(new Peer("127.0.0.1", 3000));
}
