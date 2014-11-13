package com.zpi.plagiarism_detector.websearch;
 
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import com.google.gson.Gson;
 
public class GoogleSearch {
    
    public static String[] GoogleSearch(int numberOfResults, String keywords) throws IOException {
        String[] urls = new String[numberOfResults];
        
        /**
         * Przy użyciu gson otrzymujemy wyszukane linki w partiach po 4  
         * stąd dalej są 2 IFy jeśli zadana ilość wyników nie jest wielokrotnością 4. 
         */
        for (int i = 0; i < numberOfResults; i = i + 4) {
            String address = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&start="+i+"&q=";
                    
                String query = keywords;
                String charset = "UTF-8";
                URL url = new URL(address + URLEncoder.encode(query, charset));
                Reader reader = new InputStreamReader(url.openStream(), charset);
                GoogleResults results = new Gson().fromJson(reader, GoogleResults.class);
                if ((numberOfResults - 4) < i)
                {
                    int i2 = numberOfResults % 4;
                    for (int m = 0; m < i2; m++) {
                        urls[i+m] = results.getResponseData().getResults().get(m).getUrl();
                    }                    
                }
                else
                {   
                    for (int m = 0; m <= 3; m++) {
                        urls[i+m] = results.getResponseData().getResults().get(m).getUrl();
                    }
                }
        }
    return urls;    
    }
}
 