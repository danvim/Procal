package dcheungaa.procal.History;

import java.util.ArrayList;
import java.util.List;

import fx50.API.InputToken;

/**
 * Created by Bryan on 1/23/2017.
 */

public class HistoryHandler{

    public static List<List<String>> history = new ArrayList<>();
    public static List<String> subHistory = new ArrayList<>();
    public static int flag = 0;

    public static void appendHistory(List<InputToken> expressions){
        List<String> list = new ArrayList<>();
        for (InputToken token: expressions){
            list.add(token.display);
        }
        history.add(list);

    }

    public static void appendHistory(){
        List<String> list = new ArrayList<>();
        for (String text:subHistory)
            list.add(text);
        history.add(0,list);
        //subHistory.clear();
        flag = 1;
    }
    //public static ArrayList<List<InputToken>> getHistory(){return history;}

    //public Class HistoryButton extends Button{

    //}
}
