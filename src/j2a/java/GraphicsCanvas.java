package j2a.java;

import j2a.BitmapImage;
import j2a.Color;
import j2a.Font;
import j2a.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

/**
 *
 * @author jvanek
 */
public class GraphicsCanvas implements j2a.GraphicsCanvas{

    private final Graphics2D back;

    GraphicsCanvas(Graphics2D createGraphics) {
        back=createGraphics;
    }
    
    @Override
    public void setColor(Color c) {
        back.setColor((java.awt.Color) c.getOriginal());
        
    }

    @Override
    public void fillOval(int i, int i0, int i1, int i2) {
        back.fillOval(i, i0, i1, i2);
    }

    @Override
    public void drawImage(BitmapImage mainBoardImage, int x, int y, Object obs) {
        back.drawImage((Image)(mainBoardImage.getOrigianl()), x, y, (ImageObserver) obs);
    }

    @Override
    public void fillRect(int i, int i0, int i1, int radius) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FontMetrics getFontMetrics() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void drawRect(int i, int i0, int i1, int i2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void drawString(String string, int i, int i0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void drawLine(int x, int y, int i, int i0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Font getFont() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setFont(Font deriveFont) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
