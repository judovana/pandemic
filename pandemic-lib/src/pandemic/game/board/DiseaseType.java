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
                return j2a.Factory.Color.getBLUE();
            case YELLOW_FEWER:
                return j2a.Factory.Color.getYELLOW();
            case BLACK_COUGH:
                return j2a.Factory.Color.getBLACK();
            case RABIES:
                return j2a.Factory.Color.getRED();
            default:
                throw new RuntimeException("Unsuported color/disease" + this);
        }
    }

    public static DiseaseType fromColor(Color c) {
        if (c.equals(j2a.Factory.Color.getBLUE())) {
            return FLUE;
        } else if (c.equals(j2a.Factory.Color.getYELLOW())) {
            return YELLOW_FEWER;
        } else if (c.equals(j2a.Factory.Color.getBLACK())) {
            return BLACK_COUGH;
        } else if (c.equals(j2a.Factory.Color.getRED())) {
            return RABIES;
        } else {
            throw new RuntimeException("Unsuported color/disease" + c);
        }
    }

    public static void main(String... args) {
        System.out.println(BLACK_COUGH.toColor());
        System.out.println(DiseaseType.fromColor(j2a.Factory.Color.getRED()));
    }
}
