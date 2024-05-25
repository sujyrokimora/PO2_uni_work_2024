package pt.ipbeja.app.model;


import pt.ipbeja.app.ui.LetterButton;

import java.util.List;

/**
 * View
 * @author anonymized
 * @version 2024/04/14
 */
public interface WSView {

    void update(MessageToUI messageToUI);

    void changeColor(Colors color, Position pos);

    void wordChangeColor(Colors color, List<Position> positions);
}
