package main;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

    public class sound {
        private Clip clip;

        public sound(String resourcePath) {
            try {
                // Tải âm thanh sử dụng Class Loader
                InputStream is = getClass().getResourceAsStream(resourcePath);

                AudioInputStream ais = AudioSystem.getAudioInputStream(is);
                clip = AudioSystem.getClip();
                clip.open(ais);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }

        public void start() {
            if (clip != null && !clip.isRunning()) {
                clip.setFramePosition(0);
                clip.start();
            }
        }

        public void loop() {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }

        public void stop() {
            if (clip != null && clip.isRunning()) {
                clip.stop();
            }
        }
        public void setVolume(float volume) {
            if (clip != null) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(volume); // volume in decibels
            }
        }
    }
