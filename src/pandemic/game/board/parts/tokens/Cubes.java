/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.board.parts.tokens;

import java.awt.Point;
import pandemic.game.board.DiseaseType;

/**
 *
 * @author Pípa
 */
public class Cubes extends Token {
    private DiseaseType diseasetype;

    public Cubes(Point point) {
        super(point);
    }
}
