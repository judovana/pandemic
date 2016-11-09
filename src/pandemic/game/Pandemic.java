/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game;

import pandemic.game.board.Logic;
import pandemic.game.board.Board;
import pandemic.game.roles.Roles;

/**
 *
 * @author Pípa
 */
public class Pandemic {

    /**
     * This method throws RuntimeException  if no player goes to play.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0){
            throw new RuntimeException("At least one player is expected!");
        }
        new Board(new Logic(new Roles())).startGame();
    }
    
    
}
