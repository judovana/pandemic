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
public class Token {

    protected final Point point;
    protected final Color color;

    public Token(Point point, Color c) {
        this.point = point;
        this.color = c;
    }

    int getRadius() {
        return 10;
    }

    public void draw(GraphicsCanvas g2d) {
        g2d.setColor(color);
        g2d.fillRect(point.getX(), point.getY(), getRadius(), getRadius());
        Color c2 = Color.newColor(255 - color.getRed(),
                255 - color.getGreen(),
                255 - color.getBlue());
        g2d.setColor(c2);
        g2d.drawRect(point.getX(), point.getY(), getRadius(), getRadius());
    }

    public Color getColor() {
        return color;
    }

}
