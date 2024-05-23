package pt.ipbeja.app.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.ipbeja.app.model.Position;
import pt.ipbeja.app.model.State;
import pt.ipbeja.app.model.ColorStack;
import pt.ipbeja.app.model.WSModel;

import static pt.ipbeja.app.model.State.*;

public class Letter extends Button {
    private final int SQUARE_SIZE = 80;
    private State state = DEFAULT;
    private Position position;
    public Letter(String l, int line, int col) {
        this.setText(l);
        this.setMinWidth(SQUARE_SIZE);
        this.setMinHeight(SQUARE_SIZE);
        this.position= new Position(line,col);

        BorderStroke borderStroke = new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(1));
        Border border = new Border(borderStroke);

        this.setBorder(border);
        this.setOnAction(new ButtonHandler());
        this.setBackground(Background.fill(Color.web(ColorStack.ALABASTER.getRgbCode())));
    }

    private void updateBackground() {
        switch (state) {
            case DEFAULT:
                this.setBackground(new Background(new BackgroundFill(Color.web(ColorStack.ALABASTER.getRgbCode()), CornerRadii.EMPTY, null)));
                break;
            case FOUND:
                this.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, null)));
                break;
            case PRESSED:
                this.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, null)));
                break;
        }
    }

    public Position getPosition() {
        return position;
    }

    public void setState(State state) {
        this.state = state;
        updateBackground(); // Update background when state changes
    }

    static class ButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            Letter buttonClicked = (Letter)(actionEvent.getSource());
            System.out.println(buttonClicked.getPosition());

        }
    }
}
