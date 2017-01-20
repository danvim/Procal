package dcheungaa.procal;

import fx50.IO;

/**
 * Created by Daniel on 19/1/2017.
 */

public class Fx50IO extends IO {
    @Override
    public String getInput() {
        return InputHandler.getLexableString();
    }

    @Override
    public void printOutput(String output) {
        //?, display
        super.printOutput(output);
    }
}
