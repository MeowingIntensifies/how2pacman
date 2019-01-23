import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.*;

public class Board extends JPanel implements ActionListener {

    public static final int MAPSHIFT = 50;
    public static final int DELAY = 10;                                         // Zmienna MAPSHIFT określa przesunięcie poszczególnych elementów tablicy 2d na piksele ( wielkosc siatki)
    public static final int GHOST_KILLED_POINTS = 100;
    public final static int TRANSPARENT_TIMER = 1000;
    public static final int SHOW_SCORES = -100 ;

    private Timer timer;
    private Pacman pacman;
    private LevelMap level;
    private Collision collision;
    private GhostsAI ghostsAI;
    private ArrayList<Ghost> ghostList;
    private int points;
    private int pacmanLifes;
    private Image downBar;
    private Image winScreen;
    private Image loseScreen;
    private Image scoreScreen;
    private int pointTimer;
    private int endTimer;
    private boolean endFlag;
    private boolean lostFlag;
    private boolean scoreFlag;
    private Score newScore;
    private boolean onlyScoreFlag;


    public Board(int lvl) {
        if(lvl != SHOW_SCORES) {
            onlyScoreFlag = false;
            scoreFlag = false;
            endTimer = 300;
            lostFlag = false;
            endFlag = false;
            pacmanLifes = 3;
            points = 0;
            initBoard(lvl);
        }else{
            addKeyListener(new TAdapter());
            this.setFocusable(true);
            onlyScoreFlag = true;
            endFlag = true;
            scoreFlag = true;
            newScore = new Score();
            repaint();
        }

    }

    private void initBoard(int gameLevel) {

        this.setFocusable(true);

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        loadBar();

        level = new LevelMap(gameLevel);

        pointTimer = level.getlevelTimer();

        pacman = new Pacman(level.getPacmanStartXPosition() * MAPSHIFT, level.getPacmanStartYPosition()*MAPSHIFT, level.getPacmanStartDirection());

        collision = new Collision(pacman, level);

        ghostList = new ArrayList<>();

        for(GhostEntryData ghostData : level.getGhostDataList()){
            Ghost ghost = new Ghost(ghostData.getPositionX()*MAPSHIFT,ghostData.getPositionY()*MAPSHIFT, Ghost.RIGHT, ghostData.getGhostType(),ghostData.getGhostSpawnTimer(),ghostData.getGhostreSpawnTimer());
            ghostList.add(ghost);
            if(ghost.getGhostType() == 4){
                ghost.setClydePointX(ghostData.getClydePointX());
                ghost.setClydePointY(ghostData.getClydePointY());
            }
        }

        ghostsAI = new GhostsAI(ghostList,collision,level,pacman);

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public  void loadBar(){
        ImageIcon ii = new ImageIcon("barUI.png");
        downBar = ii.getImage();
    }

    private void loadWinScreen(){
        ImageIcon ii = new ImageIcon("winScreen.png");
        winScreen = ii.getImage();
    }

    private void loadLoseScreen(){
        ImageIcon ii = new ImageIcon("loseScreen.png");
        loseScreen = ii.getImage();
    }

    private void loadScoreScreen() {
        ImageIcon ii = new ImageIcon("scoreScreen.png");
        scoreScreen = ii.getImage();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(Color.yellow);
        g2d.setFont(new Font ("Arial",Font.PLAIN ,20));

        if(endFlag == false) {
            g2d.drawImage(pacman.getImage(), pacman.getXPosition(),
                    pacman.getYPosition(), this);

            g2d.drawImage(downBar, 0,
                    800, this);

            if (pointTimer > 0) {
                g2d.drawString("Zostało " + pointTimer / 100 + " bonusowych sekund ", 300, 820);
            } else {
                g2d.drawString("Zostało " + 0 + " bonusowych sekund ", 300, 820);
            }


            for (int u = 0; u < getLifes(); u++) {
                g2d.drawImage(pacman.getLifesImage(), 50 + u * 50,
                        860, this);
            }

            g2d.drawString("Masz " + points + " punktow", 50, 820);

            for (int i = 0; i < level.getSizeX(); i++) {
                for (int j = 0; j < level.getSizeY(); j++) {
                    if (level.getMapValue(i, j) == 2) {
                        g2d.drawImage(level.getBlockImage(), i * MAPSHIFT, j * MAPSHIFT, this);
                    }
                    if (level.getMapValue(i, j) == 1 && level.isOnPointList(i, j)) {
                        g2d.drawImage(level.getPointImage(), i * MAPSHIFT, j * MAPSHIFT, this);
                    }
                    if (level.getMapValue(i, j) == 3 && level.isOnBonusList(i, j)) {
                        g2d.drawImage(level.getBonusImage(), i * MAPSHIFT, j * MAPSHIFT, this);
                    }
                }
            }
            for (Ghost ghost : ghostList) {
                if(ghost.getIsAlive()== true) {
                    g2d.drawImage(ghost.getImage(), ghost.getXPosition(),
                            ghost.getYPosition(), this);
                }else if (ghost.getIsAlive() == false && ghost.getTimer() < TRANSPARENT_TIMER ){
                    g2d.drawImage(ghost.getImage(), ghost.getStartingX(),
                            ghost.getStartingY(), this);
                }
            }
        }else if (scoreFlag == false){
            if (isLostFlag()== false) {
                loadWinScreen();
                g2d.drawImage(winScreen, 0, 0, this);
            }else{
                loadLoseScreen();
                g2d.drawImage(loseScreen, 0, 0, this);
            }
                g2d.setPaint(Color.yellow);
                g2d.setFont(new Font ("Arial", Font.BOLD, 80));
                String tempScore = String.valueOf(getPoints());
                g2d.drawString(tempScore, 525, 550);
            }else{
                loadScoreScreen();
                g2d.drawImage(scoreScreen, 0, 0, this);
                g2d.setPaint(Color.yellow);
                g2d.setFont(new Font ("Deja Vu Serif", Font.PLAIN, 40));
                 for (int i = 0; i < 10; i++) {
                     g2d.drawString(i + 1 + " ",  120, (i+5)*50);
                     g2d.drawString(newScore.getScores(i,0),  220, (i+5)*50);
                     g2d.drawString(newScore.getScores(i,1),  720, (i+5)*50);
                     }
            }
        }


    public void actionPerformed(ActionEvent e ) {
        if (didWeLose() == false && didWeWin() == false) {
            step();
        } else {
            if (endTimer < 0) {
                timer.stop();
                newScore = new Score();
                newScore.setHighScore(points);
                scoreFlag = true;
                repaint();
            } else {
                endTimer--;
            }
        }
    }

    private boolean didWeLose() {
        if(endFlag == true) {
            return true;
        }

        if((pacmanLifes < 0) ){
            addTimerPoints();
            endFlag = true;
            setLostFlag(true);
            repaint();
            return true;
        }
        return false;
    }


    private boolean didWeWin() {
        if (level.isPointListEmpty() && level.isBonusPointListEmpty())                                        // jeśli lista punktow jest pusta, stworz nową planszę;
        {
            increaseLife();
            addTimerPoints();
            if(LevelMap.isNextLevelThere(level.getCurrentLevel()+1)) {
                timer.stop();
                initBoard(level.getCurrentLevel() + 1);
                this.removeAll();
            }else{
                endFlag = true;
            }
            repaint();
            return true;
        }
        return false;
    }

    private void increaseLife() {
        if(getLifes() < 10) {
            this.pacmanLifes++;
        }else{
            increasePoints(1000);
        }
    }

    private void loseLife() {
        this.pacmanLifes--;
        decreasePoints();
    }

    private void decreasePoints(){
        this.points -= 100;
    }


    private int getLifes() {
        return pacmanLifes;
    }
    private void addTimerPoints()
    {
        if(pointTimer >0) {
            increasePoints(pointTimer % 100);
        }
        pointTimer = 4500;
    }

    private void step() {

        ghostsAI.makeGhostsMove();

        if(pacman.getInvurnerableFrames() == 0){
            pacman.setBonusStatus(false);
        }

        if (collision.checkForBonusPoints(ghostList) || collision.checkForPoints()) {
            increasePoints();
        }

        if(collision.checkforPacmanGhost(ghostList)){
            if (pacman.getBonusStatus()) {
                increasePoints(GHOST_KILLED_POINTS);
            }else{
                decreasePoints();
                loseLife();
            }
        }

        collision.checkForCollsions();

        pacman.move();


        if (!pacman.isStopped() && !pacman.getDriftingFlag()) {                                             // sprawdzamy czy nie doszło do driftingu, jeśli nie to zmienaimy obrazek
            pacman.changeImage(pacman.getDirection());
        }

        if(pacman.isStopped()) {
            pacman.changeImage(pacman.getDirection()*10);
        }

        pacman.decreaseInvurnerableFrames();
        decreasePointTimer();

        repaint(pacman.getXPosition() - MAPSHIFT, pacman.getYPosition() - MAPSHIFT,
                    pacman.getWidth() + MAPSHIFT * 2, pacman.getHeight() + MAPSHIFT * 2);


        for (Ghost ghost : ghostList) {
            ghost.changeImage(ghost.getDirection());
            repaint(ghost.getXPosition() - MAPSHIFT, ghost.getYPosition() - MAPSHIFT,
                    ghost.getWidth() + MAPSHIFT * 2, ghost.getHeight() + MAPSHIFT * 2);
        }

        repaint(50, 820, 200, 200);
        repaint();
        }

    private void decreasePointTimer() {
        pointTimer --;
    }
    public int getPoints(){
        return points;
    }

    public void increasePoints () {
            points += 10;
        }
        public void increasePoints (int points) {
            this.points += points;
        }

    public void setLostFlag(boolean lostFlag) {
        this.lostFlag = lostFlag;
    }

    public boolean isLostFlag() {
        return lostFlag;
    }


    private class TAdapter extends KeyAdapter {

            public void keyPressed(KeyEvent e) {
                pacman.keyPressed(e);

            }

        }
    }

