/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lu.btsi.simulationapp.objects;


/*import java.DrawClasses.Color;
import java.DrawClasses.Point;*/

import lu.btsi.simulationapp.DrawClasses.Color;
import android.graphics.Point;


/**
 *
 * @author cardosoken
 */
public abstract class Entity {

    /**
     *
     * @param pPoint Sets the Point of a cell
     */
    public abstract void setNewPoint(Point pPoint);

    /**
     *
     * @return point of the cell
     */
    public abstract Point getEntityPoint();

    /**
     *
     * @return X point
     */
    public abstract int getCellPointX();

    /**
     *
     * @return Y point
     */
    public abstract int getCellPointY();

    /**
     *
     * @return Cell color
     */
    public abstract Color getCellColor();

    /**
     *
     * @return class Name (Importing the type for the load function)
     */
    public abstract String getClassName();

    /**
     *
     * @return Does nothing, just trying things.
     */
    public abstract String getEnemy();

    /**
     *
     * @param pEntity The specific cell to perform the step
     * @return
     */
    public abstract Point doStep(Entity pEntity);

    /**
     *
     * @param pNewPos Reset the X point (Ref. Torus)
     */
    public abstract void resetCellPointX(int pNewPos);

    /**
     *
     * @param pNewPos Reset the Y point (Ref. Torus)
     */
    public abstract void resetCellPointY(int pNewPos);
}
