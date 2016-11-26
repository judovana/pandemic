/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.cards;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import pandemic.game.board.parts.tokens.City;

/**
 *
 * @author Pípa
 */
public class Card {

    public static class InfectionCard extends Card {

        public InfectionCard(BufferedImage bg, City fg) {
            super(bg, fg);
        }

    }

    protected final City city;
    private final BufferedImage bg;
    private Point freeCoords = new Point(0, 0);

    public Card(BufferedImage bg, City c) {
        this.bg = bg;
        this.city = c;
    }

    public Image getBackground() {
        return bg;
    }

    public BufferedImage getForeground() {
        BufferedImage b = new BufferedImage(getBackground().getWidth(null), getBackground().getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = b.createGraphics();
        g2d.setColor(city.getColor());
        int column = b.getWidth() / 6;
        g2d.fillRect(b.getWidth() / 3 - column / 2, 0, column, b.getHeight());
        g2d.fillRect(b.getWidth() * 2 / 3 - column / 2, 0, column, b.getHeight());

        /*+"  " YAH! box is newer wide enough!*/
        int w = g2d.getFontMetrics().stringWidth(city.getName()+"  ");

        g2d.setColor(Color.WHITE);
        g2d.fillRect((b.getWidth() - w) / 2, (b.getHeight() - g2d.getFontMetrics().getHeight()) / 2 - g2d.getFontMetrics().getHeight(), w, (int)(g2d.getFontMetrics().getHeight()*1.5));
        g2d.setColor(Color.BLACK);
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD));
        g2d.drawString(city.getName(), (b.getWidth() - w) / 2, (b.getHeight() - g2d.getFontMetrics().getHeight()) / 2);
        return b;
    }
}
