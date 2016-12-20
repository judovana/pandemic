package j2a;

/**
 *
 * @author jvanek
 */
public interface Point {

    public static Point newPoint(int x, int y) {
        return j2a.java.Point.newPoint(x, y);
    }

    public int getX();

    public int getY();

    public double distance(Point point);

    public Object getOriginal();

    @Override
    public boolean equals(Object o);

    @Override
    public int hashCode();
}
