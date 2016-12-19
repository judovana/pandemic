/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.cards;

import j2a.BitmapImage;
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
    }
}
