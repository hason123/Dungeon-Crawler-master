package main;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyboardInput implements KeyListener {

    public boolean diTren, diXuong, diTrai, diPhai;


    @Override //bat buoc phai giu keyTyped du no ko co gi ca
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int number = e.getKeyCode();

        if (number == KeyEvent.VK_W){
            diTren = true;
        }
        if (number == KeyEvent.VK_S){
            diXuong = true;
        }
        if (number == KeyEvent.VK_A){
            diTrai = true;
        }
        if (number == KeyEvent.VK_D){
            diPhai = true;
        }

        
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int number = e.getKeyCode();

        if (number == KeyEvent.VK_W){
            diTren = false;
        }
        if (number == KeyEvent.VK_S){
            diXuong = false;
        }
        if (number == KeyEvent.VK_A){
            diTrai = false;
        }
        if (number == KeyEvent.VK_D){
            diPhai = false;
        }
    }

}
