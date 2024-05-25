package pt.ipbeja.app.model;


import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
    //TODO esta mal temos de alterar o nome fazer o codigo
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
    //TODO mesmo em cima
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

    public void click(Position position) {
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

    /**
     * Ends the game and displays a dialog for the player to enter their name and save their score.
     * If the player cancels the exit, the game will continue.
     * After saving the score, the application will exit.
     */
    public void endGame(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("The games has ended");
        int words= BoardContent.getWords().size();
        dialog.setHeaderText("Level completed! U found:"+ wordsFound.size()+" of "+words+"("+(words/wordsFound.size())*100+"%)");
        dialog.setContentText("Insert your name here:");
        var input=dialog.showAndWait();
        if(input.isEmpty()) confirmExit();
        if(input.get().length()<3){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR!");
            alert.setContentText("Name cant be null or have less than 3 chars");
            alert.showAndWait();
            endGame();
        }
        else{
            savegame(input.get());
        }
        System.exit(0);
    }
    /**
     * Confirms the exit of the application.
     * If the user confirms the exit, the application will exit.
     * If the user cancels the exit, the game will continue.
     */
    public void confirmExit(){
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Abort");
        alert.setContentText("Are u sure want to exit? Your score wont be saved");
        var result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
        endGame();
    }
    /**
     * Saves the current game state.
     *
     * @param name the name associated with the saved game
     */
    //name-wordsfound-totalword
    public void savegame(String name){
        String content=name+"-"+this.wordsFound.size()+"-"+BoardContent.getWords().size();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("save.txt", true))) {
            writer.newLine();
            writer.write(content);
        } catch (Exception ignored) {}
    }


    private void updateWordsFound(String word){
        this.wordsFound.add(word);
    }
}
