package pt.ipbeja.app.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.ipbeja.app.model.BoardContent;
import pt.ipbeja.app.model.WSModel;

import java.io.IOException;

/**
 * Start a game with a hardcoded board
 * @author anonymized
 * @version 2024/04/14
 */
public class StartWordSearch extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        BoardContent b = new BoardContent(6,6);
        Character[][] bb = b.Board();
        String bbb = b.BoardToString(bb);

        WSModel WSModel = new WSModel(bbb);

        WSBoard WSBoard = new WSBoard(WSModel);

        primaryStage.setScene(new Scene(WSBoard));

        WSModel.registerView(WSBoard);
        WSBoard.requestFocus(); // to remove focus from first button
        primaryStage.show();
    }

    /**
     * @param args  not used
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}
