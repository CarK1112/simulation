/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

/**
 *
 * @author kenca
 */
public enum ObjectEnumNames {

    // String name (may contain spaces), className (assigned class to)

    /**
     * Enum used to generate the ComboBox
     */
    TIGER("Tiger","Tiger"),

    /**
     * Enum used to generate the ComboBox
     */
    GAZELLE("Gazelle","Gazelle");

    private String displayName,className;

    ObjectEnumNames(String displayName, String className) {
        this.displayName = displayName;
        this.className = className;
    }

    /**
     *
     * @return Name of cell
     */
    public String displayName() {
        return displayName;
    }
    @Override
    public String toString() {
        return displayName;
    }

    /**
     *
     * @return className of cell
     */
    public String getClassName() {
        return className;
    }
}
