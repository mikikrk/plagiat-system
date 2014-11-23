package CompareEngine;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ArticleComparison {
    private CompareEngine compareEngine;


    public ArticleComparison(CompareEngine compareEngine) {
        this.compareEngine = compareEngine;
    }

    public PlagiarismResult compare(String patternPath, String textPath) {
        try {
            return compareEngine.compare(patternPath, textPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
