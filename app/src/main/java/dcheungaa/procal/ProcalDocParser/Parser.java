package dcheungaa.procal.ProcalDocParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static Pattern docPattern = Pattern.compile("/\\*\\* *\\n([\\s\\S]*?)\\n *\\*/");
    private static Pattern docLinePattern = Pattern.compile(" *\\*( *[\\S]+?(?: \\S+)*?)$");
    private static Pattern docLineAtPattern = Pattern.compile("(@[\\w]+) +(\\w+) +([\\s\\S]+)$");

    public static ProcalDoc extractProcalDoc(String input) {
        Matcher docMatcher = docPattern.matcher(input);
        ProcalDoc procalDoc = new ProcalDoc();
        if (docMatcher.find()) {
            String docContent = docMatcher.group(1);
            String[] docLines = docContent.split("\\n");
            List<String> usefulDocLines = new ArrayList<>();
            for (String docLine : docLines) {
                Matcher docLineMatcher = docLinePattern.matcher(docLine);
                if (docLineMatcher.find()) {
                    usefulDocLines.add(docLineMatcher.group(1));
                }
            }
            List<String> generalLines = new ArrayList<>();
            List<List<String>> atLines = new ArrayList<>();
            for (String line : usefulDocLines) {
                //Check whether it is a @ statement
                Matcher docLineAtMatcher = docLineAtPattern.matcher(line);
                if (docLineAtMatcher.find())
                    atLines.add(new ArrayList<>(Arrays.asList(
                            docLineAtMatcher.group(1),
                            docLineAtMatcher.group(2),
                            docLineAtMatcher.group(3)
                    )));
                else
                    generalLines.add(line);
            }
            procalDoc.title = generalLines.remove(0);
            for (String generalLine : generalLines) {
                procalDoc.desc += generalLine + "\n";
            }
            for (List<String> atLine : atLines) {
                if (atLine.get(0).equals("@param")) {
                    Param param = new Param();
                    param.variableName = atLine.get(1);
                    param.desc = atLine.get(2);
                    procalDoc.params.add(param);
                }
            }
        } else
            System.out.println("Parser cannot find a doc from the input");
        return procalDoc;
    }
}
