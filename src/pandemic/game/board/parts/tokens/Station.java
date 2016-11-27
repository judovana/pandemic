/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.board.parts.tokens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Pípa
 */
public class Station extends Token {

    public Station(Point point) {
        super(point, Color.CYAN);
    }

    @Override
    int getRadius() {
        return super.getRadius(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fillRect(point.x - 2 * getRadius() + 3, point.y - 2 * getRadius(), getRadius() - 6, getRadius());
        g2d.fillRect(point.x - 2 * getRadius(), point.y - 2 * getRadius() + 3, getRadius(), getRadius() - 6);
    }

}
