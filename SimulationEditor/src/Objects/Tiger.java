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

    private Tiger Tiger;

    
    private Color color = Color.BLACK;
    //private String className = getClass().getName();
    public Tiger(Point pPoint) {
        super(pPoint);
    }

    public void setColor(Color pColor) {
        this.color = pColor;
    }

    @Override
    public Color getCellColor() {
        return color;
    }

    public String getClassName() {
        return getClass().getName();
    }
    
}
