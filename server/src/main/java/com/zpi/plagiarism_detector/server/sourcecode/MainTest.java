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
		
		
		SourceCodeComparison scc = new SourceCodeComparison();
		
		PlagiarismResult result = scc.compareFiles("E:\\test\\test2.java","E:\\test\\rb.txt");
		System.out.println(result);
		result = scc.compareFiles("E:\\test\\testtescik.java","E:\\test\\testtest.java");
		Map<PlagiarismFragment, PlagiarismFragment> mp = result.getPlagiarisedFragments();
		Iterator it = mp.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        System.out.println(((PlagiarismFragment) pairs.getKey()).getFragment() + " = " + ((PlagiarismFragment) pairs.getValue()).getFragment());
	        System.out.println("--------------------");
	        it.remove();
	    }

		
	}

}
