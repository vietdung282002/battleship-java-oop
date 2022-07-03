package org.fxapps.battleship.app.model;

public enum Difficulty {

    HARD(100),

    EASY(0);


    int hitProbability;

    Difficulty(int hitProbability) {
        this.hitProbability = hitProbability;
    }

    public int getHitProbability() {
        return hitProbability;
    }
}
