package main;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

import java.awt.*;
import java.awt.Dimension;
import java.awt.Color;
import entity.Player;
import tile.TileManager;

// kich co man hinh co the chinh linh hoat
public class GamePanel extends JPanel implements Runnable{
        public final int originalTileSize = 16; // (nhan vat va map kich co goc la 16x16 pixels)
        public final int scale = 3;
        public final int tileSize = originalTileSize * scale; // 16 * 3 = 48  (man hinh hien thi nhan vat va map kich co 48x48 pixel)

        public final int maxScreenCol = 16;
        public final int maxScreenRow = 12;
        public final int screenWidth = tileSize * maxScreenCol; //Man hinh hien thi chieu dai 64 x 15 = 960 pixels
        public final int screenHeight = tileSize * maxScreenRow; // Man hinh hien thi chieu rong  64 x 12= 768 pixels

            // world setting
        public final int maxWorldCol = 50;
        public final int maxWorldRow = 50;
        public final int worldWidth = tileSize*maxWorldCol;
        public final int worldHeight = tileSize*maxWorldRow;

        Thread gameThread; // 1 luong giu game chay cho den khi ban ket thuc no
        KeyboardInput keyInput = new KeyboardInput();
        public Player player = new Player (this,keyInput);

        TileManager tileM = new TileManager(this);

        //Trong Java vi tri goc trai tren cung man hinh la (0,0), (X,Y), X tang khi phia ben trai, Y tang khi ve phia ben duoi.
        int FPS = 60; // game chi chay 60 khung hinh/s
        public CollisionChecker cChecker = new CollisionChecker(this);

    private Key key;
    private LockedDoor lockedDoor;
    private OpenDoor openDoor;

        public GamePanel(){
            this.setPreferredSize(new Dimension(screenWidth,screenHeight));
            this.setBackground(Color.black);
            this.setDoubleBuffered(true);
            this.addKeyListener(keyInput);
            this.setFocusable(true);
            // Khởi tạo các đối tượng
            key = new Key(100, 100,player); // Thay đổi tọa độ phù hợp
            lockedDoor = new LockedDoor(300, 100,player); // Thay đổi tọa độ phù hợp
            openDoor = new OpenDoor(500, 100,player); // Thay đổi tọa độ phù hợp
        }

        public void startGameThread(){
            gameThread = new Thread(this);
            gameThread.start();
        }
        
        @Override
        public void run(){
    
            double drawInterval = 1000000000/FPS;
            double delta = 0;
            long lastTime = System.nanoTime();
            long currentTime;
    


            while(gameThread != null){
            
                currentTime = System.nanoTime();

                delta += (currentTime - lastTime) / drawInterval;
                lastTime = currentTime;
                if(delta >= 1)
                {   
                    update();
                
                    repaint();

                    delta--;
    
                }
            }
        }   


        
        public void update(){
           
            player.update();
        
        }



        public void paintComponent(Graphics g){ // phương thức paintComponent() là một đối tượng lớp Graphics. Đối tượng này cho phép chúng ta vẽ các vật thể lên JPanel.
             super.paintComponent(g);

             Graphics2D g2 = (Graphics2D)g;

             tileM.draw(g2);
             player.draw(g2);
            key.draw(g2);
            lockedDoor.draw(g2);
            openDoor.draw(g2);


            g2.dispose();

        }
}
