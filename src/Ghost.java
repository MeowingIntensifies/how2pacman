import javax.swing.*;
import java.awt.*;

public class Ghost extends Pacman{

    public static final int RED =  1;
    public static final int BLUE = 3;
    public static final int ORANG = 4;
    public static final int PINK = 2;
    public static final int SLOW = 1;

    private int ghostType;
    private boolean isAlive;
    private int respawnTimer;
    private int spawnTimer;
    private int timer;
    private boolean frigtened;
    private boolean scaredOnce;
    private int clydePointX;
    private int clydePointY;

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

    public Rectangle getCollisionSprite(int direction) {                                    //dostosowane do obrazka 50x50

        switch (direction) {
            case LEFT: {
                return new Rectangle(getXPosition() - getSpeed(), getYPosition(), getWidth(), getHeight() );
            }
            case RIGHT: {
                return new Rectangle(getXPosition(), getYPosition(), getWidth() + getSpeed(), getHeight());
            }
            case UP: {
                return new Rectangle(getXPosition(), getYPosition() - getSpeed(), getWidth(), getHeight());
            }
            case DOWN: {
                return new Rectangle(getXPosition(), getYPosition(), getWidth(), getHeight() + getSpeed());
            }
            default: {
                return new Rectangle(getXPosition(), getYPosition(), getWidth(), getHeight());              // nie powinien się nigdy wczytać
            }
        }

    }

    public int getGhostType(){
        return ghostType;
    }

    public void changeImage( int direction) {
        ImageIcon oo = new ImageIcon("Blinkey\\BlinkeyLeftGif.gif");
        if(isFrigtened()) {

            oo = new ImageIcon("ScaredGif.gif");

        }else if(getTimer() < Board.TRANSPARENT_TIMER){
            switch (ghostType) {
                case RED: {
                    oo = new ImageIcon("Blinkey\\BlinkeyLeftTransparent.png");
                    break;
                }
                case BLUE: {
                    oo = new ImageIcon("Inky\\InkyLeftTransparent.png");
                    break;
                }
                case ORANG: {
                    oo = new ImageIcon("Clyde\\ClydeLeftTransparent.png");
                    break;
                }
                case PINK: {
                    oo = new ImageIcon("Pinky\\PinkyLeftTransparent.png");
                    break;
                }
            }

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
                            oo = new ImageIcon("Inky\\InkyLeftGif.gif");
                            break;
                        }
                        case RIGHT: {
                            oo = new ImageIcon("Inky\\InkyRightGif.gif");
                            break;
                        }
                        case UP: {
                            oo = new ImageIcon("Inky\\InkyUpGif.gif");
                            break;
                        }
                        case DOWN: {
                            oo = new ImageIcon("Inky\\InkyDownGif.gif");
                            break;
                        }
                    }
                    break;
                }
                case ORANG: {
                    switch (direction) {
                        case LEFT: {
                            oo = new ImageIcon("Clyde\\ClydeLeftGif.gif");
                            break;
                        }
                        case RIGHT: {
                            oo = new ImageIcon("Clyde\\ClydeRightGif.gif");
                            break;
                        }
                        case UP: {
                            oo = new ImageIcon("Clyde\\ClydeUpGif.gif");
                            break;
                        }
                        case DOWN: {
                            oo = new ImageIcon("Clyde\\ClydeDownGif.gif");
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

    public void setClydePointX(int nextInt) {this.clydePointX = nextInt;}
    public void setClydePointY(int nextInt) { this.clydePointY = nextInt;}

    public int getClydePointX() {
        return clydePointX;
    }

    public int getClydePointY() {
        return clydePointY;
    }
}
