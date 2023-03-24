package com.example.tictacttoe.logic;

import com.example.tictacttoe.R;

public class Game {
    private static final char PLAYER_O = 'O';
    private static final char PLAYER_X = 'X';
    private char currentPlayer;
    private final byte[][] board;

    private static final int EMPTY_TILE = -1;
    private static final int O_TILE = 0;
    private static final int X_TILE = 1;
    public boolean anyWinner = false;
    public boolean anyDraw = false;


    public Game() {
        board = new byte[3][3];

        // creating empty board
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = EMPTY_TILE;
            }
        }
        chooseRandomPlayer();
    }

    private void chooseRandomPlayer() {
        double random = Math.random();
        if (random > 0.5) {
            setCurrentPlayer(PLAYER_X);
        } else {
            setCurrentPlayer(PLAYER_O);
        }
    }

    private void setCurrentPlayer(char currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public int setMarkImage(int row, int column) {
        int imageRes;

        if (currentPlayer == PLAYER_O) {
            imageRes = R.drawable.o_symbol;
            board[row][column] = O_TILE;
        } else {
            imageRes = R.drawable.x_symbol;
            board[row][column] = X_TILE;
        }
        if (checkWinner()) {
            anyWinner = true;
        } else if (isDraw()) {
            anyDraw = true;
        } else {
            switchPlayer();
        }

        return imageRes;
    }

    public boolean isValidMove(int row, int column) {
        return board[row][column] == EMPTY_TILE;
    }

    private boolean isDraw() {
        // check for empty tiles
        boolean isAnyEmpty = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY_TILE) {
                    isAnyEmpty = true;
                    break;
                }
            }
        }
        return !anyWinner && !isAnyEmpty;
    }


    private void switchPlayer() {
        if (currentPlayer == PLAYER_X) {
            setCurrentPlayer(PLAYER_O);
        } else {
            setCurrentPlayer(PLAYER_X);
        }
    }


    private boolean checkWinner() {
        // check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != EMPTY_TILE && board[i][1] != EMPTY_TILE && board[i][2] != EMPTY_TILE) {
                if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                    return true;
                }
            }
        }

        // check columns
        for (int j = 0; j < 3; j++) {
            if (board[0][j] != EMPTY_TILE && board[1][j] != EMPTY_TILE && board[2][j] != EMPTY_TILE) {
                if (board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                    return true;
                }
            }
        }

        // check diagonal
        if (board[0][0] != EMPTY_TILE && board[1][1] != EMPTY_TILE && board[2][2] != EMPTY_TILE) {
            return board[0][0] == board[1][1] && board[1][1] == board[2][2];
        }
        // check diagonal

        if (board[0][2] != EMPTY_TILE && board[1][1] != EMPTY_TILE && board[2][0] != EMPTY_TILE) {
            return board[0][2] == board[1][1] && board[1][1] == board[2][0];
        }

        return false;
    }

    public void restart() {
        resetBoard();
        clearState();
        chooseRandomPlayer();

    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = EMPTY_TILE;
            }
        }
    }

    private void clearState() {
        anyWinner = false;
        anyDraw = false;
    }
}
