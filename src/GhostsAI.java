
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
            if (ghost.getIsAlive() == false){                                                                             // sprawdzamy czy duszek nie umarł
                if(ghost.getXPosition() != GHOST_GRAVEYARD_X  && ghost.getYPosition() != GHOST_GRAVEYARD_Y ) {              // sprawdzamy czy już nie znajduje się na cmentarzu (poza mapą)// jeśli nie znaczy że jest tu pierwszy raz i nakładamy na niego nieśmiertelność
                    ghost.setXPositon(GHOST_GRAVEYARD_X);                                                                   // przenosimy pacmana na cmętarz
                    ghost.setYPositon(GHOST_GRAVEYARD_Y);
                }
                                                                                                                               //dążymy do zmniejszenia
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
            if (pacman.getBonusStatus() && ghost.getXPosition() != ghost.getStartingX() && ghost.getStartingY() != ghost.getYPosition() ){
                ghost.setFrightened(true);
                ghost.setSpeed(Ghost.SLOW);
                if (frightenedMove(ghost) == -1 ){
                    ghost.stop();
                    ghost.returnLastMove();
                    if(collsion.checkMapCollision(ghost.getCollisionSprite(ghost.getDirection()), level.getMapCollsionList())){
                        ghost.stop();
                        ghost.setDYDX(getReverseDirection(ghost));
                    }

                } else {
                    ghost.stop();
                    ghost.setDYDX(ghost.getDirection());
                }
                ghost.move();

            }else{
                ghost.setSpeed(ghost.STANDARD_SPEED);
                ghost.setFrightened(false);
                whereToGhost(ghost, ghost.getGhostType());
                ghost.stop();
                ghost.setDYDX(ghost.getDirection());
                ghost.move();
            }

        }
    }

    private  int frightenedMove(Ghost ghost) {
        int tempDirection = -1;
        Random rand = new Random();
        int trys =0;
        if (ghost.getXPosition() % 50 == 0 || ghost.getYPosition() % 50 == 0) {
            while (tempDirection == -1 && trys < 8 ) {
                int moreTempDir = rand.nextInt(4) + 1;
                if (!collsion.checkMapCollision(ghost.getCollisionSprite(moreTempDir), level.getMapCollsionList()) && getReverseDirection(ghost) != moreTempDir) {
                    tempDirection = moreTempDir;
                    break;
                }
                trys++;
            }
        }
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

        checkWhereAndIfShouldTurn(ghost);
        if (checkWhereAndIfShouldTurn(ghost) == -1) {
            ghost.returnLastMove();
        }
    }

    private double getDistance(Ghost ghost, Pacman pacman, int proposedDirection, int ghostType) {
        int directionY = 0;
        int directionX = 0;
        switch (proposedDirection) {

            /* odrócone kierunki ze względu na to, że tradycyjny
             * wzór na obliczanie odległości współpracuje z tradycyjną siatką;
             * tutaj obecna jest odwrócona
             * */

            case Ghost.UP: {
                directionY = -1;
                directionX = 0;
                break;
            }
            case Ghost.LEFT: {
                directionY = 0;
                directionX = -1;
                break;
            }
            case Ghost.RIGHT: {
                directionY = 0;
                directionX = 1;
                break;
            }
            case Ghost.DOWN: {
                directionY = 1;
                directionX = 0;
                break;
            }
        }
        if (ghostType == 2 ){
            int inkyCorrection = 4;
            double distance = 0;
            switch (pacman.getDirection()){
                case Pacman.UP:{
                  distance = Math.sqrt((int) Math.pow(((pacman.getYPosition() - inkyCorrection)  - (ghost.getYPosition() + directionY))* Board.MAPSHIFT, 2) + (int) Math.pow((pacman.getXPosition() - (ghost.getXPosition() + directionX))* Board.MAPSHIFT, 2));
                }
                case Pacman.DOWN:{
                    distance = Math.sqrt((int) Math.pow(((pacman.getYPosition() + inkyCorrection) - (ghost.getYPosition()  + directionY)) * Board.MAPSHIFT, 2) + (int) Math.pow((pacman.getXPosition() - (ghost.getXPosition() + directionX)) * Board.MAPSHIFT, 2));
                }
                case Pacman.RIGHT:{
                    distance = Math.sqrt((int) Math.pow((pacman.getYPosition() - (ghost.getYPosition()  + directionY)) * Board.MAPSHIFT, 2) + (int) Math.pow(((pacman.getXPosition()+ inkyCorrection) - (ghost.getXPosition() + directionX)) * Board.MAPSHIFT, 2));
                }
                case Pacman.LEFT:{
                    distance = Math.sqrt((int) Math.pow((pacman.getYPosition() - (ghost.getYPosition()  + directionY)) * Board.MAPSHIFT, 2) + (int) Math.pow(((pacman.getXPosition()-inkyCorrection) - (ghost.getXPosition() + directionX)) * Board.MAPSHIFT, 2));
                }
            }
            return distance;
        }

        double distance = Math.sqrt((int) Math.pow((pacman.getYPosition() - (ghost.getYPosition()  + directionY)) * Board.MAPSHIFT, 2) + (int) Math.pow((pacman.getXPosition() - (ghost.getXPosition() + directionX)) * Board.MAPSHIFT, 2));
        return distance;
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
            if (tempDistance == 0) {
                tempDirection = getReverseDirection(ghosty);
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
