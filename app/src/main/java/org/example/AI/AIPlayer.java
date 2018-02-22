package org.example.AI;

import org.example.enums.BoardStatus;

/**
 * Created by Jens Christian Rasch on 22-02-2018.
 */

public class AIPlayer {

    private BoardStatus mySeed;
    private BoardStatus opponentSeed;

    public void setSeed(BoardStatus seed) {
        mySeed = seed;
        opponentSeed = mySeed == BoardStatus.Nought ? BoardStatus.Cross : BoardStatus.Nought;
    }

    public void move() {
        // Decide on a place to move to
    }
}
