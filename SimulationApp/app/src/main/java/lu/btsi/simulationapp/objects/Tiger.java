/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lu.btsi.simulationapp.objects;

import lu.btsi.simulationapp.DrawClasses.Color;
import android.graphics.Point;



/**
 *
 * @author cardosoken
 */
public class Tiger extends Entity {

    Tiger Tiger;

    public static Color color = Color.BLACK;
    String enemy = "Mouse";
    //private String className = getClass().getName();
    Point cellPoint;

    /**
     *
     * @param pPoint Tiger points
     */
    public Tiger(Point pPoint) {
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

    /**
     *
     * @return Just testing things..
     */
    public int movedir() {
        return cellPoint.x;
    }
    /**
     *
     * @return Just testing things..
     */
    public String getEnemy() {
        return enemy;
    }

    /**
     *
     * @param pColor Color of the cell
     */
    public void setColor(Color pColor) {

        this.color = pColor;
    }

    public Color getCellColor() {
        return color;
    }

    @Override
    public String getClassName() {
        return getClass().getSimpleName();
    }

    public Point doStep(Entity pGazelle) {

        Point writeState = cellPoint;
        if (getCellPointX() < pGazelle.getCellPointX()) {
            //cellPoint.x = cellPoint.x + 1;
            writeState.x = cellPoint.x + 1;
        }
        if (getCellPointY() < pGazelle.getCellPointY()) {
            //cellPoint.y = cellPoint.y + 1;
            writeState.y = cellPoint.y + 1;
        }
        if (getCellPointX() > pGazelle.getCellPointX()) {
            //cellPoint.x = cellPoint.x - 1;
            writeState.x = cellPoint.x - 1;
        }
        if (getCellPointY() > pGazelle.getCellPointY()) {
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
