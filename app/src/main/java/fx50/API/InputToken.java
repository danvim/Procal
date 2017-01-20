package fx50.API;

public class InputToken {
    public String lexable; // "sqrt(", for parsing
    public String display; // "√(", for displaying
    public Color color = Color.NORMAL;
    public boolean spaced = true;

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

