/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.roles;

import java.awt.Graphics2D;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import pandemic.game.board.parts.tokens.City;
import pandemic.game.board.parts.Deck;
import pandemic.game.cards.Card;
import pandemic.game.cards.PlayerCard;

/**
 *
 * @author Pípa
 */
public abstract class Role {

    private String name;

    private Set<PlayerCard> cardsInHand = new HashSet<>(7);

    private City city;

    private static final Random placer = new Random();

    public abstract void doJob();

    public void moveToTheCity(City city) {
    }

    public void cureDisease() {
    }

    public void flyToTheCity(City city) {
        this.city = city;
    }

    public void makeCure() {
    }

    public void changeCards(Role role) {
    }

    public void buildStation() {
    }

    public void passTheAction() {
    }

    public void drawCards(Deck deck) {
    }

    public void discardCard() {
    }

    void paint(Graphics2D g) {
        g.drawString(this.getClass().getSimpleName(), city.getCenter().x - 20 + placer.nextInt(40), city.getCenter().y - 20 + placer.nextInt(40));
    }

    public void setCardToHand(PlayerCard c) {
        this.cardsInHand.add(c);
    }

    void drawHand(Graphics2D g) {
        for (PlayerCard c : cardsInHand) {
            c.drawPlaced(g);
        }
    }

    Card selectHand(int x, int y) {
        for (Card c : cardsInHand) {
            if (c.isFreeClicked(x, y)) {
                cardsInHand.remove(c);
                return c;
            }
        }
        return null;
    }

}
