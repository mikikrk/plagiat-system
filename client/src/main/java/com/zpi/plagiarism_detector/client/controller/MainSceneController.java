package com.zpi.plagiarism_detector.client.controller;

import com.zpi.plagiarism_detector.client.model.ClientModel;
import com.zpi.plagiarism_detector.client.model.exceptions.CannotConnectToTheServerException;
import com.zpi.plagiarism_detector.client.model.factories.ClientFactory;
import com.zpi.plagiarism_detector.client.view.ViewUtils;
import com.zpi.plagiarism_detector.commons.protocol.DocumentData;
import com.zpi.plagiarism_detector.commons.protocol.Message;
import com.zpi.plagiarism_detector.commons.protocol.ProtocolCode;
import com.zpi.plagiarism_detector.commons.protocol.plagiarism.PlagiarismDetectionResult;
import com.zpi.plagiarism_detector.commons.util.TextUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;

public class MainSceneController implements Initializable, Controller, Observer {

    private static final ClientFactory clientFactory = new ClientFactory();
    private static Stage mainWindow = new Stage();
    @FXML
    Button button;
    @FXML
    Button checkButton, codeReviewButton;
    private ClientModel model;
    @FXML
    private Label label, appTitle;
    @FXML
    private TextArea inputData, outputData, inputTitle, inputKeywords;

    public static void showMainWindow() {
        mainWindow.show();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {

//        try {
//
//            mainWindow = (Stage) checkButton.getScene().getWindow();
//            mainWindow.hide();
//            showResultScene();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        final DocumentData documentData = getViewDocumentData();

        try {
            model.openConnection();
            Message message = new Message(ProtocolCode.CHECK_FOR_PLAGIARISM, documentData);
            model.sendMessage(message);
            model.closeConnection();
        } catch (CannotConnectToTheServerException e) {
            ViewUtils.showErrorDialog("Error", "Connection error", "Server is down or there are other issues with connection!");
            e.printStackTrace();
        }

         /*
         System.out.println("You clicked me!");
         outputData.setText("Napisa?e?: " + inputData.getText());
         outputData.setStyle("-fx-highlight-fill: lightgray; -fx-highlight-text-fill: firebrick;");
         outputData.selectRange(11, outputData.getLength());
         label.setText("Hello Guys! ;)");
         */
    }

    private DocumentData getViewDocumentData() {
        String articleText = inputData.getText();
        String title = inputTitle.getText();
        String keywords = inputKeywords.getText();
        Set<String> keywordsSet = TextUtils.splitIntoSet(keywords);
        Set<String> codes = Collections.emptySet();

        return new DocumentData(title, keywordsSet, articleText, codes);
    }

    @FXML
    private void handleCodeReviewButtonAction(ActionEvent event) {
        Parent root;
        System.out.println("You clicked me, bastard!");
        URL url = getClass().getResource("/fxml/CodeScene.fxml");
        try {
            root = FXMLLoader.load(url);
            Stage stage = new Stage();
            stage.setTitle("Code source review");
            Scene scene = new Scene(root, 600, 500);
            scene.getStylesheets().add("/styles/Styles.css");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showResultScene() throws IOException {
        Parent root;
        System.out.println("You clicked me, bastard!");
        URL url = getClass().getResource("/fxml/ResultScene.fxml");
        root = FXMLLoader.load(url);
        Stage stage = new Stage();
        stage.setTitle("Result");
        Scene scene = new Scene(root, 600, 500);
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = createModel();
    }

    private ClientModel createModel() {
        ClientModel model = new ClientModel(clientFactory);
        model.addObserver(this);
        return model;
    }

    /**
     * Metoda która obsługuje komunikaty przychodzące od serwera
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        Message message = (Message) arg;

        ProtocolCode code = message.getCode();
        if(code == ProtocolCode.PLAGIARISM_CHECK_RESULT) {
            PlagiarismDetectionResult plagiarismDetectionResult = (PlagiarismDetectionResult) message.getSentObject();
        }
    }
}
