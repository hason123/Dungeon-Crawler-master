package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyboardInput;

public class Player extends Entity {
	GamePanel gp;
	KeyboardInput keyInput;
	public final int screenX;
	public final int screenY;
	public String keyState = "No"; //Trạng thái có chìa khóa hay không: Yes/No. Nếu thiết lập có thể mang nhiều chìa khóa cùng lúc thì đổi thành int keyCount = 0 (số chìa khóa)

	public Player(GamePanel gp, KeyboardInput keyInput) {
		this.gp = gp;
		this.keyInput = keyInput;

		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

		solidArea = new Rectangle();
		solidArea.x = 20;
		solidArea.y = 20;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 30;
		solidArea.height = 40;

		setDefaultValues();
		getPlayerImage();
	}

	public void setDefaultValues() {
		worldX = gp.tileSize * 5;
		worldY = gp.tileSize * 5;
		speed = 4;
		direction = "down";
	}

	public void getPlayerImage() {

		try {

			up1 = ImageIO.read(getClass().getResourceAsStream("/player/run_up_40x40_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/run_up_40x40_2.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/player/run_up_40x40_3.png"));
            up4 = ImageIO.read(getClass().getResourceAsStream("/player/run_up_40x40_4.png"));

            down1= ImageIO.read(getClass().getResourceAsStream("/player/run_down_40x40_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/run_down_40x40_2.png"));
            down3= ImageIO.read(getClass().getResourceAsStream("/player/run_down_40x40_3.png"));
            down4 = ImageIO.read(getClass().getResourceAsStream("/player/run_down_40x40_4.png"));

            left1 = ImageIO.read(getClass().getResourceAsStream("/player/run_left_40x40_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/run_left_40x40_2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/player/run_left_40x40_3.png"));
            left4 = ImageIO.read(getClass().getResourceAsStream("/player/run_left_40x40_4.png"));

            right1 = ImageIO.read(getClass().getResourceAsStream("/player/run_right_40x40_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/run_right_40x40_2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/player/run_right_40x40_3.png"));
            right4 = ImageIO.read(getClass().getResourceAsStream("/player/run_right_40x40_4.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void update() {

		if (keyInput.diPhai || keyInput.diTrai || keyInput.diTren
				|| keyInput.diXuong) {

			collisionOn = false;
			gp.cChecker.checkTile(this);

			if (keyInput.diTren) {
				direction = "up";
				if (collisionOn == false) {
					worldY -= speed;
				}
			}
			if (keyInput.diXuong) {
				direction = "down";
				if (collisionOn == false) {
					worldY += speed;
				}
			}
			if (keyInput.diTrai) {
				direction = "left";
				if (collisionOn == false) {
					worldX -= speed;
				}
			}
			if (keyInput.diPhai) {
				direction = "right";
				if (collisionOn == false) {
					worldX += speed;
				}
			}
		}

		// toggle between sprites for animation
		if (keyInput.diPhai || keyInput.diTrai || keyInput.diTren
				|| keyInput.diXuong) {

			spriteDem++;

			if (spriteDem > 8) {
				if (spriteNum == 1) {
					spriteNum = 2;
				} else if (spriteNum == 2) {
					spriteNum = 3;
				} else if (spriteNum == 3) {
					spriteNum = 4;
				} else if (spriteNum == 4) {
					spriteNum = 1;
				}
				spriteDem = 0;
			}
		}
	}
	


	public void draw(Graphics2D g2) {
//		g2.setColor(Color.white);
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize);

		BufferedImage image = null;

		switch (direction) {
		case "up":
			if (spriteNum == 1) {
				image = up1;
			}
			if (spriteNum == 2) {
				image = up2;
			}
			if (spriteNum == 3) {
				image = up3;
			}
			if (spriteNum == 4) {
				image = up4;
			}


			break;
		case "down":
			if (spriteNum == 1) {
				image = down1;
			}
			if (spriteNum == 2) {
				image = down2;
			}
			if (spriteNum == 3) {
				image = down3;
			}
			if (spriteNum == 4) {
				image = down4;
			}
			break;
		case "left":
			if (spriteNum == 1) {
				image = left1;
			}
			if (spriteNum == 2) {
				image = left2;
			}
			if (spriteNum == 3) {
				image = left3;
			}
			if (spriteNum == 4) {
				image = left4;
			}
			break;
		case "right":
			if (spriteNum == 1) {
				image = right1;
			}
			if (spriteNum == 2) {
				image = right2;
			}
			if (spriteNum == 3) {
				image = right3;
			}
			if (spriteNum == 4) {
				image = right4;
			}
			break;
		}
		g2.drawImage(image, screenX, screenY, (int) (1.6 * gp.tileSize), (int) (1.6 * gp.tileSize), null);

	}
}
