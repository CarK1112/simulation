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

    public abstract Point getEntityPoint();
    public abstract int getCellPointX();

    public abstract int getCellPointY();

    public abstract Color getCellColor();

    public abstract String getClassName();

    public abstract String getEnemy();

    public abstract void doStep(Entity pEntity);

    public abstract void resetCellPointX(int pNewPos);

    public abstract void resetCellPointY(int pNewPos);
}
