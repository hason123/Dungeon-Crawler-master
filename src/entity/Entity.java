package entity;

import main.GamePanel;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {

	public GamePanel gp;
	public int worldX, worldY;
	public int speed;

	public int maxHP,HP;
	public BufferedImage letter0,letter1;
	public BufferedImage up1, up2, up3, up4,up5,up6;
	public BufferedImage down1, down2, down3, down4,down5,down6;
	public BufferedImage left1, left2, left3, left4,left5,left6;
	public BufferedImage right1, right2, right3, right4,right5,right6;
	public String direction;
	public int spriteDem = 0;
	public int spriteNum = 1;
	public Rectangle solidArea;
	public int solidAreaDefaultX, solidAreaDefaultY; 
	public boolean collisionOn = false;

	public String[] dialogues = new String[20];


	public Entity(GamePanel gp) {
		this.gp = gp;
	}
}
