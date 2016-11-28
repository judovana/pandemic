/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.roles;

import java.awt.Graphics2D;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import pandemic.game.board.parts.Deck;
import pandemic.game.board.parts.tokens.Cities;
import pandemic.game.board.parts.tokens.City;
import pandemic.game.cards.Card;
import pandemic.game.cards.PlayerCard;
import pandemic.game.roles.implementations.Dispatcher;
import pandemic.game.roles.implementations.Medic;
import pandemic.game.roles.implementations.Researcher;
import pandemic.game.roles.implementations.Scientist;

/**
 *
 * @author Pípa
 */
public class Roles {

    private List<Role> roles = new ArrayList<>();
    private List<Point> homes = new ArrayList<>();
    private int currentPlayer;

    public Roles(String[] args) {
        for (String arg : args) {
            if (args.length > 4) {
                throw new IllegalArgumentException("Max number fo players is 4. Forced: " + arg.length());
            }
            switch (arg) {
                case "medic":
                    roles.add(new Medic());
                    break;
                case "scientist":
                    roles.add(new Scientist());
                    break;
                case "researcher":
                    roles.add(new Researcher());
                    break;
                case "dispatcher":
                    roles.add(new Dispatcher());
                    break;
                default:
                    throw new IllegalArgumentException("Invalid type of player: " + arg);
            }
        }
        try {
            initHomes();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void initHomes() throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResource("/pandemic/data/board/playerHomes").openStream(), StandardCharsets.UTF_8))) {
            while (true) {
                String s = br.readLine();
                if (s == null) {
                    break;
                }
                s = s.trim();
                if (s.startsWith("#")) {
                    continue;
                }
                String[] parts = s.split(";");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                homes.add(new Point(x, y));

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

    public void initPlayers(Cities cities, Deck deck) {
        int cards = 4;
        if (roles.size() >= 4) {
            cards = 2;
        }
        if (roles.size() == 3) {
            cards = 3;
        }
        int x = -1;
        for (Role role : roles) {
            x++;
            role.flyToTheCity(cities.getCityByName("atlanta"));
            for (int i = 0; i < cards; i++) {
                Card c = deck.getCard();
                Point p = homes.get(x);
                int xx = p.x + 20 * i;
                int yy = p.y + 20 * i;
                c.setCoords(xx, yy);
                role.setCardToHand((PlayerCard) c);
            }
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
        return getCurrentPlayer().selectHand(x, y);
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
