package j2a.java;

import j2a.GraphicsCanvas;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

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

    public static j2a.BitmapImage read(InputStream resourceAsStream) throws IOException {
        BufferedImage bf = ImageIO.read(resourceAsStream);
        return new BitmapImage(bf);
    }

    private final BufferedImage back;

    private BitmapImage(int width, int height, int type) {
        back = new BufferedImage(width, height, type);
    }
    private BitmapImage(BufferedImage bf) {
        back = bf;
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
