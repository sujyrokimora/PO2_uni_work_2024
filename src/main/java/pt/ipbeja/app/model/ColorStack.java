package pt.ipbeja.app.model;

public enum ColorStack {
    GREEN("00FF00"),
    ALABASTER("EDEADE"),
    YELLOW("FFFF00");

    private String rgbCode;

    ColorStack(String rgbCode) {
        this.rgbCode = rgbCode;
    }

    public String getRgbCode() {
        return this.rgbCode;
    }
}
