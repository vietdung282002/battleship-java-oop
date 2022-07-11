package org.fxapps.battleship.app.model;

public enum Difficulty {

    HARD(80),

    MEDIUM(50),
    EASY(20);


    int hitProbability;

    Difficulty(int hitProbability) {
        this.hitProbability = hitProbability;
    }

    public int getHitProbability() {
        return hitProbability;
    }
}