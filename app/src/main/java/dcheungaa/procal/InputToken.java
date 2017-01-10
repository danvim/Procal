package dcheungaa.procal;

public class InputToken {
    String lexable; // "sqrt(", for parsing
    String display; // "√(", for displaying
    Colors color = Colors.NORMAL;
    boolean spaced = true;

    public InputToken(String lexable, String display, boolean spaced, Colors color) {
        this.lexable = lexable;
        this.display = display;
        this.color = color;
        this.spaced = spaced;
    }

    public InputToken(String lexable, String display, boolean spaced) {
        this.lexable = lexable;
        this.display = display;
        this.spaced = spaced;
    }

    public InputToken(String lexable, String display, Colors color) {
        this.lexable = lexable;
        this.display = display;
        this.color = color;
    }

    public InputToken(String lexable, String display) {
        this.lexable = lexable;
        this.display = display;
    }
}


enum Colors {
    NORMAL, CONSTANT;
}