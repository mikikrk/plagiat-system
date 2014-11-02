package com.zpi.plagiarism_detector.server.core;

import com.zpi.plagiarism_detector.server.handlers.ConnectionHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private ServerSocket serverSocket = null;
    private ExecutorService pool = null;

    /**
     * Tworzy serwer z daną liczbą wątków w puli do obsługi klientów
     * @param poolCount liczba wątków tworzonych dla FixedThreadPool
     * @param portNumber numer portu na którym ma działać serwer
     */
    public Server(int poolCount, int portNumber) {
        try {
            serverSocket = new ServerSocket(portNumber);
            pool = Executors.newFixedThreadPool(poolCount);
        } catch(IOException e1) {
            System.out.println("Could not listen on port: 6666");
            System.exit(-1);
        }
    }

    /**
     * Oczekuje na połączenia i przekazuje je do wątków obsługujących klientów.
     */
    public void handleConnections() {
        try {
            while(!Thread.interrupted()) {
                Socket clientSocket = serverSocket.accept();
                pool.execute(new ConnectionHandler(clientSocket));
            }
        } catch(IOException e) {
            pool.shutdownNow();
            e.printStackTrace();
        }
    }
}