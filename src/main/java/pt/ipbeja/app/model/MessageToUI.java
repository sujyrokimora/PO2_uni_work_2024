package ${IJ_BASE_PACKAGE}.app.model;

import java.util.List;

/**
 * Message to be sent from the model so that the interface updates the positions in the list
 * @author anonymized
 * @version 2024/04/14
 */
public record MessageToUI(List<Position> positions, String message) {}
