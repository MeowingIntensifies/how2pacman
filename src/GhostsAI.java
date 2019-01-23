
import java.util.ArrayList;
import java.util.Random;

public class GhostsAI {

    private ArrayList<Ghost> ghostList;
    private Collision collsion;
    private LevelMap level;
    private Pacman pacman;

    public final  static  int GHOST_GRAVEYARD_X = -100;
    public final static  int GHOST_GRAVEYARD_Y = -100;

    public GhostsAI(ArrayList<Ghost> ghostList, Collision collision, LevelMap level, Pacman pacman) {
        this.ghostList = ghostList;
        this.collsion = collision;
        this.level = level;
        this.pacman = pacman;
    }

    public void makeGhostsMove() {
        for (Ghost ghost : ghostList) {
            if (pacman.getBonusStatus() && ghost.getIsAlive() && ghost.wasScaredOnce() == false) {
                ghost.setFrightened(true);
                ghost.setSpeed(Ghost.SLOW);
                frightenedMove(ghost);
                ghost.stop();
                if (ghost.getXPosition() % 50 == 0 ||  ghost.getYPosition() % 50 == 0 ){
                    ghost.setDYDX(ghost.getDirection());
                }else{
                    if(!collsion.checkMapCollision(ghost.getCollisionSprite(), level.getMapCollsionList()))
                    ghost.returnLastMove();
                    else{
                        continue;
                    }
                }
                ghost.move();
                continue;
            }
            if (ghost.getIsAlive() == false){
                ghost.changeImage(0);
                ghost.setScaredOnce(true);
                ghost.setFrightened(false);
                if(ghost.getXPosition() != GHOST_GRAVEYARD_X  && ghost.getYPosition() != GHOST_GRAVEYARD_Y ) {              // sprawdzamy czy już nie znajduje się na cmentarzu (poza mapą)// jeśli nie znaczy że jest tu pierwszy raz i nakładamy na niego nieśmiertelność
                    ghost.setXPositon(GHOST_GRAVEYARD_X);                                                                   // przenosimy pacmana na cmętarz
                    ghost.setYPositon(GHOST_GRAVEYARD_Y);
                }
                if(!shouldWeMove(ghost)){
                    ghost.stop();
                    ghost.setTimer(ghost.getTimer() - Board.DELAY);
                    continue;
                }else{
                    ghost.setTimer(ghost.getRespawnTimer());
                    ghost.setIsAlive(true);
                    ghost.returnToStartingPoint();
                }
            }
            ghost.setSpeed(ghost.STANDARD_SPEED);
            ghost.setFrightened(false);
            whereToGhost(ghost, ghost.getGhostType());
            ghost.stop();
            ghost.setDYDX(ghost.getDirection());
            ghost.move();
            }

        }

    private  int frightenedMove(Ghost ghost) {
        int tries = 0;
        int tempDirection = -1;
        Random rand = new Random();
        if (ghost.getXPosition() % 50 == 0 && ghost.getYPosition() % 50 == 0) {
            while (tries < 40) {
                int moreTempDir = rand.nextInt(4) + 1;
                if (!collsion.checkMapCollision(ghost.getCollisionSprite(moreTempDir), level.getMapCollsionList()) && getReverseDirection(ghost) != moreTempDir) {
                    tempDirection = moreTempDir;
                    break;
                }
                tries++;
            }
            if (tempDirection == -1) {
                tempDirection = getReverseDirection(ghost);
            }
        }

        if (tempDirection != -1) {
            ghost.setDirection(tempDirection);
        }
        System.out.println(ghost.getYPosition() + " " + ghost.getXPosition());
        return tempDirection;
    }

    private boolean shouldWeMove(Ghost ghost) {
        if (ghost.getTimer() <= 0) {
            return true;
        }else{
            return false;
        }
    }

    private void whereToGhost(Ghost ghost, int ghostType) {
        if (checkWhereAndIfShouldTurn(ghost) == -1) {
            ghost.returnLastMove();
        }
    }

    private double getDistance(Ghost ghost, Pacman pacman, int proposedDirection, int ghostType) {
        int directionY = 0;
        int directionX = 0;
        double distance = 0;
        switch (proposedDirection) {

                case Ghost.UP: {
                    directionY = -2;
                    directionX = 0;
                    break;
                }
                case Ghost.LEFT: {
                    directionY = 0;
                    directionX = -2;
                    break;
                }
                case Ghost.RIGHT: {
                    directionY = 0;
                    directionX = 2;
                    break;
                }
                case Ghost.DOWN: {
                    directionY = 2;
                    directionX = 0;
                    break;
                }
            }
        if (ghostType == 2 ){
            final  int pinkyCorrection = 4;
            switch (pacman.getDirection()){
                case Pacman.UP:{
                  distance = Math.sqrt((int) Math.pow(((pacman.getYPosition() - pinkyCorrection)  - (ghost.getYPosition() + directionY))* Board.MAPSHIFT, 2) + (int) Math.pow((pacman.getXPosition() - (ghost.getXPosition() + directionX))* Board.MAPSHIFT, 2));
                  break;
                }
                case Pacman.DOWN:{
                    distance = Math.sqrt((int) Math.pow(((pacman.getYPosition() + pinkyCorrection) - (ghost.getYPosition()  + directionY)) * Board.MAPSHIFT, 2) + (int) Math.pow((pacman.getXPosition() - (ghost.getXPosition() + directionX)) * Board.MAPSHIFT, 2));
                    break;
                }
                case Pacman.RIGHT:{
                    distance = Math.sqrt((int) Math.pow((pacman.getYPosition() - (ghost.getYPosition()  + directionY)) * Board.MAPSHIFT, 2) + (int) Math.pow(((pacman.getXPosition()+ pinkyCorrection) - (ghost.getXPosition() + directionX)) * Board.MAPSHIFT, 2));
                    break;
                }
                case Pacman.LEFT:{
                    distance = Math.sqrt((int) Math.pow((pacman.getYPosition() - (ghost.getYPosition()  + directionY)) * Board.MAPSHIFT, 2) + (int) Math.pow(((pacman.getXPosition()-pinkyCorrection) - (ghost.getXPosition() + directionX)) * Board.MAPSHIFT, 2));
                    break;
                }
            }
            return distance;
        }
        if (ghostType == 3 ){
            int inkyCorrection = 2;                                                         // wartosci "correction" służą do poprawiania pozycji pakmana tak, żeby duszki celowały przed nim (zależne od AI)
            int tempPacX = 0;
            int tempPacY = 0;
            int tempBlinkeyX = getBlinkeyXY('x');
            int tempBlinkeyY = getBlinkeyXY('y');
            int finalDestinationX;
            int finalDestinationY;
            switch (pacman.getDirection()){
                case Pacman.UP:{
                     tempPacX =pacman.getXPosition();
                     tempPacY =pacman.getYPosition()- inkyCorrection;
                     break;
                }
                case Pacman.DOWN:{
                     tempPacX =pacman.getXPosition();
                     tempPacY =pacman.getYPosition()+inkyCorrection;
                     break;
                }
                case Pacman.RIGHT:{
                     tempPacX =pacman.getXPosition()+inkyCorrection;
                     tempPacY =pacman.getYPosition();
                     break;
                }
                case Pacman.LEFT:{

                     tempPacX =pacman.getXPosition() - inkyCorrection;
                     tempPacY =pacman.getYPosition();
                     break;
                }
            }
            finalDestinationY = tempPacY + tempBlinkeyY;
            finalDestinationX = tempPacX + tempBlinkeyX;

            distance = Math.sqrt((int) Math.pow((finalDestinationY - (ghost.getYPosition()  + directionY)) * Board.MAPSHIFT, 2) + (int) Math.pow((finalDestinationX - (ghost.getXPosition() + directionX)) * Board.MAPSHIFT, 2));
            return distance;
        }
        if (ghostType == 4){

            if(Math.sqrt((int) Math.pow((pacman.getYPosition() - (ghost.getYPosition()  + directionY)) * Board.MAPSHIFT, 2) + (int) Math.pow((pacman.getXPosition() - (ghost.getXPosition() + directionX)) * Board.MAPSHIFT, 2))/2500 > 8) {
                distance = Math.sqrt((int) Math.pow((pacman.getYPosition() - (ghost.getYPosition() + directionY)) * Board.MAPSHIFT, 2) + (int) Math.pow((pacman.getXPosition() - (ghost.getXPosition() + directionX)) * Board.MAPSHIFT, 2));
                return distance;
            }else{
                distance = Math.sqrt((int) Math.pow((ghost.getClydePointY() - (ghost.getYPosition() + directionY)) * Board.MAPSHIFT, 2) + (int) Math.pow((ghost.getClydePointX()- (ghost.getXPosition() + directionX)) * Board.MAPSHIFT, 2));
                return distance;
            }
        }

        distance = Math.sqrt((int) Math.pow((pacman.getYPosition() - (ghost.getYPosition()  + directionY)) * Board.MAPSHIFT, 2) + (int) Math.pow((pacman.getXPosition() - (ghost.getXPosition() + directionX)) * Board.MAPSHIFT, 2));
        return distance;
    }

    private int getBlinkeyXY(char dimension) {
        int value = 0;                                                                               // wartosc ustalona tak, by wiadomo było że doszło do nieprzewidzianej sytuacji (duszek kieruje się do 0,0
        for (Ghost ghost : ghostList) {
            if (ghost.getGhostType() !=1) {
                break;
            }
            if (dimension == 'x') {
                value = ghost.getXPosition();
            } else if (dimension == 'y') {
                value = ghost.getYPosition();
            }
        }
        return value;
    }

    private int checkWhereAndIfShouldTurn(Ghost ghosty) {

        double tempDistance = 0;
        int tempDirection = -1;
        if (ghosty.getXPosition() % 50 == 0 || ghosty.getYPosition() % 50 == 0) {
            for (int i = 1; i <= 4; i++) {
                if (!collsion.checkMapCollision(ghosty.getCollisionSprite(i), level.getMapCollsionList()) && getReverseDirection(ghosty) != i ) {
                    if (tempDistance == 0) {
                        tempDistance = getDistance(ghosty, pacman, i, ghosty.getGhostType());
                        tempDirection = i;
                    } else if (tempDistance > getDistance(ghosty, pacman, i, ghosty.getGhostType())) {
                        tempDistance = getDistance(ghosty, pacman, i, ghosty.getGhostType());
                        tempDirection = i;
                    }
                }
            }
        }
        ghosty.setDirection(tempDirection);
        return tempDirection;
    }

    private int getReverseDirection(Ghost ghost) {

        switch (ghost.getDirection()) {
            case Ghost.DOWN: {
                return Ghost.UP;
            }

            case Ghost.UP: {
                return Ghost.DOWN;
            }

            case Ghost.RIGHT: {
                return Ghost.LEFT;
            }

            case Ghost.LEFT: {
                return Ghost.RIGHT;
            }
        }
        return 0;
    }
}
