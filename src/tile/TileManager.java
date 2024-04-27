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
        loadMap("/map/worldmap.txt");// cần phải sửa  đường dẫn này
    }

    public void getTileImage() {//Luu tru hinh anh cua tung Tile
        try {
            for(int j=0; j<75; j++){
                tile[j] = new Tile();
                if(j<10) {tile[j].image = ImageIO.read(getClass().getResourceAsStream("/tiles/00"+j+".png"));}
                else {tile[j].image = ImageIO.read(getClass().getResourceAsStream("/tiles/0"+j+".png"));}
            }

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

            while(col<gp.maxWorldCol && row<gp.maxWorldRow){
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
        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNumber[worldCol][worldRow]; // extract a tile number which is stored in mapTileNum[0][0]

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            worldCol++;
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;

            }
        }
    }
}
