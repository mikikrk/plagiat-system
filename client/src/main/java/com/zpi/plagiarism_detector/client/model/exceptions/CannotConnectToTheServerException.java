package com.zpi.plagiarism_detector.client.model.exceptions;

import java.io.IOException;

public class CannotConnectToTheServerException extends Exception {
    public CannotConnectToTheServerException(Throwable e) {
        super(e);
    }
}
