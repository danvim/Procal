package fx50.API;

public class InputToken {
    String lexable; // "sqrt(", for parsing
    String display; // "√(", for displaying
    Color color = Color.NORMAL;
    boolean spaced = true;

    public InputToken(String lexable, String display, boolean spaced, Color color) {
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

    public InputToken(String lexable, String display, Color color) {
        this.lexable = lexable;
        this.display = display;
        this.color = color;
    }

    public InputToken(String lexable, String display) {
        this.lexable = lexable;
        this.display = display;
    }
}

