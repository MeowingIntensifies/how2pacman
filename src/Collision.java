import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Collision {
    private Pacman pacman;
    private LevelMap level;
    private ArrayList<Ghost> ghostList;

    public Collision(Pacman pacman, LevelMap level){
        this.pacman = pacman;
        this.level = level;
    }

    public void checkForCollsions() {

        pacman.setBufferedDirection(pacman.getDirection());                                 //  buforowanie ruchu

        if (checkMapCollision(pacman.getCollisionSprite(), level.getMapCollsionList())) {
            pacman.stop();                                                                      // wykryto kolizje w następnym ruchu, zatrzymac gracza, przywracamy poprzedni ruch;
            pacman.returnLastMove();
            pacman.setDriftingFlag(true);
            if (checkMapCollision(pacman.getCollisionSprite(), level.getMapCollsionList())) {       // zatrzymujemy gracza jeśli poprzedni ruch też spowoduje kolizje
                pacman.stop();
                pacman.setDriftingFlag(false);
            }
        }

        if (pacman.getBufferedDirection() != 0) {                                                                                   // czy gracz zabuforował ruch
            if (!checkMapCollision(pacman.getCollisionSprite(pacman.getBufferedDirection()), level.getMapCollsionList())) {         // sprawdzamy czy kierunek bufora jest czysty
                pacman.stop();                                                                                                  // zatrzymujemy poprzedni ruch, zmieniamy kierunek na ten bufora i zerujemy bufor                                                                                                              ustawiamy nowy kierunek i zerujemy bufor */
                pacman.setDYDX(pacman.getBufferedDirection());
                pacman.setBufferedDirection(0);
                pacman.setDriftingFlag(false);

            }
        }
    }


    public boolean checkForPoints(){
      return checkPointCollsion(pacman.getCollisionSprite(), level.getMapPointList());  //sprawdzanie kolizji gracza z punktami
    }
    public boolean checkForBonusPoints(){
        return checkBonusPointCollsion(pacman.getCollisionSprite(), level.getMapBonusPointList());  //sprawdzanie kolizji gracza z punktami
    }

    public boolean checkforPacmanGhost(ArrayList<Ghost> ghostList) {
        for (Ghost ghost : ghostList) {
            if (ghost.getCollisionSprite().intersects(pacman.getCollisionSprite())) {
                if (pacman.getBonusStatus()) {
                    ghost.setIsAlive(false);
                }else{
                    pacman.returnToStartingPoint();
                    pacman.setInvurnerable(Pacman.INVURNERABLE_DIE);
                }
                return true;
            }
        }
        return false;
    }


    public boolean checkMapCollision(Rectangle pacmanSprite, java.util.List<Rectangle> MapCollisonList) {
        for (Rectangle kkk : MapCollisonList) {
            if (kkk.intersects(pacmanSprite)) {
                return true;
            }
        }
        return false;
    }


    private boolean checkPointCollsion(Rectangle pacmanSprite, List<Rectangle> mapPointList) {
        for (Rectangle kkk : mapPointList) {
            if (kkk.intersects(pacmanSprite)) {
                level.removePoint(kkk);
                return true;
            }
        }
        return false;
    }
    private boolean checkBonusPointCollsion(Rectangle pacmanSprite, List<Rectangle> mapPointList) {
        for (Rectangle kkk : mapPointList) {
            if (kkk.intersects(pacmanSprite)) {
                level.removeBonusPoint(kkk);
                pacman.setBonusStatus(true);
                pacman.setInvurnerable(Pacman.INVURNERABLE_BONUS);
                return true;
            }
        }
        return false;
    }

}
