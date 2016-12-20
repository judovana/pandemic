/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.board.parts.tokens;

import j2a.Color;
import j2a.GraphicsCanvas;
import j2a.Point;

/**
 *
 * @author Pípa
 */
public class Station extends Token {
    //Overriding the constructor by hardcoding the color
    public Station(Point point) {
        super(point, Color.getCYAN());
    }

    @Override
    int getRadius() {
        return super.getRadius(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void draw(GraphicsCanvas g2d) {
        g2d.setColor(color);
        g2d.fillRect(point.getX() - 2 * getRadius() + 3, point.getY() - 2 * getRadius(), getRadius() - 6, getRadius());
        g2d.fillRect(point.getX() - 2 * getRadius(), point.getY() - 2 * getRadius() + 3, getRadius(), getRadius() - 6);
    }

}
