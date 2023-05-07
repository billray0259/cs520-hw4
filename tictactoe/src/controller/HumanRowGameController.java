package controller;

import model.BlockIndex;

public class HumanRowGameController extends RowGameController {

    @Override
    public void move(BlockIndex blockIndex)  {
        moveHelper(blockIndex);
    }

}
