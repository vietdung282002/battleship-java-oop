package org.fxapps.battleship.app.model;

public enum Difficulty {

    HARD(50),
    EASY(10);


    int hitProbability;

    Difficulty(int hitProbability) {
        this.hitProbability = hitProbability;
    }

    public int getHitProbability() {
        return hitProbability;
    }
}
