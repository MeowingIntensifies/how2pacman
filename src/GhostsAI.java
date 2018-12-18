
import java.util.ArrayList;



import static java.lang.Math.*;


public class GhostsAI {

    private ArrayList<Ghost> ghostList;
    private Collision collsion;
    private LevelMap level;
    private Pacman pacman;

    public GhostsAI(ArrayList<Ghost> ghostList, Collision collision, LevelMap level, Pacman pacman){
        this.ghostList = ghostList;
        this.collsion = collision;
        this.level = level;
    }

    public void makeGhostsMove(){
        for (Ghost ghost : ghostList){
            whereToGhost(ghost,ghost.getGhostType());
        }
    }

    private void whereToGhost(Ghost ghost, int ghostType) {
        switch (ghostType){
            case 1:{
            if(checkWhereAndIfShouldTurn(ghost) != -1){
                ghost.setDYDX(checkWhereAndIfShouldTurn(ghost));
                }
                break;
            }
            case 2:{
                break;
            }
            case 3:{
                break;
            }
            case 4:{
                break;
            }
        }
    }

    private double getDistance(Ghost ghost, Pacman pacman, int proposedDirection){
        int directionY = 0 ;
        int directionX = 0 ;
        switch (proposedDirection){
            case Ghost.UP:{
                directionY = -25;
                directionX =0;
                break;
            }
            case Ghost.LEFT:{
                directionY = 0;
                directionX = - 25;
                break;
            }
            case Ghost.RIGHT: {
                directionY = 0;
                directionX = 25;
                break;
            }
            case Ghost.DOWN :{
                directionY = 25;
                directionX =0;
                break;
            }
        }
        double distance = sqrt ((int)Math.pow(pacman.getYPosition() - ghost.getYPosition() + directionY,2) + (int)Math.pow(pacman.getXPosition() - ghost.getXPosition() + directionX,2));
        return distance;
    }

    private int checkWhereAndIfShouldTurn(Ghost ghosty){
        double tempDistance = 0;
        int tempDirection = -1;
        if(ghosty.getYPosition()%50 == 0 || ghosty.getXPosition()%50 == 0){
            for (int i = 1; i <=4; i++) {
                if (!collsion.checkMapCollision(ghosty.getCollisionSprite(i),level.getMapCollsionList()) && ghosty.getDirection() != i){
                    if (tempDistance == -1 ){
                        tempDistance = getDistance(ghosty,pacman,i);
                    }
                    if(tempDistance >  getDistance(ghosty,pacman,i)){
                        tempDistance = getDistance(ghosty,pacman,i);
                        tempDirection = i;
                    }
                }
            }
            }
            ghosty.setDirection(tempDirection);
            return tempDirection;
    }
}
