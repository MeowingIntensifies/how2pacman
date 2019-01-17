public class GhostEntryData {

    private int positionY;
    private int positionX;
    private int clydePointX;
    private int clydePointY;
    private int ghostType;
    private int ghostreSpawnTimer;
    private int ghostSpawnTimer;

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getGhostType() {
        return ghostType;
    }

    public void setGhostType(int ghostType) {
        this.ghostType = ghostType;
    }

    public int getGhostSpawnTimer() {
        return ghostSpawnTimer;
    }

    public void setGhostSpawnTimer(int ghostSpawnTimer) {
        this.ghostSpawnTimer = ghostSpawnTimer;
    }

    public int getGhostreSpawnTimer() {
        return ghostreSpawnTimer;
    }

    public void setGhostreSpawnTimer(int ghostreSpawnTimer) {
        this.ghostreSpawnTimer = ghostreSpawnTimer;
    }

    public void setClydePointX(int nextInt) {this.clydePointX = nextInt;}
    public void setClydePointY(int nextInt) { this.clydePointY = nextInt;}

    public int getClydePointX() {
        return clydePointX;
    }

    public int getClydePointY() {
        return clydePointY;
    }
}
