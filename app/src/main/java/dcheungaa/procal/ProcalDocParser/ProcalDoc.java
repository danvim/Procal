package dcheungaa.procal.ProcalDocParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProcalDoc {
    public String title = "";
    public String desc = "";
    public String returnDesc = "";
    public boolean isDraft = false;
    public List<String> sampleIn = new ArrayList<>();
    public List<String> sampleOut = new ArrayList<>();
    public List<Param> params = new ArrayList<>();

    public String generateHTMLDoc() {
        String HTMLStringHolder = "";
        String headingTag = "h3";

        HTMLStringHolder += makeElem("p", desc);
        if (!params.isEmpty()) {
            ArrayList<ArrayList<String>> paramsArray = new ArrayList<>();
            for (Param param: params) {
                ArrayList<String> paramArray = new ArrayList<>();
                paramArray.add(param.variableName);
                paramArray.add(param.desc);
                paramsArray.add(paramArray);
            }
            HTMLStringHolder += makeElem(headingTag, "Parameters:");
            HTMLStringHolder += makeTable(
                    new ArrayList<String>(Arrays.asList("Variable", "Description")),
                    paramsArray
            );
        }
        if (!sampleIn.isEmpty()) {
            ArrayList<ArrayList<String>> samplesArray = new ArrayList<>();
            for (int i = 0; i < sampleIn.size(); i++) {
                ArrayList<String> sampleArray = new ArrayList<>();
                sampleArray.add(sampleIn.get(i));
                sampleArray.add(sampleOut.get(i));
                samplesArray.add(sampleArray);
            }
            HTMLStringHolder += makeElem(headingTag, "Examples:");
            HTMLStringHolder += makeTable(
                    new ArrayList<String>(Arrays.asList("Input", "Output")),
                    samplesArray
            );
        }
        if (!returnDesc.equals("")) {
            HTMLStringHolder += makeElem(headingTag, "Returns:");
            HTMLStringHolder += makeElem("p", returnDesc);
        }
        HTMLStringHolder += "<style>body {padding: 4px 16px} table {width: 100%; border-collapse: collapse} th {text-align: left; border-bottom: 2px solid #ccc} td {border-bottom:1px solid #ccc} th, td {padding: 16px 4px}</style>";
        return HTMLStringHolder;
    }

    private String makeTable (List<String> headings,List<ArrayList<String>> rowContents) {
        return makeElem("table", makeElem(
                "thead", makeElem(
                        "tr", (new Object() {
                            @Override public String toString() {
                                String temp = "";
                                for (String heading : headings) {
                                    temp += makeElem("th", heading);
                                }
                                return temp;
                            }
                        }).toString()
                )) + makeElem(
                "tbody", (new Object() {
                    @Override public String toString() {
                        String temp = "";
                        for (ArrayList<String> rowContent : rowContents) {
                            String temp2 = "";
                            for (String cellContent : rowContent) {
                                temp += makeElem("td", cellContent);
                            }
                            temp += makeElem("tr", temp2);
                        }
                        return temp;
                    }
                }).toString()
        ));
    }

    private String makeElem (String tag, String content) {
        return "<" + tag + ">" + content + "</" + tag + ">";
    }
}
