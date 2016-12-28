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
public class DrugTokens extends Token {

    private boolean fixed;

    public DrugTokens(Point point, Color c, boolean fixed) {
        super(point, c);
        this.fixed = fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }
    //the size of area for clicking on drug token
    @Override
    int getRadius() {
        return super.getRadius() * 2;
    }
    //if the disease is cured it draws colored token with cross on the surface of token
    @Override
    public void draw(GraphicsCanvas g2d) {
        super.draw(g2d);
        if (isFixed()) {
            g2d.drawLine(point.getX(), point.getY(), point.getX() + getRadius(), point.getY() + getRadius());
            g2d.drawLine(point.getX() + getRadius(), point.getY(), point.getX(), point.getY() + getRadius());
        }
    }

    public boolean isFixed() {
        return fixed;
    }

    public Point getPoint() {
        return point;
    }
    
    

}
