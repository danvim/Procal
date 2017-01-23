package fx50;

import java.util.Scanner;

public class IO {

    public String getInput(IOMessage ioMessage) {
        if (ioMessage.type.equals("inputPrompt")) {
            printOutput(ioMessage.msg);
        }
        return (new Scanner(System.in)).nextLine().trim();
    }

    public void printOutput(String output) {
        System.out.println(output);
    }

    public void doneEvaluating(Fx50ParseResult parseResult) {
        if (parseResult.getErrorString() == null)
            System.out.println("=" + parseResult.getStringResult());
        else
            System.out.println("Error: " + parseResult.getErrorString());
    }
}
