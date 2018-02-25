package org.example.entities;

import org.example.ai.IBaseAI;
import org.example.enums.BoardStatus;
import org.example.tictactoe.MainActivity;

/**
 * Abstract class for implementing players for a game of Tic-Tac-Toe.
 * Created by Jens Christian Rasch on 23-02-2018.
 */

public abstract class AbstractPlayer {

    protected BoardStatus mySeed;
    protected AbstractPlayer opponent;

    protected Board board;

    protected IBaseAI aiAlgorithm;

    public abstract void takeTurn(MainActivity caller);

    public void setSeed(BoardStatus seed) {
        mySeed = seed;
    }
    public BoardStatus getSeed() {
        return mySeed;
    }

    public BoardStatus getOpponentSeed() { return opponent.getSeed(); }

    public void setOppenent(AbstractPlayer opponent) {
        this.opponent = opponent;
    }

    public AbstractPlayer getOpponent() { return opponent; }

    public void setAiAlgorithm(IBaseAI algorithm) {
        aiAlgorithm = algorithm;
    }

}
