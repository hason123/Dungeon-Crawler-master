package main;

import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import entity.Player;
import tile.TileManager;

public abstract class objectforgem {
    protected Point position; // Vị trí của đối tượng trên bản đồ
    protected BufferedImage image; // Hình ảnh của đối tượng

    public objectforgem(int x, int y, String imagePath, int width, int height) {
        this.position = new Point(x, y);
        this.image = loadImage(imagePath, width, height);
    }
    protected BufferedImage loadImage(String path, int width, int height) {
        try {
            BufferedImage originalImage = ImageIO.read(getClass().getResourceAsStream(path));
            BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = scaledImage.createGraphics();
            g2d.drawImage(originalImage, 0, 0, width, height, null);
            g2d.dispose();
            return scaledImage;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public abstract void interact();
    public abstract void draw(Graphics2D g2);

    public void setPosition(int x, int y) {
        this.position.setLocation(x, y);
    }

    public Point getPosition() {
        return this.position;
    }
}
class Key extends objectforgem  {
    private Player player;
    private boolean followPlayer = false;
    private boolean pickedUp = false; // Thêm biến để kiểm tra xem chìa khóa đã được nhặt hay chưa
    public Key(int x, int y, Player player) {
        super(x, y, "/object/key.png", 16, 16);
        this.player = player;
    }

    @Override
    public void interact() {
        int distanceThreshold = 50; // Khoảng cách cho phép chìa khóa bắt đầu theo người chơi
        int playerX = player.getWorldX();
        int playerY = player.getWorldY();

        int distanceX = Math.abs(this.position.x - playerX);
        int distanceY = Math.abs(this.position.y - playerY);

        if (distanceX < distanceThreshold && distanceY < distanceThreshold) {
            followPlayer = true;
            player.pickUpKey(); // Người chơi nhặt chìa khóa
            pickedUp = true; // Đánh dấu chìa khóa đã được nhặt
        }

    }
    public void stopFollowing() {
        followPlayer = false;  // Dừng theo người chơi
    }

    @Override
    public void draw(Graphics2D g2) {
        int screenX, screenY;

        // Chỉ vẽ chìa khóa nếu nó đang theo người chơi và chưa được sử dụng để mở cửa
        if (followPlayer && !pickedUp) {
            // Tính toán vị trí trên màn hình khi chìa khóa đang theo người chơi
            screenX = player.screenX;
            screenY = player.screenY;
        } else if (!pickedUp) {
            // Tính toán vị trí trên màn hình khi chìa khóa không theo người chơi và chưa được nhặt lên
            screenX = this.position.x - player.getWorldX() + player.screenX;
            screenY = this.position.y - player.getWorldY() + player.screenY;
        } else {
            // Nếu chìa khóa đã được sử dụng để mở cửa, không vẽ chìa khóa
            return;
        }

        if (image != null) {
            g2.drawImage(image, screenX, screenY, image.getWidth() * 3, image.getHeight() * 3, null);
        }
    }
}

class LockedDoor extends objectforgem {
    private boolean isLocked = true;
    private Player player; // Thêm tham chiếu đến đối tượng Player
    private GamePanel gamePanel;
    private BufferedImage openImage;

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

