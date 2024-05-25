package pt.ipbeja.app.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoardContent {
    private int col;
    private int line;
    private static List<String> words;

    public BoardContent(int line, int col) {
        this.col = col;
        this.line = line;
    }

    public Character[][] Board() throws IOException {
        Character[][] boardMatrix = new Character[this.line][this.col];

        this.words = words();
        Random random = new Random();
        int control = 0;

        do {
            int r = random.nextInt(2);
            int l = random.nextInt(this.line + 1);
            int c = random.nextInt(this.col + 1);

            if (check(words.get(control), l, c, boardMatrix, r)) {
                boardMatrix = insertWord(words.get(control), l, c, boardMatrix, r);
                control++;
            }

        } while (control < words.size());

        boardMatrix = boardFill(boardMatrix);
        /*for(Character[] b: boardMatrix){
           for(Character c:b){
               System.out.print(c);
           }
            System.out.println("\n");
        }*/

        return boardMatrix;
    }

    public Character[][] boardFill(Character[][] boardMatrix) {
        for (int l = 0; l < this.line; l++) {
            for (int c = 0; c < this.col; c++) {
                if (boardMatrix[l][c] == null) {
                    boardMatrix[l][c] = randomLetter();
                }
            }
        }
        return boardMatrix;
    }

    public Character randomLetter() { //https://www.delftstack.com/howto/java/java-random-character/
        String letters = "ABCDEFGHIJKLMNOPKRSTUVWXYZ";
        Random random = new Random();
        int randomInt = random.nextInt(letters.length());
        return letters.charAt(randomInt);
        //return 'a';
    }

    public List<String> words() throws IOException { //https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
        File file = new File(
                "../words.txt");
        BufferedReader br
                = new BufferedReader(new FileReader(file));
        String st;
        List<String> words = new ArrayList<>();
        while ((st = br.readLine()) != null) {
            words.add(st);
        }
        return words;
    }

    public String BoardToString(Character[][] boardMatrix) {
        String board = "";
        for (int l = 0; l < this.line; l++) {
            for (int c = 0; c < this.col; c++) {
                board = board + boardMatrix[l][c] + "";
            }
            if (l < this.line - 1) {
                board = board + "\n";
            }
        }
        //System.out.println(board);
        return board;
    }

    public boolean check(String word, int line, int col, Character[][] matrix, int orientation) { //orientation = 0 = horizontal
        try {
            int wordSize = word.length() - 1;
            if (orientation == 0) {
                for (int p = 0; p <= wordSize; p++) {
                    if (matrix[line][col + p] != null) {
                        return false;
                    }
                    if (matrix[line][col + p] == null && p == wordSize) {
                        return true;
                    }
                }
            }
            if (orientation == 1) {
                for (int p = 0; p <= wordSize; p++) {
                    if (matrix[line + p][col] != null) {
                        return false;
                    }
                    if (matrix[line + p][col] == null && p == wordSize) {
                        return true;
                    }
                }
            }
        } catch (Exception ignored) {
            return false;
        }
        return false;
    }

    public Character[][] insertWord(String word, int l, int c, Character[][] matrix, int orientation) {

        int wordSize = word.length();
        Random random = new Random();
        boolean r = random.nextBoolean();
        if(r){word= String.valueOf(new StringBuilder(word).reverse());}

            if (orientation == 0) {
                for (int p = 0; p < wordSize; p++) {
                    matrix[l][c + p] = word.charAt(p);
                }
            }
            if (orientation == 1) {
                for (int p = 0; p < wordSize; p++) {
                    matrix[l + p][c] = word.charAt(p);
                }
            }
        return matrix;
    }

    public int getCol() {
        return col;
    }

    public int getLine() {
        return line;
    }

    public static List<String> getWords() {
        return words;
    }
}
