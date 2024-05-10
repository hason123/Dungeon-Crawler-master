package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import entity.Boss;
import javax.swing.JPanel;
import entity.Bossattack;
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
	public Key key0,key1,key2,key3,key4,key5,key6,key7,key8;
	public LockedDoor lockedDoor0, lockedDoor1, lockedDoor2,lockedDoor3,lockedDoor4,lockedDoor5,lockedDoor6,lockedDoor7,lockedDoor8;
	public Boss boss ;
	public Bossattack bossAttack;
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
	public final int titleScreen = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int gameCompletedState = 3;
	public final int gameOverState = 4;


	// SOUND
	public sound backgroundMusic;


	public GamePanel() {

		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyInput);
		this.setFocusable(true);

		//KEYS
		key0 = new Key(tileSize * 3, tileSize * 3 ,player);
		key1 = new Key(tileSize * 7, tileSize * 13 ,player);
		key2 = new Key(tileSize * 47, tileSize ,player);
		key3 = new Key((int) (tileSize * 38.5), tileSize * 20 ,player);
		key4 = new Key((int) (tileSize * 42.5), tileSize * 13 ,player);
		key5 = new Key((int) (tileSize * 42.5), tileSize * 20 ,player);
		key6 = new Key(tileSize * 44, tileSize * 34 ,player);
		key7 = new Key((int) (tileSize * 23.5), tileSize * 27 ,player);
		key8 = new Key((int) (tileSize * 27.5), (int) (tileSize * 39.5),player);







		// Thay đổi tọa độ phù hợp

		lockedDoor0 = new LockedDoor(tileSize * 11, tileSize * 11,player,this);
		lockedDoor1 = new LockedDoor(tileSize * 23, tileSize * 7,player,this);
		lockedDoor2 = new LockedDoor(tileSize * 34, tileSize * 8,player,this);
		lockedDoor3 = new LockedDoor(tileSize * 34, tileSize * 24,player,this);// Thay đổi tọa độ phù hợp
		lockedDoor4 = new LockedDoor(tileSize * 46, tileSize * 14,player,this);// Thay đổi tọa độ phù hợp
		lockedDoor5 = new LockedDoor(tileSize * 46, tileSize * 18,player,this);// Thay đổi tọa độ phù hợp
		lockedDoor6 = new LockedDoor(tileSize * 47, tileSize * 40,player,this);// Thay đổi tọa độ phù hợp
		lockedDoor7 = new LockedDoor(tileSize * 23, tileSize * 34,player,this);// Thay đổi tọa độ phù hợp
		lockedDoor8 = new LockedDoor(tileSize * 17, tileSize * 39,player,this);// Thay đổi tọa độ phù hợp





		// Thay đổi tọa độ phù hợp


		this.bossAttack = new Bossattack(this);
		this.boss = new Boss(this, bossAttack);


		//GAME SETUP
		backgroundMusic = new sound("/sound/Pixel 1.wav");
		gameState = titleScreen;
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
			boss.update();

			// KEYS
			key0.interact();
			key1.interact();
			key2.interact();
			key3.interact();
			key4.interact();
			key5.interact();
			key5.interact();
			key6.interact();
			key7.interact();
			key8.interact();

			//DOORS
			lockedDoor0.interact();
			lockedDoor1.interact();
			lockedDoor2.interact();
			lockedDoor3.interact();
			lockedDoor4.interact();
			lockedDoor5.interact();
			lockedDoor6.interact();
			lockedDoor7.interact();
			lockedDoor8.interact();


			if (player.getKeyCount() == 0 && !keyRemoved) {
				objects.remove(key0);
				objects.remove(key1);
				objects.remove(key2);
				objects.remove(key3);
				objects.remove(key4);
				objects.remove(key5);
				objects.remove(key6);
				objects.remove(key7);
				objects.remove(key8);// Loại bỏ chìa khóa khỏi danh sách đối tượng
				keyRemoved = true;  // Đánh dấu chìa khóa đã bị loại bỏ
			}
		}
		if(gameState == pauseState){//Trạng thái game tạm dừng (thêm menu hoặc gì đó)

		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		//TITLE SCREEN
		if (gameState == titleScreen) {
			ui.draw(g2);
		}
		else{
			// TILE
			tileM.draw(g2);

			//PLAYER
			player.draw(g2);
			boss.draw(g2);

			//UI
			ui.draw(g2);

			//OBJECT
			// KEYS
			key0.draw(g2);
			key1.draw(g2);
			key2.draw(g2);
			key3.draw(g2);
			key4.draw(g2);
			key5.draw(g2);
			key6.draw(g2);
			key7.draw(g2);
			key8.draw(g2);

			//DOORS
			lockedDoor0.draw(g2);
			lockedDoor1.draw(g2);
			lockedDoor2.draw(g2);
			lockedDoor3.draw(g2);
			lockedDoor4.draw(g2);
			lockedDoor5.draw(g2);
			lockedDoor6.draw(g2);
			lockedDoor7.draw(g2);
			lockedDoor8.draw(g2);
		}

		g2.dispose();
	}
//thuantaocomment de congligs
	public void playBackgroundMusic() {
		backgroundMusic.start();
		backgroundMusic.loop();
	}
	public void stopBackgroundMusic() {
		backgroundMusic.stop();
	}
}