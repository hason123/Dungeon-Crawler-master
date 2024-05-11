package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {//Thiết lập hiển thị thông tin Player trên màn hình
    GamePanel gp;
    Graphics2D g2;

    Font arial20PLAIN;
    Font arial40BOLD;
    Font gameFont;
    BufferedImage keyImage;
    BufferedImage gameName, gameClose, gameCredits, gameSettings, gameStart, titleBackground, HALAL;

    BufferedImage heart_full,heart_blank,heart_half;
    public boolean messageOn = false;
    public String message = "";
    public int commandNumber = 1;

    public UI(GamePanel gp) {
        this.gp = gp;

        //Khai báo các font muốn dùng tại đây
        arial20PLAIN = new Font("Arial", Font.PLAIN, 20); //Cài đặt phông chữ (phông chữ thông thường tránh lỗi hiển thị)
        arial40BOLD = new Font("Arial", Font.BOLD, 40);

        try {
            InputStream is = getClass().getResourceAsStream("/font/game_font.otf");
            gameFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            keyImage = ImageIO.read(getClass().getResourceAsStream("/object/key.png"));
            gameName = ImageIO.read(getClass().getResourceAsStream("/titleMaterial/gameName.png"));
            gameClose = ImageIO.read(getClass().getResourceAsStream("/titleMaterial/gameClose.png"));
            gameCredits = ImageIO.read(getClass().getResourceAsStream("/titleMaterial/gameCredits.png"));
            gameSettings = ImageIO.read(getClass().getResourceAsStream("/titleMaterial/gameSettings.png"));
            gameStart = ImageIO.read(getClass().getResourceAsStream("/titleMaterial/gameStart.png"));
            titleBackground = ImageIO.read(getClass().getResourceAsStream("/titleMaterial/titleBackground.png"));
            HALAL = ImageIO.read(getClass().getResourceAsStream("/titleMaterial/HALAL.png"));
        }catch(IOException e){
            e.printStackTrace();
        }


        //Create HUD Objects
        objectforgem heart = new Heart(gp);
        heart_full = heart.image;
        heart_blank = heart.image1;
        heart_half = heart.image2;


    }

    public void showMessage(String text) {//Phần thông báo ingame, cần hoàn thiện phần vật phẩm tương tác trước
        this.message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(gameFont);
        g2.setColor(Color.white);

        if(gp.gameState == gp.titleScreen){
            drawTitleScreen();
        }
        if(gp.gameState == gp.playState) {
            //Pending
            drawInterface(); //Hien thi thong so nguoi choi! (luot duoi cung)
            //drawPlayerHP();
        }
        if(gp.gameState == gp.pauseState) {
            drawInterface();
            drawPauseScreen();
            //drawPlayerHP();
        }
        if(gp.gameState == gp.gameCompletedState) {//Cần có điều kiện hoàn thành (chiến thắng) Game
            drawCompletedScreen();
            gp.gameThread = null;
        }
        if(gp.gameState == gp.gameOverState){
            //Pending
        }
    }

    public void drawTitleScreen() {
        //Chọn nền
        g2.drawImage(titleBackground,0,0,null);

        //Tiêu đề
        g2.drawImage(gameName,gp.screenWidth/2 - gp.tileSize*4,gp.tileSize,null);

        //Menu
        g2.drawImage(gameStart,gp.screenWidth/2 - gp.tileSize*3,(int)(gp.tileSize*7.2),gp.tileSize*5,gp.tileSize,null);
        if (commandNumber == 1){
            g2.drawImage(HALAL,gp.screenWidth/2 - (int)(gp.tileSize*4.1),(int)(gp.tileSize*7.2),gp.tileSize,gp.tileSize,null);
        }

        g2.drawImage(gameSettings,gp.screenWidth/2 - gp.tileSize*3,(int)(gp.tileSize*8.3),gp.tileSize*4,gp.tileSize,null);
        if (commandNumber == 2){
            g2.drawImage(HALAL,gp.screenWidth/2 - (int)(gp.tileSize*4.1),(int)(gp.tileSize*8.3),gp.tileSize,gp.tileSize,null);
        }

        g2.drawImage(gameCredits,gp.screenWidth/2 - gp.tileSize*3,(int)(gp.tileSize*9.4),(int)(gp.tileSize*3.5),gp.tileSize,null);
        if (commandNumber == 3){
            g2.drawImage(HALAL,gp.screenWidth/2 - (int)(gp.tileSize*4.1),(int)(gp.tileSize*9.4),gp.tileSize,gp.tileSize,null);
        }

        g2.drawImage(gameClose,gp.screenWidth/2 - gp.tileSize*3,(int)(gp.tileSize*10.5),gp.tileSize*5,gp.tileSize,null);
        if (commandNumber == 4){
            g2.drawImage(HALAL,gp.screenWidth/2 - (int)(gp.tileSize*4.1),(int)(gp.tileSize*10.5),gp.tileSize,gp.tileSize,null);
        }
    }

    public void drawPlayerHP(){
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;

        // Adjust the spacing between hearts
        int heartSpacing = gp.tileSize / 6; // Change this value to adjust spacing
        int heartWidth = heart_full.getWidth(); // Assuming all hearts have the same width

        // Draw hearts based on player's health
        for (int i = 0; i < gp.player.maxHP / 2; i++) {
            g2.drawImage(heart_full, x + i * (heartWidth + heartSpacing), y, null);
        }
    }



    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,80f));
        g2.setColor(Color.white);
        String text = "PAUSED";
        int x = getXCenteredText(text);
        int y = gp.screenHeight/2 - gp.tileSize*3;
        g2.drawString(text,x,y);

        g2.setFont(g2.getFont().deriveFont(Font.ITALIC,20f));
        text = "Tip: Maybe you want to press M?";
        x = getXCenteredText(text);
        y = gp.screenHeight/2 + gp.tileSize*3;
        g2.drawString(text,x,y);
    }

    public void drawCompletedScreen(){
        int x;
        int y;

        g2.setFont(arial40BOLD);
        g2.setColor(Color.white);
        x = getXCenteredText("Congratulations!");
        y = gp.screenHeight/2 - gp.tileSize;
        g2.drawString("Congratulations!",x,y);

        g2.setFont(arial20PLAIN);
        x = getXCenteredText("You have defeated the demon boss!");
        y = gp.screenHeight/2 + gp.tileSize*2;
        g2.drawString("You have defeated the demon boss!",x,y);
        //Có thể thêm bộ đếm thời gian hoàn thành game ở màn hình kết thúc nếu cần
}

    public int getXCenteredText(String text) {
        int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - textLength/2;
    }

    public void drawKeyCount(){
        g2.setFont(arial20PLAIN);
        g2.setColor(Color.white);
        g2.drawImage(keyImage, 12, 525, gp.tileSize, gp.tileSize, null);
        g2.drawString(gp.player.getKeyCount()+"/"+"20",60,558);
        //g2.drawString(gp.player.getKeyCount()+"/"+gp.player.getTotalKeyPicked(),60,558);
    }

    public void drawInterface(){//Cần hiển thị thông số nào trong khi chơi thì thêm vào đây
        drawKeyCount();
        drawPlayerHP();
    }
}
