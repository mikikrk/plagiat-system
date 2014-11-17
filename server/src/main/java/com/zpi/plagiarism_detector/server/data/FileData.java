package com.zpi.plagiarism_detector.server.data;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileData {
    public void createDir(String path) throws IOException {
        FileUtils.forceMkdir(new File(path));
    }

    public void writeToFile(String filePath, String text) throws IOException {
        File file = new File(filePath);
        FileUtils.writeStringToFile(file, text);
    }
}
