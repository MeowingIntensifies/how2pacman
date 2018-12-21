import javax.swing.*;
import java.awt.*;

public class Ghost extends Pacman{

    public static final int RED =  1;
    public static final int BLUE = 4;
    public static final int ORANG = 3;
    public static final int PINK = 2;
    public static final int SLOW = 1;

    private int ghostType;
    private boolean isAlive;
    private int respawnTimer;
    private int spawnTimer;
    private int timer;
    private boolean frigtened;
    private boolean scaredOnce;

    public Ghost(int position_x, int position_y, int direction, int ghostType, int spawnTimer, int respawnTimer) {
        super(position_x, position_y, direction);
        this.ghostType = ghostType;
        this.spawnTimer = spawnTimer;
        this.respawnTimer = respawnTimer;
        this.timer = spawnTimer;
        this.isAlive = false;
        this.scaredOnce = false;

        this.loadImage(ghostType);


    }

    public int getGhostType(){
        return ghostType;
    }

    public void changeImage( int direction) {
        ImageIcon oo = new ImageIcon("Blinkey\\BlinkeyLeftGif.gif");
        if(isFrigtened()) {
            oo = new ImageIcon("ScaredGif.gif");
        }
        else{
            switch (ghostType) {
                case RED: {
                    switch (direction) {
                        case LEFT: {
                            oo = new ImageIcon("Blinkey\\BlinkeyLeftGif.gif");
                            break;
                        }
                        case RIGHT: {
                            oo = new ImageIcon("Blinkey\\BlinkeyRightGif.gif");
                            break;
                        }
                        case UP: {
                            oo = new ImageIcon("Blinkey\\BlinkeyUpGif.gif");
                            break;
                        }
                        case DOWN: {
                            oo = new ImageIcon("Blinkey\\BlinkeyDownGif.gif");
                            break;
                        }
                    }

                    break;
                }
                case BLUE: {
                    switch (direction) {
                        case LEFT: {
                            oo = new ImageIcon("blueGhostLeft.png");
                            break;
                        }
                        case RIGHT: {
                            oo = new ImageIcon("blueGhostRight.png");
                            break;
                        }
                        case UP: {
                            oo = new ImageIcon("blueGhostUp.png");
                            break;
                        }
                        case DOWN: {
                            oo = new ImageIcon("blueGhostDown.png");
                            break;
                        }
                    }
                    break;
                }
                case ORANG: {
                    switch (direction) {
                        case LEFT: {
                            oo = new ImageIcon("orangGhostLeft.png");
                            break;
                        }
                        case RIGHT: {
                            oo = new ImageIcon("orangGhostRight.png");
                            break;
                        }
                        case UP: {
                            oo = new ImageIcon("orangGhostUp.png");
                            break;
                        }
                        case DOWN: {
                            oo = new ImageIcon("orangGhostDown.png");
                            break;
                        }
                    }
                    break;
                }
                case PINK: {
                    switch (direction) {
                        case LEFT: {
                            oo = new ImageIcon("Pinky\\PinkyLeftGif.gif");
                            break;
                        }
                        case RIGHT: {
                            oo = new ImageIcon("Pinky\\PinkyRightGif.gif");
                            break;
                        }
                        case UP: {
                            oo = new ImageIcon("Pinky\\PinkyUpGif.gif");
                            break;
                        }
                        case DOWN: {
                            oo = new ImageIcon("Pinky\\PinkyDownGif.gif");
                            break;
                        }
                    }
                    break;
                }
            }
        }
        image = oo.getImage();
        w = image.getWidth(null);
        h = image.getHeight(null);
    }


    public void loadImage(int ghostType) {
        ImageIcon oo = new ImageIcon("redGhost.png");
        switch (ghostType) {
            case RED: {
                oo = new ImageIcon("Blinkey\\BlinkeyRightGif.gif");
                break;
            }
            case BLUE: {
                oo = new ImageIcon("blueGhost.png");
                break;
            }
            case ORANG: {
                oo = new ImageIcon("orangGhost.png");
                break;
            }
            case PINK: {
                oo = new ImageIcon("Pinky\\PinkyRightGif.gif");
                break;
            }
        }
        image = oo.getImage();
        w = image.getWidth(null);
        h = image.getHeight(null);
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public int getRespawnTimer() {
        return respawnTimer;
    }

    public void setRespawnTimer(int respawnTimer) {
        this.respawnTimer = respawnTimer;
    }

    public int getSpawnTimer() {
        return spawnTimer;
    }

    public void setSpawnTimer(int spawnTimer) {
        this.spawnTimer = spawnTimer;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public void setFrightened(boolean b) {
        frigtened = b;
    }

    public boolean isFrigtened() {
        return frigtened;
    }

    public boolean wasScaredOnce() {return scaredOnce; }

    public void setScaredOnce(boolean b) { scaredOnce = b; }
}
