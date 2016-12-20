package pandemic.game.board;

import j2a.Color;


/**
 * This class is unused - it appeared to be more practical to use direct colors
 * on implementation level.
 * 
 * Now, after splitting to lib and game for java and android implementation, I regret very much...
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
                return Color.getBLUE();
            case YELLOW_FEWER:
                return Color.getYELLOW();
            case BLACK_COUGH:
                return Color.getBLACK();
            case RABIES:
                return Color.getRED();
            default:
                throw new RuntimeException("Unsuported color/disease" + this);
        }
    }

    public static DiseaseType fromColor(Color c) {
        if (c.equals(Color.getBLUE())) {
            return FLUE;
        } else if (c.equals(Color.getYELLOW())) {
            return YELLOW_FEWER;
        } else if (c.equals(Color.getBLACK())) {
            return BLACK_COUGH;
        } else if (c.equals(Color.getRED())) {
            return RABIES;
        } else {
            throw new RuntimeException("Unsuported color/disease" + c);
        }
    }

    public static void main(String... args) {
        System.out.println(BLACK_COUGH.toColor());
        System.out.println(DiseaseType.fromColor(Color.getRED()));
    }
}
