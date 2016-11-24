/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.cards;

import java.awt.image.BufferedImage;

/**
 *
 * @author Pípa
 */
public class PlayerCard extends Card {

    public PlayerCard(BufferedImage bg, BufferedImage fg) {
        super(bg, fg);
    }
    
    public static class EventCard extends PlayerCard {

        public EventCard(BufferedImage bg, BufferedImage fg) {
            super(bg, fg);
        }
    }

    public static class Epidemy extends PlayerCard {

        public Epidemy(BufferedImage bg, BufferedImage fg) {
            super(bg, fg);
        }
    }
}