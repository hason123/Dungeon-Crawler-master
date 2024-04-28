package main;

import entity.Entity;

public class CollisionChecker {

    GamePanel gp;
    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }
    public void checkTile(Entity entity){
        // Khong phai nguoi choi ma la ENtity vi không chỉ check va chạm với người chơi mà va chạm với quái và NPC
        // Va cham can duoc phat hien dua vao toa do solid area
        // 4 gia tri can check left x, rightx, topy,bottom y
        // solidAreax = 8; solidAreay = 16;solidwidth = solidheight = 32
        int entityLeftWorldX = entity.worldX+ entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x+entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
        // Dua vao toa do nay tim row and collum
        int entityLeftCol = entityLeftWorldX/ gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1,tileNum2;
        //Chung ta can kiem tra 2 o cho moi huong
        switch (entity.huongDi){

            case "up":
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
                //Du doan huong di nguoi choi
                tileNum1 = gp.tileM.mapTileNumber[entityLeftCol][entityTopRow];//Luu tru tat ca thong tin ban do
                tileNum2 = gp.tileM.mapTileNumber[entityRightCol][entityTopRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                //Du doan huong di nguoi choi
                tileNum1 = gp.tileM.mapTileNumber[entityLeftCol][entityBottomRow];//Luu tru tat ca thong tin ban do
                tileNum2 = gp.tileM.mapTileNumber[entityLeftCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
                //Du doan huong di nguoi choi
                tileNum1 = gp.tileM.mapTileNumber[entityLeftCol][entityTopRow];//Luu tru tat ca thong tin ban do
                tileNum2 = gp.tileM.mapTileNumber[entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case" right":
                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
                //Du doan huong di nguoi choi
                tileNum1 = gp.tileM.mapTileNumber[entityRightCol][entityTopRow];//Luu tru tat ca thong tin ban do
                tileNum2 = gp.tileM.mapTileNumber[entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;

        }

    }
}
