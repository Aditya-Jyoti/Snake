import javax.swing.*;
import java.awt.*;

public class Cell extends JComponent {
    private int xIdx;
    private int yIdx;
    private int state = -1; // -1 empty, 0 snake, 1 food
    private Color colour = Color.BLACK;

    public Cell(int xIdx, int yIdx) {
        this.xIdx = xIdx;
        this.yIdx = yIdx;

        this.setBounds(
                this.xIdx * Constants.CELL_SIZE,
                this.yIdx * Constants.CELL_SIZE,
                Constants.CELL_SIZE,
                Constants.CELL_SIZE
        );

        this.setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(colour);
        g.fillRect(0, 0, Constants.CELL_SIZE, Constants.CELL_SIZE);
    }

    private void updateCell() {
        switch (state) {
            case -1:
                colour = Color.BLACK;
                break;
            case 0:
                colour = Color.WHITE;
                break;
            case 1:
                colour = Color.RED;
                break;
        }
    }

    /* setters */
    public void setState(int newState) {
        this.state = newState;

        this.updateCell();
        this.repaint();
    }

    public void setXIdx(int newXIdx) {
        this.xIdx = newXIdx;

        this.updateCell();
        this.repaint();
    }

    public void setYIdx(int newYIdx) {
        this.yIdx = newYIdx;

        this.updateCell();
        this.repaint();
    }

    /* getters */
    public Color getColour() {
        return this.colour;
    }

    public int getXIdx() {
        return this.xIdx;
    }

    public int getYIdx() {
        return this.yIdx;
    }
}
