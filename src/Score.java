import javax.swing.*;
import java.io.*;
import java.util.Scanner;

    public class Score {
        private String scores[][];
        private String name;

        public Score(){
            makeTemporaryArray();
        }

        public boolean setHighScore(int score) {
            int place = checkScore(score);
            if(place != -1 ){
                setName();
                for(int i = 8; i >= place; i--) {
                        scores[i+1][0] = scores[i][0];
                        scores[i+1][1] = scores[i][1];
                }
                scores[place][0] = name;
                scores[place][1] = Integer.toString(score);
                copyScoresToFile();
                return true;
            }
            return false;
        }

        private void copyScoresToFile() {
            PrintWriter pw = null;
            try {
                pw = new PrintWriter("scores.txt");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.err.println(" To coś się nie powinno nigdy zdarzyć, ale się zdarzyło");
            }
            pw.close();
            try {
                pw = new PrintWriter("scores.txt");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.err.println(" To coś się nie powinno nigdy zdarzyć, ale się zdarzyło");
            }
            for( int i = 0; i <10 ;i ++ ){
                pw.println(scores[i][0]+" "+ scores[i][1]);
            }
            pw.close();
        }

        private int checkScore(int score){
            int temporaryScore = Integer.parseInt (scores[9][1]);
            int temporaryPlace = -1;
            if ( temporaryScore < score){
                for (int i = 9; i >= 0; i--) {
                  if( Integer.parseInt( scores[i][1]) < score){
                      temporaryPlace = i;
                  }
                }
            }
            return temporaryPlace;
        }

        private void makeTemporaryArray() {
            scores = new String[10][2];
            try {
                File file = new File("scores.txt");
                Scanner in = new Scanner(file);
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j< 2; j++) {
                        scores[i][j] = in.next();
                    }
                }
            }
            catch (IOException k) {
                PrintWriter writer = null;
                try {
                    writer = new PrintWriter("scores.txt", "UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Wyniknął problem z tworzeniem nowego pliku wyniku");
                }
                writer.println("A 10");
                writer.println("B 9");
                writer.println("C 8");
                writer.println("D 7");
                writer.println("E 6");
                writer.println("F 5");
                writer.println("G 4");
                writer.println("H 3");
                writer.println("I 2");
                writer.println("J 1");
                writer.close();
            }
        }
        public String getScores(int x, int y){
            return scores[x][y];
        }

        public  void setName(){
            name = JOptionPane.showInputDialog(null, "Gratulacje, znalazłeś się w pierwszej dziesiątce! Wpisz swoje imie ", "Nowy rekord", JOptionPane.QUESTION_MESSAGE);
            while(name.length() > 20 || name.contains(" ") ){
                name = JOptionPane.showInputDialog(null, "Imie nie może zawierać spacji ani mieć więcej niż 20 znaków!", "Nowy rekord", JOptionPane.QUESTION_MESSAGE);
            }
        }
    }

