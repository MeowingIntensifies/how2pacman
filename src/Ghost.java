import javax.swing.*;
import java.awt.*;

public class Ghost extends Pacman{

    public static final int RED =  1;
    public static final int BLUE = 2;
    public static final int ORANG = 3;
    public static final int PINK = 4;

    private int ghostType;

    public Ghost(int position_x, int position_y, int direction, int ghostType) {
        super(position_x, position_y, direction);
        this.ghostType = ghostType;
    }
    public int getGhostType(){
        return ghostType;
    }

    public void changeImage( int direction) {
        ImageIcon oo = new ImageIcon("pacWHOLE.png");
        switch (ghostType) {
            case RED: {
                switch (direction) {
                    case LEFT: {
                        oo = new ImageIcon("pacWHOLE.png");
                    }
                    case RIGHT: {
                        oo = new ImageIcon("pacWHOLE.png");
                    }
                    case UP: {
                        oo = new ImageIcon("pacWHOLE.png");
                    }
                    case DOWN: {
                        oo = new ImageIcon("pacWHOLE.png");
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
                        oo = new ImageIcon("pinkGhostLeft.png");
                        break;
                    }
                    case RIGHT: {
                        oo = new ImageIcon("pinkGhostRight.png");
                        break;
                    }
                    case UP: {
                        oo = new ImageIcon("pinkGhostUp.png");
                        break;
                    }
                    case DOWN: {
                        oo = new ImageIcon("pinkGhostDown.png");
                        break;
                    }
                }
                break;
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
                oo = new ImageIcon("pacWHOLE.png");
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
                oo = new ImageIcon("pinkGhost.png");
                break;
            }
        }
        image = oo.getImage();
        w = image.getWidth(null);
        h = image.getHeight(null);
    }

}
