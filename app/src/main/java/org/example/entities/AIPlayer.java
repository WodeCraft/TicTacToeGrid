package org.example.entities;

import org.example.ai.Move;
import org.example.enums.FirstMove;
import org.example.tictactoe.MainActivity;

/**
 * Default implementation of an AI player for a game of Tic-Tac-Toe
 * Created by Jens Christian Rasch on 22-02-2018.
 */

public class AIPlayer extends AbstractPlayer {

    private int[] squareIds = new int[9];
    public FirstMove centerFirstMove = FirstMove.CENTER;

    public AIPlayer(int[] squareIds, Board board) {
        this.board = board;
        this.squareIds = squareIds;
    }

    @Override
    public void takeTurn(MainActivity caller) {

        Move move = aiAlgorithm.ply(board, this);

        caller.performMove(move.squareId);
    }

}
