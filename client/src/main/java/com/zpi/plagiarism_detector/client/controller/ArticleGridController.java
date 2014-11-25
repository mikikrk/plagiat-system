/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zpi.plagiarism_detector.client.controller;

import com.zpi.plagiarism_detector.commons.database.DocumentType;
import com.zpi.plagiarism_detector.commons.protocol.DocumentData;
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
public class ArticleGridController implements Initializable {
    
    @FXML
    private GridPane inputArticleGrid;
    @FXML
    private TextArea inputTitle;
    @FXML
    private TextArea inputData;
    @FXML
    private TextArea inputKeywords;
    @FXML
    private Label currentArticleNr;
    @FXML
    private Label totalArticleCnt;
    @FXML
    private TextArea outputData;
    private List<List<PlagiarismResult>> allResults = ResultSceneController.getSeparatedDocuments();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        List<PlagiarismResult> allData = separateArticles().get(0);
//        System.out.println("all data size: " + allData.size());
//        inputData.setText(allData.get(0).getNewDocument());
//        outputData.setText(allData.get(0).getExistingDocument());
    }
    
    private List<List<PlagiarismResult>> separateArticles() {
        LinkedList<List<PlagiarismResult>> filteredArticles = new LinkedList<List<PlagiarismResult>>();
        List<PlagiarismResult> tempList = new LinkedList<PlagiarismResult>();
        for (List<PlagiarismResult> resultList : allResults) {
            for (PlagiarismResult singleResult : resultList) {
                if (singleResult.getType().equals(DocumentType.TEXT)) {
                    if(tempList.size()>0) {
                        tempList.add(singleResult);
                    }
                }
            }
            filteredArticles.add(tempList);
        }
        return filteredArticles;
    }
}
