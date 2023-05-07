package controller;

import model.BlockIndex;
import model.RowGameModel;

public class ComputerRowGameController extends RowGameController {

    @Override
    public void move(BlockIndex blockIndex)  {
        moveHelper(blockIndex);
        
        BlockIndex computerMove = computeMove(gameModel);
        if (computerMove != null) {
            moveHelper(computerMove);
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
