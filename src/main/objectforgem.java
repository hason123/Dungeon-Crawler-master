package main;

import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import entity.Player; // Giả sử lớp Player nằm trong package entity
import tile.TileManager;

public abstract class objectforgem {
    protected Point position; // Vị trí của đối tượng trên bản đồ
    protected BufferedImage image; // Hình ảnh của đối tượng

    public objectforgem(int x, int y, String imagePath, int width, int height) {
        this.position = new Point(x, y);
        this.image = loadImage(imagePath, width, height);
    }
    private BufferedImage loadImage(String path, int width, int height) {
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
class Key extends objectforgem {
    // Đối tượng người chơi cần được truyền vào để xác định vị trí màn hình của chìa khóa
    private Player player;

    public Key(int x, int y, Player player) {
        super(x, y, "/object/key.png", 16, 16); // Kích thước hình ảnh của chìa khóa là 16x16 pixel
        this.player = player;
    }

    @Override
    public void interact() {

    }

    @Override
    public void draw(Graphics2D g2) {
        if (image != null) {
            // Tính toán vị trí của chìa khóa trên màn hình dựa vào vị trí của người chơi
            int screenX = position.x - player.worldX + player.screenX;
            int screenY = position.y - player.worldY + player.screenY;

            g2.drawImage(image, screenX, screenY, image.getWidth() * 3, image.getHeight() * 3, null);
        }
    }
}

class LockedDoor extends objectforgem {
    private boolean isOpen;
    private Player player; // Thêm tham chiếu đến đối tượng Player

    public LockedDoor(int x, int y, Player player) {
        super(x, y, "/object/door.png", 32, 32); // LockedDoor image is 32x32 pixels
        this.isOpen = false;
        this.player = player; // Khởi tạo tham chiếu người chơi
    }

    @Override
    public void interact() {

    }
    public void unlock() {

    }
    @Override
    public void draw(Graphics2D g2) {
        if (image != null) {
            int screenX = position.x - player.worldX + player.screenX;
            int screenY = position.y - player.worldY + player.screenY;

            g2.drawImage(image, screenX, screenY, image.getWidth() * 2, image.getHeight() * 2, null);
        }
    }
}

class OpenDoor extends objectforgem {
    private Player player; // Thêm tham chiếu đến đối tượng Player

    public OpenDoor(int x, int y, Player player) {
        super(x, y, "/object/door_open.png", 32, 32); // OpenDoor image is 32x32 pixels
        this.player = player; // Khởi tạo tham chiếu người chơi
    }

    @Override
    public void interact() {

    }
    @Override
    public void draw(Graphics2D g2) {
        if (image != null) {
            int screenX = position.x - player.worldX + player.screenX;
            int screenY = position.y - player.worldY + player.screenY;

            g2.drawImage(image, screenX, screenY, image.getWidth() * 2, image.getHeight() * 2, null);
        }
    }
}

