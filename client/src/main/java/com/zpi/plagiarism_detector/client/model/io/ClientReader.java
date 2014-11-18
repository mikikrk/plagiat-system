package com.zpi.plagiarism_detector.client.model.io;

import com.zpi.plagiarism_detector.commons.protocol.Message;
import com.zpi.plagiarism_detector.commons.protocol.ProtocolCode;
import com.zpi.plagiarism_detector.commons.util.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInput;

public class ClientReader extends Observable {
    Thread thread;
    private ObjectInput in;
    private static final Logger log = LoggerFactory.getLogger(ClientReader.class);
    private Message message;

    public ClientReader(ObjectInput in) {
        this.in = in;
    }

    /**
     * Uruchamia wątek odbierania wiadomości, Dodane dla zgodności z metodami klasy Thread
     */
    public void start() {
        run();
    }

    /**
     * @see ClientReader#start()
     */
    public void run() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                runAlg();
            }
        });
        thread.setName("ClientReader");
        thread.start();
    }

    private void runAlg() {
        log.debug("entering run()");
        try {
            receiveMessages();
        } catch (IOException e) {
            log.debug("ClientReader stopped receiving messages");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            close();
            log.debug("leaving run()");
        }
    }

    /**
     * Odbiera wiadomości przychodzące z serwera
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void receiveMessages() throws IOException, ClassNotFoundException {
        while (tryReadMessage()) {
            processMessage();
        }
    }

    /**
     * próbuje odczytać wiadomość z strumienia
     * @return wartość logiczna określająca czy udało się odebrać wiadomość
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public boolean tryReadMessage() throws ClassNotFoundException, IOException {
        message = (Message) in.readObject();
        return message != null;
    }

    private void processMessage() {
        notifyObservers(message);
        ProtocolCode cmd = message.getCode();
        if (cmd == ProtocolCode.TEST) {
            log.debug("received test message");
        }
    }


    /**
     * Zamkyka strumień danych
     */
    public void close() {
        log.debug("entering close()");
        try {
            in.close();
        } catch (IOException e) {
            log.debug("closing with an exception clientReader thread: {}", e);
        } finally {
            log.debug("leaving close()");
            thread.interrupt();
        }
    }
}