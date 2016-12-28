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

        public Epidemy(BitmapImage bg, City fg) {
            super(bg, fg);
        }

        @Override
        public BitmapImage getForeground() {
            BitmapImage b = j2a.Factory.BitmapImage.newBitmapImage(getBackground().getWidth(), getBackground().getHeight());
            GraphicsCanvas g2d = b.createGraphics();
            g2d.setColor(Factory.Color.getBLACK());
            g2d.drawRect(0, 0, b.getWidth() - 1, b.getHeight() - 1);
            g2d.setColor(Factory.Color.getCYAN());
            g2d.drawLine(0, 0, getBackground().getWidth(), getBackground().getHeight());
            g2d.drawLine(getBackground().getWidth(), 0, 0, getBackground().getHeight());
            return b;
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName() + " Epidemy! ";
        }

    }
}
