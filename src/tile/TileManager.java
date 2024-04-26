package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    int mapTileNumber[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[1000];
        mapTileNumber = new int[gp.maxWorldCol][gp.maxWorldRow];//Luu tru du lieu cua cac file Map.txt chua thu tu sap xep cac Tiles

        getTileImage();
        loadMap("res/map/worldmap");// cần phải sửa  đường dẫn này
    }

    public void getTileImage() {//Luu tru hinh anh cua tung Tile
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/000.png"));
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/001.png"));
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/002.png"));
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/003.png"));
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/004.png"));




        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String mapPath){//Doc du lieu file .txt chua thong tin Map
        try{
            //Hai dong lenh thuc hien viec doc file .txt
            InputStream is = getClass().getResourceAsStream(mapPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col<gp.maxWorldRow && row<gp.maxWorldRow){
                String line = br.readLine(); //Lay du lieu o mot dong

                while(col<gp.maxWorldCol){//Luu du lieu cac Tiles vao Array tuong ung
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);//Chuyen du lieu dang String thanh Integer
                    mapTileNumber[col][row] = num;
                    col++;
                }
                if(col==gp.maxWorldCol){
                    col=0;
                    row++;
                }
            }
            br.close();
        }catch(Exception e) {}
    }

    public void draw(Graphics2D g2){

        //Ve ban do dua tren du lieu tu cac file Map.txt
        int worldCol = 0;
        int worldRow = 0;
        int worldX = worldCol * gp.tileSize;
        int worldY = worldRow * gp.tileSize;
        int screenx = worldX - gp.player.worldx + gp.player.screenX;
        int screeny = worldY - gp.player.worldy + gp.player.screenY;
        int tileNumber = mapTileNumber[worldCol][worldRow];
        if(worldX> gp.player.worldx-gp.player.screenX && worldX < gp.player.worldx+ gp.player.screenX
        && worldY> gp.player.worldy-gp.player.screenY && worldY < gp.player.worldy+ gp.player.screenY) {
            g2.drawImage(tile[tileNumber].image,screeny,screeny,gp.tileSize,gp.tileSize,null);
        }
            worldCol++;
            if(worldCol==gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;

            }

    }
}
