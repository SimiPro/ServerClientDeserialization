package com.simipro.client;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Simi on 25.09.2015.
 */
public class Client implements Runnable {
    private final Socket socketli;

    public Client(int port) throws IOException {
        socketli = new Socket(InetAddress.getLoopbackAddress(), port);
    }


    @Override
    public void run() {
        try {
            DataOutputStream out = new DataOutputStream(socketli.getOutputStream());
            out.writeChars("hello world");
            out.flush();


            socketli.close();
        } catch (IOException e) {
            System.out.println("Something strange must have occured by communicating with serverli btw i hate semicolons :(");
            e.printStackTrace();
        }


    }

}
