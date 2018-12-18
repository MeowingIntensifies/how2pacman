import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class LevelMap {

    public static final int POINTSHIFT = 12;

    private int currentLevel;
    private int actual_map[][];
    private int sizeX;
    private int sizeY;
    private  Image blockImage;
    private Image pointImage;
    private Image bonusImage;
    private int blockWidth;
    private int blockHeight;
    private int pointWidth;
    private int pointHeight;


    private List<Rectangle> listaKolizjiMapy;
    private List<Rectangle> listaPunktowMapy;

    private int pacmanStartXPosition;
    private int pacmanStartYPosition;
    private int pacmanStartDirection;


    public LevelMap( int level){
        currentLevel = level;
        constructMap(currentLevel);
        loadImage();
        initCollision();

    }
    public Image getBlockImage() { return blockImage; }

    public Image getPointImage()  {return  pointImage;}

    public Image getBonusImage() { return  bonusImage;}

    public int getCurrentLevel(){
            return currentLevel;
    }

    public int getSizeX(){ return sizeX; }

    public int getSizeY(){ return sizeY; }

    public int getPacmanStartXPosition() { return pacmanStartXPosition; }

    public int getPacmanStartYPosition() { return pacmanStartYPosition; }

    public  int getPacmanStartDirection(){ return pacmanStartDirection; }

    public int getMapValue(int x, int y){
        return actual_map[x][y];
    }


    private  void loadImage(){
        ImageIcon ii = new ImageIcon("klocek.png");
        blockImage = ii.getImage();
        blockWidth = blockImage.getWidth(null);
        blockHeight = blockImage.getHeight(null);

        ii = new ImageIcon("point.png");
        pointImage = ii.getImage();
        pointWidth = blockImage.getWidth(null);
        pointHeight = blockImage.getHeight(null);

        ii = new ImageIcon("bonus.png");
        bonusImage = ii.getImage();


    }

    private void initCollision() {
        listaKolizjiMapy = new ArrayList<>();
        listaPunktowMapy = new ArrayList<>();

        for (int i = 0; i < this.getSizeX(); i++) {
            for (int j = 0; j < this.getSizeY(); j++) {
                if (this.getMapValue(i, j) == 2  ) {
                    Rectangle tileRect = new Rectangle(i * Board.MAPSHIFT, j * Board.MAPSHIFT, blockWidth, blockHeight);
                    this.listaKolizjiMapy.add(tileRect);
                } else {
                    Rectangle tileRect = new Rectangle((i * Board.MAPSHIFT)+ POINTSHIFT, (j * Board.MAPSHIFT)+POINTSHIFT, 20, 20);
                    this.listaPunktowMapy.add(tileRect);
                }
            }
        }
    }

    public List<Rectangle>  getMapCollsionList(){
            return listaKolizjiMapy;
    }
    public boolean isPointListEmpty(){
        return listaPunktowMapy.isEmpty();
    }

    private void constructMap (int level) {
        String pathname;
        switch (level) {
            case 1: {
                pathname = "1.txt";
                break;
            }
            case 2: {
                pathname = "2.txt";
                break;
            }
            case 3: {
                pathname = "3.txt";
                break;
            }
            case 4: {
                pathname = "4.txt";
                break;
            }
            case 5: {
                pathname = "5.txt";
                break;
            }
            default: {
                pathname = "1.txt";
                System.err.println("Nie znaleziono poziomu, wczytuje poziom pierwszzy");
                break;
            }
        }
            try {
                File file = new File(pathname);
                Scanner in = new Scanner(file);
                this.sizeX = in.nextInt();
                this.sizeY = in.nextInt();
                this.pacmanStartXPosition = in.nextInt();
                this.pacmanStartYPosition = in.nextInt();
                this.pacmanStartDirection = in.nextInt();
                this.actual_map = new int[this.sizeX][this.sizeY];
                for (int i = 0; i < sizeX; i++) {
                    for (int j = 0; j < sizeY; j++) {
                        if (in != null && in.hasNextInt()) {
                            this.actual_map[i][j] = in.nextInt();
                        }
                    }

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.err.println("Nie znaleziono pliku/plik ma niepoprawną nazwę ");
            }
        }

    public List<Rectangle> getMapPointList() {
        return listaPunktowMapy;
    }

    public void removePoint(Rectangle kkk) {
        listaPunktowMapy.remove(kkk);
    }

    public boolean isOnList(int i, int j) {
        for(Rectangle point: this.listaPunktowMapy){
               if( point.getX() == ((i*Board.MAPSHIFT)+POINTSHIFT)  && point.getY() == ((j*Board.MAPSHIFT)+POINTSHIFT )) {
                   return true;
               }
        }
             return false;
        }
}

