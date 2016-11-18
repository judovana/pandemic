/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.board;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import javax.imageio.ImageIO;
import pandemic.game.board.parts.Deck;
import pandemic.game.board.parts.InfecetionRate;
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
    private Drugs drugs;
    private BufferedImage currentBoard;

    public Board(Logic logic) throws IOException {
        currentBoard = ImageIO.read(new File("C:\\Users\\Petr\\Desktop\\Pandemic\\image003.jpg"));
        this.notifyObservers();
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

}
