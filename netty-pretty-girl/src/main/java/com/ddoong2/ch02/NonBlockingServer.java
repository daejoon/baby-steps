package com.ddoong2.ch02;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
public class NonBlockingServer {

    private Map<SocketChannel, List<byte[]>> keepDataTrack = new HashMap<>();
    private ByteBuffer buffer = ByteBuffer.allocate(2 * 1024);

    private void startEchoServer() {

        try (
                Selector selector = Selector.open();
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()
        ) {

            if (serverSocketChannel.isOpen() && selector.isOpen()) {
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.bind(new InetSocketAddress(8888));

                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                log.info("접속 대기중");

                while (true) {
                    selector.select();
                    final Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

                    while (keys.hasNext()) {
                        final SelectionKey key = keys.next();
                        keys.remove();

                        if (!key.isValid()) {
                            continue;
                        }

                        if (key.isAcceptable()) {
                            this.acceptOPP(key, selector);
                        } else if (key.isReadable()) {
                            this.readOP(key);
                        } else if (key.isWritable()) {
                            this.writeOP(key);
                        }

                    }
                }

            } else {
                log.info("서버 소캣을 생성하지 못했습니다.");
            }

        } catch (IOException e) {
            log.error("{}", e);
        }

    }

    private void acceptOPP(final SelectionKey key, final Selector selector) throws IOException {
        final ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        final SocketChannel socketChannel = serverChannel.accept();
        socketChannel.configureBlocking(false);

        log.info("클라이언트 연결됨");
    }

    private void readOP(final SelectionKey key) {
        try {
            final SocketChannel socketChannel = (SocketChannel) key.channel();
            buffer.clear();
            int numRead = -1;

            try {
                socketChannel.read(buffer);
            } catch (IOException e) {
                log.error("데이터 읽기 에러!");
            }

            if (numRead == -1) {
                this.keepDataTrack.remove(socketChannel);
                log.info("클라이언트 연결 종료 : {}", socketChannel.getRemoteAddress());
                socketChannel.close();
                key.cancel();
                return;
            }

            byte[] data = new byte[numRead];
            System.arraycopy(buffer.array(), 0, data, 0, numRead);
            log.info("{} from {}", new String(data, "UTF-8"), socketChannel.getRemoteAddress());
            doEchoJob(key, data);
        } catch (Exception e) {
            log.error("{}", e);
        }

    }

    private void writeOP(final SelectionKey key) throws IOException {
        final SocketChannel socketChannel = (SocketChannel) key.channel();

        List<byte[]> channelData = keepDataTrack.get(socketChannel);
        final Iterator<byte[]> its = channelData.iterator();

        while (its.hasNext()) {
            byte[] it = its.next();
            its.remove();
            socketChannel.write(ByteBuffer.wrap(it));
        }

        key.interestOpsOr(SelectionKey.OP_READ);
    }

    private void doEchoJob(final SelectionKey key, final byte[] data) {
        final SocketChannel socketChannel = (SocketChannel) key.channel();
        final List<byte[]> channelData = keepDataTrack.get(socketChannel);
        channelData.add(data);

        key.interestOps(SelectionKey.OP_WRITE);
    }

    public static void main(String[] args) {
        NonBlockingServer server = new NonBlockingServer();
        server.startEchoServer();
    }
}
