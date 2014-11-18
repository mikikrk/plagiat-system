package com.zpi.plagiarism_detector.client.model;

import com.zpi.plagiarism_detector.client.model.core.Client;
import com.zpi.plagiarism_detector.client.model.exceptions.CannotConnectToTheServerException;
import com.zpi.plagiarism_detector.client.model.factories.AbstractClientFactory;
import com.zpi.plagiarism_detector.commons.protocol.ApplicationProperties;
import com.zpi.plagiarism_detector.commons.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ClientModel implements Model{
    private Logger log = LoggerFactory.getLogger(ClientModel.class);
    private AbstractClientFactory clientFactory;
    private Client client;

    public ClientModel(AbstractClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    public void openConnection() throws CannotConnectToTheServerException {
        try {
            client = clientFactory.create(ApplicationProperties.HOSTNAME, ApplicationProperties.PORT);
            client.openConnection();
        } catch (IOException e) {
            throw new CannotConnectToTheServerException(e);
        }
    }

    public void sendMessage(Message message) {
        client.sendSyncMessage(message);
    }

    public void closeConnection() {
        try {
            client.closeConnection();
        } catch (InterruptedException | IOException e) {
//            e.printStackTrace();
        } finally {
            log.debug("Connection closed");
        }
    }
}
