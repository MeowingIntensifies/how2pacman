
import java.util.ArrayList;

import static java.lang.Math.*;

public class GhostsAI {

    private ArrayList<Ghost> ghostList;
    private Collision collsion;
    private LevelMap level;
    private Pacman pacman;

    public GhostsAI(ArrayList<Ghost> ghostList, Collision collision, LevelMap level, Pacman pacman) {
        this.ghostList = ghostList;
        this.collsion = collision;
        this.level = level;
        this.pacman = pacman;
    }

    public void makeGhostsMove() {
        for (Ghost ghost : ghostList) {
            whereToGhost(ghost, ghost.getGhostType());
            ghost.stop();
            ghost.setDYDX(ghost.getDirection());
            ghost.move();
        }
    }

    private void whereToGhost(Ghost ghost, int ghostType) {
        ;
        switch (ghostType) {
            case 1: {
                checkWhereAndIfShouldTurn(ghost);
                if (checkWhereAndIfShouldTurn(ghost) == -1) {
                    ghost.returnLastMove();
                }
                break;
            }
            case 2: {
                break;
            }
            case 3: {
                break;
            }
            case 4: {
                break;
            }
        }
    }

    private double getDistance(Ghost ghost, Pacman pacman, int proposedDirection) {
        int directionY = 0;
        int directionX = 0;
        switch (proposedDirection) {

            /* odrócone kierunki ze względu na to, że tradycyjny
             * wzór na obliczanie odległości współpracuje z tradycyjną siatką;
             * tutaj obecna jest odwrócona
             * */

            case Ghost.DOWN: {
                directionY = -1;
                directionX = 0;
                break;
            }
            case Ghost.RIGHT: {
                directionY = 0;
                directionX = -1;
                break;
            }
            case Ghost.LEFT: {
                directionY = 0;
                directionX = 1;
                break;
            }
            case Ghost.UP: {
                directionY = 1;
                directionX = 0;
                break;
            }
        }
        double distance = Math.sqrt((int) Math.pow(pacman.getYPosition() * Board.MAPSHIFT - ghost.getYPosition() * Board.MAPSHIFT + directionY * Board.MAPSHIFT, 2) + (int) Math.pow(pacman.getXPosition() * Board.MAPSHIFT - ghost.getXPosition() * Board.MAPSHIFT + directionX * Board.MAPSHIFT, 2));
        return distance;
    }

    private int checkWhereAndIfShouldTurn(Ghost ghosty) {
        double tempDistance = 0;
        int tempDirection = -1;
        if (ghosty.getXPosition() % 50 == 0 || ghosty.getYPosition() % 50 == 0) {
            for (int i = 1; i <= 4; i++) {
                if (!collsion.checkMapCollision(ghosty.getCollisionSprite(i), level.getMapCollsionList()) && getReverseDirection(ghosty) != i ) {
                    if (tempDistance == 0) {
                        tempDistance = getDistance(ghosty, pacman, i);
                        tempDirection = i;
                    } else if (tempDistance > getDistance(ghosty, pacman, i)) {
                        tempDistance = getDistance(ghosty, pacman, i);
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
