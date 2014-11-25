/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.zpi.plagiarism_detector.client.controller;

import com.zpi.plagiarism_detector.commons.database.DocumentType;
import com.zpi.plagiarism_detector.commons.protocol.plagiarism.PlagiarismResult;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Agat
 */
public class CodeGridController implements Initializable {
    @FXML
    private GridPane inputArticleGrid;
    @FXML
    private Label currentArticleNr;
    @FXML
    private Label totalArticleCnt;
    @FXML
    private TextArea newCode;
    @FXML
    private Label currentArticleNr1;
    @FXML
    private Label totalArticleCnt1;
    @FXML
    private TextArea foundCode;
    private List<List<PlagiarismResult>> allResults;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        allResults = ResultSceneController.getSeparatedDocuments();
        List<PlagiarismResult> allData = separateCodes().get(0);
        if (allData.size() > 0) {
            newCode.setText(allData.get(0).getNewDocument());
            foundCode.setText(allData.get(0).getExistingDocument());
            System.out.println("Mikiiiiiiiiiiiii! <3");
        }
    }    
    private List<List<PlagiarismResult>> separateCodes() {
        LinkedList<List<PlagiarismResult>> filteredArticles = new LinkedList<List<PlagiarismResult>>();
        List<PlagiarismResult> tempList = new LinkedList<PlagiarismResult>();
        for (List<PlagiarismResult> resultList : allResults) {
            System.out.println(resultList.toString());
            for (PlagiarismResult singleResult : resultList) {
                if (singleResult.getType().equals(DocumentType.CODE)) {
                    if (tempList.size() > 0) {
                        tempList.add(singleResult);
                    }
                }
            }
            filteredArticles.add(tempList);
        }
        return filteredArticles;
    }
}
