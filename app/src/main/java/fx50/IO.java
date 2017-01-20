package fx50;

import java.util.Scanner;

public class IO {

    public String getInput() {
        return (new Scanner(System.in)).nextLine().trim();
    }

    public void printOutput(String output) {
        System.out.println(output);
    }
}
