/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.board.parts;

import j2a.GraphicsCanvas;
import j2a.Point;
import j2a.BitmapImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    protected final BitmapImage backgroud;
    protected final Cities cities;

    public Deck(Cities c) {
        this("/pandemic/data/images/backgrounds/playerCard.jpg", c);
        for (City city : cities.getCities()) {
            Card card = new PlayerCard(backgroud, city);
            cards.add(card);
        }
        Collections.shuffle(cards);
    }
    /**
     * Loading the background and remembering the cities
     * @param bg path to the back ground image
     * @param c list of all cities that are generated
     */
    Deck(String bg, Cities c) {
        this.cities = c;
        try {
            backgroud = BitmapImage.read(this.getClass().getResourceAsStream(bg));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    Point getCorner() {
        return Point.newPoint(714, 615);
    }

    Point getDiscarCorner() {
        return Point.newPoint(874, 615);
    }
    /**
     * removes cards to discard corner
     * @return the top card from the deck
     */
    public Card getCard() {
        Card c = cards.remove(cards.size() - 1);
        return c;
    }

    public void shuffle() {
    }
    /**
     * Drawing the used cards and not used cards
     * @param g graphic to draw to
     */
    public void draw(GraphicsCanvas g) {
        for (int i = 0; i < usedCards.size(); i++) {
            Card c = usedCards.get(i);
            g.drawImage(c.getForeground(), getDiscarCorner().getX() + i / 2, getDiscarCorner().getY() + i / 2, null);
        }
        for (int i = 0; i < cards.size(); i++) {
            Card c = cards.get(i);
            g.drawImage(c.getBackground(), getCorner().getX() + i / 2, getCorner().getY() + i / 2, null);
        }
    }
    /**
     * When clicking to the area of player card, the card on the top is selected
     * @param x coordinate recalculeted to the size of an image
     * @param y coordinate recalculeted to the size of an image
     * @return if the click on the deck it gives a card else it returns null
     */
    public Card clicked(int x, int y) {
        if (x > getCorner().getX()
                && x < getCorner().getX() + backgroud.getWidth()
                && y > getCorner().getY()
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
