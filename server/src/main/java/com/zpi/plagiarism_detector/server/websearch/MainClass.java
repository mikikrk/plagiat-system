package com.zpi.plagiarism_detector.server.websearch;

import com.zpi.plagiarism_detector.commons.protocol.DocumentData;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainClass {
    public static void main(String[] args) throws IOException {
        String[] linksArray; //tablica ze znalezionymi linkami
        String[] linksInDatabase = {"http://www.przykladowylink1.com", "http://liris.cnrs.fr/Documents/Liris-3768.pdf"};
        int numberOfResults = 20; //liczba wynikow do przeszuaknia w Google
        char searchType = 'K'; //rodzaj wyszukiwania - artykul (A) lub kod (K)
        Set<String> keywords = new HashSet<>();
        keywords.add("java");
        keywords.add("interface"); //slowa kluczowe do wyszukania
        List<DocumentData> serres; //lista ze znalezionymi pasujacymi wynikami

        /**
         * Jako argumenty metody GoogleSearch podajemy liczbe wynikow w Google 
         * i slowa kluczowe ktore zostana uzyte w zapytaniu Google. 
         */
        linksArray = GoogleSearch.GoogleSearch(numberOfResults, keywords);

        /**
         * Jako argumenty metody WebsiteAnalyze podajemy tablice linkow 
         * linksArray zwrocona przez metode GoogleSearch, tablice 
         * linkow linksInDatabase czyli linki obecne juz w bazie danych w celu 
         * porownania oraz tresc zapytania do Google.
         */
        if (linksArray[0] != null) {
            serres = WebsiteAnalyze.WebsiteAnalyze(linksArray, linksInDatabase, keywords, searchType);
        }
    }
}
