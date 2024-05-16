package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyboardInput;
import main.LockedDoor;
import main.UtilityTools;

public class Player extends Entity {
    GamePanel gp;
    KeyboardInput keyInput;
    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyboardInput keyInput) {
        super(gp);

        this.gp = gp;
        this.keyInput = keyInput;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 30;
        solidArea.y = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 20;
        solidArea.height = 40;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() { // cac gia tri mac dinh cua nhan vat
        worldX = gp.tileSize * 6; //Vi tri cua nhan vat
        worldY = gp.tileSize * 38;
        speed = 4; // toc do di chuyen cua nhan vat
        direction = "down"; // huong nhan vat ban dau
        // Luong mau cua nhan vat
        maxHP = 16;
        HP = maxHP;
    }
    private int keyCount = 0;
    private int totalKeyPicked = 0;
    public void pickUpKey() {
        keyCount++;
        totalKeyPicked++;
    }

    public void useKey() {
        if (keyCount > 0) {
            keyCount--;
        }
    }

    public boolean hasKey() {
        return keyCount > 0;
    }

    public int getKeyCount() {
        return keyCount;
    }
    public int getTotalKeyPicked() {return totalKeyPicked;}

    public int getWorldX() {
        return worldX;
    }
    public int getWorldY() {
        return worldY;
    }

    public BufferedImage playerImageSetup(String imageName){
        UtilityTools util = new UtilityTools();
        BufferedImage resizedImage = null;

        try{
            resizedImage = ImageIO.read(getClass().getResourceAsStream("/player/"+imageName+".png"));
            resizedImage = util.scaleImage(resizedImage, (int) (1.6*gp.tileSize), (int) (1.6*gp.tileSize));
        }catch(IOException e) {
            e.printStackTrace();
        }
        return resizedImage;
    }

    public void getPlayerImage() {
            up1 = playerImageSetup("run_up_40x40_1");
            up2 = playerImageSetup("run_up_40x40_2");
            up3 = playerImageSetup("run_up_40x40_3");
            up4 = playerImageSetup("run_up_40x40_4");

            down1 = playerImageSetup("run_down_40x40_1");
            down2 = playerImageSetup("run_down_40x40_2");
            down3 = playerImageSetup("run_down_40x40_3");
            down4 = playerImageSetup("run_down_40x40_4");

            left1 = playerImageSetup("run_left_40x40_1");
            left2 = playerImageSetup("run_left_40x40_2");
            left3 = playerImageSetup("run_left_40x40_3");
            left4 = playerImageSetup("run_left_40x40_4");

            right1 = playerImageSetup("run_right_40x40_1");
            right2 = playerImageSetup("run_right_40x40_2");
            right3 = playerImageSetup("run_right_40x40_3");
            right4 = playerImageSetup("run_right_40x40_4");
    }

    public void update() {

        if (keyInput.diPhai || keyInput.diTrai || keyInput.diTren
                || keyInput.diXuong) {

            collisionOn = false;
            gp.cChecker.checkTile(this);
            int potentialX = worldX;
            int potentialY = worldY;

            // Giả sử đầu vào từ bàn phím để điều chỉnh tọa độ tiềm năng
            if (keyInput.diTren) {
                direction = "up";
                if (collisionOn == false) {
                    potentialY = worldY - 4;
                }
            }
            if (keyInput.diXuong) {
                direction = "down";
                if (collisionOn == false) {
                    potentialY = worldY + 4;
                }
            }
            if (keyInput.diTrai) {
                direction = "left";
                if (collisionOn == false) {
                    potentialX = worldX - 4;

                }
            }
            if (keyInput.diPhai) {
                direction = "right";
                if (collisionOn == false) {
                    potentialX = worldX + 4;
                }
            }


                // Kiểm tra va chạm với cửa
                boolean collision = false;
                LockedDoor door0 = gp.lockedDoor0;
                LockedDoor door1 = gp.lockedDoor1;
                LockedDoor door2 = gp.lockedDoor2;
                LockedDoor door3 = gp.lockedDoor3;
                LockedDoor door4 = gp.lockedDoor4;
                LockedDoor door5 = gp.lockedDoor5;
                LockedDoor door6 = gp.lockedDoor6;
                LockedDoor door7 = gp.lockedDoor7;
                LockedDoor door8 = gp.lockedDoor8;




                if (door0.isLocked && door0.checkCollision(potentialX, potentialY)) {
                    collision = true;
                }
                if (door1.isLocked && door1.checkCollision(potentialX, potentialY)) {
                    collision = true;
                }
                if (door2.isLocked && door2.checkCollision(potentialX, potentialY)) {
                    collision = true;
                }
                if (door3.isLocked && door3.checkCollision(potentialX, potentialY)) {
                    collision = true;
                }
                if (door4.isLocked && door4.checkCollision(potentialX, potentialY)) {
                    collision = true;
                }
                if (door5.isLocked && door5.checkCollision(potentialX, potentialY)) {
                    collision = true;
                }
                if (door6.isLocked && door6.checkCollision(potentialX, potentialY)){
                    collision = true;
                }
                if (door7.isLocked && door7.checkCollision(potentialX, potentialY)) {
                    collision = true;
                }
                if (door8.isLocked && door8.checkCollision(potentialX, potentialY)) {
                   collision = true;
                }




                // Cập nhật vị trí người chơi nếu không có va chạm
                if (!collision) {
                    worldX = potentialX;
                    worldY = potentialY;
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



    public void draw(Graphics2D g2){
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
            g2.drawImage(image, screenX, screenY, null);

        }
}