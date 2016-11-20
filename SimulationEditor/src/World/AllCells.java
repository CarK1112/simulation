/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package World;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author kenca
 */
public class AllCells {

    public ArrayList<Cell> allCells = new ArrayList<>(25);

    public void addCell(Cell pCell) {
        allCells.add(pCell);
    }
        

    public void remove(int i) {
        allCells.remove(i);
    }

}
