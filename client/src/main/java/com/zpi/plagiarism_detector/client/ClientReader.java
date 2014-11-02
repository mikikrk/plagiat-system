package com.zpi.plagiarism_detector.client;

import com.zpi.plagiarism_detector.commons.protocol.Message;
import com.zpi.plagiarism_detector.commons.protocol.ProtocolCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientReader extends Thread {
    private ObjectInputStream in;
    private Client client;
    private static final Logger log = LoggerFactory.getLogger(ClientReader.class);

    public ClientReader(Client client, ObjectInputStream in) {
        this.in = in;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            receiveMessages();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            client.notifyObservers(new Message(ProtocolCode.TEST));
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                // general failure :(
            }
        }
    }

    private void receiveMessages() throws IOException, ClassNotFoundException {
        Object obj;
        ProtocolCode cmd;
        while ((obj = in.readObject()) != null) {
            Message response = (Message) obj;
            cmd = response.getCode();
            if (cmd == ProtocolCode.TEST) {
                client.notifyObservers(response);
            }
        }
    }

    public void close() {
        log.debug("entering close()");
        try {
            in.close();
        } catch (IOException e) {
            log.debug("closing with an exception clientReader thread: {}", e);
        } finally {
            this.interrupt();
        }
        log.debug("leaving close()");
    }
}