package simulation;

import World.AllCells;
import World.Cell;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

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
    private static final int myX = 1000;
    private static final int myY = 800;

    boolean firstTimeDraw = false;
    //private final JPanel[][] tiles = new JPanel[rows][cols];
    AllCells allCells = new AllCells();

    public DrawPanel() {
        setLayout(new GridLayout(rows, cols));

    }

    @Override
    @SuppressWarnings("empty-statement")
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (firstTimeDraw == false) {
            // Build field
            g.setColor(Color.BLACK);
            // Game Dimension field
            g.drawRect(0, 0, ((rows * 20)), (cols * 20));

            int xRows = 0, y = -1;
            for (int i = 20; i <= (rows * 20); i += 20) {

                if (cols == y) {
                    y = 0;
                } else {
                    y++;
                }
                for (int j = 20; j <= (cols * 20); j += 20) {
                    if (rows == xRows) {
                        xRows = 0;
                    } else {
                        xRows++;
                    }
                    g.drawLine(i, 0, i, (cols * 20));
                    g.drawLine(0, j, (rows * 20), j);                    
                }

            }

            firstTimeDraw = true;
        } else {
            // Rebuild field
            g.setColor(Color.BLACK);
            // Game Dimension field
            g.drawRect(0, 0, ((rows * 20)), (cols * 20));
            //System.err.println(((rows * 20)) + " " + (cols * 20));

            int x = 0, y = 0;
            for (int i = 20; i <= (rows * 20); i += 20) {
                for (int j = 20; j <= (cols * 20); j += 20) {
                    g.drawLine(i, 0, i, (cols * 20));
                    g.drawLine(0, j, (rows * 20), j);
                }
            }
            // Insert the new cells
            if (allCells.allCells.size() > 0) {
                for (Cell fillCell : allCells.allCells) {
                    int cellX = (fillCell.getCellPointX() * 20);
                    int cellY = (fillCell.getCellPointY() * 20);
                    //System.err.println("Cell found with X:" + fillCell.getCellPointX() + " val:"+(fillCell.getCellPointX() * 20)+" Y:" + fillCell.getCellPointY()+" val:"+(fillCell.getCellPointY() * 20));
                    g.setColor(fillCell.getCellColor());
                    g.fillRect(cellX, cellY, 20, 20);
                }
            }
        }

 

        /*g.setColor(Color.BLACK);
        // Game Dimension field
        g.drawRect(0, 0, ((rows * 20)), (cols * 20));
        //System.err.println(((rows * 20)) + " " + (cols * 20));

        int x = 0, y = 0;
        for (int i = 20; i <= (rows * 20); i += 20) {

            for (int j = 20; j <= (cols * 20); j += 20) {

                
                g.drawLine(i, 0, i, (cols * 20));
                g.drawLine(0, j, (rows * 20), j);

            }
            System.err.println(x + " x : y " + y);
            Cell newCell = new Cell(new Point(x, y));
            allCells.allCells.add(newCell);
            y++;
            x++;
        }*/
 /*for (int i = 20; i <= (rows * 20); i += 20) {
            g.drawLine(i, 0, i, (cols * 20));
        }
        for (int i = 20; i <= (cols * 20); i += 20) {
            g.drawLine(0, i, (rows * 20), i);
        }*/
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(myX, myY);
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

}
