package com.zpi.plagiarism_detector.commons.protocol;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Message implements Serializable {
    private ProtocolCode code;
    private Object sendObject;

    public Message(ProtocolCode code) {
        this.code = code;
    }

    public Message(ProtocolCode code, Object obj) {
        this.code = code;
        sendObject = obj;
    }

    public ProtocolCode getCode() {
        return code;
    }

    public Object getSentObject() {
        return sendObject;
    }
}