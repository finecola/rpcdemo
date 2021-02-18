package com.choco.transport;

import com.choco.proto.Peer;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by choco on 2021/2/16 14:54
 */
public class HttpTransportClient implements TransportClient {
    private String url;

    @Override
    public void connect(Peer peer) {
        this.url = "http://" + peer.getHost() + ":" + peer.getPort();
    }

    @Override
    public InputStream write(InputStream data) {
        try {
            HttpURLConnection httpConn = (HttpURLConnection) new URL(url).openConnection();
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            httpConn.setUseCaches(false);
            httpConn.setRequestMethod("POST");

            httpConn.connect();
            //获取 传输内容 的输入流,转换为输出流进行网络传输
            //想一想图片上传的案例
            IOUtils.copy(data, httpConn.getOutputStream());

            //200
            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //获取的是response的输入流
                return httpConn.getInputStream();
            }
            return httpConn.getErrorStream();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() {

    }
}
