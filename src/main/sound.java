package main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

 class sound {
    private Clip clip;

    public sound(String path) {
        try {
            // Tạo một AudioInputStream từ một file
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
            // Lấy một Clip để phát AudioInputStream
            clip = AudioSystem.getClip();
            // Mở AudioInputStream vào Clip và chuẩn bị nó để phát
            clip.open(ais);
            // Đặt Clip để lặp lại liên tục
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        if (clip != null && !clip.isRunning()) {
            clip.start();  // Bắt đầu phát
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();   // Dừng phát
            clip.close();  // Đóng clip để giải phóng tài nguyên
        }
    }
}
