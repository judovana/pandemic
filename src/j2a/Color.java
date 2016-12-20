package j2a;

/**
 *
 * @author jvanek
 */
public interface Color {

    public static Color getBLUE() {
        return j2a.java.Color.getBLUE();
    }

    public static Color getYELLOW() {
        return j2a.java.Color.getYELLOW();
    }

    public static Color getBLACK() {
        return j2a.java.Color.getBLACK();
    }

    public static Color getRED() {
        return j2a.java.Color.getRED();
    }

    public static Color getCYAN() {
        return j2a.java.Color.getCYAN();
    }

    public static Color newColor(int r, int g, int b, int a) {
        return j2a.java.Color.newColor(r, g, b, a);
    }

    public static Color newColor(int r, int g, int b) {
        return j2a.java.Color.newColor(r, g, b);
    }

    public static Color getWHITE() {
        return j2a.java.Color.getWHITE();
    }

    public Object getOriginal();

    public int getRed();

    public int getGreen();

    public int getBlue();

    @Override
    public boolean equals(Object o);

    @Override
    public int hashCode();
}
