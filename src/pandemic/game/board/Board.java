/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.board;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import javax.imageio.ImageIO;
import pandemic.game.board.parts.Deck;
import pandemic.game.board.parts.InfecetionRate;
import pandemic.game.board.parts.InfectionDeck;
import pandemic.game.board.parts.Outbreaks;
import pandemic.game.board.parts.tokens.Cities;
import pandemic.game.board.parts.tokens.City;
import pandemic.game.board.parts.tokens.Drugs;
import pandemic.game.cards.Card;
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
    private final Logic logic;
    private final Deck deck;
    private final InfectionDeck infDeck;
    private Drugs drugs;
    private BufferedImage currentBoard;
    private BufferedImage mainBoardImage;
    private Object selected;

    public Board(Logic logic) throws IOException {
        this.logic = logic;
        cities = new Cities();
        outbreaks = new Outbreaks();
        deck = new Deck(cities);
        infDeck = new InfectionDeck(cities);
        infectionRate = new InfecetionRate();
        logic.getRoles().initPlayers(cities);
        loadResources();
        drawBoard();
        this.notifyObservers();
    }

    private void drawBoard() {
        currentBoard = new BufferedImage(mainBoardImage.getWidth(), mainBoardImage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        currentBoard.createGraphics().drawImage(mainBoardImage, 0, 0, null);
        cities.drawStations(currentBoard.createGraphics());
        logic.getRoles().drawPlayers(currentBoard.createGraphics());
        if (selected instanceof City) {
            ((City) selected).draw(currentBoard.createGraphics());
        }
        outbreaks.draw(currentBoard.createGraphics());
        infectionRate.draw(currentBoard.createGraphics());
        deck.draw(currentBoard.createGraphics());
        infDeck.draw(currentBoard.createGraphics());
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

    public void higlight(int x, int y) {
        Card c = infDeck.clicked(x, y);
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
            selected = found;
            drawBoard();
            return;
        }
        selected = null;
        System.out.println("Unselected");
        drawBoard();

    }

}
