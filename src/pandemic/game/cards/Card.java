/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.cards;

import java.awt.Image;
import java.awt.image.BufferedImage;
import pandemic.game.board.DiseaseType;

/**
 *
 * @author Pípa
 */
public class Card {

    public static class InfectionCard extends Card {

        public InfectionCard(BufferedImage bg, BufferedImage fg) {
            super(bg, fg);
        }

    }

    private String cityName;
    private DiseaseType diseaseType;
    private final BufferedImage bg;
    private final BufferedImage fg;

    public Card(BufferedImage bg, BufferedImage fg) {
        this.bg = bg;
        this.fg = fg;
    }

    public Image getBackground() {
        return bg;
    }

    public Image getForeground() {
        return fg;
    }
}
