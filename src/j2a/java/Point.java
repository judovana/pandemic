package j2a.java;

import java.awt.geom.Point2D;

/**
 *
 * @author jvanek
 */
public class Point implements j2a.Point{

    private final java.awt.Point back;

    public static j2a.Point newPoint(int x, int y) {
        return new Point(x, y);
    }

    public Point(java.awt.Point back) {
        this.back = back;
    }

    public Point(int x, int y) {
        this.back = new java.awt.Point(x, x);
    }

    @Override
    public int getX() {
        return back.x;
    }

    @Override
    public int getY() {
        return back.y;
    }

    @Override
    public double distance(j2a.Point point) {
        return  back.distance((Point2D) point.getOriginal());
    }

    @Override
    public Object getOriginal() {
        return back;
    }

}
