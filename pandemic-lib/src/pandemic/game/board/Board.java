/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.board;

import j2a.BitmapImage;
import j2a.Factory;
import j2a.GraphicsCanvas;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
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

    private final Roles roles;
    private final Cities cities;
    private final Outbreaks outbreaks;
    private final InfecetionRate infectionRate;
    private final Deck deck;
    private final InfectionDeck infDeck;
    private BitmapImage currentBoard;
    private BitmapImage mainBoardImage;
    private Object selected;
    private final Drugs cures;
    private final OtherActionsProvider oa;

    public Board(Roles roles, OtherActionsProvider oa, boolean randomize, int epidemyCards) throws IOException {
        this.oa = oa;
        this.roles = roles;
        cities = new Cities();
        outbreaks = new Outbreaks();
        deck = new Deck(cities);
        roles.initPlayers(cities, deck);
        infDeck = new InfectionDeck(cities);
        cities.initialDiseases(randomize ? null : infDeck);
        infectionRate = new InfecetionRate();
        cures = new Drugs();
        deck.insertEpidemies(epidemyCards);
        loadResources();
        drawBoard();
        this.notifyObservers();
    }

    public void drawBoard() {
        currentBoard = j2a.Factory.BitmapImage.newBitmapImage(mainBoardImage.getWidth(), mainBoardImage.getHeight());
        currentBoard.createGraphics().drawImage(mainBoardImage, 0, 0, null);
        cures.draw(currentBoard.createGraphics());
        cities.drawStations(currentBoard.createGraphics());
        roles.drawPlayers(currentBoard.createGraphics());
        if (selected instanceof City) {
            ((City) selected).draw(currentBoard.createGraphics());
            /* when city is selected the directions are drow
             */
        }
        outbreaks.draw(currentBoard.createGraphics());
        infectionRate.draw(currentBoard.createGraphics());
        deck.draw(currentBoard.createGraphics());
        infDeck.draw(currentBoard.createGraphics());

        roles.drawPlayersHands(currentBoard.createGraphics());

        String selctedCardName = "";
        if (selected instanceof Card) {
            Card cc = ((Card) selected);
            selctedCardName = " -> " + cc.toString();
            cc.drawPlaced(currentBoard.createGraphics());
            if (cc.getFreeCoords() != null) {
                GraphicsCanvas gc = currentBoard.createGraphics();
                gc.setColor(Factory.Color.getCYAN());
                gc.drawRect(cc.getFreeCoords().getX(), cc.getFreeCoords().getY(), cc.getWidth(), cc.getHeight());
            }
        }
        GraphicsCanvas cg = currentBoard.createGraphics();
        cg.setColor(Factory.Color.getWHITE());
        String ss = roles.getCurrentPlayer().getTitle() + selctedCardName;
        int ssw = cg.getFontMetrics().stringWidth(ss);
        cg.drawString(ss, getOrigWidth() / 2 - ssw / 2, 20);
        notifyObservers();
    }

    //loading the picture from path in the jar
    private void loadResources() throws IOException {
        mainBoardImage = j2a.Factory.BitmapImage.read(this.getClass().getResourceAsStream("/pandemic/data/images/board.jpg"));
    }

    /**
     * {@inheritDoc }
     */
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

    /**
     * On the basis of clicking bz the left button decides what will happen.
     * e.g. moving to the city, highlighting, taking cards, dropping cards,
     * currently selected card may affect the game
     *
     * @param x coordinate recalculated to the size of an image
     * @param y coordinate recalculated to the size of an image
     */
    public void mainClick(int x, int y) {
        System.out.println("left!");
        if (selected instanceof Card) {
            Card card = (Card) selected;
            //special cards
            if (card.getCity() == null) {
                return;
            }
            //Condition for moving with the city cards
            City clickedCity = cities.getCityByCoord(j2a.Factory.Point.newPoint(x, y));
            if (clickedCity != null) {
                if (card.getCity().equals(clickedCity)
                        || card.getCity().equals(roles.getCurrentPlayer().getCity())) {
                    deck.returnCard(card);
                    selected = null;
                    roles.getCurrentPlayer().flyToTheCity(cities.getCityByCoord(j2a.Factory.Point.newPoint(x, y)));
                    drawBoard();
                }
                return;
            } else {
                return;
            }
        }
        //selecting players card and drawing them on the board
        Card c = roles.selectPlayersHands(x, y);
        if (c != null) {
            selected = c;
            System.out.println(selected);
            drawBoard();
            return;
        }
        //card selected from infection cards 
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
            if (selected instanceof  PlayerCard.Epidemy){
                ((PlayerCard.Epidemy)(selected)).stealCity(infDeck.getBottomCardInfo());
            }
            System.out.println(selected);
            drawBoard();
            return;
        }
        //decide what to do when the city is selected
        City found = cities.getCityByCoord(j2a.Factory.Point.newPoint(x, y));
        if (found != null) {
            System.out.println(found.getName());
            if (selected == found && roles.getCurrentPlayer().getCity().isNigbouring(found)) {
                roles.getCurrentPlayer().flyToTheCity(found);
                selected = null;
                drawBoard();
                return;
            }
            if (selected == found && roles.getCurrentPlayer().getCity().equals(found)) {
                oa.provide(roles, deck);
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

    /**
     * moving to a city by using a card
     *
     * @param x coordinate recalculated to the size of an image
     * @param y coordinate recalculated to the size of an image
     */
    public void move(int x, int y) {
        if (selected instanceof Card) {
            ((Card) selected).setCoords(x, y);
            drawBoard();
        }
    }

    /**
     * Clicking the right button allows to drop infection cards and do the
     * infecting or take cards to players hands
     *
     * @param real coordinate recalculated to the size of an image
     * @param real0 coordinate recalculated to the size of an image
     */
    public void second(int real, int real0) {
        System.out.println("right!");
        if (selected instanceof PlayerCard) {
            if (selected instanceof PlayerCard.Epidemy) {
                deck.returnCard((Card) selected);
                infectionRate.addInfectionRate();
                infDeck.playEpidmey(cities);
            } else {
                roles.getCurrentPlayer().setCardToHand((PlayerCard) selected);
            }
            selected = null;
            drawBoard();
        }
        if (selected instanceof Card.InfectionCard) {
            infDeck.used((Card.InfectionCard) selected);
            if (!cures.isFixed(((Card.InfectionCard) selected).getCity().getColor())) {
                ((Card.InfectionCard) selected).getCity().infect(((Card.InfectionCard) selected).getCity().getColor(), new ArrayList<City>());
            }
            selected = null;
            drawBoard();
        }
    }

}
