package com.zpi.plagiarism_detector.server.handlers;

import com.zpi.plagiarism_detector.commons.protocol.Message;
import com.zpi.plagiarism_detector.commons.protocol.ProtocolCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import static com.zpi.plagiarism_detector.commons.protocol.ProtocolCode.TEST;

public class MessageHandler {
    private ObjectInput in;
    private ObjectOutput out;
    private final MessageDispatcher messageDispatcher;
    private Message message;
    private static final Logger log = LoggerFactory.getLogger(MessageHandler.class);

    /**
     * Tworzy MessageHandler dla strumieni wyjścia/wejścia
     * @param out
     * @param in
     * @return utworzony obiekt
     * @throws IOException
     */
    public static MessageHandler create(ObjectOutput out, ObjectInput in, MessageDispatcher messageDispatcher) {
        return new MessageHandler(out, in, messageDispatcher);
    }

    private MessageHandler(ObjectOutput out, ObjectInput in, MessageDispatcher messageDispatcher)  {
        this.out = out;
        this.in = in;
        this.messageDispatcher = messageDispatcher;
    }

    /**
     * Obsługuje przychodzące wiadomości
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void handleMessages() throws IOException, ClassNotFoundException {
        Message response = null;
        while(tryReadMessage()) {
            response = messageDispatcher.dispatchMessage(message);
        }
        if (response != null) {
            sendMessage(response);
        }
    }

    public boolean tryReadMessage() {
        try {
            message = (Message)in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            message = null;
        } finally {
            return message != null;
        }
    }

    /**
     * Wysyła odpowiedź
     * @param message wysyłana wiadomość
     * @throws IOException
     */
    public void sendMessage(Message message) throws IOException {
        out.writeObject(message);
    }

    /**
     * Zwalnia zasoby strumienia danych
     * @throws IOException
     */
    public void freeResources() throws IOException {
        in.close();
        out.close();
    }
}
