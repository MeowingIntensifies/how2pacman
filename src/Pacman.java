import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;


public class Pacman {


    public static final int LEFT = 4;
    public static final int RIGHT = 2;
    public static final int DOWN = 3;
    public static final int UP = 1;
    private static final int STOPRIGHT = 20;
    private static final int STOPDOWN = 30;
    private static final int STOPLEFT = 40;
    private static final int STOPUP = 10;


    protected int position_x;

    public int getStartingX() {
        return startingX;
    }

    public int getStartingY() {
        return startingY;
    }

    protected int startingX;
    protected int startingY;
    protected int position_y;
    protected int direction;
    protected int lastDX;
    protected int lastDY;
    protected int dx;
    protected int dy;
    protected int w;
    protected int h;
    protected Image image;
    private Image lifeImage;
    private int bufferedDirection;
    private boolean driftingFlag;
    private int invurnerableFrames;
    private boolean bonusStatus;
    public static final int INVURNERABLE_BONUS = 1000;
    public static final int INVURNERABLE_DIE = 500 ;
    protected int speed;
    protected final int STANDARD_SPEED = 2;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Pacman(int position_x, int position_y, int direction) {
        this.position_x = position_x;
        this.position_y = position_y;
        this.direction = direction;

        this.startingX = position_x;
        this.startingY = position_y;

        this.speed = STANDARD_SPEED;
        this.invurnerableFrames = 0;
        this.bonusStatus = false;

        loadImage();
    }


    public void setXPositon(int x) {
        this.position_x = x;
    }

    public void setYPositon(int y) {
        this.position_y = y;
    }

    public int getXPosition() {
        return position_x;
    }

    public int getYPosition() {
        return position_y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    public Image getImage() {
        return image;
    }

    public void returnToStartingPoint(){
        setYPositon(startingY);
        setXPositon(startingX);
    }

    public void teleportToPoint(int x, int y){
        setYPositon(y);
        setXPositon(x);
    }

    public void setBufferedDirection(int direction) {
        bufferedDirection = direction;
    }

    public int getBufferedDirection() {
        return bufferedDirection;
    }

    public Rectangle getCollisionSprite() {

        if (dx < 0 || dy < 0) {
            return new Rectangle(getXPosition() + dx, getYPosition() + dy, getWidth(), getHeight() - 1);
        } else {
            return new Rectangle(getXPosition(), getYPosition(), getWidth() + dx, getHeight() - 1 + dy);
        }
    }

    public Rectangle getAIPlayerCollisionSprite() {

        if (dx < 0 || dy < 0) {
            return new Rectangle(getXPosition() + dx +12, getYPosition() + dy +12 , 25, 25);
        } else {
            return new Rectangle(getXPosition() +12 , getYPosition() +12 , 25+dx, 25+dy);
        }
    }

    public Rectangle getCollisionSprite(int direction) {

        switch (direction) {
            case LEFT: {
                return new Rectangle(getXPosition() - getSpeed(), getYPosition(), getWidth(), getHeight() - 1);
            }
            case RIGHT: {
                return new Rectangle(getXPosition(), getYPosition(), getWidth() + getSpeed(), getHeight() - 1);
            }
            case UP: {
                return new Rectangle(getXPosition(), getYPosition() - getSpeed(), getWidth(), getHeight() - 1);
            }
            case DOWN: {
                return new Rectangle(getXPosition(), getYPosition(), getWidth(), getHeight() - 1 + getSpeed());
            }
            default: {
                return new Rectangle(getXPosition(), getYPosition(), getWidth(), getHeight());              // nie powinien się nigdy wczytać
            }
        }

    }

    public void stop() {

        dx = 0;
        dy = 0;
    }


    public void move() {

        position_x += dx;
        position_y += dy;

        lastDX = dx;
        lastDY = dy;
    }

    public boolean isStopped() {
        if (dx == 0 && dy == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void setDYDX(int direction) {
        switch (direction) {
            case LEFT:
                this.dx = -getSpeed();
                break;
            case RIGHT:
                this.dx = getSpeed();
                break;
            case UP:
                this.dy = -getSpeed();
                break;
            case DOWN:
                this.dy = getSpeed();
                break;
        }
    }

    public void returnLastMove() {
        dx = lastDX;
        dy = lastDY;

        lastDY = 0;
        lastDY = 0;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -getSpeed();
            dy = 0;
            setDirection(LEFT);

        } else if (key == KeyEvent.VK_RIGHT) {

            dx = getSpeed();
            dy = 0;
            setDirection(RIGHT);
        } else if (key == KeyEvent.VK_UP) {

            dy = -getSpeed();
            dx = 0;
            setDirection(UP);
        } else if (key == KeyEvent.VK_DOWN) {

            dy = getSpeed();
            dx = 0;
            setDirection(DOWN);
        }


    }

    private void loadImage() {
        ImageIcon ii = new ImageIcon("pacLEFT.png");
        image = ii.getImage();
        lifeImage = ii.getImage();

        w = image.getWidth(null);
        h = image.getHeight(null);
    }

    public void changeImage(int direction) {
        ImageIcon oo = new ImageIcon("pacUP.png");
        switch (direction) {
            case UP: {
                oo = new ImageIcon("pacGifUp.gif");
                break;
            }
            case RIGHT: {
                oo = new ImageIcon("pacGifRight.gif");
                break;
            }
            case DOWN: {
                oo = new ImageIcon("pacGifDown.gif");
                break;
            }
            case LEFT: {
                oo = new ImageIcon("pacGifLeft.gif");
                break;
            }
            case STOPRIGHT: {
                oo = new ImageIcon("pacRIGHT.png");
                break;
            }
            case STOPDOWN: {
                oo = new ImageIcon("pacDOWN.png");
                break;
            }
            case STOPLEFT: {
                oo = new ImageIcon("pacLEFT.png");
                break;
            }
            case STOPUP: {
                oo = new ImageIcon("pacUP.png");
                break;
            }
        }
            image = oo.getImage();

    }

        public void setDriftingFlag ( boolean flag){
            if (flag == true) {
                driftingFlag = true;
            } else {
                driftingFlag = false;
            }
        }
        public boolean getDriftingFlag () {
            return driftingFlag;
        }

        public Image getLifesImage () {
            return lifeImage;
        }

    public void setInvurnerable(int time) {
        invurnerableFrames = time;
    }

    public void decreaseInvurnerableFrames(){
        if(invurnerableFrames > 0) {
            invurnerableFrames--;
        }
    }

    public int  getInvurnerableFrames() {
        return invurnerableFrames;
    }

    public boolean getBonusStatus() {
        return bonusStatus;
    }

    public void setBonusStatus(boolean b) {
        bonusStatus = b;
    }
}
