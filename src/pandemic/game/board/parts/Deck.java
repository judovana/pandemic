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
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import pandemic.game.board.parts.tokens.Cities;
import pandemic.game.board.parts.tokens.City;
import pandemic.game.cards.Card;
import pandemic.game.cards.PlayerCard;

/**
 *
 * @author Pípa
 */
public class Deck {

    //TODO
    //not only regular card city/color are implemented.
    //missing are events and infections from plyers' deck (this Deck)
    protected List<Card> cards = new ArrayList<>(48);
    protected List<Card> usedCards = new ArrayList<>(48);
    protected final BufferedImage backgroud;
    protected final Cities cities;

    public Deck(Cities c) {
        this("/pandemic/data/images/backgrounds/playerCard.jpg", c);
        for (City city : cities.getCities()) {
            Card card = new PlayerCard(backgroud, city);
            cards.add(card);
        }
        Collections.shuffle(cards);
    }

    Deck(String bg, Cities c) {
        this.cities = c;
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
        Card c = cards.remove(cards.size() - 1);
        return c;
    }

    public void shuffle() {
    }

    public void draw(Graphics2D g) {
        for (int i = 0; i < usedCards.size(); i++) {
            Card c = usedCards.get(i);
            g.drawImage(c.getForeground(), getDiscarCorner().x + i / 2, getDiscarCorner().y + i / 2, null);
        }
        for (int i = 0; i < cards.size(); i++) {
            Card c = cards.get(i);
            g.drawImage(c.getBackground(), getCorner().x + i / 2, getCorner().y + i / 2, null);
        }
    }

    public Card clicked(int x, int y) {
        if (x > getCorner().x
                && x < getCorner().getX() + backgroud.getWidth()
                && y > getCorner().y
                && y < getCorner().getY() + backgroud.getHeight()
                && cards.size() > 0) {
            Card c = getCard();
            c.setCoords(x, y);
            return c;
        }
        return null;
    }

    public void returnCard(Card card) {
        usedCards.add(card);
    }
}
