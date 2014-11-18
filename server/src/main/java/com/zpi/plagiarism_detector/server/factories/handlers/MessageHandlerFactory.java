package com.zpi.plagiarism_detector.server.factories.handlers;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.zpi.plagiarism_detector.server.data.FileData;
import com.zpi.plagiarism_detector.server.data.ServerData;
import com.zpi.plagiarism_detector.server.data.WebData;
import com.zpi.plagiarism_detector.server.database.Dao;
import com.zpi.plagiarism_detector.server.database.DaoFactory;
import com.zpi.plagiarism_detector.server.detector.ComparingAlgorithm;
import com.zpi.plagiarism_detector.server.detector.PlagiarismDetector;
import com.zpi.plagiarism_detector.server.handlers.MessageDispatcher;
import com.zpi.plagiarism_detector.server.handlers.MessageHandler;
import com.zpi.plagiarism_detector.server.websearch.GoogleSearch;
import com.zpi.plagiarism_detector.server.websearch.WebsiteAnalyze;

public class MessageHandlerFactory extends AbstractMessageHandlerFactory {

    private MessageDispatcher messageDispatcher;

    public MessageHandler createForSocket(Socket socket) throws IOException {
        initFields();

        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

        return MessageHandler.create(outputStream, inputStream, messageDispatcher);
    }

    public MessageHandler createForStreams(ObjectOutput outputStream, ObjectInput inputStream) throws IOException {
        initFields();

        return MessageHandler.create(outputStream, inputStream, messageDispatcher);
    }

    private void initFields() {
        GoogleSearch googleSearch = new GoogleSearch();
        WebsiteAnalyze websiteAnalyze = new WebsiteAnalyze();
        Dao dao = DaoFactory.createDao();
        FileData fileData = new FileData();
        ServerData serverData = new ServerData(dao, fileData);
        WebData webData = new WebData(googleSearch, websiteAnalyze);
        ComparingAlgorithm comparingAlgorithm = new ComparingAlgorithm();

        PlagiarismDetector plagiarismDetector = new PlagiarismDetector(serverData, webData, comparingAlgorithm);
        messageDispatcher = new MessageDispatcher(plagiarismDetector);
    }
}
