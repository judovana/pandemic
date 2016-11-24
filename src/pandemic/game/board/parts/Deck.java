/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.board.parts;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.imageio.ImageIO;
import pandemic.game.cards.Card;
import pandemic.game.cards.PlayerCard;

/**
 *
 * @author Pípa
 */
public class Deck {

    protected List<Card> cards = new ArrayList<>(40);
    protected List<Card> usedCards = new ArrayList<>(40);
    protected final BufferedImage backgroud;

    public Deck() {
        this("/pandemic/data/images/backgrounds/playerCard.jpg");
        for (int i = 0; i < 40; i++) {
            Card c = new PlayerCard(backgroud, null);
            cards.add(c);
        }
    }

    Deck(String bg) {
        try {
            backgroud = ImageIO.read(this.getClass().getResourceAsStream(bg));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    Point getCorner() {
        return new Point(714, 615);
    }

    Point getDiscarCorner() {
        return new Point(874, 615);
    }

    public Card getCard() {
        return null;
    }

    public void shuffle() {
    }

    public void draw(Graphics2D g) {
        for (int i = 0; i < usedCards.size(); i++) {
            Card c = usedCards.get(i);
            g.drawImage(c.getForeground(), getDiscarCorner().x + i, getDiscarCorner().y + i, null);
        }
        for (int i = 0; i < cards.size(); i++) {
            Card c = cards.get(i);
            g.drawImage(c.getBackground(), getCorner().x + i, getCorner().y + i, null);
        }
    }
}
