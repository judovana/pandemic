/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.board.parts;

import java.awt.Point;
import pandemic.game.board.parts.tokens.Cities;
import pandemic.game.board.parts.tokens.City;
import pandemic.game.cards.Card;

/**
 *
 * @author Pípa
 */
public class InfectionDeck extends Deck {

    public InfectionDeck(Cities c) {
        super("/pandemic/data/images/backgrounds/infectionCard.jpg", c);
        for (City city : cities.getCities()) {
            Card card = new Card.InfectionCard(backgroud, city);
            cards.add(card);
        }
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
}
