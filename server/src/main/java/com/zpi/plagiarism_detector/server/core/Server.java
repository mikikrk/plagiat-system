package com.zpi.plagiarism_detector.server.core;

import com.zpi.plagiarism_detector.server.factories.handlers.MessageHandlerFactory;
import com.zpi.plagiarism_detector.server.handlers.ConnectionHandler;
import com.zpi.plagiarism_detector.server.handlers.MessageHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class Server {
    private ServerSocket serverSocket;
    private ExecutorService pool;
    private MessageHandlerFactory messageHandlerFactory;

    public Server(ServerSocket serverSocket, ExecutorService pool, MessageHandlerFactory messageHandlerFactory) {
        this.serverSocket = serverSocket;
        this.pool = pool;
        this.messageHandlerFactory = messageHandlerFactory;
    }

    /**
     * Oczekuje na połączenia i przekazuje je do wątków obsługujących klientów.
     */
    public void handleConnections() {
        try {
            while(!Thread.interrupted()) {
                Socket clientSocket = serverSocket.accept();
                MessageHandler messageHandler = messageHandlerFactory.createForSocket(clientSocket);
                pool.execute(new ConnectionHandler(clientSocket, messageHandler));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}