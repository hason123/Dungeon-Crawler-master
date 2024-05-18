package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {

    public boolean diTren, diXuong, diTrai, diPhai, attack,interact;
    GamePanel gp;

    public KeyboardInput(GamePanel gp) {
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //TITLE SCREEN
        if (gp.gameState == gp.titleScreen){
            if (e.getKeyCode() == KeyEvent.VK_W) {
                gp.ui.commandNumber--;
                if (gp.ui.commandNumber == 0) {gp.ui.commandNumber = 4;}
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                gp.ui.commandNumber++;
                if (gp.ui.commandNumber == 5) {gp.ui.commandNumber = 1;}
            }
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNumber != 4) {
                    switch (gp.ui.commandNumber) {
                        case 1:
                            gp.gameState = gp.playState;
                            gp.playBackgroundMusic();
                        case 2:
                            //Pending
                        case 3:
                            //Pending
                    }
                }
                    else {System.exit(0);}
            }
        }

        //PLAY STATE
        if (e.getKeyCode() == KeyEvent.VK_W) {
            diTren = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            diXuong = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            diTrai = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            diPhai = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            attack = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_M) {
            if(gp.gameState == gp.playState) {gp.gameState = gp.pauseState;}
            else if(gp.gameState == gp.pauseState) {gp.gameState = gp.playState;}
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            diTren = false;
       }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            diXuong = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            diTrai = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            diPhai = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            attack = false;
        }

    }
}

//	public boolean isAnyKeyPressed() {
//		if(diTren == true || diXuong == true || diTrai == true || diPhai == true) {
//			anyKeyPressed = true;
//		}
//		return anyKeyPressed;
// Phan o duoi la phan viet lai code
/*import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {

    public boolean diTren, diXuong, diTrai, diPhai;

    public KeyboardInput() {
        // Initialize all key states to false
        diTren = false;
        diXuong = false;
        diTrai = false;
        diPhai = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not needed for this implementation
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Set the appropriate key state to true when a key is pressed
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                diTren = true;
                break;
            case KeyEvent.VK_S:
                diXuong = true;
                break;
            case KeyEvent.VK_A:
                diTrai = true;
                break;
            case KeyEvent.VK_D:
                diPhai = true;
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Set the appropriate key state to false when a key is released
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                diTren = false;
                break;
            case KeyEvent.VK_S:
                diXuong = false;
                break;
            case KeyEvent.VK_A:
                diTrai = false;
                break;
            case KeyEvent.VK_D:
                diPhai = false;
                break;
            default:
                break;
        }
    }

    // Getters for the key states
    public boolean isdiTren() {
        return diTren;
    }

    public boolean isdiXuong() {
        return diXuong;
    }

    public boolean isdiTrai() {
        return diTrai;
    }

    public boolean isdiPhai() {
        return diPhai;
    }
}
*/
	
	
	


