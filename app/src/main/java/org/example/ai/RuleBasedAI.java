package org.example.ai;

import org.example.entities.AIPlayer;
import org.example.entities.AbstractPlayer;
import org.example.entities.Board;
import org.example.enums.BoardStatus;
import org.example.enums.FirstMove;

import java.util.List;
import java.util.Random;

/**
 * Implementation of a Rule Based AI for playing Tic-Tac-Toe.
 * Created by Jens Christian Rasch on 25-02-2018.
 */

public class RuleBasedAI implements IBaseAI {

    // NB: This is not used at the moment
    Move lastMove = new Move();

    @Override
    public Move ply(Board board, AbstractPlayer player) {

        Move bestMove = new Move();
        boolean moveChosen = false;
        List<Integer> availSquares = board.getAllEmptySquares();

        if (availSquares.size() == 9) {
            // I am to take the first move!
            // The best move is the center or the corner, but the choice can be overridden
            FirstMove firstMove = ((AIPlayer)player).centerFirstMove;
            if (firstMove.equals(FirstMove.CENTER)) {
                bestMove.squareId = board.getCenterSquare();
            } else {
                Random rnd = new Random();
                int squarePicked = rnd.nextInt(4);
                if (firstMove.equals(FirstMove.CORNER)) {
                    List<Integer> cornerSquares = board.getCornerSquares();
                    bestMove.squareId = cornerSquares.get(squarePicked);
                } else {
                    List<Integer> edgeSquares = board.getEdgeSquares();
                    bestMove.squareId = edgeSquares.get(squarePicked);
                }
            }
        } else if (availSquares.size() == 8) {
            // I go second.
            // Have to respond according to opponents first move!
            FirstMove firstMove = ((AIPlayer)player).centerFirstMove;
            if (firstMove.equals(FirstMove.CENTER)
                    && board.checkSquareStatus(board.getCenterSquare()).equals(BoardStatus.EMPTY)) {
                bestMove.squareId = board.getCenterSquare();
            } else {
                Random rnd = new Random();
                if (firstMove.equals(FirstMove.EDGE)) {
                    List<Integer> edgeSquares = board.getEmptyEdgeSquares();
                    int squarePicked = rnd.nextInt(edgeSquares.size());
                    bestMove.squareId = edgeSquares.get(squarePicked);
                } else {
                    List<Integer> cornerSquares = board.getEmptyCornerSquares();
                    int squarePicked = rnd.nextInt(cornerSquares.size());
                    bestMove.squareId = cornerSquares.get(squarePicked);
                }
            }
        } else if (availSquares.size() == 1) {
            // If there is only one available move, then take it
            bestMove.squareId = availSquares.get(0);
        } else {
            // Rest of the game moves
            // Check for a win condition and make the appropriate play
            for (int square : availSquares) {
                int rowId = board.getRowForSquare(square);
                int columnId = board.getColumnForSquare(square);
                int diagonalId = board.getDiagonalForSquare(square);

                int rowScorePlayer = board.checkRow(rowId, player.getSeed(), player.getOpponentSeed());
                int columnScorePlayer = board.checkColumn(columnId, player.getSeed(), player.getOpponentSeed());
                int diagonalScorePlayer = board.checkDiagonal(diagonalId, player.getSeed(), player.getOpponentSeed());
                if (square == board.getCenterSquare()) {
                    int diagonalOne = board.checkDiagonal(1, player.getSeed(), player.getOpponentSeed());
                    int diagonalTwo = board.checkDiagonal(2, player.getSeed(), player.getOpponentSeed());
                    diagonalScorePlayer = diagonalOne == 20 ? diagonalOne : diagonalTwo == 20 ? diagonalTwo : 0;
                }

                if (rowScorePlayer == 20 || columnScorePlayer == 20 || diagonalScorePlayer == 20) {
                    bestMove.squareId = square;
                    moveChosen = true;
                    break;
                }
            }

            // Check for a lose condition and make the appropriate play
            if (!moveChosen) {
                for (int square : availSquares) {
                    int rowId = board.getRowForSquare(square);
                    int columnId = board.getColumnForSquare(square);
                    int diagonalId = board.getDiagonalForSquare(square);

                    int rowScoreOpponent = board.checkRow(rowId, player.getOpponentSeed(), player.getSeed());
                    int columnScoreOpponent = board.checkColumn(columnId, player.getOpponentSeed(), player.getSeed());
                    int diagonalScoreOpponent = board.checkDiagonal(diagonalId, player.getOpponentSeed(), player.getSeed());
                    if (square == board.getCenterSquare()) {
                        int diagonalOne = board.checkDiagonal(1, player.getOpponentSeed(), player.getSeed());
                        int diagonalTwo = board.checkDiagonal(2, player.getOpponentSeed(), player.getSeed());
                        diagonalScoreOpponent = diagonalOne == 20 ? diagonalOne : diagonalTwo == 20 ? diagonalTwo : 0;
                    }

                    if (rowScoreOpponent == 20 || columnScoreOpponent == 20 || diagonalScoreOpponent == 20) {
                        bestMove.squareId = square;
                        moveChosen = true;
                        break;
                    }
                }
            }

            // Check for forks to create
            if (!moveChosen) {
                int square = board.checkForForksToCreate(player.getSeed(), player.getOpponentSeed());
                if (square > 0) {
                    bestMove.squareId = square;
                    moveChosen = true;
                }
            }

            // Check for forks to block
            if (!moveChosen) {
                int square = board.checkForForksToBlock(player.getOpponentSeed());
                if (square > 0) {
                    bestMove.squareId = square;
                    moveChosen = true;
                }
            }

            // This should never end up being a possibility, since we have already selected this as
            // the first or second move!
            if (!moveChosen && board.checkSquareStatus(board.getCenterSquare()).equals(BoardStatus.EMPTY)) {
                bestMove.squareId = board.getCenterSquare();
                moveChosen = true;
            }

            // Opposite corner
            if (!moveChosen) {
                for (int square : board.getEmptyCornerSquares()) {
                    if (board.getOppositeSquareStatus(square).equals(player.getOpponentSeed())) {
                        bestMove.squareId = square;
                        moveChosen = true;
                        break;
                    }
                }
            }

            // Empty corner
            if (!moveChosen) {
                List<Integer> squares = board.getEmptyCornerSquares();
                if (squares.size() > 0) {
                    Random rnd = new Random();
                    bestMove.squareId = squares.get(rnd.nextInt(squares.size()));
                    moveChosen = true;
                }
            }

            // Empte edge
            if (!moveChosen) {
                List<Integer> squares = board.getEmptyEdgeSquares();
                if (squares.size() > 0) {
                    Random rnd = new Random();
                    bestMove.squareId = squares.get(rnd.nextInt(squares.size()));
                }
            }
        }

//        lastMove = bestMove;
        return bestMove;
    }
}
