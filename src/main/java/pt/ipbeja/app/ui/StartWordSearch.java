package ${IJ_BASE_PACKAGE}.app.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ${IJ_BASE_PACKAGE}.app.model.WSModel;

/**
 * Start a game with a hardcoded board
 * @author anonymized
 * @version 2024/04/14
 */
public class StartWordSearch extends Application {

    @Override
    public void start(Stage primaryStage) {

        final String boardContent =
                """
                CASAIAED
                FFWFMERW
                WIQFELAA
                OFLFESFI
                EFFAFFPP""";

        WSModel WSModel = new WSModel(boardContent);

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
