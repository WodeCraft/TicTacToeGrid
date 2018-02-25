package org.example.ai;

import org.example.entities.AbstractPlayer;
import org.example.entities.Board;
import org.example.enums.BoardStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the MinMax AI algorithm.
 * Created by Jens Christian Rasch on 24-02-2018.
 */

public class MinmaxAI implements IBaseAI {

    @Override
    public Move ply(Board board, AbstractPlayer player) {
        Move move = new Move();
        List<Integer> availSquares = board.getAllEmptySquares();

        if (board.checkForWinCondition(BoardStatus.NOUGHT)) {
            // I win
            move.score = -10;
            return move;
        } else if (board.checkForWinCondition(player.getOpponentSeed())) {
            // Opponent wins
            move.score = 10;
            return move;
        } else if (availSquares.size() == 0) {
            // Tie
            move.score = 0;
            return move;
        }

        List<Move> moves = new ArrayList<>();

        for(Integer square : availSquares) {
            Move newMove = new Move();
            newMove.squareId = square;

            // Set the empty square to be filled by the current player
            board.setSquareStatus(square, player.getSeed());

            // Recursive call to ply with opponent
            Move result = ply(board, player.getOpponent());
            newMove.score = result.score;

            // Reset the square to empty
            board.setSquareStatus(square, BoardStatus.EMPTY);

            moves.add(newMove);
        }

        Move bestMove = new Move();
        if (player.getSeed().equals(BoardStatus.CROSS)) {
            int bestScore = -10000;
            for(Move m : moves) {
                if (m.score > bestScore) {
                    bestScore = m.score;
                    bestMove = m;
                }
            }
        } else {
            int bestScore = 10000;
            for(Move m : moves) {
                if (m.score < bestScore) {
                    bestScore = m.score;
                    bestMove = m;
                }
            }
        }

        return bestMove;
    }
}
