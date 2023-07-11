package app.newenergyschool.game;

import android.graphics.Color;

public class Brick {
    private boolean isVisible;
    public int row, column, width, height;
    Color color;

    public Brick(int row, int column, int width, int height, Color color) {
        isVisible = true;
        this.row = row;
        this.column = column;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible() {
        isVisible = false;
    }
}
