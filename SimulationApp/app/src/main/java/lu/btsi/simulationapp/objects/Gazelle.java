/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lu.btsi.simulationapp.objects;
/*import java.awt.Color;
import java.awt.Point;*/


import lu.btsi.simulationapp.DrawClasses.Color;

import android.graphics.Point;

/**
 *
 * @author cardosoken
 */


public class Gazelle extends Entity {

    private Gazelle Gazelle;

    public static Color color = Color.YELLOW;
    private String enemy = "Tiger";
    //private String className = getClass().getName();
    Point cellPoint;

    /**
     *
     * @param pPoint Set point
     */
    public Gazelle(Point pPoint) {
        this.cellPoint = pPoint;
    }
    public void setNewPoint(Point pPoint) {
        this.cellPoint = pPoint;
    }
    public Point getEntityPoint() {
        return cellPoint;
    }

    public int getCellPointX() {
        return cellPoint.x;
    }

    public int getCellPointY() {
        return cellPoint.y;
    }

    public String getEnemy() {
        return enemy;
    }

    /**
     *
     * @param pColor Set Color
     */
    public void setColor(Color pColor) {
        this.color = pColor;
    }

    public Color getCellColor() {
        return color;
    }

    public String getClassName() {
        return getClass().getSimpleName();
    }

    public Point doStep(Entity pTiger) {
        Point writeState = cellPoint;
        if (getCellPointX() < pTiger.getCellPointX()) {
            //cellPoint.x = cellPoint.x + 1;
            writeState.x = cellPoint.x - 1;
        }
        if (getCellPointY() < pTiger.getCellPointY()) {
            //cellPoint.y = cellPoint.y + 1;
            writeState.y = cellPoint.y - 1;
        }
        if (getCellPointX() > pTiger.getCellPointX()) {
            //cellPoint.x = cellPoint.x - 1;
            writeState.x = cellPoint.x + 1;
        }
        if (getCellPointY() > pTiger.getCellPointY()) {
            //cellPoint.y = cellPoint.y - 1;
            writeState.y = cellPoint.y - 1;
        }
        return writeState;
    }

    @Override
    public void resetCellPointX(int pNewPos) {
        cellPoint.x = pNewPos;
    }

    @Override
    public void resetCellPointY(int pNewPos) {
        cellPoint.y = pNewPos;
    }
}