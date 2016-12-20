/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.board.parts.tokens;

import j2a.Color;
import j2a.Point;
import pandemic.game.board.DiseaseType;

/**
 *just for differ from another tokens
 * @author Pípa
 */
public class Cubes extends Token {

    private DiseaseType diseasetype;

    public Cubes(Point point, Color c) {
        super(point, c);
    }
}
