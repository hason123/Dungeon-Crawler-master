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
        mapTileNumber = new int[gp.maxScreenCol][gp.maxScreenRow];//Luu tru du lieu cua cac file Map.txt chua thu tu sap xep cac Tiles

        getTileImage();
        loadMap("/map/blabla.txt");
    }

    public void getTileImage() {//Luu tru hinh anh cua tung Tile
        try {
            for(int j=0; j<75; j++){
                tile[j] = new Tile();
                if(j<10) {tile[j].image = ImageIO.read(getClass().getResourceAsStream("/tiles/00"+j+".png"));}
                else {tile[j].image = ImageIO.read(getClass().getResourceAsStream("/tiles/0"+ j +".png"));}
            }

        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    //Hien chi Load duoc map co kich thuoc 15x12 ??
    public void loadMap(String mapPath){//Doc du lieu file .txt chua thong tin Map
        try{
            //Hai dong lenh thuc hien viec doc file .txt
            InputStream is = getClass().getResourceAsStream(mapPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col<gp.maxScreenCol && row<gp.maxScreenRow){
                String line = br.readLine(); //Lay du lieu o mot dong

                while(col<gp.maxScreenCol){//Luu du lieu cac Tiles vao Array tuong ung
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);//Chuyen du lieu dang String thanh Integer
                    mapTileNumber[col][row] = num;
                    col++;
                }
                if(col==gp.maxScreenCol){
                    col=0;
                    row++;
                }
            }
            br.close();
        }catch(Exception e) {}
    }

    public void draw(Graphics2D g2){

        //Ve ban do dua tren du lieu tu cac file Map.txt
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col<gp.maxScreenCol && row<gp.maxScreenRow) {
            int tileNumber = mapTileNumber[col][row];
            g2.drawImage(tile[tileNumber].image,x,y,gp.tileSize,gp.tileSize,null);
            col++;
            x += gp.tileSize;

            if(col==gp.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }
    }
}
