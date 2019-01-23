import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;


public class Game extends JFrame {

    public static  int lvl = -1;

    public Game(int lvl) {

            initMenu();
            if( lvl != -1){
                initUI(lvl);
            }
        }

        private void initUI( int  lvl) {
                add(new Board(lvl));

                setTitle("Pacman ver 0.80");
                setSize(1200, 1000);

                setLocationRelativeTo(null);
                setResizable(false);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        }
            private void initMenu() {
                createMenuBar();
                System.out.println("fugg");
                setTitle("Pacman ver 0.80");
                setSize(300, 550);

                setLocationRelativeTo(null);
                setResizable(false);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }

        private void createMenuBar() {

            var menubar = new JMenuBar();
            var exitIcon = new ImageIcon("src/resources/exit.png");

            var fileMenu = new JMenu("Menu");
            fileMenu.setMnemonic(KeyEvent.VK_F);

            var eMenuItem = new JMenuItem("Wyjście", exitIcon);
            eMenuItem.setMnemonic(KeyEvent.VK_E);
            eMenuItem.setToolTipText("Zakańcza grę bez zapisania wyniku");
            eMenuItem.addActionListener((event) -> System.exit(0));

            var eMenuItem2 = new JMenuItem("Nowa Gra", exitIcon);
            eMenuItem2.setMnemonic(KeyEvent.VK_E);
            eMenuItem2.setToolTipText("Zacznij grę od początku");
            eMenuItem2.addActionListener((event) -> startGame());

            var eMenuItem3 = new JMenuItem("Wybierz poziom", exitIcon);
            eMenuItem3.setMnemonic(KeyEvent.VK_E);
            eMenuItem3.setToolTipText("Zacznij grę od podanego poziomu");
            eMenuItem3.addActionListener((event) -> chooseLevel());

            var eMenuItem4 = new JMenuItem("Najlepsze wyniki", exitIcon);
            eMenuItem4.setMnemonic(KeyEvent.VK_E);
            eMenuItem4.setToolTipText("Wyświetl najlepsze wyniki");
            eMenuItem4.addActionListener((event) ->startGame(Board.SHOW_SCORES));

            fileMenu.add(eMenuItem);
            fileMenu.add(eMenuItem2);
            fileMenu.add(eMenuItem3);
            fileMenu.add(eMenuItem4);
            menubar.add(fileMenu);

            setJMenuBar(menubar);

            ImageIcon i = new ImageIcon("titleScreen.png");
            JLabel l = new JLabel();
            l.setIcon(i);
            add(l);
        }


    public void startGame() {
        Game k = new Game(1);
        k.setVisible(true);
        this.setVisible(false);
    }
    public void startGame(int level) {
        Game k = new Game(level);
        k.setVisible(true);
        this.setVisible(false);
    }


        public void chooseLevel(){
            try {
                String wynik = JOptionPane.showInputDialog(null, "Podaj poziom", "Wybór poziomu", JOptionPane.QUESTION_MESSAGE);
                lvl = Integer.parseInt(wynik);
            }catch (Exception ex){
                JOptionPane.showMessageDialog(null, "Błąd we wczytywaniu, wczytuję poziom 1", "Uwaga", JOptionPane.INFORMATION_MESSAGE );
                lvl = 1;
            }
            startGame(lvl);
        }



    public static void main(String[] args) {

                    EventQueue.invokeLater(() -> {
                            Game ex = new Game(lvl);
                            ex.setVisible(true);
                    });
                }

    }

