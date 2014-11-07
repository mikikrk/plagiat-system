package com.zpi.plagiarism_detector.server.core;

import com.zpi.plagiarism_detector.server.factories.handlers.MessageHandlerFactory;
import com.zpi.plagiarism_detector.server.handlers.ConnectionHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class Server {
    private ServerSocket serverSocket = null;
    private ExecutorService pool = null;

    public Server(ServerSocket serverSocket, ExecutorService pool) {
        this.serverSocket = serverSocket;
        this.pool = pool;
    }

    /**
     * Oczekuje na połączenia i przekazuje je do wątków obsługujących klientów.
     */
    public void handleConnections() {
        try {
            while(!Thread.interrupted()) {
                Socket clientSocket = serverSocket.accept();
                pool.execute(new ConnectionHandler(clientSocket, new MessageHandlerFactory()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}