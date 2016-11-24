/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.board;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
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
import pandemic.game.roles.Roles;

/**
 *
 * @author Pípa
 */
public class Board extends Observable {

    private Roles roles;
    private Cities cities;
    private Outbreaks outbreaks;
    private InfecetionRate infectionRate;
    private Logic logic;
    private Deck deck;
    private InfectionDeck infDeck;
    private Drugs drugs;
    private BufferedImage currentBoard;
    private BufferedImage mainBoardImage;
    private City higlightCity;

    public Board(Logic logic) throws IOException {
        this.logic = logic;
        cities = new Cities();
        outbreaks = new Outbreaks();
        deck = new Deck();
        infDeck = new InfectionDeck();
        logic.getRoles().initPlayers(cities);
        loadResources();
        drawBoard();
        this.notifyObservers();
    }

    private void drawBoard() {
        currentBoard = new BufferedImage(mainBoardImage.getWidth(), mainBoardImage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        currentBoard.createGraphics().drawImage(mainBoardImage, 0, 0, null);
        logic.getRoles().drawPlayers(currentBoard.createGraphics());
        if (higlightCity != null) {
            higlightCity.draw(currentBoard.createGraphics());
        }
        outbreaks.draw(currentBoard.createGraphics());
        deck.draw(currentBoard.createGraphics());
        infDeck.draw(currentBoard.createGraphics());
        
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
        City found = cities.getCityByCoord(new Point(x, y));
        if (found != null) {
            System.out.println(found.getName());
            higlightCity = found;
            drawBoard();
            return;
        }
        higlightCity = null;
        drawBoard();

    }

}
