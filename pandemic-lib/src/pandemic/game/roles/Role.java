/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.roles;

import j2a.GraphicsCanvas;
import j2a.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import pandemic.game.board.parts.tokens.City;
import pandemic.game.cards.Card;
import pandemic.game.cards.PlayerCard;

/**
 *
 * @author Pípa
 */
public abstract class Role {

    private String name;

    private List<PlayerCard> cardsInHand = new ArrayList<>(7);

    private City city;

    private static final Random placer = new Random();
    //Homes fot the players (cards in corners)
    private Point home;

    public abstract void doJob();

    public void cureDisease() {
    }

    public void flyToTheCity(City city) {
        if (city == null) {
            throw new RuntimeException("Moving to nullcity!");
        }
        System.out.println(this.getName() + " moved to " + city.getName());
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

    public void discardCard(Card c) {
        cardsInHand.remove(c);
    }
    //drawing the name of role next to the city the role is standing in. The coordinates are affected by random noise
    void paint(GraphicsCanvas g) {
        g.drawString(getName(), getCity().getCenter().getX() - 20 + placer.nextInt(40), city.getCenter().getY() - 20 + placer.nextInt(40));
    }

    public void setCardToHand(PlayerCard c) {
        this.cardsInHand.add(c);
    }

    void drawHand(GraphicsCanvas g) {
        for (PlayerCard c : cardsInHand) {
            c.drawPlaced(g);
        }
    }
    /** on the basis of coordinates Select the card from the player's hand
     * 
     * @param x the coordinate x
     * @param y the coordinate y
     * @return the card from the hand or null if no card found on given coordinates for this player
     */
    Card selectHand(int x, int y) {
        for(int i = cardsInHand.size()-1; i>=0; i--){
            Card c = cardsInHand.get(i);
            if (c.isFreeClicked(x, y)) {
                cardsInHand.remove(c);
                return c;
            }
        }
        return null;
    }

    public City getCity() {
        if (city == null) {
            throw new RuntimeException("Null city!");
        }
        return city;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public List<PlayerCard> getCardsInHand() {
        return cardsInHand;
    }
    /**
     * 
     * @return return the coodrdinates the players home on the board
     */
    public Point getHome() {
        return home;
    }
    /**
     * 
     * @param home sets the coodrdinates of the players home on the board
     */
    public void setHome(Point home) {
        this.home = home;
    }

   

}
