package controller;

import model.BlockIndex;
import model.RowGameModel;
import model.Player;
import view.RowGameGUI;

public abstract class RowGameController {
    public RowGameModel gameModel;
    public RowGameGUI gameView;

    /**
     * Creates a new game initializing the GUI.
     */
    public RowGameController() {
        gameModel = new RowGameModel();
        gameView = new RowGameGUI(this);

        resetBoard();

        gameView.update(gameModel);
    }

    /**
     * Resets the board to its initial state.
     */
    private void resetBoard() {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                gameModel.blocksData[row][column].setContents("");
                gameModel.blocksData[row][column].setIsLegalMove(true);
            }
        }
    }

    /**
     * Moves the current player into the block at the given block index.
     *
     * @param blockIndex The index of the block to be moved to by the current player
     */
    public abstract void move(BlockIndex blockIndex);
	
	/**
	 * Check if the game has been won by the last move.
	 *
	 * @param row The row of the last move
	 * @param col The column of the last move
	 * @return true if the game is won, false otherwise
	 */
	public boolean checkWinner(int row, int col) {
		String currentPlayerSymbol = gameModel.blocksData[row][col].getContents();

		// Check row
		if (gameModel.blocksData[row][0].getContents().equals(currentPlayerSymbol) &&
				gameModel.blocksData[row][1].getContents().equals(currentPlayerSymbol) &&
				gameModel.blocksData[row][2].getContents().equals(currentPlayerSymbol)) {
			return true;
		}

		// Check column
		if (gameModel.blocksData[0][col].getContents().equals(currentPlayerSymbol) &&
				gameModel.blocksData[1][col].getContents().equals(currentPlayerSymbol) &&
				gameModel.blocksData[2][col].getContents().equals(currentPlayerSymbol)) {
			return true;
		}

		// Check diagonal
		if (row == col &&
				gameModel.blocksData[0][0].getContents().equals(currentPlayerSymbol) &&
				gameModel.blocksData[1][1].getContents().equals(currentPlayerSymbol) &&
				gameModel.blocksData[2][2].getContents().equals(currentPlayerSymbol)) {
			return true;
		}

		// Check anti-diagonal
		if (row + col == 2 &&
				gameModel.blocksData[0][2].getContents().equals(currentPlayerSymbol) &&
				gameModel.blocksData[1][1].getContents().equals(currentPlayerSymbol) &&
				gameModel.blocksData[2][0].getContents().equals(currentPlayerSymbol)) {
			return true;
		}

		return false;
	}

    /**
     * Performs undo for the last move taken if the game is not
     * in its initial configuration or has been finished.
     */
    public void undo() {
        if (gameModel.movesLeft == 9 || gameModel.getFinalResult() != null) {
            throw new UnsupportedOperationException("Undo is currently disallowed.");
        }

        gameModel.undo();

        gameView.update(gameModel);
    }

    /**
     * Ends the game disallowing further player turns.
     */
    public void endGame() {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                gameModel.blocksData[row][column].setIsLegalMove(false);
            }
        }

        gameView.update(gameModel);
    }

    /**
     * Resets the game to be able to start playing again.
     */
    public void resetGame() {
        resetBoard();
        gameModel.setPlayer(Player.PLAYER_1);
        gameModel.movesLeft = 9;
        gameModel.setFinalResult(null);

        gameModel.clearHistoryOfMoves();

        gameView.update(gameModel);
    }
}