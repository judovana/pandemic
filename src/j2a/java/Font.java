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
public class Font implements j2a.Font {

    public static int getBOLD() {
        return java.awt.Font.BOLD;
    }

    java.awt.Font back;

    public  Font(java.awt.Font back) {
        this.back = back;
    }

    @Override
    public j2a.Font deriveFont(int mod) {
        back.deriveFont(mod);
        return new Font(back);
    }

    @Override
    public Object getOriginal() {
        return back;
    }

}
