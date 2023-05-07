package controller;

import model.BlockIndex;
import model.Player;
import model.RowGameModel;

public class ComputerRowGameController extends RowGameController {

    @Override
    public void move(BlockIndex blockIndex)  {
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

        // computer's turn if it's player 2's turn
        if (gameModel.getPlayer().equals(Player.PLAYER_2)) {
            BlockIndex computerMove = computeMove(gameModel);
            if (computerMove != null) {
                move(computerMove);
            }
        }
    }

    private BlockIndex computeMove(RowGameModel gameModel) {
        // find first legal move
        for (int i = 0; i < gameModel.blocksData.length; i++) {
            for (int j = 0; j < gameModel.blocksData[i].length; j++) {
                if (gameModel.blocksData[i][j].getIsLegalMove()) {
                    return new BlockIndex(i, j);
                }
            }
        }
        return null;
    }

}
