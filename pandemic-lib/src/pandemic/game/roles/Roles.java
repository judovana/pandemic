/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.roles;

import j2a.GraphicsCanvas;
import j2a.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pandemic.game.board.parts.Deck;
import pandemic.game.board.parts.tokens.Cities;
import pandemic.game.board.parts.tokens.City;
import pandemic.game.cards.Card;
import pandemic.game.cards.PlayerCard;
import pandemic.game.roles.implementations.ContingencyPlanner;
import pandemic.game.roles.implementations.Dispatcher;
import pandemic.game.roles.implementations.Medic;
import pandemic.game.roles.implementations.OperationExpert;
import pandemic.game.roles.implementations.QuarantineSpecialist;
import pandemic.game.roles.implementations.Researcher;
import pandemic.game.roles.implementations.Scientist;

/**
 *
 * @author Pípa
 */
public class Roles {

    private List<Role> roles = new ArrayList<>();
    private int currentPlayer;

    public static final String[] knownRoles = new String[]{
        "medic",
        "scientist",
        "researcher",
        "dispatcher",
        "hygienist",
        "analytic",
        "operator"
    };
    /**
     * List of all existing roles
     */
    public static final List<String> knownRolesList = new ArrayList<>(Arrays.asList(Roles.knownRoles));
    /**
     * Helping structure mapping the name of the role to the implementation(instnce of class)
     */
    public static final Map<String, Role> rolesInstances = new HashMap<>();
    //filling the map with values
    static {
        rolesInstances.put(knownRoles[0], new Medic());
        rolesInstances.put(knownRoles[1], new Scientist());
        rolesInstances.put(knownRoles[2], new Researcher());
        rolesInstances.put(knownRoles[3], new Dispatcher());
        rolesInstances.put(knownRoles[4], new QuarantineSpecialist());
        rolesInstances.put(knownRoles[5], new ContingencyPlanner());
        rolesInstances.put(knownRoles[6], new OperationExpert());
    }

    ;
    /**
     * Creates new instance of roles and checks the game conditions (min max number of players and availability of players)
     * @param args names of the roles as deliverd from the command line
     */
    public Roles(String[] args) {
        if (args.length > 4) {
            throw new IllegalArgumentException("Max number fo players is 4. Forced: " + args.length);
        }
        if (args.length <= 0) {
            throw new IllegalArgumentException("At least one palyer expected. Forced: " + args.length);
        }
        for (String arg : args) {
            if (knownRolesList.contains(arg)) {
                roles.add(rolesInstances.get(arg));
            } else {
                throw new IllegalArgumentException("Invalid type of player: " + arg);
            }
        }
        try {
            initHomes();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    //loading the coordinates of players homes
    private void initHomes() throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResource("/pandemic/data/board/playerHomes").openStream(), StandardCharsets.UTF_8))) {
            int home = -1;
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
                home++;
                if (home >= roles.size()) {
                    break;
                }
                roles.get(home).setHome(j2a.Factory.Point.newPoint(x, y));

            }
        }
    }

    public Role setNextPlayer() {
        getCurrentPlayer().resetActionCounter();
        currentPlayer++;
        if (currentPlayer >= roles.size()) {
            currentPlayer = 0;
        }
        getCurrentPlayer().resetActionCounter();
        return getCurrentPlayer();
    }

    public void drawPlayers(GraphicsCanvas g) {
        for (Role role : roles) {
            role.paint(g);
        }
    }
    /**
     * prepare player for game : select (specified) number of cards from deck and set various coords
     * @param cities to find coordinates of home city (Atlanta)
     * @param deck take cards from
     */
    public void initPlayers(Cities cities, Deck deck) {
        int cards = 4;
        //set the number of cards according to a number of players
        if (roles.size() >= 4) {
            cards = 2;
        }
        if (roles.size() == 3) {
            cards = 3;
        }
        for (Role role : roles) {
            //moving all players to the default city (Atlanta)
            role.flyToTheCity(cities.getCityByName("atlanta"));
            role.resetActionCounter();
            for (int i = 0; i < cards; i++) {
                Card c = deck.getCard();
                Point p = role.getHome();
                int xx = p.getX() + 20 * i;
                int yy = p.getY() + 20 * i;
                //set coordinates for cards (cards in corners)
                c.setCoords(xx, yy);
                //gives card to the players hand
                role.setCardToHand((PlayerCard) c);
            }
        }

        currentPlayer = 0;
    }

    public Role getCurrentPlayer() {
        return roles.get(currentPlayer);
    }

    public void drawPlayersHands(GraphicsCanvas g) {
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

    public int cardsToCure() {
        if (getCurrentPlayer() instanceof Scientist){
            return 4;
        }
        return 5;
    }

   
}
