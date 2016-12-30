/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.cards;

import j2a.BitmapImage;
import j2a.Factory;
import j2a.GraphicsCanvas;
import pandemic.game.board.parts.tokens.City;

/**
 *
 * @author Pípa
 */
public class PlayerCard extends Card {

    public PlayerCard(BitmapImage bg, City fg) {
        super(bg, fg);
    }

    public static class EventCard extends PlayerCard {

        public EventCard(BitmapImage bg, City fg) {
            super(bg, fg);
        }
    }

    public static class Epidemy extends PlayerCard {

        public void stealCity(Card c) {
            if (c != null) {
                this.city = c.getCity();
            }
        }

        public Epidemy(BitmapImage bg, City fg) {
            super(bg, fg);
        }

        @Override
        public BitmapImage getForeground() {
            BitmapImage b = j2a.Factory.BitmapImage.newBitmapImage(getBackground().getWidth(), getBackground().getHeight());
            GraphicsCanvas g2d = b.createGraphics();
            g2d.setColor(Factory.Color.getBLACK());
            g2d.drawRect(0, 0, b.getWidth() - 1, b.getHeight() - 1);
            if (city == null) {
                g2d.setColor(Factory.Color.getCYAN());
            } else {
                g2d.setColor(city.getColor());
            }
            for (int i = 0; i < getBackground().getWidth() / 6; i++) {
                g2d.drawLine(0 + i, 0, getBackground().getWidth() - i, getBackground().getHeight());
                g2d.drawLine(getBackground().getWidth() - i, 0, 0 + i, getBackground().getHeight());
            }
            if (city != null) {
                drawCityName(g2d);
            }
            return b;
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName() + " Epidemy! ";
        }

    }
}
