package com.zpi.plagiarism_detector.server.factories.handlers;

import com.zpi.plagiarism_detector.server.factories.AbstractFactory;
import com.zpi.plagiarism_detector.server.handlers.MessageHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public abstract class AbstractMessageHandlerFactory extends AbstractFactory<MessageHandler> {
    public abstract MessageHandler createForSocket(Socket socket) throws IOException;

    public abstract MessageHandler createForStreams(ObjectOutputStream outputStream, ObjectInputStream inputStream) throws IOException;
}
