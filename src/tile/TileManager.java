package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {

	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];

	public TileManager(GamePanel gp) {
		this.gp = gp;

		tile = new Tile[100];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

		getTileImage();
		loadMap("/map/worldmap.txt");
	}

	public void getTileImage() {
		
		//System.out.println("Image loading started");

		try {

			for(int j=0; j<75; j++){
				tile[j] = new Tile();
				if(j<10) {tile[j].image = ImageIO.read(getClass().getResourceAsStream("/tiles/00"+j+".png"));}
				else {tile[j].image = ImageIO.read(getClass().getResourceAsStream("/tiles/0"+j+".png"));}
			}
			tile[0].collision = true;
			for(int i = 8;i<= 36;i++){
				tile[i].collision = true;
			}
			for(int i = 40;i<= 42;i++){
				tile[i].collision = true;
			}
			for(int i = 49 ;i<= 55;i++){
				tile[i].collision = true;
			}
			tile[57].collision = true;
			for(int i = 59;i<= 66;i++){
				tile[i].collision = true;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		//System.out.println("Image loading finished");
	}

	public void loadMap(String filePath) {

		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			int col = 0;
			int row = 0;

			while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

				String line = br.readLine();

				while (col < gp.maxWorldCol) {
					String numbers[] = line.split(" ");

					int num = Integer.parseInt(numbers[col]);

					mapTileNum[col][row] = num;
					col++;
				}
				if (col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();

		} catch (Exception e) {

		}

	}

	public void draw(Graphics2D g2) {

		int worldCol = 0;
		int worldRow = 0;

		while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

			int tileNum = mapTileNum[worldCol][worldRow];

			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			//if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
					//worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
					//worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
					//worldY - gp.tileSize < gp.player.worldY + gp.player.screenY ) {
				
			g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			//}

			
			worldCol++;

			if (worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;

			}

		}

	}

}
