/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.board;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import javax.imageio.ImageIO;
import pandemic.game.OtherActions;
import pandemic.game.board.parts.Drugs;
import pandemic.game.board.parts.Deck;
import pandemic.game.board.parts.InfecetionRate;
import pandemic.game.board.parts.InfectionDeck;
import pandemic.game.board.parts.Outbreaks;
import pandemic.game.board.parts.tokens.Cities;
import pandemic.game.board.parts.tokens.City;
import pandemic.game.cards.Card;
import pandemic.game.cards.PlayerCard;
import pandemic.game.roles.Roles;

/**
 *
 * @author Pípa
 */
public class Board extends Observable {

    private Roles roles;
    private final Cities cities;
    private final Outbreaks outbreaks;
    private final InfecetionRate infectionRate;
    private final Deck deck;
    private final InfectionDeck infDeck;
    private BufferedImage currentBoard;
    private BufferedImage mainBoardImage;
    private Object selected;
    private final Drugs cures;

    public Board(Roles roles) throws IOException {
        this.roles = roles;
        cities = new Cities();
        outbreaks = new Outbreaks();
        deck = new Deck(cities);
        infDeck = new InfectionDeck(cities);
        infectionRate = new InfecetionRate();
        cures = new Drugs();
        roles.initPlayers(cities);
        loadResources();
        drawBoard();
        this.notifyObservers();
    }

    private void drawBoard() {
        currentBoard = new BufferedImage(mainBoardImage.getWidth(), mainBoardImage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        currentBoard.createGraphics().drawImage(mainBoardImage, 0, 0, null);
        cures.draw(currentBoard.createGraphics());
        cities.drawStations(currentBoard.createGraphics());
        roles.drawPlayers(currentBoard.createGraphics());
        if (selected instanceof City) {
            ((City) selected).draw(currentBoard.createGraphics());
        }
        outbreaks.draw(currentBoard.createGraphics());
        infectionRate.draw(currentBoard.createGraphics());
        deck.draw(currentBoard.createGraphics());
        infDeck.draw(currentBoard.createGraphics());

        roles.drawPlayersHands(currentBoard.createGraphics());

        if (selected instanceof Card) {
            ((Card) selected).drawPlaced(currentBoard.createGraphics());
        }
        notifyObservers();
    }

    private void loadResources() throws IOException {
        mainBoardImage = ImageIO.read(this.getClass().getResourceAsStream("/pandemic/data/images/board.jpg"));
    }

    @Override
    public void notifyObservers() {
        this.setChanged();
        this.notifyObservers(currentBoard);
    }

    public City getCity() {
        return null;
    }

    public Roles getRoles() {
        return null;
    }

    public void startGame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getOrigWidth() {
        return mainBoardImage.getWidth();
    }

    public int getOrigHeight() {
        return mainBoardImage.getHeight();
    }

    public void mainClick(int x, int y) {
        System.out.println("left!");
        if (selected instanceof Card) {
            Card card = (Card) selected;
            //special cards
            if (card.getCity() == null) {
                return;
            }
            if (card.getCity().equals(cities.getCityByCoord(new Point(x, y)))
                    || card.getCity().equals(roles.getCurrentPlayer().getCity())) {
                deck.returnCard(card);
                selected = null;
                roles.getCurrentPlayer().flyToTheCity(cities.getCityByCoord(new Point(x, y)));
                drawBoard();
            }
            return;
        }
        Card c = roles.selectPlayersHands(x, y);
        if (c != null) {
            selected = c;
            System.out.println(selected);
            drawBoard();
            return;
        }
        c = infDeck.clicked(x, y);
        if (c != null) {
            selected = c;
            System.out.println(selected);
            drawBoard();
            return;
        }
        c = deck.clicked(x, y);
        if (c != null) {
            selected = c;
            System.out.println(selected);
            drawBoard();
            return;
        }
        City found = cities.getCityByCoord(new Point(x, y));
        if (found != null) {
            System.out.println(found.getName());
            if (selected == found && roles.getCurrentPlayer().getCity().isNigbouring(found)) {
                roles.getCurrentPlayer().flyToTheCity(found);
                selected = null;
                drawBoard();
                return;
            }
            if (selected == found && roles.getCurrentPlayer().getCity().equals(found)) {
                new OtherActions(roles, deck);
                selected = null;
                drawBoard();
                return;
            }
            if (selected == found && roles.getCurrentPlayer().getCity().haveStation() && found.haveStation() && found != roles.getCurrentPlayer().getCity()) {
                roles.getCurrentPlayer().flyToTheCity(found);
                selected = null;
                drawBoard();
                return;
            }
            selected = found;
            drawBoard();
            return;
        }
        selected = null;
        System.out.println("Unselected");
        drawBoard();

    }

    public void move(int x, int y) {
        if (selected instanceof Card) {
            ((Card) selected).setCoords(x, y);
            drawBoard();
        }
    }

    public void second(int real, int real0) {
        System.out.println("right!");
        if (selected instanceof PlayerCard) {
            roles.getCurrentPlayer().setCardToHand((PlayerCard) selected);
            selected = null;
            drawBoard();
        }
        if (selected instanceof Card.InfectionCard) {
            infDeck.used((Card.InfectionCard) selected);
            if (!cures.isFixed(((Card.InfectionCard) selected).getCity().getColor())) {
                ((Card.InfectionCard) selected).getCity().infect(((Card.InfectionCard) selected).getCity().getColor(), new ArrayList<>());
            }
            selected = null;
            drawBoard();
        }
    }

}
