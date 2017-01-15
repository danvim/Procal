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
    CONSTANT (0xff607D8B),
    GREEK_VARIABLE (0xff009688),
    LATIN_VARIABLE (0xff3F51B5),
    SPECIAL_VARIABLE (0xffC51162),
    MEMORY (0xff673AB7);

    int value;

    Color(int value) {
        this.value = value;
    }

    public int getColor() {
        return this.value;
    }
}