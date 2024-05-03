package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import main.GamePanel;

public class Boss extends Entity {
    GamePanel gp;
    private BufferedImage[] idleSprites; // Mảng chứa các sprite khi boss đứng yên
    private BufferedImage[] attackSprites; // Mảng chứa các sprite cho mỗi hoạt ảnh tấn công
    private int currentFrame = 0;
    private int frameDelay = 0; // Đếm ngược cho đến khi thay đổi frame tiếp theo
    private int frameDelayLimit = 10; // Giới hạn thời gian chờ để thay đổi frame, có thể điều chỉnh để làm chậm animation
    private boolean attacking = false;
    private int width = 64;
    private int height = 64;

    public Boss(GamePanel gp) {
        this.gp = gp;
        loadSprites();
        setDefaultValues();
    }

    private void setDefaultValues() {
        worldX = gp.tileSize * 6;
        worldY = gp.tileSize * 38;
    }

    private void loadSprites() {
        try {
            BufferedImage idleSheet = ImageIO.read(getClass().getResourceAsStream("/boss/idleboss.png"));
            idleSprites = new BufferedImage[4];
            for (int i = 0; i < 4; i++) {
                idleSprites[i] = idleSheet.getSubimage(i * width, 0, width, height);
            }

            attackSprites = new BufferedImage[5];
            for (int i = 0; i < 5; i++) {
                attackSprites[i] = ImageIO.read(getClass().getResourceAsStream("/boss/bossattack" + (i + 1) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        frameDelay++;
        if (frameDelay >= frameDelayLimit) {
            currentFrame = (currentFrame + 1) % (attacking ? 5 : 4);
            frameDelay = 0; // Đặt lại bộ đếm thời gian để chờ đến frame tiếp theo
            if (attacking && currentFrame == 0) {
                attacking = false; // Kết thúc chuỗi tấn công
            }
        }
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        BufferedImage image = attacking ? attackSprites[currentFrame] : idleSprites[currentFrame];
        g2.drawImage(image, screenX, screenY, width*4, height*4, null);
    }

    public void startAttack() {
        attacking = true;
        currentFrame = 0;
        frameDelay = 0; // Đặt lại bộ đếm thời gian khi bắt đầu tấn công
    }
}
