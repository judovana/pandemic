package j2a.java;

import j2a.GraphicsCanvas;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 *
 * @author jvanek
 */
public class BitmapImage implements j2a.BitmapImage {

    public static int getTYPE_4BYTE_ABGR() {
        return BufferedImage.TYPE_4BYTE_ABGR;
    }

    public static j2a.BitmapImage newBitmapImage(int width, int height, int type) {
        return new BitmapImage(width, height, type);
    }

    public static int getTYPE_INT_ARGB() {
        return BufferedImage.TYPE_INT_ARGB;
    }

    public static j2a.BitmapImage read(InputStream resourceAsStream) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private final BufferedImage back;

    private BitmapImage(int width, int height, int type) {
        back = new BufferedImage(width, height, type);
    }

    @Override
    public int getWidth() {
        return back.getWidth();
    }

    @Override
    public int getHeight() {
        return back.getHeight();
    }

    @Override
    public GraphicsCanvas createGraphics() {
        return new j2a.java.GraphicsCanvas(back.createGraphics());
    }

    @Override
    public Object getOrigianl() {
        return back;
    }

}
