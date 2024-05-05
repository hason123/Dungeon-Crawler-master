package entity;

import main.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

public class Bossattack {
    enum AttackType {
        LUADEN, GIATSET
    }

    private BufferedImage[][] sprites;
    private AttackType currentAttackType = AttackType.LUADEN;
    private int[] maxFrames = {10, 7};
    private int currentFrame = 0;
    private int frameDelay = 0;
    private int frameDelayLimit = 10;
    private boolean attacking = false;
    private int width = 32;
    private int height = 32;
    private int worldX, worldY;
    private GamePanel gp;
    private int attackTimer = 0;
    private int attackCooldown = 10;
    private int attackDelay = 100;
    private int attackStartTimer = 0;
    private Random rand = new Random();

    public Bossattack(GamePanel gp) {
        this.gp = gp;
        loadSprites();
    }

    private void loadSprites() {
        sprites = new BufferedImage[2][];
        try {
            sprites[0] = new BufferedImage[10];
            for (int i = 0; i < 10; i++) {
                sprites[0][i] = ImageIO.read(getClass().getResourceAsStream("/boss/luaden0" + i + ".png"));
            }
            sprites[1] = new BufferedImage[7];
            for (int i = 0; i < 7; i++) {
                sprites[1][i] = ImageIO.read(getClass().getResourceAsStream("/boss/giatset" + i + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startAttack(AttackType type) {
        attacking = true;
        currentAttackType = type;
        currentFrame = 0;
        frameDelay = 0;
    }

    public void update() {
        attackStartTimer++;
        if (attacking) {
            frameDelay++;
            if (frameDelay >= frameDelayLimit) {
                currentFrame = (currentFrame + 1) % maxFrames[currentAttackType.ordinal()];
                frameDelay = 0;
                if (currentFrame == 0) {
                    attacking = false;
                }
            }
        } else {
            attackTimer++;
            if (attackTimer >= attackCooldown) {
                startRandomAttack();
                attackTimer = 0;
            }
        }
    }

    private void startRandomAttack() {
        setAttackPositionRelativeToPlayer();
        startAttack(rand.nextBoolean() ? AttackType.LUADEN : AttackType.GIATSET);
    }

    private void setAttackPositionRelativeToPlayer() {
        worldX = gp.tileSize * 6;
        worldY = gp.tileSize * 38;
    }

    public void draw(Graphics2D g2) {
        if (attacking && attackStartTimer >= attackDelay) {
            BufferedImage image = getCurrentSprite();
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;
            g2.drawImage(image, screenX, screenY, width * 5, height * 5, null);
        }
    }

    private BufferedImage getCurrentSprite() {
        return sprites[currentAttackType.ordinal()][currentFrame];
    }
}
