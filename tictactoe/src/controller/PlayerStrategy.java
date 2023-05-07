package controller;

import model.BlockIndex;
import model.Player;
import model.RowGameModel;

public interface PlayerStrategy {
    public BlockIndex getNextMove(RowGameModel game, Player player);

}
