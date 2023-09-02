import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import java.util.List;

public class Main extends JFrame {
    private final List<List<Cell>> gameboard = new ArrayList<>();
    private final List<Cell> snake = new ArrayList<>();
    private Cell food;

    private boolean running = true;
    private char dir = 'w';

    public Main() {
        super("Snake");

        this.setSize(Constants.WIDTH, Constants.HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.BLACK);

        this.setLayout(new GridLayout(Constants.CELLS_IN_ROW, Constants.CELLS_IN_COL));

        for (int y = 0; y < Constants.CELLS_IN_COL; y++) {
            List<Cell> cellRow = new ArrayList<>();
            for (int x = 0; x < Constants.CELLS_IN_ROW; x++) {
                Cell cell = new Cell(x, y);
                cellRow.add(cell);
                this.add(cell);
            }
            this.gameboard.add(cellRow);
        }

        Cell startCell = this.gameboard.get(Constants.CELLS_IN_COL / 2).get(Constants.CELLS_IN_ROW / 2);
        startCell.setState(0);
        this.snake.add(startCell);

        this.food = this.gameboard.get(1).get(1);
        this.food.setState(1);

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == 'w') {
                    Main.this.dir = 'w';
                } else if (e.getKeyChar() == 's') {
                    Main.this.dir = 's';
                } else if (e.getKeyChar() == 'a') {
                    Main.this.dir = 'a';
                } else if (e.getKeyChar() == 'd') {
                    Main.this.dir = 'd';

                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    Main.this.running = false;
                    Main.this.dispose();
                }
            }
        });
    }

    private void generateFood() {
        Random random = new Random();
        int randomX = random.nextInt(Constants.CELLS_IN_ROW - 1);
        int randomY = random.nextInt(Constants.CELLS_IN_COL - 1);

        for (Cell cell: this.snake) {
            if (randomX == cell.getXIdx() && randomY == cell.getYIdx()) {
                generateFood();
            }
        }

        this.food = this.gameboard.get(randomY).get(randomX);
        this.food.setState(1);
    }

    private void growSnake(Cell growthCell) {
        this.snake.add(growthCell);
    }

    private void moveSnake() {
        Cell headCell = this.snake.get(0);

        if (headCell.getXIdx() == this.food.getXIdx() && headCell.getYIdx() == this.food.getYIdx()) {
            this.food.setState(-1);

            Cell lastCell = this.snake.get(this.snake.size() - 1);
            Cell growthCell = this.gameboard.get(lastCell.getYIdx()).get(lastCell.getXIdx() + 1);
            growthCell.setState(0);
            this.growSnake(growthCell);

            this.generateFood();
        }

        if (this.dir == 'w') {
            Cell addingCell = this.gameboard.get(headCell.getYIdx() - 1).get(headCell.getXIdx());
            addingCell.setState(0);
            this.snake.add(0, addingCell);

        } else if (this.dir == 'a') {
            Cell addingCell = this.gameboard.get(headCell.getYIdx()).get(headCell.getXIdx() - 1);
            addingCell.setState(0);
            this.snake.add(0, addingCell);

        } else if (this.dir == 's') {
            Cell addingCell = this.gameboard.get(headCell.getYIdx() + 1).get(headCell.getXIdx());
            addingCell.setState(0);
            this.snake.add(0, addingCell);

        } else if (this.dir == 'd') {
            Cell addingCell = this.gameboard.get(headCell.getYIdx()).get(headCell.getXIdx() + 1);
            addingCell.setState(0);
            this.snake.add(0, addingCell);

        }

        Cell removingCell = this.snake.get(this.snake.size() - 1);
        removingCell.setState(-1);
        this.snake.remove(removingCell);
    }

    public void run() {
        this.setVisible(true);
        while (this.running) {
            this.moveSnake();

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        Main snake = new Main();
        snake.run();
    }
}