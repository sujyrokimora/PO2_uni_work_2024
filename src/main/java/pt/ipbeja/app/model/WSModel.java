package pt.ipbeja.app.model;


import pt.ipbeja.app.ui.Letter;

import java.util.ArrayList;
import java.util.List;

/**
 * Game model
 * @author anonymized
 * @version 2024/04/14
 */
public class WSModel {
    // The following matrix could also be List<List<Character>>
    // for a more complex game, it should be a List<List<Cell>>
    // where Letter is a class with the letter and other attributes
    private final List<List<String>> lettersGrid;
    private WSView wsView;

    public WSModel(String boardContent) {
        this.lettersGrid = new ArrayList<>();
        lettersGrid.add(new ArrayList<>());
        for(char c : boardContent.toCharArray()) {
            if (c == '\n') lettersGrid.add(new ArrayList<>());
            else lettersGrid.get(lettersGrid.size() - 1).add(c + "");
        }
    }

    public int nLines() { return this.lettersGrid.size(); }
    public int nCols() { return this.lettersGrid.get(0).size(); }

    public void registerView(WSView wsView) {
        this.wsView = wsView;
    }

    /**
     * Get the text in a position
     * @param position  position
     * @return  the text in the position
     */
    public String textInPosition(Position position) {
        return this.lettersGrid.get(position.line()).get(position.col());
    }

    /**
     * Check if all words were found
     * @return  true if all words were found
     */
    public boolean allWordsWereFound() {
        // TODO: implement this method
        return true;
    }

    /**
     * Check if the word is in the board
     * @param word
     * @return true if the word is in the board
     */
    public static String wordFound(String word) {
        List<String> words = BoardContent.getWords();
        for (String s : words) {
            if (s.equals(word)) return word;
        }
        return "0";
    }

    /**
     * Check if the word with wildcard is in the board
     * @param word
     * @return  true if the word with wildcard is in the board
     */
    public String wordWithWildcardFound(String word) {
        List<String> words = BoardContent.getWords();
        int control = 1;
        for (String s : words) {
            if(s.length()==word.length()) {
                for(int i = 0; i < s.length(); i++){
                    if(s.charAt(i)==word.charAt(i) || s.charAt(i)=='*')control = control + 1;
                }
                if(control==word.length()) return s;
                control = 1;
            }
        }
        return "0";
    }

    public void updater(Position pos){

    }
}
