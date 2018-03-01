package org.example.entities;

import org.example.enums.BoardStatus;
import org.example.tictactoe.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Class for handling a Tic-Tac-Toe board with methods for checking the status of a board.
 * Created by Jens Christian Rasch on 23-02-2018.
 */

public class Board implements Serializable {

    private Map<Integer, BoardStatus> board = new HashMap<>();

    public Board() {

    }

    /**
     * This method will return the BoardStatus of the square specified by the parameter.
     * @param squareId
     * @return
     */
    public BoardStatus checkSquareStatus(int squareId) {
        return board.get(squareId);
    }

    /**
     * Setter for setting the square specified by the first parameter to a status equal to
     * the second parameter.
     * @param squareId
     * @param status
     */
    public void setSquareStatus(int squareId, BoardStatus status) {
        board.put(squareId, status);
    }

    /**
     * Returns a list of all the empty squares on the board.
     * @return
     */
    public List<Integer> getAllEmptySquares() {
        List<Integer> emptySquares = new ArrayList<>();
        for(Map.Entry<Integer, BoardStatus> kvp : board.entrySet()) {
            if (kvp.getValue().equals(BoardStatus.EMPTY)) {
                emptySquares.add(kvp.getKey());
            }
        }
        return emptySquares;
    }

    /**
     * Will return a list of all the empty edge squares of the board.
     * @return
     */
    public List<Integer> getEmptyEdgeSquares() {
        List<Integer> emptySquares = new ArrayList<>();
        for(Map.Entry<Integer, BoardStatus> kvp : board.entrySet()) {
            if (isEdgeSquare(kvp.getKey()) && kvp.getValue().equals(BoardStatus.EMPTY)) {
                emptySquares.add(kvp.getKey());
            }
        }
        return emptySquares;
    }

    /**
     * Will return all the empty corner squares of the board.
     * @return
     */
    public List<Integer> getEmptyCornerSquares() {
        List<Integer> emptySquares = new ArrayList<>();
        for(Map.Entry<Integer, BoardStatus> kvp : board.entrySet()) {
            if (isCornerSquare(kvp.getKey()) && kvp.getValue().equals(BoardStatus.EMPTY)) {
                emptySquares.add(kvp.getKey());
            }
        }
        return emptySquares;
    }

    /**
     * Method for returning the IDs of all the corner squares of the board.
     * @return
     */
    public List<Integer> getCornerSquares() {
        List<Integer> cornerSquares = new ArrayList<>();
        cornerSquares.add(R.id.felt1);
        cornerSquares.add(R.id.felt3);
        cornerSquares.add(R.id.felt7);
        cornerSquares.add(R.id.felt9);
        return cornerSquares;
    }

    /**
     * Will return all the IDs of the edge squares of the board.
     * @return
     */
    public List<Integer> getEdgeSquares() {
        List<Integer> edgeSquares = new ArrayList<>();
        edgeSquares.add(R.id.felt2);
        edgeSquares.add(R.id.felt4);
        edgeSquares.add(R.id.felt6);
        edgeSquares.add(R.id.felt8);
        return edgeSquares;
    }

    /**
     * Returns the ID of the center square.
     * @return
     */
    public int getCenterSquare() {
        return R.id.felt5;
    }

    /**
     * Returns the row number that the square specified by the parameter is located in.
     * @param squareId
     * @return
     */
    public int getRowForSquare(int squareId) {
        int retValue = 0;
        switch(squareId) {
            case R.id.felt1:
                retValue = 1;
                break;
            case R.id.felt2:
                retValue = 1;
                break;
            case R.id.felt3:
                retValue = 1;
                break;
            case R.id.felt4:
                retValue = 2;
                break;
            case R.id.felt5:
                retValue = 2;
                break;
            case R.id.felt6:
                retValue = 2;
                break;
            case R.id.felt7:
                retValue = 3;
                break;
            case R.id.felt8:
                retValue = 3;
                break;
            case R.id.felt9:
                retValue = 3;
                break;
        }
        return retValue;
    }

    /**
     * Returns the column number that the square specified by the parameter is located in.
     * @param squareId
     * @return
     */
    public int getColumnForSquare(int squareId) {
        int retValue = 0;
        switch(squareId) {
            case R.id.felt1:
                retValue = 1;
                break;
            case R.id.felt2:
                retValue = 2;
                break;
            case R.id.felt3:
                retValue = 3;
                break;
            case R.id.felt4:
                retValue = 1;
                break;
            case R.id.felt5:
                retValue = 2;
                break;
            case R.id.felt6:
                retValue = 3;
                break;
            case R.id.felt7:
                retValue = 1;
                break;
            case R.id.felt8:
                retValue = 2;
                break;
            case R.id.felt9:
                retValue = 3;
                break;
        }
        return retValue;
    }

    /**
     * Returns the diagonal number for which the square specified by the parameter is located.
     * @param squareId
     * @return
     */
    public int getDiagonalForSquare(int squareId) {
        if (squareId == R.id.felt1 || squareId == R.id.felt9) {
            return 1;
        } else if (squareId == R.id.felt3 || squareId == R.id.felt7) {
            return 2;
        } else {
            return 0;
        }
    }

    /**
     * Returns the BoardStatus of the opposite corner of the square specified by the parameter.
     * @param squareId
     * @return
     */
    public BoardStatus getOppositeSquareStatus(int squareId) {
        BoardStatus retValue = BoardStatus.EMPTY;
        switch(squareId) {
            case R.id.felt1:
                retValue = checkSquareStatus(R.id.felt9);
                break;
            case R.id.felt3:
                retValue = checkSquareStatus(R.id.felt7);
                break;
            case R.id.felt7:
                retValue = checkSquareStatus(R.id.felt3);
                break;
            case R.id.felt9:
                retValue = checkSquareStatus(R.id.felt1);
                break;
        }
        return retValue;
    }

    /**
     * Returns the score for the row specified by the parameter rowId.
     * @param rowId
     * @param playerSeed
     * @param opponentSeed
     * @return
     */
    public int checkRow(int rowId, BoardStatus playerSeed, BoardStatus opponentSeed) {
        int result = 0;
        switch(rowId) {
            case 1:
                result += checkSquareStatus(R.id.felt1).equals(playerSeed) ? 10 : checkSquareStatus(R.id.felt1).equals(opponentSeed) ? -10 : 0;
                result += checkSquareStatus(R.id.felt2).equals(playerSeed) ? 10 : checkSquareStatus(R.id.felt2).equals(opponentSeed) ? -10 : 0;
                result += checkSquareStatus(R.id.felt3).equals(playerSeed) ? 10 : checkSquareStatus(R.id.felt3).equals(opponentSeed) ? -10 : 0;
                break;
            case 2:
                result += checkSquareStatus(R.id.felt4).equals(playerSeed) ? 10 : checkSquareStatus(R.id.felt4).equals(opponentSeed) ? -10 : 0;
                result += checkSquareStatus(R.id.felt5).equals(playerSeed) ? 10 : checkSquareStatus(R.id.felt5).equals(opponentSeed) ? -10 : 0;
                result += checkSquareStatus(R.id.felt6).equals(playerSeed) ? 10 : checkSquareStatus(R.id.felt6).equals(opponentSeed) ? -10 : 0;
                break;
            case 3:
                result += checkSquareStatus(R.id.felt7).equals(playerSeed) ? 10 : checkSquareStatus(R.id.felt7).equals(opponentSeed) ? -10 : 0;
                result += checkSquareStatus(R.id.felt8).equals(playerSeed) ? 10 : checkSquareStatus(R.id.felt8).equals(opponentSeed) ? -10 : 0;
                result += checkSquareStatus(R.id.felt9).equals(playerSeed) ? 10 : checkSquareStatus(R.id.felt9).equals(opponentSeed) ? -10 : 0;
                break;
        }

        return result;
    }

    public int checkColumn(int columnId, BoardStatus playerSeed, BoardStatus opponentSeed) {
        int result = 0;
        switch(columnId) {
            case 1:
                result += checkSquareStatus(R.id.felt1).equals(playerSeed) ? 10 : checkSquareStatus(R.id.felt1).equals(opponentSeed) ? -10 : 0;
                result += checkSquareStatus(R.id.felt4).equals(playerSeed) ? 10 : checkSquareStatus(R.id.felt4).equals(opponentSeed) ? -10 : 0;
                result += checkSquareStatus(R.id.felt7).equals(playerSeed) ? 10 : checkSquareStatus(R.id.felt7).equals(opponentSeed) ? -10 : 0;
                break;
            case 2:
                result += checkSquareStatus(R.id.felt2).equals(playerSeed) ? 10 : checkSquareStatus(R.id.felt2).equals(opponentSeed) ? -10 : 0;
                result += checkSquareStatus(R.id.felt5).equals(playerSeed) ? 10 : checkSquareStatus(R.id.felt5).equals(opponentSeed) ? -10 : 0;
                result += checkSquareStatus(R.id.felt8).equals(playerSeed) ? 10 : checkSquareStatus(R.id.felt8).equals(opponentSeed) ? -10 : 0;
                break;
            case 3:
                result += checkSquareStatus(R.id.felt3).equals(playerSeed) ? 10 : checkSquareStatus(R.id.felt3).equals(opponentSeed) ? -10 : 0;
                result += checkSquareStatus(R.id.felt6).equals(playerSeed) ? 10 : checkSquareStatus(R.id.felt6).equals(opponentSeed) ? -10 : 0;
                result += checkSquareStatus(R.id.felt9).equals(playerSeed) ? 10 : checkSquareStatus(R.id.felt9).equals(opponentSeed) ? -10 : 0;
                break;
        }

        return result;
    }

    public int checkDiagonal(int diagonalId, BoardStatus playerSeed, BoardStatus opponentSeed) {
        int result = 0;
        if (diagonalId == 1) {
            result += checkSquareStatus(R.id.felt1).equals(playerSeed) ? 10 : checkSquareStatus(R.id.felt1).equals(opponentSeed) ? -10 : 0;
            result += checkSquareStatus(R.id.felt5).equals(playerSeed) ? 10 : checkSquareStatus(R.id.felt5).equals(opponentSeed) ? -10 : 0;
            result += checkSquareStatus(R.id.felt9).equals(playerSeed) ? 10 : checkSquareStatus(R.id.felt9).equals(opponentSeed) ? -10 : 0;
        } else if (diagonalId == 2) {
            result += checkSquareStatus(R.id.felt3).equals(playerSeed) ? 10 : checkSquareStatus(R.id.felt3).equals(opponentSeed) ? -10 : 0;
            result += checkSquareStatus(R.id.felt5).equals(playerSeed) ? 10 : checkSquareStatus(R.id.felt5).equals(opponentSeed) ? -10 : 0;
            result += checkSquareStatus(R.id.felt7).equals(playerSeed) ? 10 : checkSquareStatus(R.id.felt7).equals(opponentSeed) ? -10 : 0;
        }
        return result;
    }

    public boolean checkForWinCondition(BoardStatus seed) {
        return checkRow(checkSquareStatus(R.id.felt1), checkSquareStatus(R.id.felt2), checkSquareStatus(R.id.felt3), seed)
                || checkRow(checkSquareStatus(R.id.felt4), checkSquareStatus(R.id.felt5), checkSquareStatus(R.id.felt6), seed)
                || checkRow(checkSquareStatus(R.id.felt7), checkSquareStatus(R.id.felt8), checkSquareStatus(R.id.felt9), seed)
                || checkRow(checkSquareStatus(R.id.felt1), checkSquareStatus(R.id.felt4), checkSquareStatus(R.id.felt7), seed)
                || checkRow(checkSquareStatus(R.id.felt2), checkSquareStatus(R.id.felt5), checkSquareStatus(R.id.felt8), seed)
                || checkRow(checkSquareStatus(R.id.felt3), checkSquareStatus(R.id.felt6), checkSquareStatus(R.id.felt9), seed)
                || checkRow(checkSquareStatus(R.id.felt1), checkSquareStatus(R.id.felt5), checkSquareStatus(R.id.felt9), seed)
                || checkRow(checkSquareStatus(R.id.felt3), checkSquareStatus(R.id.felt5), checkSquareStatus(R.id.felt7), seed);
    }

    public int checkForForksToBlock(BoardStatus seed) {
        // Arrowhead
        if (checkSquareStatus(R.id.felt2).equals(seed) && checkSquareStatus(R.id.felt4).equals(seed) && checkSquareStatus(R.id.felt1).equals(BoardStatus.EMPTY)) {
            return R.id.felt1;
        }
        if (checkSquareStatus(R.id.felt2).equals(seed) && checkSquareStatus(R.id.felt6).equals(seed) && checkSquareStatus(R.id.felt3).equals(BoardStatus.EMPTY)) {
            return R.id.felt3;
        }
        if (checkSquareStatus(R.id.felt4).equals(seed) && checkSquareStatus(R.id.felt8).equals(seed) && checkSquareStatus(R.id.felt7).equals(BoardStatus.EMPTY)) {
            return R.id.felt7;
        }
        if (checkSquareStatus(R.id.felt6).equals(seed) && checkSquareStatus(R.id.felt8).equals(seed) && checkSquareStatus(R.id.felt9).equals(BoardStatus.EMPTY)) {
            return R.id.felt9;
        }

        // Triangle
        // Is already handled since the algorithm will place corners before edges

        // Encirclement
        if ((checkSquareStatus(R.id.felt1).equals(seed) && checkSquareStatus(R.id.felt9).equals(seed))
                || (checkSquareStatus(R.id.felt7).equals(seed) && checkSquareStatus(R.id.felt3).equals(seed))) {
            // Select an edge square
            List<Integer> edgeSquares = getEmptyEdgeSquares();
            Random rnd = new Random();
            return edgeSquares.get(rnd.nextInt(edgeSquares.size()));
        }

        return 0;
    }

    public int checkForForksToCreate(BoardStatus seed, BoardStatus opponentSeed) {
        Random rnd = new Random();
        // Encirclement

        // Arrowhead
        if (checkSquareStatus(R.id.felt2).equals(seed)
                && checkRow(1, seed, opponentSeed) == 10
                && (checkColumn(1, seed, opponentSeed) == 0
                    || checkColumn(3, seed, opponentSeed) == 0)) {
            return checkColumn(1, seed, opponentSeed) == 0 ? R.id.felt4 : R.id.felt6;
        } else if (checkSquareStatus(R.id.felt4).equals(seed)
                && checkColumn(1, seed, opponentSeed) == 10
                && (checkRow(1, seed, opponentSeed) == 0
                    || checkRow(3, seed, opponentSeed) == 0)) {
            return checkRow(1, seed, opponentSeed) == 0 ? R.id.felt2 : R.id.felt8;
        } else if (checkSquareStatus(R.id.felt6).equals(seed)
                && checkColumn(3, seed, opponentSeed) == 10
                && (checkRow(1, seed, opponentSeed) == 0
                || checkRow(3, seed, opponentSeed) == 0)){
            return checkRow(1, seed, opponentSeed) == 0 ? R.id.felt2 : R.id.felt8;
        } else if (checkSquareStatus(R.id.felt8).equals(seed)
                && checkRow(3, seed, opponentSeed) == 10
                && (checkColumn(1, seed, opponentSeed) == 0
                || checkColumn(3, seed, opponentSeed) == 0)){
            return checkColumn(1, seed, opponentSeed) == 0 ? R.id.felt4 : R.id.felt6;
        } else if (checkSquareStatus(R.id.felt2).equals(seed)
                && checkSquareStatus(R.id.felt4).equals(seed)
                && checkSquareStatus(R.id.felt1).equals(BoardStatus.EMPTY)
                && checkSquareStatus(R.id.felt3).equals(BoardStatus.EMPTY)
                && checkSquareStatus(R.id.felt7).equals(BoardStatus.EMPTY)) {
            return R.id.felt1;
        } else if (checkSquareStatus(R.id.felt2).equals(seed)
                && checkSquareStatus(R.id.felt6).equals(seed)
                && checkSquareStatus(R.id.felt1).equals(BoardStatus.EMPTY)
                && checkSquareStatus(R.id.felt3).equals(BoardStatus.EMPTY)
                && checkSquareStatus(R.id.felt9).equals(BoardStatus.EMPTY)) {
            return R.id.felt3;
        } else if (checkSquareStatus(R.id.felt4).equals(seed)
                && checkSquareStatus(R.id.felt8).equals(seed)
                && checkSquareStatus(R.id.felt1).equals(BoardStatus.EMPTY)
                && checkSquareStatus(R.id.felt7).equals(BoardStatus.EMPTY)
                && checkSquareStatus(R.id.felt9).equals(BoardStatus.EMPTY)) {
            return R.id.felt7;
        } else if (checkSquareStatus(R.id.felt8).equals(seed)
                && checkSquareStatus(R.id.felt6).equals(seed)
                && checkSquareStatus(R.id.felt7).equals(BoardStatus.EMPTY)
                && checkSquareStatus(R.id.felt9).equals(BoardStatus.EMPTY)
                && checkSquareStatus(R.id.felt3).equals(BoardStatus.EMPTY)) {
            return R.id.felt9;
        }

        // Triangle
        if (checkSquareStatus(getCenterSquare()).equals(seed)) {
            if (checkSquareStatus(R.id.felt1).equals(seed)
                    && checkRow(1, seed, opponentSeed) == 10
                    && checkColumn(1, seed, opponentSeed) == 10) {
                return rnd.nextInt(2) == 0 ? R.id.felt3 : R.id.felt7;
            } else if (checkSquareStatus(R.id.felt3).equals(seed)
                    && checkRow(1, seed, opponentSeed) == 10
                    && checkColumn(3, seed, opponentSeed) == 10) {
                return rnd.nextInt(2) == 0 ? R.id.felt1 : R.id.felt9;
            } else if (checkSquareStatus(R.id.felt7).equals(seed)
                    && checkRow(3, seed, opponentSeed) == 10
                    && checkColumn(1, seed, opponentSeed) == 10) {
                return rnd.nextInt(2) == 0 ? R.id.felt1 : R.id.felt9;
            } else if (checkSquareStatus(R.id.felt9).equals(seed)
                    && checkRow(3, seed, opponentSeed) == 10
                    && checkColumn(3, seed, opponentSeed) == 10) {
                return rnd.nextInt(2) == 0 ? R.id.felt3 : R.id.felt7;
            }
        }

        return 0;
    }

    /**
     * Will return a boolean indicating whether the square identified by the parameter is a
     * corner square or not.
     * @param squareId
     * @return
     */
    private boolean isCornerSquare(int squareId) {
        return R.id.felt1 == squareId
                || R.id.felt3 == squareId
                || R.id.felt7 == squareId
                || R.id.felt9 == squareId;
    }

    /**
     * Will return a boolean indication whether the square identified by the parameter is an edge
     * square or not.
     * @param squareId
     * @return
     */
    private boolean isEdgeSquare(int squareId) {
        return R.id.felt2 == squareId
                || R.id.felt4 == squareId
                || R.id.felt6 == squareId
                || R.id.felt8 == squareId;
    }

    /**
     * Will return true if the fields specified in the parameters are all of the same BoardStatus
     * as the parameter seed.
     * @param field1
     * @param field2
     * @param field3
     * @param seed
     * @return
     */
    private boolean checkRow(BoardStatus field1, BoardStatus field2, BoardStatus field3, BoardStatus seed) {
        return field1.equals(seed) && field1.equals(field2) && field1.equals(field3);
    }

}
