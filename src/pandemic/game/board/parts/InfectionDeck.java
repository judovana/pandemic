/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.board.parts;

import java.awt.Point;
import pandemic.game.cards.Card;

/**
 *
 * @author Pípa
 */
public class InfectionDeck extends Deck {

    public InfectionDeck() {
        super("/pandemic/data/images/backgrounds/infectionCard.jpg");
        for (int i = 0; i < 40; i++) {
            Card c = new Card.InfectionCard(backgroud, null);
            cards.add(c);
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
