package com.zpi.plagiarism_detector.client.model.core;

import com.zpi.plagiarism_detector.client.model.io.ClientReader;
import com.zpi.plagiarism_detector.client.model.io.ClientWriter;
import com.zpi.plagiarism_detector.commons.protocol.Message;
import com.zpi.plagiarism_detector.commons.util.Observable;

import java.io.IOException;
import java.net.Socket;
import java.util.Observer;

public class Client extends Observable implements Observer {
    private Socket socket;
    private ClientReader reader;
    private ClientWriter writer;
    private boolean waitForResponse;

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
    public synchronized void closeConnection() throws InterruptedException, IOException {
        waitForResponse();
        writer.close();
        writer.joinWriter();
        reader.close();
        socket.close();
    }

    private void waitForResponse() throws InterruptedException {
        int i = 0;
        while (waitForResponse && i < 10) {
            Thread.sleep(1000);
            ++i;
        }
    }


    /**
     * Wysyła wiadomość do serwera
     *
     * @param msg message which is sent to the server
     */
    public synchronized void sendSyncMessage(Message msg) {
        try {
            waitForResponse = true;
            writer.writeMessage(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(java.util.Observable o, Object arg) {
        waitForResponse=false;
        notifyObservers(arg);
    }
}