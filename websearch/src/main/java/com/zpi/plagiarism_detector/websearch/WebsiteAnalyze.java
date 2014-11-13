package com.zpi.plagiarism_detector.websearch;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
//import org.htmlparser.*;
import org.apache.commons.io.FileUtils;

public class WebsiteAnalyze { 
    
    public static String WebsiteAnalyze(String[] linksArray, String[] linksInDatabase) throws IOException {
        String fileName = null;
        for (int i = 0; i < linksArray.length; i++) {
            boolean check = false;
            for (int j = 0; j < linksInDatabase.length; j++) {
                
                /**
                 * Sprawdzanie czy dany link jest już w bazie.
                 */
                if (linksArray[i].equals(linksInDatabase[j])) {check = true;}
            }
            
            /**
             * ...jeśli tak oraz przykładowo plik jest pdfem to zapisywany jest 
             * w podanym istniejącym folderze.
             */
            if (linksArray[i].endsWith(".pdf") && check != true) {
                String dirName = "C:\\FolderArtykuly";
                fileName = linksArray[i].substring(linksArray[i].lastIndexOf("/") + 1);
                saveFileFromUrl(dirName + "\\" + fileName, linksArray[i]);
                
            }
        }
        return fileName;    
    }
    
    public static void saveFileFromUrl(String fileName, String fileUrl) throws MalformedURLException, IOException {
    FileUtils.copyURLToFile(new URL(fileUrl), new File(fileName));
    }
}
