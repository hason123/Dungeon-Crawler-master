package entity; //luu tru cac thuoc tinh cua nhan vat (player, enemies,......)


import java.awt.image.BufferedImage;

public class Entity {
    public int worldx,worldy;
    public int screenY, sceenY;
    public int speed;

    public BufferedImage up1,up2,up3,up4,up5,up6;
    public BufferedImage down1,down2,down3,down4,down5,down6;
    public BufferedImage left1,left2,left3,left4,left5,left6;
    public BufferedImage right1,right2,right3,right4,right5,right6;

    public String huongDi;

    public int spriteDem = 0;

    public int spriteNum = 1;




}
