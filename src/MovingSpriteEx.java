import java.awt.*;
import javax.swing.JFrame;


    public class MovingSpriteEx extends JFrame {

        public MovingSpriteEx( int lvl) {

            initUI(lvl);
        }

        private void initUI( int  lvl) {
             add(new Board(lvl));

            setTitle("Sprite_test");
            setSize(1000, 1000);

            setLocationRelativeTo(null);
            setResizable(false);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        }

        public static void main(String[] args) {
            int lvl = 3;

                EventQueue.invokeLater(() -> {
                    MovingSpriteEx ex = new MovingSpriteEx(lvl);
                    ex.setVisible(true);
                });

        }
    }

