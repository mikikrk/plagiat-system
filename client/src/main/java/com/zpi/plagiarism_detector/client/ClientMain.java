package com.zpi.plagiarism_detector.client;

import com.zpi.plagiarism_detector.commons.protocol.ApplicationProperties;
import com.zpi.plagiarism_detector.commons.protocol.Message;
import com.zpi.plagiarism_detector.commons.protocol.ProtocolCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ClientMain {
    public static void main(String[] args) {
        try {
            Client client = new Client(ApplicationProperties.PORT);
            client.openConnection();
            Message message = new Message(ProtocolCode.TEST, "TEST");
            client.sendMessage(message);
            client.closeConnection();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
