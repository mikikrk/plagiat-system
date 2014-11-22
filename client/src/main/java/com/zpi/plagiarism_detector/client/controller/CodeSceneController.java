/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zpi.plagiarism_detector.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Agat
 */
public class CodeSceneController implements Initializable, Controller {

    /**
     * Initializes the controller class.
     */
    @FXML
    Button codeCancelButton;

    @FXML
    private void handleCodeCancelButtonAction(ActionEvent event) {

        System.out.println("You clicked me, dumbass!");
        Stage stage = (Stage) codeCancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
