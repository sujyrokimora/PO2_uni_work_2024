package pt.ipbeja.po2.app.model;

import org.junit.jupiter.api.Test;
import pt.ipbeja.app.model.BoardContent;
import pt.ipbeja.app.model.MessageToUI;
import pt.ipbeja.app.model.WSModel;
import pt.ipbeja.app.model.WSView;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WSModelTest {
    @Test
    void testWordFound() throws IOException {
        BoardContent board= new BoardContent(8,8);
        Character[][] bb = board.Board();
        String bbb = board.BoardToString(bb);
        WSModel model = new WSModel(bbb);
        this.registerEmptyView(model);
        assertEquals("XAVIER", model.wordFound("XAVIER"));
    }
    @Test
    void testWordWithWildcardFound() throws IOException {
        BoardContent board = new BoardContent(8,8);
        Character[][] bb = board.Board();
        WSModel model = new WSModel("X*VIER\nEDDAGH\nIDDSKL\nMSDDOP");
        this.registerEmptyView(model);
        assertEquals("XAVIER", model.wordWithWildcardFound("X*VIER"));
    }
    @Test
    void testAllWordsWereFound() {
        WSModel model = new WSModel("MALA\nECGH\nIAKL\nMSOP");
        this.registerEmptyView(model);
        assertEquals("MALA", model.wordFound("MALA"));
        assertEquals("CA", model.wordFound("CA"));
        assertTrue(model.allWordsWereFound());
    }


    private void registerEmptyView(WSModel model) {
        model.registerView(new WSView() {
            @Override
            public void update(MessageToUI message) {
                // do nothing
            }
        });
    }
}

