package com.zpi.plagiarism_detector.server.factories.handlers;

import com.zpi.plagiarism_detector.server.detector.PlagiarismDetector;
import com.zpi.plagiarism_detector.server.handlers.MessageDispatcher;
import com.zpi.plagiarism_detector.server.handlers.MessageHandler;

import java.io.*;
import java.net.Socket;

public class MessageHandlerFactory extends AbstractMessageHandlerFactory {
    public MessageHandler createForSocket(Socket socket) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

        PlagiarismDetector plagiarismDetector = new PlagiarismDetector();
        MessageDispatcher messageDispatcher = new MessageDispatcher(plagiarismDetector);

        return MessageHandler.create(outputStream, inputStream, messageDispatcher);
    }

    public MessageHandler createForStreams(ObjectOutput outputStream, ObjectInput inputStream) throws IOException {
        PlagiarismDetector plagiarismDetector = new PlagiarismDetector();
        MessageDispatcher messageDispatcher = new MessageDispatcher(plagiarismDetector);

        return MessageHandler.create(outputStream, inputStream, messageDispatcher);
    }
}
