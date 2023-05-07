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
	

    protected void moveHelper(BlockIndex blockIndex) {
        if (blockIndex == null || !gameModel.blocksData[blockIndex.getRow()][blockIndex.getColumn()].getIsLegalMove()) {
            throw new UnsupportedOperationException("Moving to row " + blockIndex.getRow() + " and column " + blockIndex.getColumn() + " is an illegal move.");
        }

        gameModel.movesLeft--;

        int row = blockIndex.getRow();
        int col = blockIndex.getColumn();

        if (gameModel.getPlayer().equals(Player.PLAYER_1)) {
            gameModel.blocksData[row][col].setContents("X");
        } else {
            gameModel.blocksData[row][col].setContents("O");
        }

        gameModel.blocksData[row][col].setIsLegalMove(false);

		// Check for winner
		if (checkWinner(row, col)) {
			gameModel.setFinalResult("Player " + gameModel.getPlayer() + " wins!");
			// Set all blocks to illegal moves
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					gameModel.blocksData[i][j].setIsLegalMove(false);
				}
			}
		} else if (gameModel.movesLeft == 0) {
			gameModel.setFinalResult(RowGameModel.GAME_END_NOWINNER);
		}

		Player otherPlayer = gameModel.getPlayer().equals(Player.PLAYER_1) ? Player.PLAYER_2 : Player.PLAYER_1;
        gameModel.setPlayer(otherPlayer);

        gameModel.addToHistoryOfMoves(blockIndex);

        gameView.update(gameModel);
    }
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