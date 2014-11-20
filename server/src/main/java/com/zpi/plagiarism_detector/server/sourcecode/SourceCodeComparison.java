package com.zpi.plagiarism_detector.server.sourcecode;

import java.io.File;
import java.io.IOException;

import com.zpi.plagiarism_detector.commons.protocol.plagiarism.PlagiarismResult;

public class SourceCodeComparison {
	private String language;
	private SourceCode sc;
	
	public SourceCodeComparison() {
		this.language = null;
	}
	
	public void getOriginalLanguage(String path){
		
		this.sc = new SourceCode(20);
		this.language = sc.recognizeLanguage(path);
		this.sc.setLanguageString(this.language);
	}
	
	/**
	 * Metoda porównuj¹ca dwa teksty
	 * @param pathNew - sciezka do pliku od uzytkownika	
	 * @param pathExist - sciezka do istniejacego pliku
	 * @return PlagiarismResult w przypadku sukcesu(puste PlagiarismFragment gdy nic nie znalazlo)
	 * 			null jesli podany plik jest w innym jezyku
	 * @throws IOException
	 */
	public PlagiarismResult compareFiles(String pathNew, String pathExist) throws IOException{
		
		
		getOriginalLanguage(pathNew);
		this.sc.init();
		
		//dodanie pliku uzytkownika jako pierwszego
		this.sc.add(new File(pathNew));
		try{
			//dodanie pliku do porownania
			this.sc.add(new File(pathExist));
		}
		catch(net.sourceforge.pmd.lang.ast.TokenMgrError e){
			//inny kod zrodlowy programu
			return null;
		}
		//uruchomienie porownania
		this.sc.go();
		
		PlagiarismResult result = sc.convertXml(sc.getMatches(pathNew),pathNew);
		
		return result;
	}
}
