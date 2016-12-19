package j2a;

/**
 *
 * @author jvanek
 */
public interface GraphicsCanvas {

    public void setColor(Color newColor);

    public void fillOval(int i, int i0, int i1, int i2);

    public void drawImage(BitmapImage mainBoardImage, int i, int i0, Object object);

    public void fillRect(int i, int i0, int i1, int radius);

    public FontMetrics getFontMetrics();

    public void drawRect(int i, int i0, int i1, int i2);

    public void drawString(String string, int i, int i0);

    public void drawLine(int x, int y, int i, int i0);

    public Font getFont();

    public void setFont(Font deriveFont);

    
}
