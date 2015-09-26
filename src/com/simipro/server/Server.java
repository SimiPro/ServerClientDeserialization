package com.simipro.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Simi on 25.09.2015.
 */
public class Server extends  AbstractServer {

    private class MyClientSession extends AbstractServer.ClientSession {
        private final AsynchronousSocketChannel channel;

        public MyClientSession(AsynchronousSocketChannel channel) {
            this.channel = channel;
        }

        public AsynchronousSocketChannel getChannel() {
            return channel;
        }
    }


    public Server(int serverPort) throws IOException {
        super(serverPort);
    }


    @Override
    public void doAccept() {
        serverSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel channel, Object attachment) {
                MyClientSession session = new MyClientSession(channel);
                final ByteBuffer bufferli = ByteBuffer.allocate(256);
                channel.read(bufferli, session, new CompletionHandler<Integer, MyClientSession>() {
                    @Override
                    public void completed(Integer len, MyClientSession sessionli) {
                        if (sessionli.handleInput(bufferli,len)) {
                            sessionli.getChannel().read(bufferli, sessionli, this);
                        } else {
                                // here we can handle the final message! °,..,°
                                System.out.println("Client: " + sessionli.getId() + " Says: " +  sessionli.getMsg().getHello() + " !");
                                try {
                                    sessionli.getChannel().close();
                                } catch (IOException e) {
                                    e.printStackTrace(); // ignore not really importent if its crashing while ending
                                }
                        }
                    }

                    @Override
                    public void failed(Throwable exc, MyClientSession sessionli) {
                        System.out.println("Client connection Problem : " + exc);
                        exc.printStackTrace();
                        sessionli.handleFailure();
                        try {
                            sessionli.getChannel().close();
                        } catch (IOException e) {
                            e.printStackTrace(); // same here
                        }
                    }
                });
                serverSocketChannel.accept(null, this);
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("Server socket Problem : " + exc);
                exc.printStackTrace();
            }
        });
    }
}
