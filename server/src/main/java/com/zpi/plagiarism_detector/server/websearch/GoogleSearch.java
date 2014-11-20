package com.zpi.plagiarism_detector.server.websearch;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

public class GoogleSearch implements GoogleSearchInterface {
    
    public static String[] GoogleSearch(int numberOfResults, Set<String> keywords) throws IOException {
        String keyword = StringUtils.join(keywords, " ");
        String[] urls = new String[numberOfResults];
        String cx = "003774498586208713767:hnxd8xpprx4"; //klucz cx google przypisany do mojego konta
        String key = "AIzaSyCT9MHJyBqmOuplt9-Rfzz2y0vDB-1xnHI"; //klucz api google - max 100 wyszukan dziennie
        String charset = "UTF-8";
        
        /**
         * Uzycie petli poniewaz za pomoca Google Custom Search uzyskujemy
         * max 10 wynikow na kazde wywolanie. 
         */
        for (int i = 0; i < numberOfResults; i = i + 10) {
            
            int ii = i + 1;
            String queryArguments = key + "&cx=" + cx + "&q=" + keywords + "&alt=json" + "&start=" + ii; //tworzenie adresu Google Custom Search
            
            /**
             * Zamiana blednych znakow powstalych w koncowej czesci adresu
             * przy kodowaniu URL. 
             */
            String addition = URLEncoder.encode(queryArguments, "UTF-8")
            .replaceAll("\\%28", "(") 
            .replaceAll("\\%29", ")") 
            .replaceAll("\\+", "%20") 
            .replaceAll("\\%27", "'") 
            .replaceAll("\\%21", "!") 
            .replaceAll("\\%7E", "~")
            .replaceAll("\\%26", "&")
            .replaceAll("\\%3D", "=")
            .replaceAll("\\%3A", ":");    

            String url = "https://www.googleapis.com/customsearch/v1?key=";
            String total = url + addition;
            URL url2 = new URL(total);

            Reader reader = new InputStreamReader(url2.openStream(), charset);
            GoogleResults results = new Gson().fromJson(reader, GoogleResults.class); //konwersja Json do Javy

            if(results.getThing(0) != null) {

                /**
                 * Zmniejszanie ilosci wynikow jesli zadana liczba nie jest wielokrotnoscia 10
                 */
                if ((numberOfResults - 10) < i && numberOfResults % 10 != 0)
                {
                    int i2 = numberOfResults % 10;
                    for (int m = 0; m < i2; m++) {
                        String urlFound = results.getThing(m).link;
                        urls[i+m] = results.getThing(m).link;
                    }                    
                }
                else
                {   
                    for (int m = 0; m < 10; m++) {
                        String urlFound = results.getThing(m).link;
                        urls[i+m] = results.getThing(m).link;
                }
                }                
                
            }
            else
                System.out.println("No results");
        }
    return urls;    
    }

    @Override
    public String[] search(int numberOfResults, Set<String> keywords) {
        String[] urls = null;
        try {
            urls = GoogleSearch.GoogleSearch(numberOfResults, keywords);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return urls;
    }
}
 