package dobrowol.styloweplywanie.utils;

/**
 * Created by dobrowol on 09.04.17.
 */

public enum SwimmingStyles {
    FREESTYLE ("FREESTYLE"),
    BACKSTROKE ("BACKSTROKE"),
    BREASTSTROKE ("BREASTSTROKE"),
    BUTTERFLY("BUTTERFLY"),
    UNDERWATER("UNDERWATER"),
    OTHER("OTHER");

    private final String name;

    private SwimmingStyles(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
