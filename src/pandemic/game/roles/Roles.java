/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.roles;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import pandemic.game.board.parts.tokens.Cities;
import pandemic.game.board.parts.tokens.City;
import pandemic.game.cards.Card;
import pandemic.game.roles.implementations.Medic;
import pandemic.game.roles.implementations.Scientist;

/**
 *
 * @author Pípa
 */
public class Roles {

    private List<Role> roles = new ArrayList<>();
    private int currentPlayer;

    public Roles(String[] args) {
        for (String arg : args) {
            switch (arg) {
                case "medic":
                    roles.add(new Medic());
                    break;
                case "scientist":
                    roles.add(new Scientist());
                    break;
                default:
                    throw new IllegalArgumentException("Invalid type of player: " + arg);
            }
        }
    }

    public Role setNextPlayer() {
        currentPlayer++;
        if (currentPlayer >= roles.size()) {
            currentPlayer = 0;
        }
        return getCurrentPlayer();
    }

    public void drawPlayers(Graphics2D g) {
        for (Role role : roles) {
            role.paint(g);
        }
    }

    public void initPlayers(Cities cities) {
        for (Role role : roles) {
            role.flyToTheCity(cities.getCityByName("atlanta"));
        }
        currentPlayer = 0;
    }

    public Role getCurrentPlayer() {
        return roles.get(currentPlayer);
    }

    public void drawPlayersHands(Graphics2D g) {
        for (Role role : roles) {
            role.drawHand(g);
        }
    }

    public Card selectPlayersHands(int x, int y) {
        for (Role role : roles) {
            return role.selectHand(x, y);
        }
        return null;
    }

    public List<Role> getPlayersInCity(City city) {
        List<Role> r = new ArrayList<>(roles.size());
        for (Role role : roles) {
            if (role.getCity().equals(city)) {
                r.add(role);
            }
        }
        return r;
    }

}
