/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lu.btsi.simulationapp.objects;

/**
 *
 * @author kenca
 */
public enum ObjectEnumNames {

    // String name (may contain spaces), className (assigned class to)
    TIGER("Tiger","Tiger"),
    GAZELLE("Gazelle","Gazelle");

    private String displayName,className;

    ObjectEnumNames(String displayName, String className) {
        this.displayName = displayName;
        this.className = className;
    }

    public String displayName() {
        return displayName;
    }
    @Override
    public String toString() {
        return displayName;
    }

    public String getClassName() {
        return className;
    }
}
