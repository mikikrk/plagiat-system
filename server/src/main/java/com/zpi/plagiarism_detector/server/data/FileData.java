package com.zpi.plagiarism_detector.server.data;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

public class FileData {
    public void createDir(String path) throws IOException {
        File directory = new File(path);
        if(directory.exists()) {
            throw new FileAlreadyExistsException(path);
        }
        FileUtils.forceMkdir(directory);
    }

    public void writeToFile(String filePath, String text) throws IOException {
        File file = new File(filePath);
        FileUtils.writeStringToFile(file, text);
    }
}
