package com.zpi.plagiarism_detector.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController implements Initializable {

    @FXML
    private Label label, appTitle;

    @FXML
    private TextArea inputData, outputData;

    @FXML
    Button button;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        outputData.setText("Napisa³eœ: " + inputData.getText());
        outputData.setStyle("-fx-highlight-fill: lightgray; -fx-highlight-text-fill: firebrick;");
        outputData.selectRange(11, outputData.getLength());

        label.setText("Hello Guys! ;)");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        button.setAlignment(Pos.TOP_CENTER);

    }
}
