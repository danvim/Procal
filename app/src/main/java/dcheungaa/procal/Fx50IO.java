package dcheungaa.procal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import fx50.Fx50ParseResult;
import fx50.IO;
import fx50.IOMessage;

/**
 * Created by Daniel on 19/1/2017.
 */

public class Fx50IO extends IO {
    public static boolean isRequestingInput = false;
    public static final List<String> inputHolder = new ArrayList<>();
    private ExecutorService pool = Executors.newFixedThreadPool(1);

    @Override
    public String getInput(IOMessage ioMessage) {
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("Requesting input...");
                synchronized (inputHolder) {
                    isRequestingInput = true;
                    try {
                        while (inputHolder.isEmpty()) {
                            inputHolder.wait();
                        }
                    } catch (InterruptedException e) {
                        System.out.println("input interrupted");
                    }
                    isRequestingInput = false;
                    return inputHolder.remove(0);
                }
            }
        });
        Thread t = new Thread(futureTask);
        t.start();
        String returnInput;
        try {
            returnInput = futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(System.out);
            returnInput = "Unexpected exception";
        }
        return returnInput;
    }

    @Override
    public void printOutput(String output) {
        //?, display
        super.printOutput(output);
    }

    @Override
    public void doneEvaluating(Fx50ParseResult parseResult) {
        try {
            if (parseResult.getErrorString() != null)
                throw new Exception(parseResult.getErrorString());
            MainActivity.resultDisplay.setText(parseResult.getStringResult());
            System.out.println(parseResult.getStringResult());
            System.out.println(parseResult.getBigDecimalResult());
        } catch (Exception e) {
            MainActivity.matrixDisplay.setText(e.getMessage());
            e.printStackTrace(System.out);
        }
    }
}
