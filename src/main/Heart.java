package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Heart extends objectforgem{
    GamePanel gp;
    public static final String objName = "Heart";

    public Heart(GamePanel gp) {

        this.gp = gp;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/hearts/heart_full.png"));
            image1 = ImageIO.read(getClass().getResourceAsStream("/hearts/heart_blank.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/hearts/heart_half.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void interact() {

    }

    @Override
    public void draw(Graphics2D g2) {

    }
}
