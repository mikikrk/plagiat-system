package com.zpi.plagiarism_detector.client.core;

import com.zpi.plagiarism_detector.client.io.ClientReader;
import com.zpi.plagiarism_detector.client.io.ClientWriter;
import com.zpi.plagiarism_detector.commons.protocol.Message;
import com.zpi.plagiarism_detector.commons.util.Observable;

import java.io.IOException;
import java.net.Socket;
import java.util.Observer;

public class Client extends Observable implements Observer {
    private Socket socket;
    private ClientReader reader;
    private ClientWriter writer;

    public Client(Socket socket, ClientReader reader, ClientWriter writer) {
        this.socket = socket;
        this.reader = reader;
        this.writer = writer;
    }

    /**
     * Otwiera połączenie pomiędzy klientem a serwerem.
     */
    public void openConnection() {
        reader.addObserver(this);
        reader.start();
        writer.start();
    }

    /**
     * Zamyka połączenie z serwerem.
     *
     * @throws InterruptedException
     */
    public void closeConnection() throws InterruptedException, IOException {
        writer.close();
        writer.joinWriter();
        reader.close();
        socket.close();
    }

    /**
     * Wysyła wiadomość do serwera
     *
     * @param msg message which is sent to the server
     */
    public synchronized void sendMessage(Message msg) {
        try {
            writer.writeMessage(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(java.util.Observable o, Object arg) {
        notifyObservers(arg);
    }
}