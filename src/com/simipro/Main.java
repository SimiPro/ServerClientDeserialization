package com.simipro;

import com.simipro.client.Client;
import com.simipro.server.AbstractServer;
import com.simipro.server.Server;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class Main {
    private static final int serverport = 8888;

    public static void main(String[] args) throws IOException {
        AbstractServer daServer = new Server(serverport);
        System.out.println("Server started.. ");
        daServer.doAccept();
        ExecutorService pool = new ThreadPoolExecutor(8,8,0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(64), new ThreadPoolExecutor.CallerRunsPolicy());
        byte[] buffer = new byte[256];
        for (int i = 0; i < 1024; i++) {
            try {
                pool.submit(new Client(serverport));
                System.out.println("Shall we continue? Waiting for input: ");
                System.out.flush();
                //try {System.in.read(buffer);} catch (IOException e) {}
            }catch(IOException e) {
                System.out.println("Exception creating clients" + e);
                e.printStackTrace();
            }
        }
    }
}
