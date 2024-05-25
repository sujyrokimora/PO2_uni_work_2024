package pt.ipbeja.app.model;


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
    private Position first;
    private Position last;
    private List<String> wordsFound = new ArrayList<>();

    public WSModel(String boardContent) {
        this.lettersGrid = new ArrayList<>();
        lettersGrid.add(new ArrayList<>());
        for(char c : boardContent.toCharArray()) {
            if (c == '\n') lettersGrid.add(new ArrayList<>());
            else lettersGrid.get(lettersGrid.size() - 1).add(c + "");
        }
        for (List<String> row : lettersGrid) { //testar se funciona
            for (String letter : row) {
                System.out.print(letter + " ");
            }
            System.out.println();
        }
        wordBetween(new Position(0,0), new Position(0,5));
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
     */// para modificar
    public boolean allWordsWereFound() {
        if(this.wordsFound.size()==BoardContent.getWords().size()){
            return true;
        }
        return false;
    }
    /**
     * Check if the word is in the board
     * @param word
     * @return true if the word is in the board
     */
    //para modificar same has other
    public String wordFound(String word) {
        List<String> words = BoardContent.getWords();
        for (String s : words) {
            if (s.equals(word)){
                updateWordsFound(word);
                return word;
            }
        }
        return "0";
    }
    /**
     * Check if the word with wildcard is in the board
     * @param word
     * @return  true if the word with wildcard is in the board
     */
    //para modificar deve percorrer a board e verificar se a palavra se encontra na board
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
    public String wordBetween(Position first, Position last){
        String between = "";
        if(first.line() == last.line()){
            for(int i = first.col(); i <= last.col(); i++){
                between = between + textInPosition(new Position(first.line(), i));
                        //lettersGrid.get(first.line()).get(i);
            }
        }
        else{
            for(int i = first.line(); i <= last.line(); i++){
                between = between + textInPosition(new Position(i, first.col()));
                        //lettersGrid.get(i).get(first.col());
            }
        }
        System.out.println(between);
        return between;
    }

    public void click(Position position){
        if (this.first==null){
            this.first=position;
            wsView.changeColor(Colors.YELLOW, this.first);
        }
        else{
            this.last=position;

            if(wordBetween(this.first,this.last).equals(wordFound(wordBetween(this.first,this.last)))){
                wsView.wordChangeColor(Colors.GREEN, lettersToChange(this.first, this.last));
            }

            else if (reverseString(wordBetween(this.last, this.first)).equals(wordFound(reverseString(wordBetween(this.last, this.first))))){
                wsView.wordChangeColor(Colors.GREEN, lettersToChange(this.last, this.first));
            }
            else {
                wsView.changeColor(Colors.GREY, this.first);
            }
            List<Position> pos = new ArrayList<>();
            MessageToUI message = new MessageToUI(pos, "coiso e tal e na se que");
            wsView.update(message);
            this.first=null;
            this.last=null;
        }
    }

    private List<Position> lettersToChange(Position first, Position last){
        List<Position> pos = new ArrayList<>();
        if(first.line() == last.line()){
            for(int i = first.col(); i <= last.col(); i++){
                pos.add(new Position(first.line(), i));
                //lettersGrid.get(first.line()).get(i);
            }
        }
        else{
            for(int i = first.line(); i <= last.line(); i++){
                pos.add(new Position(i, first.col()));
                //lettersGrid.get(i).get(first.col());
            }
        }
        return pos;
    }

    private String reverseString(String s){
        String inverted = "";
        for(int i = s.length()-1; i >= 0; i--){
            inverted = inverted + s.charAt(i) + "";
        }
        System.out.println(inverted);
        return inverted;
    }

    private void updateWordsFound(String word){
        this.wordsFound.add(word);
    }
}
