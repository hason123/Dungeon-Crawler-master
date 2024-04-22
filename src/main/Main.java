package main;

import javax.swing.*;
import java.awt.*;


public class Main {

    public static JFrame manHinh;

    public static void main(String[] args) {
        manHinh = new JFrame();
        manHinh.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        manHinh.setResizable(false);
        manHinh.setTitle("Dungeon Crawler");

        GamePanel gp = new GamePanel();
        manHinh.add(gp);

        manHinh.pack();

        manHinh.setLocationRelativeTo(null);
        manHinh.setVisible(true);

        gp.startGameThread();
    }
}



