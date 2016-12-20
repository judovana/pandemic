/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j2a.java;

/**
 *
 * @author jvanek
 */
public class FontMetrics implements j2a.FontMetrics {
    private final java.awt.FontMetrics back;

    FontMetrics(java.awt.FontMetrics fontMetrics) {
        this.back=fontMetrics;
    }

    @Override
    public int getHeight() {
        return back.getHeight();
    }

    @Override
    public int stringWidth(String s) {
        return back.stringWidth(s);
    }
    
}
