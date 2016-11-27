/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import java.awt.Color;
import java.awt.Point;
/**
 *
 * @author cardosoken
 */
public abstract class Entity {

    Point cellPoint;

    public Entity(Point pPoint) {

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
        cellPoint.x = cellPoint.x + pStepX;
        return cellPoint.x;
    }

    public int setCellPointY(int pStepY) {
        cellPoint.y = cellPoint.y + pStepY;
        return cellPoint.y;
    }

    public abstract Color getCellColor();

    //public abstract String getClassName();
}
