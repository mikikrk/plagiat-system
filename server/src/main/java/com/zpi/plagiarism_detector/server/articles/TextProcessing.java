package CompareEngine;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class TextProcessing {

    private String patternPath;
    private String textPath;
    private PlagiarismResult plagiarismResult = new PlagiarismResult();
    private Map<PlagiarismFragment, PlagiarismFragment> map = new LinkedHashMap<PlagiarismFragment, PlagiarismFragment>();

    public TextProcessing() {
    	plagiarismResult.setType(DocumentType.TEXT);

    }

    /**
     * W�asciwe por�wnanie
     *
     * @param str
     * @param p
     */
    public PlagiarismResult compareTexts(String str, String p) {
        int m = p.length();
        int[] next = new int[m];
        next = getNext(p);
        plagiarismResult.setPlagiarisedFragments(findKMPSub(str,p,next));
        return plagiarismResult;
    }


    public int[] getNext(String p) {
        int i = 1, j = 0;
        int[] next = new int[p.length() + 2];
        char[] pattern = p.toCharArray();

        next[0] = -1;
        next[1] = 0;
        while (i < p.length() - 1) {
            if (pattern[i] == pattern[j]) {
                i++;
                j++;
                next[i] = next[j];
            } else if (j == 0) {
                next[i + 1] = 0;
                i++;
            } else {
                j = next[j];
            }
        }
        return next;
    }

    /**
     * Znajduje podstring p w przeszukiwanym tekscie str
     */
    private  Map<PlagiarismFragment, PlagiarismFragment> findKMPSub(String str, String p, int next[]) {
        char[] string = str.toCharArray();
        char[] pattern = p.toCharArray();
        int i = 0;
        int j = 0;
        while (i < str.length()) {
            if (j == -1 || string[i] == pattern[j]) {
                i++;
                j++;
            } else {
                j = next[j];
            }
            if (j == p.length()) {
                j = 0;
                map.put(new PlagiarismFragment(p, 0, 0, p.length()),
                        new PlagiarismFragment(str.substring(i-p.length(),i), i - p.length(), i, p.length()));
            }
        }
        return map;
    }

}


