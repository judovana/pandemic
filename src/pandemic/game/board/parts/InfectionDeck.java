/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.board.parts;

import java.awt.Point;
import java.util.Collections;
import pandemic.game.board.parts.tokens.Cities;
import pandemic.game.board.parts.tokens.City;
import pandemic.game.cards.Card;

/**
 *
 * @author Pípa
 */
public class InfectionDeck extends Deck {
    /**
     * Loading the background and draws single cards
     * @param c list of all citie from which cards are generated
     */
    public InfectionDeck(Cities c) {
        super("/pandemic/data/images/backgrounds/infectionCard.jpg", c);
        for (City city : cities.getCities()) {
            Card card = new Card.InfectionCard(backgroud, city);
            cards.add(card);
        }
        Collections.shuffle(cards);
    }

    Point getCorner() {
        return new Point(714, 47);
    }

    Point getDiscarCorner() {
        return new Point(909, 47);
    }

    public Card returnUsedCards() {
        return null;
    }
    /**
     * Giving the used infection cards back to infection cards
     * @param infectionCard 
     */
    public void used(Card.InfectionCard infectionCard) {
        usedCards.add(infectionCard);
    }
}
