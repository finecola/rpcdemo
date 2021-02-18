package com.choco.transport;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class HttpTransportServer implements TransportServer {
    private RequestHandler handler;
    private Server server;

    @Override
    public void init(Integer port, RequestHandler handler) {
        this.handler = handler;
        //对port端口的监听 3000
        this.server = new Server(port);

        /**
         * ServletContextHandler上下文handler, 继承contextHandler
         * 包含servlet, servlet处理请求即重写doPOst方法
         */
        ServletContextHandler ctx = new ServletContextHandler();
        ServletHolder holder = new ServletHolder(new RequestServlet());
        // *处理所有请求
        ctx.addServlet(holder, "/*");

        server.setHandler(ctx);
    }

    @Override
    public void start() {
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 处理输入流,并返回输出流
     */
    class RequestServlet extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            log.info("client connect");
            InputStream in = req.getInputStream();
            OutputStream out = resp.getOutputStream();

            if (handler != null) {
                handler.onRequest(in, out);
            }
            //把输出流通过网络传输至client, 在write方法之后调用强制把缓冲区的数据写入到目的地
            out.flush();
        }
    }
}
