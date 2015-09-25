package com.simipro.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Simi on 25.09.2015.
 */
public abstract class AbstractServer {
    protected final AsynchronousServerSocketChannel serverSocketChannel;

    public AbstractServer(int serverPort) throws IOException {
        serverSocketChannel = AsynchronousServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(serverPort));
    }

    public abstract void doAccept();


    protected static class ClientSession {
        private static final AtomicInteger clientCnt = new AtomicInteger(1);
        private final int Id = clientCnt.getAndIncrement();
        private volatile int byteCnt;


        public boolean handleInput(ByteBuffer buf, int len) {
            if (len >= 0) {
                byteCnt += len;
                return true;
            } else {
                System.out.println("received " + byteCnt + " bytes from client " + Id + " by " + Thread.currentThread().getName());
                return false;
            }
        }

        public void handleFailure() {
            System.out.println("*** failure *** received " + byteCnt + " bytes from client " + Id + " by " + Thread.currentThread());
        }

        @Override
        public String toString() {
            return ("ClientSession " + Id);
        }
    }

}
