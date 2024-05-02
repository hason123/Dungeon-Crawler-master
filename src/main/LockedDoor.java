package main;

import entity.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LockedDoor extends objectforgem {
    public boolean isLocked = true;
    private Player player; // Thêm tham chiếu đến đối tượng Player
    private GamePanel gamePanel;
    private BufferedImage openImage;
    private int width = 32; // chiều rộng của cánh cửa
    private int height = 32; // chiều cao của cánh cửa
    public LockedDoor(int x, int y, Player player, GamePanel gamePanel) {
        super(x, y, "/object/door.png", 32, 32); // LockedDoor image is 32x32 pixels
        this.player = player;
        this.gamePanel = gamePanel;
        // Tải hình ảnh cửa mở
        this.openImage = loadImage("/object/door_open.png", 32, 32);
    }

    public boolean doorOpened = false;

    public void unlock() {
        int distanceThreshold = 50;
        int playerX = player.getWorldX();
        int playerY = player.getWorldY();

        int distanceX = Math.abs(this.position.x - playerX);
        int distanceY = Math.abs(this.position.y - playerY);

        if (distanceX < distanceThreshold && distanceY < distanceThreshold) {
            this.image = openImage; // Thay đổi hình ảnh sang cửa mở
            isLocked = false; // Đánh dấu cửa đã mở
            player.useKey(); // Người chơi sử dụng chìa khóa để mở khóa
            doorOpened = true;
        }
    }
    public boolean checkCollision(int playerX, int playerY) {
        // Kích thước của người chơi là 32x32, kích thước của cửa là 64x64
        Rectangle playerRect = new Rectangle(playerX, playerY, 32, 32);
        Rectangle doorRect = new Rectangle(this.position.x, this.position.y, 64, 64);

        return doorRect.intersects(playerRect);
    }
    @Override
    public void interact() {
        if (isLocked && player.hasKey()) {
            unlock();
        }
    }

    @Override
    public void draw(Graphics2D g2) {

        int screenX = position.x - player.worldX + player.screenX;
        int screenY = position.y - player.worldY + player.screenY;

        g2.drawImage(image, screenX, screenY, image.getWidth() * 2, image.getHeight() * 2, null);

    }
}
