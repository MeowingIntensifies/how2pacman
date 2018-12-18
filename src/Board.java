import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    public static final int MAPSHIFT = 50;          // Zmienna mapshift określa przesunięcie poszczególnych elementów tablicy 2d na piksele
    private static final int GHOST_KILLED_POINTS = 100;
    private static final int INVURNERABLE_BONUS = 1000;
    private static final int INVURNERABLE_DIE = 300 ;

    private Timer timer;
    private Pacman pacman;
    private LevelMap level;
    private Collision collision;
    private GhostsAI ghostsAI;
    private final int DELAY = 10;
    private int animationTimer;
    private ArrayList<Ghost> ghostList;
    private int points;
    private int pacmanLifes;


    public Board(int lvl) {
        pacmanLifes = 3;
        animationTimer = 0;
        points = 0;
        initBoard(lvl);
    }

    private void initBoard(int gameLevel) {

        this.setFocusable(true);

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        level = new LevelMap(gameLevel);

        pacman = new Pacman(level.getPacmanStartXPosition() * MAPSHIFT, level.getPacmanStartYPosition()*MAPSHIFT, level.getPacmanStartDirection());
        collision = new Collision(pacman, level);
        ghostList = new ArrayList<>();
        Ghost ghost = new Ghost(5*MAPSHIFT,5*MAPSHIFT,Ghost.RIGHT, Ghost.RED);
        ghostList.add(ghost);
        ghostsAI = new GhostsAI(ghostList,collision,level,pacman);

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(pacman.getImage(), pacman.getXPosition(),
                pacman.getYPosition(), this);

        for(Ghost ghost : ghostList){
            g2d.drawImage(ghost.getImage(), ghost.getXPosition(),
                    ghost.getYPosition(), this);
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
    }

    public void actionPerformed(ActionEvent e) {
        didWeLose();
        didWeWin();
        step();
    }

    private void didWeLose() {
        if(pacmanLifes < 0 ){

        }
    }


    private void didWeWin() {
        if (level.isPointListEmpty())                                        // jeśli lista punktow jest pusta, stworz nową planszę;
        {
            timer.stop();
            increaseLife();
            initBoard(level.getCurrentLevel() + 1);
            repaint();
        }
    }

    private void increaseLife() {
        this.pacmanLifes++;
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

    private void step() {

        ghostsAI.makeGhostsMove();

        if(pacman.getInvurnerableFrames() == 0){
            pacman.setBonusStatus(false);
        }

        if (collision.checkForBonusPoints()) {
            increasePoints();
            pacman.setBonusStatus(true);
            pacman.setInvurnerable(INVURNERABLE_BONUS);
        }

        if(collision.checkforPacmanGhost(ghostList)){
            if (pacman.getBonusStatus()) {
                increasePoints(GHOST_KILLED_POINTS);
            }else{
                loseLife();
                pacman.returnToStartingPoint();
                pacman.setInvurnerable(INVURNERABLE_DIE);
            }
        }

        if(pacman.getInvurnerableFrames() == 0){
            pacman.setBonusStatus(false); 
        }

        collision.checkForCollsions();

       if (collision.checkForPoints()) {
           increasePoints();
       }

        pacman.move();


        if (!pacman.isStopped() && !pacman.getDriftingFlag()) {                                             // sprawdzamy czy nie doszło do driftingu, jeśli nie to zmienaimy obrazek
            pacman.changeImage(pacman.getDirection());
        }
        if(pacman.isStopped()) {
            pacman.changeImage(pacman.getDirection()*10);
        }

        pacman.decreaseInvurnerableFrames();

        repaint(pacman.getXPosition() - MAPSHIFT, pacman.getYPosition() - MAPSHIFT,
                    pacman.getWidth() + MAPSHIFT * 2, pacman.getHeight() + MAPSHIFT * 2);

        for (Ghost ghost : ghostList) {
            repaint(ghost.getXPosition() - MAPSHIFT, ghost.getYPosition() - MAPSHIFT,
                    ghost.getWidth() + MAPSHIFT * 2, ghost.getHeight() + MAPSHIFT * 2);
        }

        repaint(50, 820, 200, 200);
        }

        public void increasePoints () {
            points += 10;
        }

        public void increasePoints (int points) {
            this.points += points;
        }


        private class TAdapter extends KeyAdapter {

            public void keyPressed(KeyEvent e) {
                pacman.keyPressed(e);

            }

        }
    }

