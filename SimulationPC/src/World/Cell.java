/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package World;

import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author cardosoken
 */
public abstract class Cell {

    Point cellPoint;

    public Cell(Point pPoint) {
        this.cellPoint = pPoint;
    }

    public int getCellPointX() {
        if (cellPoint != null) {
            return cellPoint.x;
        } else {
            return -1;
        }
    }

    public int getCellPointY() {
        if (cellPoint != null) {
            return cellPoint.y;
        } else {
            return -1;
        }
    }

    public int setCellPointX(int pStepX) {
        return cellPoint.x + pStepX;
    }

    public int setCellPointY(int pStepY) {
        return cellPoint.y + pStepY;
    }

    public abstract Color getCellColor();

    public abstract String getClassName();
}
