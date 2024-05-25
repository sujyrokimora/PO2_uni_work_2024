package pt.ipbeja.app.ui;

import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.ipbeja.app.model.Position;
import pt.ipbeja.app.model.Colors;


public class LetterButton extends Button {
    private final int SQUARE_SIZE = 80;
    private final Position position;
    public LetterButton(String l, int line, int col) {
        this.setText(l);
        this.setMinWidth(SQUARE_SIZE);
        this.setMinHeight(SQUARE_SIZE);
        this.position = new Position(line,col);

        BorderStroke borderStroke = new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(1));
        Border border = new Border(borderStroke);

        this.setBorder(border);
        this.setBackground(Background.fill(Color.LIGHTGREY));

    }
    public void setGreen(){
        this.setBackground(Background.fill(Color.GREEN));
    }
    public void setGREY(){
        this.setBackground(Background.fill(Color.LIGHTGREY));
    }
    public void setYellow(){
        this.setBackground(Background.fill(Color.YELLOW));
    }

    public Position getPosition() {
        return position;
    }

    public void setColor(Colors color) {
        switch (color) {
            case GREY -> {
                setGREY();
            }
            case GREEN -> {
                setGreen();
            }
            case YELLOW -> {
                setYellow();
            }
        }
    }
}
