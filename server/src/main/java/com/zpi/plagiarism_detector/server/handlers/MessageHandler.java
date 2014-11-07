package com.zpi.plagiarism_detector.server.handlers;

import com.zpi.plagiarism_detector.commons.protocol.Message;
import com.zpi.plagiarism_detector.commons.protocol.ProtocolCode;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MessageHandler {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Message message;

    /**
     * Tworzy MessageHandler dla strumieni wyjścia/wejścia danego socket'u
     * @param out
     * @param in
     * @return utworzony obiekt
     * @throws IOException
     */
    public static MessageHandler create(ObjectOutputStream out, ObjectInputStream in) {
        return new MessageHandler(out, in);
    }

    private MessageHandler(ObjectOutputStream out, ObjectInputStream in)  {
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

    private void dispatchMessage(Message message) {
        ProtocolCode cmd = message.getCode();
        if(cmd == ProtocolCode.TEST) {
            System.out.println(message.getSentObject());
        }
    }

    /**
     * Wysyła odpowiedź do klienta
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
