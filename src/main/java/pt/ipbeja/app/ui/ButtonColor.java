package pt.ipbeja.app.ui;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import pt.ipbeja.app.model.ColorStack;

public class ButtonColor {
    private final WSBoard board;

    public ButtonColor(WSBoard board) {
        this.board = board;
    }

    public void changeButton(int line,int col,ColorStack colorStack){
        Button button = board.getButton(line,col);
        button.setBackground(Background.fill(Color.web(colorStack.getRgbCode())));
    }
}
