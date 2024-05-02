package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import main.LockedDoor;
import main.Key;
import javax.swing.JPanel;

import entity.Player;

import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
	// SCREEN SETTINGS
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 3;

	public final int tileSize = originalTileSize * scale; // 48x48 tile
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 768
	public final int screenHeight = tileSize * maxScreenRow; // 576
	public Key key;
	public LockedDoor lockedDoor;
	
	// WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;

	
	// FPS
	int FPS = 60;
	
	
	// SYSTEM
	public TileManager tileM = new TileManager(this);
	public KeyboardInput keyInput = new KeyboardInput(this);

	public CollisionChecker cChecker = new CollisionChecker(this);
	public UI ui = new UI(this);


	Thread gameThread;
	
	
	// ENTITY AND OBJECT
	public Player player = new Player(this, keyInput);

	// GAME STATE
	public int gameState;
	public final int playState = 1;
	public final int pauseState = 2;

	// SOUND
	public sound backgroundMusic;


	public GamePanel() {

		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyInput);
		this.setFocusable(true);
		key = new Key(100, 100,player); // Thay đổi tọa độ phù hợp
		lockedDoor = new LockedDoor(300, 100,player,this); // Thay đổi tọa độ phù hợp

		//GAME SETUP
		backgroundMusic = new sound("/sound/Pixel 1.wav");
		playBackgroundMusic();
		gameState = playState;
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		double drawInterval = 1_000_000_000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;

		while (gameThread != null) {

			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);

			lastTime = currentTime;

			if (delta >= 1) {

				update();
				repaint();
				delta--;
				drawCount++;
			}

			if (timer >= 1_000_000_000) {
				//System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}

		}
	}
	private List<objectforgem> objects = new ArrayList<>(); // Danh sách các đối tượng trên màn hình

	private boolean keyRemoved = false;
	public void update() {
		if(gameState == playState){//Trạng thái game hoạt động
			player.update();
			key.interact();
			lockedDoor.interact();
			if (player.isKeyUsed() && !keyRemoved) {
				objects.remove(key);  // Loại bỏ chìa khóa khỏi danh sách đối tượng
				keyRemoved = true;  // Đánh dấu chìa khóa đã bị loại bỏ
			}
		}
		if(gameState == pauseState){//Trạng thái game tạm dừng (thêm menu hoặc gì đó)

		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		// TILE
		tileM.draw(g2);

		//PLAYER
		player.draw(g2);

		//UI
		ui.draw(g2);
		key.draw(g2);
		lockedDoor.draw(g2);
		g2.dispose();

	}

	public void playBackgroundMusic() {
		backgroundMusic.start();
		backgroundMusic.loop();
	}
	public void stopBackgroundMusic() {
		backgroundMusic.stop();
	}
}
