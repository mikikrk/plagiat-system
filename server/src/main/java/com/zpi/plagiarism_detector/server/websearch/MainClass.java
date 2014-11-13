package com.zpi.plagiarism_detector.server.websearch;

import java.io.IOException;

public class MainClass {
    public static void main(String[] args) throws IOException {
        String[] linksArray;
        String[] linksInDatabase = {"http://www.przykladowylink1.com", "http://liris.cnrs.fr/Documents/Liris-3768.pdf"};
        String lastFileName;
        
        /**
         * Jako argumenty metody GoogleSearch podajemy liczbę wyników w Google 
         * i słowa kluczowe które zostaną użyte w zapytaniu Google. 
         */
        linksArray = GoogleSearch.GoogleSearch(20, "health knowledge management");
        
        /**
         * Jako argumenty metody WebsiteAnalyze podajemy tablicę linków 
         * linksArray zwróconą przez metodę GoogleSearch oraz tablicę 
         * linków linksInDatabase czyli linki obecne już w bazie danych w celu 
         * porównania.
         */        
        lastFileName = WebsiteAnalyze.WebsiteAnalyze(linksArray, linksInDatabase);
        
        System.out.println("Nazwa ostatnio zapisanego pliku: " + lastFileName + "\n");
    }
}
