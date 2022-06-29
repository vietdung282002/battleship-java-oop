package org.fxapps.battleship.model;

public enum Ship {

    CARRIER("Carrier", 4),
    BATTLESHIP("Battleship", 3),
    CRUISER("Cruiser", 2),
    SUBMARINE("Submarine", 3),
    DESTROYER("Destroyer", 5);

    final int spaces;
    final String name;

    Ship(String name, int spaces) {
        this.name = name;
        this.spaces = spaces;
    }

    public int getSpaces() {
        return spaces;
    }

    public String getName() {
        return name;
    }
   
}