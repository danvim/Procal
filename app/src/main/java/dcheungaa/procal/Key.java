package dcheungaa.procal;

/**
 * The instance of this class stores the information of a key on the keypad. This is generally generated from the keypad json description file.
 */
public class Key {
    String id;
    String text;
    String style;
    String lexable;
    String display;
    String color;
    Boolean spaced;
    Key shift;
    Key alpha;
    Key hyp;
}
