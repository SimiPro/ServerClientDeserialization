package com.simipro.client;

import com.simipro.serialization.Message;

import java.io.*;
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
    private final int id;

    public Client(int port, int id) throws IOException {
        this.id = id;
        socketli = new Socket(InetAddress.getLoopbackAddress(), port);
    }


    @Override
    public void run() {
        try {
            Message msg = new Message("hello from client: " + id);
            ObjectOutputStream out = new ObjectOutputStream(socketli.getOutputStream());
            out.writeObject(msg);
            out.flush();


           /* // Just testing serialization
            ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
            ObjectOutputStream out2 = new ObjectOutputStream(byteArrayOutput);
            out2.writeObject(msg);
            byte b[] = byteArrayOutput.toByteArray();
            ByteArrayInputStream inp = new ByteArrayInputStream(b);
            ObjectInputStream ibjINp = new ObjectInputStream(inp);
            try {
                Message dezmsg = (Message) ibjINp.readObject();
                System.out.println("Ive worked: " + dezmsg.getHello());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //
            */


            socketli.close();
        } catch (IOException e) {
            System.out.println("Something strange must have occured by communicating with serverli btw i hate semicolons :(");
            e.printStackTrace();
        }


    }

}
