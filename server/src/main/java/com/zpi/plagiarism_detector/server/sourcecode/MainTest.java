package com.zpi.plagiarism_detector.server.sourcecode;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;

import javax.script.ScriptException;

import com.zpi.plagiarism_detector.commons.protocol.plagiarism.PlagiarismFragment;
import com.zpi.plagiarism_detector.commons.protocol.plagiarism.PlagiarismResult;



public class MainTest {
	/**
	 * @param args
	 * @throws ScriptException 
	 * @throws NoSuchMethodException 
	 */
	public static void main(String[] args) throws IOException, ScriptException, NoSuchMethodException{
		// TODO Auto-generated method stub
		
		SourceCode sc = new SourceCode(20);
		//rozpoznanie jezyka przykladu
		String lang = sc.recognizeLanguage("E:\\test\\testtest.java");
		sc.setLanguageString(lang);
		//inicjacja zmiennych
		sc.init();
		
		try {
			//dodanie pliku uzytkownika jako pierwszego
			sc.add(new File("E:\\test\\testtest.java"));
			
			//dodanie pliku do porownania
			sc.add(new File("E:\\test\\test2.java"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//uruchomienie porownania
		sc.go();
		
		/*System.out.print(sc.getMatches());
		System.out.println("-------------------------");
		System.out.println("");*/
		
		//zwrocenie wynikow porownania, sciezka do pliku uzytkownika
		System.out.println(sc.getMatches("E:\\test\\testtest.java"));
		
		//przetworzenie xml na obiekt PlagiarismResult
		PlagiarismResult result = sc.convertXml(sc.getMatches("E:\\test\\testtest.java"),"E:\\test\\testtest.java");
		Map<PlagiarismFragment, PlagiarismFragment> mp = result.getPlagiarisedFragments();
		Iterator it = mp.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        System.out.println(((PlagiarismFragment) pairs.getKey()).getEnd() + " = " + ((PlagiarismFragment) pairs.getValue()).getEnd());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		//System.out.println();

		
	}

}
