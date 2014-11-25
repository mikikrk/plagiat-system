package com.zpi.plagiarism_detector.client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import com.zpi.plagiarism_detector.client.model.ClientModel;
import com.zpi.plagiarism_detector.client.model.factories.ClientFactory;
import com.zpi.plagiarism_detector.client.view.SwitchButton;
import com.zpi.plagiarism_detector.commons.protocol.plagiarism.PlagiarismFragment;
import com.zpi.plagiarism_detector.commons.protocol.plagiarism.PlagiarismResult;
import javafx.scene.control.TextField;

public class ResultSceneController implements Initializable, Controller {

    @FXML
    SwitchButton switchButton;
    @FXML
    GridPane container;
    @FXML
    TextField statWordsArtic, statWordsOvall, statPercArtic, statPercOvall;
    Node articleGridNode, codeGridNode;
    private static List<List<PlagiarismResult>> allDocuments;
    private static final List<PlagiarismResult> returnedResult = MainSceneController.getAllResults();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FXMLLoader loader = new FXMLLoader();
        try {
            articleGridNode = (Node) loader.load(getClass().getResource("/fxml/includes/articleGrid.fxml"));
            codeGridNode = (Node) loader.load(getClass().getResource("/fxml/includes/codeGrid.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        handleSwitchButtonAction();
        allDocuments = separateDocuments(returnedResult);
        statWordsArtic.setText(null);
        statWordsOvall.setText(Integer.toString(getAmountOfSimilarSentencesInAllResults(returnedResult)));
        statPercArtic.setText(null);
        statPercOvall.setText(Integer.toString(getPercantageOfSimilarityInAllResults(returnedResult)));
    }

    @FXML
    private void handleSwitchButtonAction() {

        Node gridNode = articleGridNode;
        if (switchButton.switchOnProperty().getValue() == true) {
            gridNode = codeGridNode;
        }
        container.getChildren().clear();
        container.getChildren().add(gridNode);
        System.out.println("Switch switchy switch!");

    }

    @FXML
    private void handleBackButtonAction() {
        container.getScene().getWindow().hide();
        MainSceneController.showMainWindow();
    }

    /**
     * Rozdzielenie listy wyników na listy poszczególnych plików (tekst,
     * kody)
     *
     * @param results
     * @return
     */
    private List<List<PlagiarismResult>> separateDocuments(List<PlagiarismResult> results) {
        String doc = "";
        LinkedList<List<PlagiarismResult>> allResults = new LinkedList<List<PlagiarismResult>>();
        LinkedList<PlagiarismResult> docResults;

        while (!results.isEmpty() && doc != null) {
            doc = null;
            docResults = new LinkedList<PlagiarismResult>();
            for (PlagiarismResult result : results) {
                if (result != null) {
                    if (doc == null) {
                        doc = result.getNewDocument();
                        docResults.add(result);
                        results.remove(result);
                    } else {
                        if (result.getNewDocument().equals(doc)) {
                            docResults.add(result);
                            results.remove(result);
                        }
                    }
                }
            }
            allResults.add(docResults);
        }
        return allResults;
    }
    
    public static List<List<PlagiarismResult>> getSeparatedDocuments() {
        return allDocuments;
    }

    /**
     * Obliczanie ilości znalezionych zdań/linii podobnych w aktualnie
     * wyświetlanym wyniku (ile zdań/linii w artykule podanym jest podobnych
     * do zdań/linii w artykule znalezionym - właśnie wyświetlanym)
     *
     * @param result
     * @return
     */
    private int getAmountOfSimilarSentences(PlagiarismResult result) {
        return result.getPlagiarisedFragments().size();
    }

    /**
     * Obliczanie ilości znalezionych zdań/linii podobnych z zdaniami/liniami
     * we wszystkich znalezionych dokumentach. Wynik dla poszczególnego pliku
     *
     * @param results
     * @return
     */
    private int getAmountOfSimilarSentencesInAllResults(List<PlagiarismResult> results) {
        int amount = 0;
        for (PlagiarismResult result : results) {
            amount += getAmountOfSimilarSentences(result);
        }
        return amount;
    }

    /**
     * Obliczanie procentu długości znalezionych zdań/linii podobnych z
     * aktualnie wyświetlanym znalezionym dokumentem w stosunku do długości
     * przesłanego pliku
     *
     * @param result
     * @return
     */
    private int getPercantageOfSimilarity(PlagiarismResult result) {
        int fragmentsLength = 0;
        for (Entry<PlagiarismFragment, PlagiarismFragment> fragments : result.getPlagiarisedFragments().entrySet()) {
            fragmentsLength += fragments.getKey().getSize();
        }
        return fragmentsLength / result.getNewDocument().length();
    }

    /**
     * Obliczanie procentu długości znalezionych zdań/linii podobnych z
     * zdaniami/liniami we wszystkich znalezionych dokumentach, w stosunku do
     * długości przesłanego pliku Wynik dla poszczególnego pliku
     *
     * @param results
     * @return
     */
    private int getPercantageOfSimilarityInAllResults(List<PlagiarismResult> results) {
        int fragmentsLength = 0;
        for (PlagiarismResult result : results) {
            fragmentsLength += getPercantageOfSimilarity(result);
        }
        if (!results.isEmpty()) {
            return fragmentsLength / results.get(0).getNewDocument().length();
        }
        return 0;
    }
}
