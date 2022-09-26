package com.ddoong2.ch02;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class BlockingServer {

    public static void main(String[] args) throws IOException {
        BlockingServer server = new BlockingServer();
        server.run();
    }

    private void run() throws IOException {
        ServerSocket server = new ServerSocket(8888);
        log.info("접속 대기중");

        while (true) {
            Socket sock = server.accept();
            log.info("클라이언트 연결됨");

            final OutputStream out = sock.getOutputStream();
            final InputStream in = sock.getInputStream();

            while (true) {
                try {
                    final int request = in.read();
                    out.write(request);
                } catch (IOException e) {
                    break;
                }
            }
        }

    }
}
