/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.board.parts.tokens;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Pípa
 */
public class Token {

    private final Point point;

    public Token(Point point) {
        this.point = point;
    }

    int getRadius() {
        return 10;
    }

    public void draw(Graphics2D g2d) {
        g2d.fillRect(point.x, point.y, getRadius(), getRadius());
    }

}
