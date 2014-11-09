package com.zpi.plagiarism_detector.client.io;

import com.zpi.plagiarism_detector.commons.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectOutput;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientWriter extends Thread {
    private ObjectOutput out;
    BlockingQueue<Message> sendQueue = new LinkedBlockingQueue<>();
    private boolean stop = false;
    private static final Logger log = LoggerFactory.getLogger(ClientWriter.class);

    public ClientWriter(ObjectOutput out) {
        this.out = out;
    }

    @Override
    public void run() {
        log.debug("entering run()");
        try {
            sendQueuedMessages();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            releaseResources();
            log.debug("leaving run()");
        }
    }

    private void sendQueuedMessages() throws InterruptedException, IOException {
        log.debug("entering sendQueuedMessages()");
        while (isInRunningState()) {
            sendMessage();
        }
        log.debug("leaving sendQueuedMessages()");
    }

    private synchronized boolean isInRunningState() {
        return !stop;
    }

    private synchronized void sendMessage() throws InterruptedException, IOException {
        Message message = sendQueue.take();
        if (message == Message.POISON_PILL) {
            stopWriter();
        } else {
            log.debug("processing next message from sendQueue: [{}]", message);
            out.writeObject(message);
        }
    }

    private synchronized void stopWriter() {
        stop = true;
    }

    private synchronized void releaseResources() {
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Dodaje wysyłaną wiadomość do kolejki
     * @param message
     * @throws InterruptedException
     */
    public void writeMessage(Message message) throws InterruptedException {
        sendQueue.put(message);
    }

    /**
     * Powoduje zamknięcie obiektu wysyłającego wiadomości
     * @throws InterruptedException
     */
    public void close() throws InterruptedException {
        writeMessage(Message.POISON_PILL);
    }

    /**
     * Dodana na użytek testów, ponieważ nie można zamokować Thread.join();
     */
    public void joinWriter() throws InterruptedException {
        this.join();
    }
}
