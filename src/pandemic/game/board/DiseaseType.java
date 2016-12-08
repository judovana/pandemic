package pandemic.game.board;

import java.awt.Color;

/**
 * This class is unused - it appeared to be more practical to use direct colors
 * on implementation level.
 *
 * @author Pípa
 */
public enum DiseaseType {
    /*blue*/
    FLUE,
    /*yellow*/
    YELLOW_FEWER,
    /*black*/
    BLACK_COUGH,
    /*red*/
    RABIES;

    public Color toColor() {
        switch (this) {
            case FLUE:
                return Color.blue;
            case YELLOW_FEWER:
                return Color.yellow;
            case BLACK_COUGH:
                return Color.black;
            case RABIES:
                return Color.red;
            default:
                throw new RuntimeException("Unsuported color/disease" + this);
        }
    }

    public static DiseaseType fromColor(Color c) {
        if (c.equals(Color.blue)) {
            return FLUE;
        } else if (c.equals(Color.yellow)) {
            return YELLOW_FEWER;
        } else if (c.equals(Color.black)) {
            return BLACK_COUGH;
        } else if (c.equals(Color.red)) {
            return RABIES;
        } else {
            throw new RuntimeException("Unsuported color/disease" + c);
        }
    }

    public static void main(String... args) {
        System.out.println(BLACK_COUGH.toColor());
        System.out.println(DiseaseType.fromColor(Color.red));
    }
}
