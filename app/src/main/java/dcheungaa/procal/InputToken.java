package dcheungaa.procal;

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


enum Color {
    NORMAL (0xff000000),
    CONSTANT (0xff7B1FA2),
    GREEK_VARIABLE (0xff00796B),
    LATIN_VARIABLE (0xff0288D1),
    MEMORY (0xffC2185B);

    int value;

    Color(int value) {
        this.value = value;
    }

    public int getColor() {
        return this.value;
    }
}