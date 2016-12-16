package Draw;

import Objects.AllEntities;
import Objects.Entity;
import Objects.Gazelle;
import Objects.Tiger;
import java.awt.*;
import java.util.Iterator;
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
    private static final int myY = 500; // 800

    private final int cellSize = 20;

    /**
     *
     * @return
     */
    public int getCellSize() {
        return cellSize;
    }

    boolean firstTimeDraw = false;
    //private final JPanel[][] tiles = new JPanel[rows][cols];

    /**
     * all Cells
     */
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
            g.drawRect(1, 1, ((rows * cellSize) - 1), (cols * cellSize) - 1);
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
                allCells.allEntities.forEach((fillCell) -> {
                    int cellX = (fillCell.getCellPointX() * cellSize);
                    int cellY = (fillCell.getCellPointY() * cellSize);
                    g.setColor(fillCell.getCellColor());
                    g.fillRect(cellX, cellY, cellSize, cellSize);
                });
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(myX, myY);
    }

    /**
     *
     * @return Field and cell size X
     */
    public int getGameFieldSizeX() {
        return rows * cellSize;
    }

    /**
     *
     * @return Field and cell size Y
     */
    public int getGameFieldSizeY() {
        return cols * cellSize;
    }

    /**
     *
     * @param myCols columns
     * @param myRows rows
     */
    public void setColsRows(int myCols, int myRows) {
        cols = myCols;
        rows = myRows;
    }

    /**
     *
     * @param mybackgroundColor background color
     */
    public void setBackgroundColor(Color mybackgroundColor) {
        this.backgroundColor = mybackgroundColor;
    }

    /**
     *
     * @param myforgroundColor foreground color (grid color)
     */
    public void setForgroundColor(Color myforgroundColor) {
        this.forgroundColor = myforgroundColor;
    }

    /**
     *
     * @return columns count
     */
    public int getCols() {
        return cols;
    }

    /**
     *
     * @return rows count
     */
    public int getRows() {
        return rows;
    }

    /**
     *
     * @param pGazelle
     * @return Current latest point of a tiger (to escape from)
     */
    public Entity getTiger(Point pGazelle) {
        Entity e = null;
        Iterator<Entity> iter = allCells.allEntities.iterator();
        while (iter.hasNext()) {
            Entity entity = iter.next();
            if (entity instanceof Tiger) {
                // Check if a Gazelle has the same write state
                if (pGazelle == entity.getEntityPoint()) {
                    System.err.println("Matching tiger was found");
                    e = entity;
                } else {
                    e = entity;
                }
            }
        }
        return e;
    }

    /**
     *
     * @return Current latest point of a gazelle (to catch on)
     */
    public Entity getGazelle() {
        Entity e = null;
        Iterator<Entity> iter = allCells.allEntities.iterator();
        while (iter.hasNext()) {
            Entity entity = iter.next();
            if (entity instanceof Gazelle) {
                e = entity;
            }
        }
        return e;
    }

    /**
     * peform cells movements
     */
    public void doSteps() {
        if (allCells.allEntities.size() > 0) {
            Iterator<Entity> iter = allCells.allEntities.iterator();
            while (iter.hasNext()) {
                Entity entity = iter.next();
                if (entity instanceof Tiger) {
                    if (getGazelle() != null) {
                        if (entity.getCellPointX() == (rows - 1)) {
                            entity.resetCellPointX(0);
                        }
                        if (entity.getCellPointY() == (cols - 1)) {
                            entity.resetCellPointY(0);
                        }
                        entity.doStep(getGazelle());
                    }
                }
                if (entity instanceof Gazelle) {
                    // The old one the read state
                    Point readState = entity.getEntityPoint();
                    if (entity.getCellPointX() == (rows - 1)) {
                        entity.resetCellPointX(0);
                    }
                    if (entity.getCellPointY() == (cols - 1)) {
                        entity.resetCellPointY(0);
                    }
                    if (getTiger(null) != null) {
                        // Get the write sate
                        Point writeState = entity.doStep(getTiger(null));
                        if (readState.equals(getTiger(readState).getEntityPoint())) {
                            System.err.println("Cell was eaten");
                            iter.remove();
                        } else {
                            entity.setNewPoint(writeState);
                        }
                    }

                }

            }
        }
    }
}
