package pt.ipbeja.app.ui;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import pt.ipbeja.app.model.*;

import java.util.List;


/**
 * Game interface. Just a GridPane of buttons. No images. No menu.
 * @author anonymized
 * @version 2024/04/14
 */
public class WSBoard extends GridPane implements WSView {

    private final WSModel wsModel;
    private static final int SQUARE_SIZE = 80;

    /**
     * Create a board with letters
     */
    public WSBoard(WSModel wsModel) {
        this.wsModel = wsModel;
        this.buildGUI();
    }

    /**
     * Build the interface
     */
    private void buildGUI() {
        assert (this.wsModel != null);
        // create one label for each position
        ButtonHandler bHandler = new ButtonHandler();
        for (int line = 0; line < this.wsModel.nLines(); line++) {
            for (int col = 0; col < this.wsModel.nCols(); col++) {
                String textForButton = this.wsModel.textInPosition(new Position(line, col));
                LetterButton button = new LetterButton(textForButton, line, col);
                button.setOnAction(bHandler);
                this.add(button, col, line); // add button to GridPane
            }
        }
        this.requestFocus();
    }

    public WSModel getWSModel() {
        return this.wsModel;
    }

    class ButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            LetterButton buttonClicked = (LetterButton) (actionEvent.getSource());
            getWSModel().click(buttonClicked.getPosition());        }
    }

    /**
     * Can be optimized using an additional matrix with all the buttons
     * @param line line of label in board
     * @param col column of label in board
     * @return the button at line, col
     */
    public Button getButton(int line, int col) {
        ObservableList<Node> children = this.getChildren();
        for (Node node : children) {
            if(GridPane.getRowIndex(node) == line && GridPane.getColumnIndex(node) == col) {
                assert(node.getClass() == Button.class);
                return (Button)node;
            }
        }
        assert(false); // must not happen
        return null;
    }

    private LetterButton getLetterButton(Position position) {
        ObservableList<Node> children = this.getChildren();
        for (Node node : children) {
            if (node instanceof LetterButton) {
                LetterButton button = (LetterButton) node;
                if (GridPane.getRowIndex(node) == position.line() && GridPane.getColumnIndex(node) == position.col()) {
                    return button;
                }
            }
        }
        return null;
    }
    /**
     * Simply updates the text for the buttons in the received positions
     *
     * @param messageToUI the WS model
     */
    @Override
    public void update(MessageToUI messageToUI) {
        for (Position p : messageToUI.positions()) {
            String s = this.wsModel.textInPosition(p);
            this.getButton(p.line(), p.col()).setText(s);
        }
        if (this.wsModel.allWordsWereFound()) {
            this.wsModel.endGame();
        }
    }

    @Override
    public void changeColor(Colors color, Position position) {
        LetterButton btn = getLetterButton(position);
        btn.setColor(color);
    }

    @Override
    public void wordChangeColor(Colors color, List<Position> positions) {
        for(int i = 0; i < positions.size(); i++){
            LetterButton btn = getLetterButton(positions.get(i));
            btn.setColor(color);
        }
    }

}
