/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.cards;
import j2a.Color;
import j2a.Font;
import j2a.GraphicsCanvas;
import j2a.BitmapImage;
import j2a.Point;
import pandemic.game.board.parts.tokens.City;

/**
 *
 * @author Pípa
 */
public class Card {
    
    public static class InfectionCard extends Card {
        /**
         * creating new card from background image and represented city
         * @param bg background image
         * @param fg city affecting the play of this card and affecting the rendering of the foreground.
         */
        public InfectionCard(BitmapImage bg, City fg) {
            super(bg, fg);
        }
        
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " for " + city.getName();
    }
    
    protected final City city;
    private final BitmapImage bg;
    private Point freeCoords = null;
    
    /**
     * for all cards exception infection cards
     * @param bg background image
     * @param c city
     */
    public Card(BitmapImage bg, City c) {
        this.bg = bg;
        this.city = c;
    }
    
    public BitmapImage getBackground() {
        return bg;
    }
    /**
     * Drawing foreground of cards
     * @return rendered background
     */
    public BitmapImage getForeground() {
        BitmapImage b = BitmapImage.newBitmapImage(getBackground().getWidth(), getBackground().getHeight());
        GraphicsCanvas g2d = b.createGraphics();
        g2d.setColor(city.getColor());
        int column = b.getWidth() / 6;
        g2d.fillRect(b.getWidth() / 3 - column / 2, 0, column, b.getHeight());
        g2d.fillRect(b.getWidth() * 2 / 3 - column / 2, 0, column, b.getHeight());
        g2d.drawRect(0, 0, b.getWidth() - 1, b.getHeight() - 1);
        /*+"  " YAH! box is newer wide enough!*/
        int w = g2d.getFontMetrics().stringWidth(city.getName() + "  ");
        
        g2d.setColor(Color.getWHITE());
        g2d.fillRect((b.getWidth() - w) / 2, (b.getHeight() - g2d.getFontMetrics().getHeight()) / 2 - g2d.getFontMetrics().getHeight(), w, (int) (g2d.getFontMetrics().getHeight() * 1.5));
        g2d.setColor(Color.getBLACK());
        g2d.setFont(g2d.getFont().deriveFont(Font.getBOLD()));
        g2d.drawString(city.getName(), (b.getWidth() - w) / 2, (b.getHeight() - g2d.getFontMetrics().getHeight()) / 2);
        return b;
    }
    
    public void drawPlaced(GraphicsCanvas g) {
        g.drawImage(getForeground(), freeCoords.getX(), freeCoords.getY(), null);
    }
    
    public void setCoords(int x, int y) {
        setCoords(Point.newPoint(x, y));
    }

    public void setCoords(Point p) {
        this.freeCoords = p;
    }
    /**
     * if the card isnt in the deck its finding out wthether the card was clicked or not
     * @param x the coordinate x
     * @param y the coordinate y
     * @return 
     */
    public boolean isFreeClicked(int x, int y) {
        if (freeCoords == null) {
            return false;
        }
        return (x > freeCoords.getX()
                && x < freeCoords.getX() + getBackground().getWidth()
                && y > freeCoords.getY()
                && y < freeCoords.getX() + getBackground().getHeight());
    }
    
    public City getCity() {
        return city;
    }
    
}
