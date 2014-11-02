package com.zpi.plagiarism_detector.client;

import com.zpi.plagiarism_detector.commons.protocol.Message;
import com.zpi.plagiarism_detector.commons.protocol.ProtocolCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Robert on 2014-10-29.
 */
public class ClientWriter extends Thread {
    private ObjectOutputStream out;
    private BlockingQueue<Message> sendQueue = new LinkedBlockingQueue<>();
    private boolean stop = false;
    private Client client;
    private static final Logger log = LoggerFactory.getLogger(ClientWriter.class);

    public ClientWriter(Client client, ObjectOutputStream out) {
        this.out = out;
        this.client = client;
    }

    @Override
    public void run() {
        log.debug("entering run()");
        try {
            sendQueuedMessages();
        } catch (InterruptedException e) {
            System.out.println("Writer Interrutped");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            try {
//                client.joinReader();
//                out.close();
//            } catch (IOException | InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        log.debug("leaving run()");
    }

    private void sendQueuedMessages() throws InterruptedException, IOException {
        log.debug("entering sendQueuedMessages()");
        Message message;
        while (!stop || !sendQueue.isEmpty()) {
            message = sendQueue.take();
            log.debug("processing next message from sendQueue: [{}]", message);
            out.writeObject(message);
            if (message.getCode() == ProtocolCode.TEST) {
                break;
            }
        }
        log.debug("leaving sendQueuedMessages()");
    }

    public synchronized void writeMessage(Message message) throws InterruptedException {
        sendQueue.put(message);
    }

    public synchronized void close() {
        stop = true;
    }
}
