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
public class Gazelle extends Entity {

    private Gazelle Gazelle;

    private Color color = Color.YELLOW;
    private String enemy = "Tiger";
    //private String className = getClass().getName();
    Point cellPoint;

    public Gazelle(Point pPoint) {

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

    public void setColor(Color pColor) {
        this.color = pColor;
    }

    @Override
    public Color getCellColor() {
        return color;
    }

    public String getClassName() {
        return getClass().getSimpleName();
    }

    public void doStep(Entity pTiger) {
        if (getCellPointX() < pTiger.getCellPointX()) {
            cellPoint.x = cellPoint.x - 1;
        } else if (getCellPointY() < pTiger.getCellPointY()) {
            cellPoint.y = cellPoint.y - 1;
        } else if (getCellPointX() > pTiger.getCellPointX()) {
            cellPoint.x = cellPoint.x + 1;
        } else if (getCellPointY() > pTiger.getCellPointY()) {
            cellPoint.y = cellPoint.y + 1;
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
