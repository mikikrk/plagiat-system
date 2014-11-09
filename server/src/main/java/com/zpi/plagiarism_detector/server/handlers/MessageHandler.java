package com.zpi.plagiarism_detector.server.handlers;

import com.zpi.plagiarism_detector.commons.protocol.Message;
import com.zpi.plagiarism_detector.commons.protocol.ProtocolCode;

import java.io.*;

public class MessageHandler {
    private ObjectInput in;
    private ObjectOutput out;
    private Message message;

    /**
     * Tworzy MessageHandler dla strumieni wyjścia/wejścia
     * @param out
     * @param in
     * @return utworzony obiekt
     * @throws IOException
     */
    public static MessageHandler create(ObjectOutput out, ObjectInput in) {
        return new MessageHandler(out, in);
    }

    private MessageHandler(ObjectOutput out, ObjectInput in)  {
        this.out = out;
        this.in = in;
    }

    /**
     * Obsługuje przychodzące wiadomości
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void handleMessages() throws IOException, ClassNotFoundException {
        while(tryReadMessage()) {
            dispatchMessage(message);
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

    private void dispatchMessage(Message message) throws IOException {
        ProtocolCode cmd = message.getCode();
        if(cmd == ProtocolCode.TEST) {
            System.out.println(message.getSentObject());
            Message response = new Message(ProtocolCode.TEST);
            sendMessage(response);
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
