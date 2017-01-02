package dcheungaa.procal;

/**
 * Created by Daniel on 1/1/2017.
 */

public class JsonHelper {
    public class KeypadRows {
        Key[][] rows;
    }

    public class Key {
        String key;
        String text;
        String style;
        Key shift;
        Key alpha;
    }
}
