package dcheungaa.procal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dcheungaa.procal.Func.FuncItem;
import fx50.API.Color;
import fx50.API.InputToken;

import static dcheungaa.procal.InputHandler.getLexableString;

public class FileHandler {

    public static List<InputToken> getInputTokensFromContent (FuncItem funcItem) {
        boolean isDraft = funcItem.getProcalDoc().isDraft;
        List<InputToken> inputExpression = new ArrayList<>();
        String content = funcItem.getProcalContentString();
        if (!isDraft) {
            System.out.println("Interpreting a procal file...");
            inputExpression = MainActivity.fx50Parser.parseToInputExpression(content);
            if (inputExpression == null)
                return new ArrayList<>();
            return inputExpression;
        } else {
            System.out.println("Interpreting a draft procal file...");
            content = content.replaceAll("/\\*\\*[\\s\\S]*?\\*/", "");
            System.out.println(content);
            Scanner scanner = new Scanner(content);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.trim().equals("")) {
                    String[] desc = line.trim().split("\\|");
                    System.out.println(line);
                    //lexable display spaced color
                    inputExpression.add(new InputToken(desc[0], desc[1], desc[2].equals("true"), Color.valueOf(desc[3])));
                }
            }
            return inputExpression;
        }
    }

    public static SaveContent makeSaveContent (List<InputToken> inputExpression) {
        String lexableString = getLexableString(inputExpression);
        SaveContent saveContent = new SaveContent();
        boolean isParsable = false;
        if(MainActivity.fx50Parser.parseToInputExpression(lexableString) == null) {
            //parsing error
            String draftString = "";
            for (InputToken token : inputExpression) {
                draftString += token.lexable + "|" +
                        token.display + "|" +
                        Boolean.toString(token.spaced) + "|" +
                        token.color.name() + "\n";
            }
            saveContent.content = draftString;
            saveContent.isDraft = true;
        } else {
            saveContent.content = lexableString;
            saveContent.isDraft = false;
        }
        return saveContent;
    }
}
