package j2a;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author jvanek
 */
public interface BitmapImage {

    public static BitmapImage newBitmapImage(int width, int height, int type) {
        return j2a.java.BitmapImage.newBitmapImage(width, height, type);
    }

    public static int getTYPE_4BYTE_ABGR() {
        return j2a.java.BitmapImage.getTYPE_4BYTE_ABGR();
    }

    public static int getTYPE_INT_ARGB(){
        return j2a.java.BitmapImage.getTYPE_INT_ARGB();
    }

    public static BitmapImage read(InputStream resourceAsStream) throws IOException{
        return j2a.java.BitmapImage.read(resourceAsStream);
    }

    public int getWidth();

    public int getHeight();

    public GraphicsCanvas createGraphics();

    public Object getOrigianl();

}
