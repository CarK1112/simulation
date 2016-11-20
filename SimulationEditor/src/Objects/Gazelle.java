/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import World.Cell;
import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author cardosoken
 */
public class Gazelle extends Cell{
    
        private Color color = Color.YELLOW;
    public Gazelle(Point pPoint) {
        super(pPoint);
    }

    public void setColor(Color pColor) {
        this.color = pColor;
    }

    public Color getCellColor() {
        return color;
    }
}
