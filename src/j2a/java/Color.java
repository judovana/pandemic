package j2a.java;

/**
 *
 * @author jvanek
 */
public class Color implements j2a.Color {

    public static j2a.Color getBLUE() {
        return new Color(java.awt.Color.blue);
    }

    public static j2a.Color getYELLOW() {
        return new Color(java.awt.Color.yellow);
    }

    public static j2a.Color getBLACK() {
        return new Color(java.awt.Color.black);
    }

    public static j2a.Color getRED() {
        return new Color(java.awt.Color.red);
    }

    public static j2a.Color newColor(int r, int g, int b, int a) {
        return new Color(r, g, b, a);
    }

    public static j2a.Color getCYAN() {
        return new Color(java.awt.Color.cyan);
    }

    public static j2a.Color getWHITE() {
        return new Color(java.awt.Color.white);
    }

    public static j2a.Color newColor(int r, int g, int b) {
        return newColor(r, g, b, 255);
    }

    private final java.awt.Color back;

    public Color(java.awt.Color c) {
        this.back = c;
    }

    private Color(int r, int g, int b, int a) {
        back = new java.awt.Color(r, g, b, a);
    }

    @Override
    public Object getOriginal() {
        return back;
    }

    @Override
    public int getRed() {
        return back.getRed();
    }

    @Override
    public int getGreen() {
        return back.getGreen();
    }

    @Override
    public int getBlue() {
        return back.getBlue();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof j2a.Color)) {
            return false;
        }
        return this.getOriginal().equals(((j2a.Color)o).getOriginal());
    }

    @Override
    public int hashCode(){
        return back.hashCode();
    }

}
