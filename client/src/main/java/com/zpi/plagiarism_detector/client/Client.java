package com.zpi.plagiarism_detector.client;

import com.zpi.plagiarism_detector.commons.protocol.Message;
import com.zpi.plagiarism_detector.commons.protocol.ProtocolCode;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Client extends Observable {
    private Socket socket = null;
    private int port;

    private ClientReader reader = null;
    private ClientWriter writer = null;

    public Client(int port) {
        this.port = port;
    }

    /**
     * Otwiera połączenie pomiędzy klientem a serwerem.
     *
     * @throws UnknownHostException
     * @throws IOException
     */
    public void openConnection() throws UnknownHostException, IOException {
        socket = new Socket("localhost", port);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

        createIOThreads(objectOutputStream, objectInputStream);

        reader.start();
        writer.start();
    }

    private void createIOThreads(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
        reader = new ClientReader(this, objectInputStream);
        reader.setName("ClientReader");
        writer = new ClientWriter(this, objectOutputStream);
        writer.setName("ClientWriter");
    }

    /**
     * Zamyka połączenie z serwerem.
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void closeConnection() throws IOException, InterruptedException {
        writer.close();
        writer.join();
        reader.close();
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

    /**
     * Widok/Kontroler zarejestruje się jako obserwator i ta metoda będzie informowała obserwatorów o zmianie w modelu
     * (w tym przypadku gdy jakieś dane przyjdą od serwera).
     *
     * @see java.util.Observable#notifyObservers(java.lang.Object)
     */
    public void notifyObservers(Object arg) {
        setChanged();
        super.notifyObservers(arg);
    }
}