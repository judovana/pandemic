/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.board;

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
public class Board {

    private Roles roles;
    private Cities cities;
    private Outbreaks outbreaks;
    private InfecetionRate infectionRate;
    private Logic logic;
    private Deck deck;
    private Drugs drugs;
    
    public Board(Logic logic) {
    }

    public City getCity() {
        return null;
    }
    
   public Roles getRoles(){
        return null;
    }
    
   
    
     public void startGame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
