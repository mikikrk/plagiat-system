package com.zpi.plagiarism_detector.client.main;

import com.zpi.plagiarism_detector.client.model.core.Client;
import com.zpi.plagiarism_detector.client.model.factories.AbstractClientFactory;
import com.zpi.plagiarism_detector.client.model.factories.ClientFactory;
import com.zpi.plagiarism_detector.commons.protocol.ApplicationProperties;
import com.zpi.plagiarism_detector.commons.protocol.Message;
import com.zpi.plagiarism_detector.commons.protocol.ProtocolCode;

import java.io.IOException;

public class ClientMain {
    private static boolean waitForAnswer = true;
    private static AbstractClientFactory clientFactory = new ClientFactory();

    public static void main(String[] args) {
        try {
            Client client = clientFactory.create("localhost", ApplicationProperties.PORT);
            client.openConnection();
            Message message = new Message(ProtocolCode.TEST, "TEST");
            client.sendMessage(message);

            if(!waitForAnswer) {
                client.closeConnection();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
