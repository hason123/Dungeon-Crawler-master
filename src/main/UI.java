package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UI {//Thiết lập hiển thị thông tin Player trên màn hình
    GamePanel gp;
    Graphics2D g2;

    Font arial20PLAIN;
    Font arial40BOLD;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";

    public UI(GamePanel gp) {
        this.gp = gp;

        //Khai báo các font muốn dùng tại đây
        arial20PLAIN = new Font("Arial", Font.PLAIN, 20); //Cài đặt phông chữ (phông chữ thông thường tránh lỗi hiển thị)
        arial40BOLD = new Font("Arial", Font.BOLD, 40);
        try {
            keyImage = ImageIO.read(getClass().getResourceAsStream("/object/key.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void showMessage(String text) {//Phần thông báo ingame, cần hoàn thiện phần vật phẩm tương tác trước
        this.message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(arial20PLAIN);
        g2.setColor(Color.white);

        if(gp.gameState == gp.playState) {
            //Pending
            drawInterface();
        }
        if(gp.gameState == gp.pauseState) {
            drawInterface();
            drawPauseScreen();
        }
        if(gp.gameState == gp.gameCompletedState) {//Cần có điều kiện hoàn thành (chiến thắng) Game
            drawCompletedScreen();
            gp.gameThread = null;
        }
        if(gp.gameState == gp.defeatedState){
            //Pending
        }
        if(gp.gameState == gp.gameOpenState){
            //Pending
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
        g2.drawString(gp.player.getKeyCount()+"/"+gp.player.getTotalKeyPicked(),60,558);
    }

    public void drawInterface(){//Cần hiển thị thông số nào trong khi chơi thì thêm vào đây
        drawKeyCount();
    }
}
