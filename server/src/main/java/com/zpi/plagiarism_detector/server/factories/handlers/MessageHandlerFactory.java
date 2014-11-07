package com.zpi.plagiarism_detector.server.factories.handlers;

import com.zpi.plagiarism_detector.server.handlers.MessageHandler;

import java.io.*;
import java.net.Socket;

public class MessageHandlerFactory extends AbstractMessageHandlerFactory {
    public MessageHandler createForSocket(Socket socket) throws IOException {
        ObjectOutputStream outputStream = (ObjectOutputStream) socket.getOutputStream();
        ObjectInputStream inputStream = (ObjectInputStream) socket.getInputStream();
        return MessageHandler.create(outputStream, inputStream);
    }

    public MessageHandler createForStreams(ObjectOutputStream outputStream, ObjectInputStream inputStream) throws IOException {
        return MessageHandler.create(outputStream, inputStream);
    }
}
