package com.zpi.plagiarism_detector.server.websearch;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import com.snowtide.PDF;
import com.snowtide.pdf.OutputTarget;
import com.zpi.plagiarism_detector.commons.protocol.DocumentData;
import java.io.FileOutputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

public class WebsiteAnalyze implements WebsiteAnalyzeInterface{ 
    
    public static List<DocumentData> WebsiteAnalyze(String[] linksArray, String[] linksInDatabase, Set<String> keywords, char searchType) throws IOException {
        
        List<DocumentData> sr = new ArrayList<>();
        String keyword = StringUtils.join(keywords, " ");
        for (int i = 0; i < linksArray.length; i++) {
            
            boolean check = false;
            for (int j = 0; j < linksInDatabase.length; j++) {
                
                /**
                 * Sprawdzanie czy dany link jest juz w bazie.
                 */
                if (linksArray[i].equals(linksInDatabase[j])) {check = true;}
            }
            
            if (check != true) {
                String fileName = null;
                String dirName = "C:\\FolderArtykuly";
                URL url = new URL(linksArray[i]);
                URLConnection connection = url.openConnection();
                connection.connect();
                String contentType = "n/a";
                if (connection.getContentType() != null) {
                    contentType = connection.getContentType();
                }
                
                /**
                 * Jesli link jest pdfem to zapisywany jest 
                 * w podanym istniejacym folderze jako plik txt i zwracany wraz 
                 * z innymi parametrami z uzyciem klasy SearchResult.
                 */
                if (searchType == 'A' && contentType.contains("application/pdf")) {
                    DocumentData serres = new DocumentData();
                    Set<String> codesArray = new HashSet<>();
                    (new File(dirName)).mkdirs(); //tworzenie folderu jesli jeszcze go nie ma
                    fileName = "Article from PDF (keyword - " + keyword + "; url nr - " + i + ").pdf";
                    String full = dirName + "\\" + fileName;
                    saveFileFromUrl(full, linksArray[i]); //zapis pliku pdf na dysk

                    com.snowtide.pdf.Document pdf = PDF.open(full);
                    StringBuilder text = new StringBuilder(1024);
                    pdf.pipe(new OutputTarget(text)); //wyciaganie tekstu z pliku pdf
                    String pdfTitle;
                    if (pdf.getAttribute("ATTR_TITLE") == null) {
                        pdfTitle = linksArray[i];
                    }
                    else pdfTitle = pdf.getAttribute("ATTR_TITLE").toString();
                    pdf.close();
                    String text2 = text.toString();
                    SaveFile(dirName + "\\" + fileName.substring(0,fileName.length() - 4) + ".txt", text2); //zapis pliku txt
                    File file = new File(dirName + "\\" + fileName);
                    file.delete(); //usuniecie pliku pdf
                    serres.setArticle(text2);
                    serres.setFilePath(full);
                    serres.setUrl(linksArray[i]);
                    serres.setTitle(pdfTitle);
                    serres.setKeywords(keywords);
                    codesArray.add("n/a");
                    serres.setCodes(codesArray);
                    sr.add(serres);
                }
                
                /**
                 * Jesli wyszukana strona ma artykul (np. jako abstract) jest on 
                 * zapisywany w podanym istniejacym folderze jako plik txt i zwracany
                 * z uzyciem klasy SearchResult wraz z pozostalymi parametrami.
                 */
                else if (searchType == 'A' && contentType.contains("text/html")) {
                    DocumentData serres = new DocumentData();
                    Set<String> codesArray = new HashSet<>();
                    String siteTitle;
                    org.jsoup.Connection con = Jsoup.connect(linksArray[i]).ignoreHttpErrors(true).userAgent("Mozilla"); //przygotowanie pliku html do analizy
                    //org.jsoup.Connection.Response resp = con.execute();
                    org.jsoup.nodes.Document doc = null;
                    if (con.execute().statusCode() == 200) {
                        doc = con.get();
                        siteTitle = doc.title();
                    
                        String filePath = null;
                        String articleText = null;

                        /**
                         * Tu wpisujemy wybrane tagi lub klasy CSS.
                         * Klasy zaczynaja sie ".", id "#", a tagi to sama nazwa. 
                         */
                        Elements texts = doc.select(".abstract, .description, .abstr"); 
                        for (org.jsoup.nodes.Element text : texts) {
                            articleText = text.text();

                            /**
                            * Sprawdzanie czy artykul jest wystarczajaco dlugi.
                            */
                            if (articleText.length() > 500) {
                                fileName = "Article from HTTP (keyword - " + keyword + "; length - " + articleText.length() + ").txt";
                                filePath = dirName + "\\" + fileName;       
                                SaveFile(filePath, articleText); //zapis kodu do txt
                            } 
                        }
                        if (articleText != null) {
                            serres.setArticle(articleText);
                            serres.setFilePath(filePath);
                            serres.setUrl(linksArray[i]);
                            serres.setTitle(siteTitle);
                            serres.setKeywords(keywords);
                            codesArray.add("n/a");
                            serres.setCodes(codesArray);
                            sr.add(serres);
                        }
                    }
                }
                
                /**
                 * Jesli wyszukana strona ma pole z kodem jest on zapisywany 
                 * w podanym istniejacym folderze jako plik txt i zwracana jest 
                 * lista wszystkich kodow z uzyciem klasy SearchResult.
                 */
                else if (searchType == 'K' && contentType.contains("text/html")) {
                    DocumentData serres = new DocumentData();
                    Set<String> codesArray = new HashSet<>();
                    String siteTitle;
                    org.jsoup.Connection con = Jsoup.connect(linksArray[i]).ignoreHttpErrors(true).userAgent("Mozilla"); //przygotowanie pliku html do analizy                    
                    
                    org.jsoup.nodes.Document doc = null;
                    if (con.execute().statusCode() == 200) {
                        doc = con.get();
                        
                        siteTitle = doc.title();
                        String filePath = null;
                        String codeText = null;
                        Elements codes = doc.select("pre, code, prettyprint, brush"); //tu wpisujemy tagi lub klasy CSS zawierajace w htmlu kod 
                        for (org.jsoup.nodes.Element code : codes) {
                            codeText = code.text();

                            /**
                            * Sprawdzanie czy kod ma minimum dwie linijki i czy jest
                            * wystarczajaco dlugi.
                            */
                            if (codeText.contains("\n") && codeText.length() > 150) {
                                fileName = "Code (keyword - " + keyword + "; length - " + codeText.length() + ").txt";
                                filePath = dirName + "\\" + fileName;       
                                SaveFile(filePath, codeText); //zapis kodu do txt
                                codesArray.add(codeText);
                            } 
                        }
                        if (codesArray != null) {
                            serres.setArticle("n/a");
                            serres.setFilePath(filePath);
                            serres.setUrl(linksArray[i]);
                            serres.setTitle(siteTitle);
                            serres.setKeywords(keywords);
                            serres.setCodes(codesArray);
                            sr.add(serres);

                        }
                    }  
                }        
            }
        }
    return sr;
    }
    
    public static void saveFileFromUrl(String fileName, String fileUrl) throws MalformedURLException, IOException {
        FileUtils.copyURLToFile(new URL(fileUrl), new File(fileName));
    }
    
    public static void SaveFile(String path, String text) {
    FileOutputStream fop = null;
    File file;

    try {

        file = new File(path);
        fop = new FileOutputStream(file);

        if (!file.exists()) {
            file.createNewFile();
        }

        byte[] contentInBytes = text.getBytes();

        fop.write(contentInBytes);
        fop.flush();
        fop.close();

    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
            if (fop != null) {
                fop.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

    @Override
    public List<DocumentData> analyze(String[] linksArray, String[] linksInDatabase, Set<String> keywords, char searchType) {
        List<DocumentData> ret = new ArrayList<>();
        try {
            ret = WebsiteAnalyze.WebsiteAnalyze(linksArray, linksInDatabase, keywords, searchType);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ret;
    }
   
}