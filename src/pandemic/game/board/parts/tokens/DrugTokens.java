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
public class DrugTokens extends Token {

    private boolean fixed;

    public DrugTokens(Point point, Color c, boolean fixed) {
        super(point, c);
        this.fixed = fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    @Override
    int getRadius() {
        return super.getRadius() * 2;
    }

    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
        if (isFixed()) {
            g2d.drawLine(point.x, point.y, point.x + getRadius(), point.y + getRadius());
            g2d.drawLine(point.x + getRadius(), point.y, point.x, point.y + getRadius());
        }
    }

    public boolean isFixed() {
        return fixed;
    }

}
