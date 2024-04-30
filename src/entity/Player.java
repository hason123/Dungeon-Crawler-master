
package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


import main.GamePanel;
import main.KeyboardInput;

import javax.imageio.ImageIO;

public class Player extends Entity {

    GamePanel gp;
    KeyboardInput keyInput;

    // cho biet vi tri toi ve nguoi choi tren man hinh
    public final int screenX;
    public final int screenY;
    public Player(GamePanel gp, KeyboardInput keyInput){
        this.gp = gp;
        this.keyInput = keyInput;
        // de nhan vạt nằm giữa màn hình
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        solidArea =new Rectangle(8,16,32,32);//Khi khoi tao hinh chu nhat nay co 4 doi soos truyen vao (x,y,height,width)
        // Mong muon tao 1 hinh chu nhat nho hon kich thuoc nhan vat

        setGiaTriMacDinh();
        getPlayerImage();
    }

    public void setGiaTriMacDinh(){
        //Toa do nhan vat va toc do mac dinh
        worldX = gp.tileSize * 5;
        worldY = gp.tileSize * 2 ;
        speed = 4;
        huongDi = "xuong"; // huong di mac dinh cua nhan vat

    }




    private boolean hasKey = false;
    private boolean keyUsed =true;

    public void pickUpKey() {
        hasKey = true;
    }

    public void useKey() {
        hasKey = false;
        keyUsed = true;  // Cài đặt khi chìa khóa được sử dụng
    }

    public boolean hasKey() {
        return hasKey;
    }

    public boolean isKeyUsed() {
        return keyUsed;
    }

    public void resetKeyUsed() {
        keyUsed = false;  // Đặt lại cờ sau khi đã xử lý
    }
    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }


    public void update(){ // Co the thay doi trang thai nhan vat khi dung yen (hoac la van dang trong animation di chuyen hoac la dung yen han)
        if (keyInput.diPhai || keyInput.diTrai || keyInput.diTren || keyInput.diXuong){ //Nhan vat se o trang thai "DUNG YEN" khi khong co nut nao duoc bam
            if (keyInput.diTren){
                huongDi = "len";
                worldY -= speed;
            }
            if (keyInput.diXuong){
                huongDi = "xuong";
                worldY += speed;
            }
            if (keyInput.diTrai){
                huongDi = "trai";
                worldX -= speed;
            }
            if (keyInput.diPhai){
                huongDi = "phai";
                worldX += speed;
            }
            // Check tile collision
            collisionOn = false;
            gp.cChecker.checkTile(this);//vi lop player la lop con cua entity collision co the nhan lop player la entity
            // if collision false, can move
            if(collisionOn == false){
                switch (huongDi){
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }
            spriteDem++; // Moi X frames lai thay doi hinh anh nhan vat
            if (spriteDem > 6){ // hieu don gian la phuong thuc update duoc goi 60 lan/giay. Hinh anh nhan vat se duoc doi theo chu trinh 1-2-3-4-1
                switch(spriteNum){ // cu moi lan update() duoc goi thi spriteDem tang them 1. Khi spriteDem() > X, bat dau chu trinh di chuyen (animation)
                    case 1: spriteNum = 2; break;
                    case 2: spriteNum = 3; break;
                    case 3: spriteNum = 4; break;
                    case 4: spriteNum = 1; break;
                }
                spriteDem = 0;
            }
        }
    }
    public void draw(Graphics2D g2){

        BufferedImage image = null;
        switch(huongDi){
            case "len":
                if(spriteNum == 1) {
                    image = up1;
                }
                if(spriteNum == 2) {
                    image = up2;
                }
                if(spriteNum == 3){
                    image = up3;
                }
                if (spriteNum == 4){
                    image = up4;
                }
                if (spriteNum == 5){
                    image = up5;
                }
                if (spriteNum == 6){
                    image = up6;
                }
                break;
            case "xuong":
                if(spriteNum == 1) {
                    image = down1;
                }
                if(spriteNum == 2) {
                    image = down2;
                }
                if(spriteNum == 3){
                    image = down3;
                }
                if (spriteNum == 4){
                    image = down4;
                }
                if (spriteNum == 5){
                    image = down5;
                }
                if (spriteNum == 6){
                    image = down6;
                }
                break;
            case "trai":
                if(spriteNum == 1) {
                    image = left1;
                }
                if(spriteNum == 2) {
                    image = left2;
                }
                if(spriteNum == 3){
                    image = left3;
                }
                if (spriteNum == 4){
                    image = left4;
                }
                if (spriteNum == 5){
                    image = left5;
                }
                if (spriteNum == 6){
                    image = left6;
                }
                break;
            case "phai":
                if(spriteNum == 1) {
                    image = right1;
                }
                if(spriteNum == 2) {
                    image = right2;
                }
                if(spriteNum == 3){
                    image = right3;
                }
                if (spriteNum == 4){
                    image = right4;
                }
                if (spriteNum == 5){
                    image = right5;
                }
                if (spriteNum == 6){
                    image = right6;
                }
                break;
        }
        g2.drawImage(image,screenX,screenY,(int) (1.5 * gp.tileSize),(int) (1.5 * gp.tileSize),null);
    }

    public void getPlayerImage(){
        try{

            up1 = ImageIO.read(getClass().getResourceAsStream("/player/run_up_40x40_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/run_up_40x40_2.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/player/run_up_40x40_3.png"));
            up4 = ImageIO.read(getClass().getResourceAsStream("/player/run_up_40x40_4.png"));
            up5 = ImageIO.read(getClass().getResourceAsStream("/player/run_up_40x40_5.png"));
            up6 = ImageIO.read(getClass().getResourceAsStream("/player/run_up_40x40_6.png"));

            down1= ImageIO.read(getClass().getResourceAsStream("/player/run_down_40x40_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/run_down_40x40_2.png"));
            down3= ImageIO.read(getClass().getResourceAsStream("/player/run_down_40x40_3.png"));
            down4 = ImageIO.read(getClass().getResourceAsStream("/player/run_down_40x40_4.png"));
            down5= ImageIO.read(getClass().getResourceAsStream("/player/run_down_40x40_5.png"));
            down6= ImageIO.read(getClass().getResourceAsStream("/player/run_down_40x40_6.png"));

            left1 = ImageIO.read(getClass().getResourceAsStream("/player/run_left_40x40_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/run_left_40x40_2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/player/run_left_40x40_3.png"));
            left4 = ImageIO.read(getClass().getResourceAsStream("/player/run_left_40x40_4.png"));
            left5 = ImageIO.read(getClass().getResourceAsStream("/player/run_left_40x40_5.png"));
            left6 = ImageIO.read(getClass().getResourceAsStream("/player/run_left_40x40_6.png"));



            right1 = ImageIO.read(getClass().getResourceAsStream("/player/run_right_40x40_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/run_right_40x40_2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/player/run_right_40x40_3.png"));
            right4 = ImageIO.read(getClass().getResourceAsStream("/player/run_right_40x40_4.png"));
            right5 = ImageIO.read(getClass().getResourceAsStream("/player/run_right_40x40_5.png"));
            right6 = ImageIO.read(getClass().getResourceAsStream("/player/run_right_40x40_6.png"));


        }catch(IOException e){
            e.printStackTrace();
        }
    }

}