package ${IJ_BASE_PACKAGE}.po2.app.model;

import org.junit.jupiter.api.Test;
import ${IJ_BASE_PACKAGE}.app.model.MessageToUI;
import ${IJ_BASE_PACKAGE}.app.model.WSModel;
import ${IJ_BASE_PACKAGE}.app.model.WSView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WSModelTest {

    @Test
    void testWordFound() {
        WSModel model = new WSModel("ACCD\nEAGH\nISKL\nMAOP");
        this.registerEmptyView(model);

        assertEquals("CASA", model.wordFound("CASA"));
    }

    @Test
    void testWordWithWildcardFound() {
        WSModel model = new WSModel("MA*A\nEAGH\nISKL\nMSOP");
        this.registerEmptyView(model);
        assertEquals("MALA", model.wordWithWildcardFound("MALA"));
    }

    @Test
    void testallWordsWereFound() {
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

