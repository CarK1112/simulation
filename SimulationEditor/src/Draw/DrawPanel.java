package Draw;

import Objects.AllEntities;
import Objects.Entity;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author carke112
 */
public class DrawPanel extends JPanel {

    private int rows = 10;
    private int cols = 10;
    //int backgroundColor, forgroundColor;
    Color backgroundColor = Color.WHITE;
    Color forgroundColor = Color.BLACK;
    // Field resolution
    private static final int myX = 500; // 1000
    private static final int myY = 400; // 800

    private int cellSize = 20;

    public int getCellSize() {
        return cellSize;
    }

    boolean firstTimeDraw = false;
    //private final JPanel[][] tiles = new JPanel[rows][cols];
    public AllEntities allCells = new AllEntities();

    @Override
    @SuppressWarnings("empty-statement")
    protected void paintComponent(Graphics g) {
        //
        super.paintComponent(g);
        if (firstTimeDraw == false) {
            // Build field
            g.setColor(Color.BLACK);
            // Game Dimension field
            g.drawRect(0, 0, ((rows * cellSize)), (cols * cellSize));

            int xRows = 0, y = -1;
            for (int i = cellSize; i <= (rows * cellSize); i += cellSize) {

                if (cols == y) {
                    y = 0;
                } else {
                    y++;
                }
                for (int j = cellSize; j <= (cols * cellSize); j += cellSize) {
                    if (rows == xRows) {
                        xRows = 0;
                    } else {
                        xRows++;
                    }
                    g.drawLine(i, 0, i, (cols * cellSize));
                    g.drawLine(0, j, (rows * cellSize), j);
                }

            }

            firstTimeDraw = true;
        } else {
            // Rebuild field
            g.setColor(Color.BLACK);
            // Game Dimension field
            g.drawRect(0, 0, ((rows * cellSize)), (cols * cellSize));
            //System.err.println(((rows * cellSize)) + " " + (cols * cellSize));

            int x = 0, y = 0;
            for (int i = cellSize; i <= (rows * cellSize); i += cellSize) {
                for (int j = cellSize; j <= (cols * cellSize); j += cellSize) {
                    g.drawLine(i, 0, i, (cols * cellSize));
                    g.drawLine(0, j, (rows * cellSize), j);
                }
            }
            // Insert the new cells
            if (allCells.allEntities.size() > 0) {
                for (Entity fillCell : allCells.allEntities) {
                        int cellX = (fillCell.getCellPointX() * cellSize);
                        int cellY = (fillCell.getCellPointY() * cellSize);
                        g.setColor(fillCell.getCellColor());
                        g.fillRect(cellX, cellY, cellSize, cellSize);

                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(myX, myY);
    }

    public int getGameFieldSizeX() {
        return rows * cellSize;
    }

    public int getGameFieldSizeY() {
        return cols * cellSize;
    }

    public void setColsRows(int myCols, int myRows) {
        cols = myCols;
        rows = myRows;
    }

    public void setBackgroundColor(Color mybackgroundColor) {
        this.backgroundColor = mybackgroundColor;
    }

    public void setForgroundColor(Color myforgroundColor) {
        this.forgroundColor = myforgroundColor;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public void doSteps() {
        if (allCells.allEntities.size() > 0) {
            for (Entity fillCell : allCells.allEntities) {
                fillCell.setCellPointX(1);
                fillCell.setCellPointY(1);
            }
        }
    }
}
