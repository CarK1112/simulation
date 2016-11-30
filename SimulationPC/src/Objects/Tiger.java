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
public class Tiger extends Entity {

    Tiger Tiger;

    Color color = Color.BLACK;
    String enemy = "Mouse";
    //private String className = getClass().getName();
    Point cellPoint;

    public Tiger(Point pPoint) {

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
    public int movedir() {
        return cellPoint.x;
    }

    public String getEnemy() {
        return enemy;
    }

    public void setColor(Color pColor) {
        this.color = pColor;
    }

    @Override
    public Color getCellColor() {
        return color;
    }

    @Override
    public String getClassName() {
        return getClass().getSimpleName();
    }

    public void doStep(Entity pGazelle) {

        if (getCellPointX() < pGazelle.getCellPointX()) {
            cellPoint.x = cellPoint.x + 1;
        }
        else if (getCellPointY() < pGazelle.getCellPointY()) {
            cellPoint.y = cellPoint.y + 1;
        }
        else if (getCellPointX() > pGazelle.getCellPointX()) {
            cellPoint.x = cellPoint.x - 1;
        }
        else if (getCellPointY() > pGazelle.getCellPointY()) {
            cellPoint.y = cellPoint.y - 1;
        }
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
