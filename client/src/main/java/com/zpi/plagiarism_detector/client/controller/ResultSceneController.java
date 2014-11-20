package com.zpi.plagiarism_detector.client.controller;

import com.zpi.plagiarism_detector.client.model.ClientModel;
import com.zpi.plagiarism_detector.client.model.exceptions.CannotConnectToTheServerException;
import com.zpi.plagiarism_detector.client.model.factories.ClientFactory;
import com.zpi.plagiarism_detector.client.view.SwitchButton;
import com.zpi.plagiarism_detector.client.view.ViewUtils;
import com.zpi.plagiarism_detector.commons.protocol.Message;
import com.zpi.plagiarism_detector.commons.protocol.ProtocolCode;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ResultSceneController implements Initializable, Controller {

    private static final ClientFactory clientFactory = new ClientFactory();
    private ClientModel model;

    @FXML
    private Label label, appTitle;

    @FXML
    private TextArea inputData, outputData;

    @FXML
    Button button;

    @FXML
    Button checkButton, codeReviewButton;
    
    @FXML
    SwitchButton switchButton;
    
    @FXML
    Parent inputArticleGrid, outputArticleGrid;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        
         try {
         model.openConnection();
         Message message = new Message(ProtocolCode.TEST);
         model.sendMessage(message);
         model.closeConnection();
         } catch (CannotConnectToTheServerException e) {
         ViewUtils.showErrorDialog("Error", "Connection error", "Server is down or there are other issues with connection!");
         }
         /*
         System.out.println("You clicked me!");
         outputData.setText("Napisa?e?: " + inputData.getText());
         outputData.setStyle("-fx-highlight-fill: lightgray; -fx-highlight-text-fill: firebrick;");
         outputData.selectRange(11, outputData.getLength());
         label.setText("Hello Guys! ;)"); 
         */
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


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = createModel();
        System.out.println("switchButton: " + switchButton.switchOnProperty().toString());
    //    outputData.setText(switchButton.switchOnProperty().toString());
    }

    private ClientModel createModel() {
        ClientModel model = new ClientModel(clientFactory);
        return model;
    }
}
