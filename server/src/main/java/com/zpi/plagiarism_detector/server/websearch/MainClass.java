package com.zpi.plagiarism_detector.server.websearch;

import java.io.IOException;

public class MainClass {
    private static GoogleSearch googleSearch = new GoogleSearch();
    private static WebsiteAnalyze websiteAnalyze = new WebsiteAnalyze();
    public static void main(String[] args) throws IOException {
        String[] linksArray;
        String[] linksInDatabase = {"http://www.przykladowylink1.com", "http://liris.cnrs.fr/Documents/Liris-3768.pdf"};
        String lastFileName;
        
        /**
         * Jako argumenty metody search podajemy liczbę wyników w Google
         * i słowa kluczowe które zostaną użyte w zapytaniu Google. 
         */
        linksArray = googleSearch.search(20, "health knowledge");
        
        /**
         * Jako argumenty metody analyze podajemy tablicę linków
         * linksArray zwróconą przez metodę search oraz tablicę
         * linków linksInDatabase czyli linki obecne już w bazie danych w celu 
         * porównania.
         */        
        lastFileName = websiteAnalyze.analyze(linksArray, linksInDatabase);
        
        System.out.println("Nazwa ostatnio zapisanego pliku: " + lastFileName + "\n");
    }
}
