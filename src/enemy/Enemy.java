package enemy;

import main.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public abstract class Enemy {
    enum State {
        IDLE, RUN, ATTACK, HURT, DEATH
    }

    protected BufferedImage[] sprites;
    protected State state;
    protected int worldX, worldY;
    protected int speed;
    protected GamePanel gp;

    public Enemy(GamePanel gp, int worldX, int worldY, int speed) {
        this.gp = gp;
        this.worldX = worldX;
        this.worldY = worldY;
        this.speed = speed;
        this.state = State.IDLE;
        loadSprites();
    }

    protected abstract void loadSprites();
    // Cac trang thai cua Enemy
    public void update() {
        switch (state) {
            case IDLE:
                if (isPlayerInRange()) {
                    state = State.RUN;
                }
                break;
            case RUN:
                moveToPlayer();
                if (isPlayerInAttackRange()) {
                    state = State.ATTACK;
                } else if (!isPlayerInRange()) {
                    state = State.IDLE;
                }
                break;
            case ATTACK:
                attackPlayer();
                if (!isPlayerInAttackRange()) {
                    state = State.RUN;
                }
                break;
            case HURT:

                break;
            case DEATH:

                break;
        }
    }

    protected boolean isPlayerInRange() {
        int playerX = gp.player.getWorldX();
        int playerY = gp.player.getWorldY();
        return Math.hypot(worldX - playerX, worldY - playerY) <= 200; //tầm nhìn
    }

    protected boolean isPlayerInAttackRange() {
        int playerX = gp.player.getWorldX();
        int playerY = gp.player.getWorldY();
        return Math.hypot(worldX - playerX, worldY - playerY) <= 25; //tầm đánh
    }

    protected void moveToPlayer() {
        int playerX = gp.player.getWorldX();
        int playerY = gp.player.getWorldY();
        if (worldX < playerX) {
            worldX += speed;
        } else if (worldX > playerX) {
            worldX -= speed;
        }

        if (worldY < playerY) {
            worldY += speed;
        } else if (worldY > playerY) {
            worldY -= speed;
        }
    }

    protected void attackPlayer() {
        gp.player.receiveDamage(2);
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(sprites[state.ordinal()], worldX, worldY, null);
    }
}
