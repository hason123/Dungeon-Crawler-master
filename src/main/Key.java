package main;

import entity.Player;
import main.LockedDoor;
import java.awt.*;

public class Key extends objectforgem {
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
