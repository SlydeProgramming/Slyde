package slyde.utils;

public class Indent {
    private int lvl = -3;
    private String indentType = "  ";

    public Indent() {
        lvl = 0;
    }

    public Indent(String indentType) {
        lvl = 0;
        this.indentType = indentType;
    }

    public String get() {
        String str = "";
        for (int i = 0; i < lvl; i++) {
            str += indentType;
        }
        return str;
    }

    public String up() {
        lvl++;
        return get();
    }

    public String down() {
        lvl--;
        return get();
    }

    @Override
    public String toString() {
        return get();
    }
}