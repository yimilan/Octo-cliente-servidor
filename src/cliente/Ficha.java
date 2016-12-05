/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author anyel
 */
public class Ficha {

    public static URL FICHA;
    public int TAM_H = 70;
    public int TAM_V = 45;
    int pos_fV;
    int pos_fH;
    String dirFicha = "";
    ImageIcon imgFich;

    public Ficha() {

    }

    public JButton setFicha(int pos_fH, int pos_fV, String dirFicha, JLabel containerFicha, JButton fix) {

        this.pos_fV = pos_fV;
        this.pos_fH = pos_fH;
        this.dirFicha = dirFicha;
        String dirURL = "ImagenesJuego/" + dirFicha;
        FICHA = this.getClass().getResource(dirURL);
        imgFich = new ImageIcon(FICHA);
        fix = new JButton(imgFich);
        fix.setBounds(pos_fH, pos_fV, TAM_H, TAM_V);
        containerFicha.add(fix);
        return fix;
    }

    public void setNewImgFich(String dirFicha, JButton fix) {

        String dirURL = "ImagenesJuego/" + dirFicha;
        FICHA = this.getClass().getResource(dirURL);
        imgFich = new ImageIcon(FICHA);
        fix.setIcon(imgFich);

    }
}
