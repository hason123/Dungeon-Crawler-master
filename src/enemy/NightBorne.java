package enemy;

import main.GamePanel;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics2D;

public class NightBorne extends Enemy {

    private BufferedImage idleSpriteSheet, runSpriteSheet, attackSpriteSheet, hurtSpriteSheet, deathSpriteSheet;
    private int currentFrame = 0;
    private int frameWidth = 80;
    private int frameHeight = 80;
    private int frameDelay = 0; // Biến để đếm độ trễ
    private final int frameDelayLimit = 5;

    public NightBorne(GamePanel gp) {
        super(gp, 0, 0, 2);
        setDefaultValues();
        loadSprites();
    }

    private void setDefaultValues() {

        worldX = gp.tileSize * 5;
        worldY = gp.tileSize * 5;
    }

    @Override
    protected void loadSprites() {
        try {
            idleSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/enemy/ide.png"));
            runSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/enemy/run.png"));
            attackSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/enemy/attack.png"));
            hurtSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/enemy/hurt.png"));
            deathSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/enemy/death.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage currentSpriteSheet = getCurrentSpriteSheet();
        int frameCount = getCurrentFrameCount();
        // phần dưới giúp di chuển của enemy khong bi bug
        // Kiểm tra xem liệu currentFrame có hợp lệ không trước khi tạo subimage
        if (currentFrame < 0 || currentFrame >= frameCount) {
            currentFrame = 0; // Đặt lại về frame đầu tiên nếu không hợp lệ
        }
        int frameX = currentFrame * frameWidth;
        // Đảm bảo frameX + frameWidth không vượt quá chiều rộng của spritesheet
        if (frameX + frameWidth > currentSpriteSheet.getWidth()) {
            frameX = currentSpriteSheet.getWidth() - frameWidth;
        }
        BufferedImage frame = currentSpriteSheet.getSubimage(frameX, 0, frameWidth, frameHeight);
        int screenX = worldX - gp.player.getWorldX() + gp.screenWidth / 2;
        int screenY = worldY - gp.player.getWorldY() + gp.screenHeight / 2;

        g2.drawImage(frame, screenX, screenY, frameWidth * 2, frameHeight * 2, null);
        updateFrame();
    }
    private void updateFrame() {
        frameDelay++;
        if (frameDelay >= frameDelayLimit) {
            currentFrame = (currentFrame + 1) % getCurrentFrameCount();
            frameDelay = 0;
        }
    }

    private BufferedImage getCurrentSpriteSheet() {
        switch (state) {
            case IDLE:
                return idleSpriteSheet;
            case RUN:
                return runSpriteSheet;
            case ATTACK:
                return attackSpriteSheet;
            case HURT:
                return hurtSpriteSheet;
            case DEATH:
                return deathSpriteSheet;
            default:
                return idleSpriteSheet;
        }
    }

    private int getCurrentFrameCount() {
        switch (state) {
            case IDLE:
                return 9;
            case RUN:
                return 6;
            case ATTACK:
                return 12;
            case HURT:
                return 5;
            case DEATH:
                return 23;
            default:
                return 1;
        }
    }

}

