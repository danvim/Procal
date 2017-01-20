package fx50.API;

public enum Color {
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
