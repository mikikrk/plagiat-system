package com.zpi.plagiarism_detector.server.handlers;

import com.zpi.plagiarism_detector.commons.protocol.DocumentData;
import com.zpi.plagiarism_detector.commons.protocol.Message;
import com.zpi.plagiarism_detector.commons.protocol.PlagiarismDetectionResult;
import com.zpi.plagiarism_detector.commons.protocol.ProtocolCode;
import com.zpi.plagiarism_detector.server.detector.PlagiarismDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.zpi.plagiarism_detector.commons.protocol.ProtocolCode.TEST;

public class MessageDispatcher {
    private static final Logger log = LoggerFactory.getLogger(MessageDispatcher.class);
    private Message response = null;
    private PlagiarismDetector plagiarismDetector;

    public MessageDispatcher(PlagiarismDetector plagiarismDetector) {
        this.plagiarismDetector = plagiarismDetector;
    }

    public Message dispatchMessage(Message message) {
        ProtocolCode command = message.getCode();
        Object sentObject = message.getSentObject();
        log.debug(command.toString());

        if(command == TEST) {
            handleTestMessage();
        } else if (command == ProtocolCode.CHECK_FOR_PLAGIARISM) {
            handlePlagiarismChecking(sentObject);
        }
        return response;
    }

    private void handleTestMessage() {
        response = new Message(TEST);
    }

    private void handlePlagiarismChecking(Object sentObject) {
        if (sentObject == null) {
            response = new Message(ProtocolCode.NULL_DOCUMENT_ERROR);
        } else {
            DocumentData document = (DocumentData) sentObject;
            PlagiarismDetectionResult result = plagiarismDetector.checkForPlagiarism(document);
            response = new Message(ProtocolCode.PLAGIARISM_CHECK_RESULT, result);
        }
    }
}
