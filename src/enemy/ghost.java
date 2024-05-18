package enemy;

import main.GamePanel;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics2D;

public class ghost extends Enemy {

    private BufferedImage[] idleSprites, runSprites, attackSprites, hurtSprites, deathSprites;
    private int currentFrame = 0;
    private int frameDelay = 0; // Biến để đếm độ trễ
    private final int frameDelayLimit = 15;

    /*public NightBorne(GamePanel gp) {
        super(gp, 0, 0, 2);
        setDefaultValues();
        loadSprites();
    }

     */
    public ghost(GamePanel gp, int initialWorldX, int initialWorldY) {
        super(gp, initialWorldX, initialWorldY, 2);
        setDefaultValues();
        loadSprites();
    }

    public void setDefaultValues() {

        //worldX = gp.tileSize * 5;
        // worldY = gp.tileSize * 5;
    }

    @Override
    protected void loadSprites() {
        try {
            idleSprites = new BufferedImage[4];
            for (int i = 0; i < idleSprites.length; i++) {

                idleSprites[i] = ImageIO.read(getClass().getResourceAsStream("/enemy/ghost/idle/idle_" + i + ".png"));

            }

            runSprites = new BufferedImage[4];
            for (int i = 0; i < runSprites.length; i++) {
                runSprites[i] = ImageIO.read(getClass().getResourceAsStream("/enemy/ghost/idle/idle_" + i + ".png"));
            }

            attackSprites = new BufferedImage[4];
            for (int i = 0; i < attackSprites.length; i++) {
                attackSprites[i] = ImageIO.read(getClass().getResourceAsStream("/enemy/ghost/idle/idle_" +  i + ".png"));
            }

            hurtSprites = new BufferedImage[4];
            for (int i = 0; i < hurtSprites.length; i++) {
                hurtSprites[i] = ImageIO.read(getClass().getResourceAsStream("/enemy/ghost/hurt/hurt_" + i + ".png"));
            }

            deathSprites = new BufferedImage[8];
            for (int i = 0; i < deathSprites.length; i++) {
                deathSprites[i] = ImageIO.read(getClass().getResourceAsStream("/enemy/ghost/dead/dead_" + i + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage[] currentSpriteArray = getCurrentSpriteArray();
        if (currentFrame < 0 || currentFrame >= currentSpriteArray.length) {
            currentFrame = 0;
        }
        BufferedImage frame = currentSpriteArray[currentFrame];
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        int screenX = worldX - gp.player.getWorldX() + gp.screenWidth / 2 - frameWidth; // Trừ đi toàn bộ chiều rộng ban đầu
        int screenY = worldY - gp.player.getWorldY() + gp.screenHeight / 2 - frameHeight; // Trừ đi toàn bộ chiều cao ban đầu

        g2.drawImage(frame, screenX, screenY, frameWidth * 2, frameHeight * 2, null);
        updateFrame();
    }
    private void updateFrame() {
        frameDelay++;
        if (frameDelay >= frameDelayLimit) {
            currentFrame = (currentFrame + 1) % getCurrentSpriteArray().length;
            frameDelay = 0;
        }
    }

    private BufferedImage[] getCurrentSpriteArray() {
        switch (state) {
            case IDLE:
                return idleSprites;
            case RUN:
                return runSprites;
            case ATTACK:
                return attackSprites;
            case HURT:
                return hurtSprites;
            case DEATH:
                return deathSprites;
            default:
                return idleSprites;
        }
    }

}

