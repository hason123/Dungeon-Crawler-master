package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UI {//Thiết lập hiển thị thông tin Player trên màn hình
    GamePanel gp;
    Graphics2D g2;

    Font arial20PLAIN; //Có thể thay đổi phông chữ tốt hơn
    Font arial40BOLD;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageTimer;
    public boolean gameComplete = false;

    public UI(GamePanel gp) {
        this.gp = gp;

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

    public void draw(Graphics2D g2D) {
        if(gameComplete) {//Cần có điều kiện hoàn thành (chiến thắng) Game
            int x;
            int y;

            g2D.setFont(arial40BOLD);
            g2D.setColor(Color.WHITE);
            x = getXCenteredText("Congratulations!");
            y = gp.screenHeight/2 - gp.tileSize;
            g2D.drawString("Congratulations!",x,y);

            g2D.setFont(arial20PLAIN);
            g2D.setColor(Color.WHITE);
            x = getXCenteredText("You have defeated the demon boss!");
            y = gp.screenHeight/2 + gp.tileSize*2;
            g2D.drawString("You have defeated the demon boss!",x,y);
            //Có thể thêm bộ đếm thời gian hoàn thành game ở màn hình kết thúc nếu cần

            gp.gameThread = null;
        }
        else {
            g2D.setFont(arial20PLAIN);
            g2D.setColor(Color.white);
            g2D.drawImage(keyImage, 12, 525, gp.tileSize, gp.tileSize, null);

            //Trạng thái mang chìa khóa của Player: Yes/No?
            //Hiện chỉ dùng nếu có 1 chìa/cửa, cần chỉnh nếu sử dụng nhiều chìa/cửa
            /*if(gp.player.isKeyUsed()){
                g2D.drawString("? Yes (Used)", 60, 558);}
                else if(gp.player.hasKey()) {
                g2D.drawString("? Yes (Hold)", 60, 558);}
                    else {
                g2D.drawString("? No", 60, 558);}*/

            //Message (Cần có các Items trên Map trước)
            if(messageOn) {
                g2D.setFont(g2D.getFont());
                g2D.drawString(message, 384, 288);

                messageTimer ++;
                if(messageTimer > 150) {
                    messageTimer = 0;
                    messageOn = false;
                }
            }
        }

        this.g2 = g2D;
        g2.setFont(arial20PLAIN);
        g2D.setColor(Color.white);

        if(gp.gameState == gp.playState) {
            //Pending
        }
        if(gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,80f));
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

    public int getXCenteredText(String text) {
        int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - textLength/2;
    }
}
