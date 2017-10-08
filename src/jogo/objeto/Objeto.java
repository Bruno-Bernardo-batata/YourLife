/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo.objeto;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Jhonatan
 */
public class Objeto {
    public int x;
    public int y;
    public int largura;
    public int altura;

    public Objeto(int x, int y, int largura, int altura) {
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
    }
    
    public int getCentroX(){
        return this.x+(this.largura/2);
    }
    
    public int getCentroY(){
        return this.y+(this.altura/2);
    }
    
    public void desenha(Graphics g, BufferedImage img){
        g.drawImage(img, x, y, null);
    }
}
