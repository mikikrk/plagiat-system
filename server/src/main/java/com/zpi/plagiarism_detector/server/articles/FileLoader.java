package CompareEngine;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileLoader {
	
	private String patternPath;
	private String textPath;
	private String pattern;
	private String text;
	
	public FileLoader(String _patternPath, String _textPath) {
		this.patternPath=_patternPath;
		this.textPath=_textPath;
	}
	
	//Returns pattern String
	public String getPattern(){
		return this.pattern;
	}
	
	//Returns article String
	public String getText(){
		return this.text;
	}
	
	//Loads files from files
	public void loadFiles() throws IOException{
		
		File f = new File(patternPath);
		FileInputStream fin = new FileInputStream(f);			
		byte[] buffer = new byte[(int) f.length()];
		new DataInputStream(fin).readFully(buffer);				   
		fin.close();
		pattern = new String(buffer, "UTF-8");
		//pattern = pattern.toLowerCase();
		
		File f2 = new File(textPath);
		FileInputStream fin2 = new FileInputStream(f2);			
		byte[] buffer2 = new byte[(int) f2.length()];
		new DataInputStream(fin2).readFully(buffer2);				   
		fin2.close();
		text = new String(buffer2, "UTF-8");	
		//text = text.toLowerCase();
	}
		
}
