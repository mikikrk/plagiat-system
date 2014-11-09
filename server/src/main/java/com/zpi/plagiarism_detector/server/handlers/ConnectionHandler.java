package com.zpi.plagiarism_detector.server.handlers;

import com.zpi.plagiarism_detector.server.factories.handlers.AbstractMessageHandlerFactory;

import java.io.IOException;
import java.net.Socket;

/**
 * Odpowiada za odbiór danych od klientów, odpowiednie przetworzenie i wysłanie odpowiedzi.
 */
public class ConnectionHandler implements Runnable {
    private Socket socket;
    private MessageHandler messageHandler;
    private AbstractMessageHandlerFactory messageHandlerFactory;

    /**
     * Tworzy obiekt uchwyt który przechowuje Socket oraz mapę id i socket handlerów.
     *
     * @param socket socket potrzebny do odbioru/wysyłania danych.
     * @param messageHandler
     */
    public ConnectionHandler(Socket socket, MessageHandler messageHandler) {
        this.socket = socket;
        this.messageHandler = messageHandler;
    }

    @Override
    public void run() {
        try {
            messageHandler.handleMessages();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            freeHandlerResources();
        }
    }

    private void freeHandlerResources() {
        try {
            synchronized (this) {
                if (messageHandler != null) {
                    messageHandler.freeResources();
                }
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}