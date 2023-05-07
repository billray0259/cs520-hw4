import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import model.BlockIndex;
import model.Player;
import model.RowGameModel;
import model.RowBlockModel;
import controller.HumanRowGameController;
import controller.ComputerRowGameController;
import controller.RowGameController;

/**
 * An example test class, which merely shows how to write JUnit tests.
 */
public class TestExample {
	private RowGameController humanGame;
	private RowGameController computerGame;

	@Before
	public void setUp() {
		humanGame = new HumanRowGameController();
		computerGame = new ComputerRowGameController();
	}

	@After
	public void tearDown() {
		humanGame = null;
		computerGame = null;
	}

	

	private void checkInitialConfiguration() {
		// Check the post-conditions for the new RowGameModel
		assertNotNull(humanGame.gameModel);
		// The first player has the initial move
		assertEquals(Player.PLAYER_1, humanGame.gameModel.getPlayer());
		// There are initially 9 possible moves left to make
		assertEquals(9, humanGame.gameModel.movesLeft);
		// Each block is empty and a legal move
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				assertEquals("", humanGame.gameModel.blocksData[row][col].getContents());
				assertEquals(true, humanGame.gameModel.blocksData[row][col].getIsLegalMove());
			} // end for col
		} // end for row
			// The final result is null
		assertNull(humanGame.gameModel.getFinalResult());
	}


	// Against a computer player: After performing a legal move, the game is updated appropriately.

	@Test
	public void testNewGame() {
		// The @Before method performs the setup and calls the unit under test.
		//
		// Check the post-conditions (i.e. class invariants)
		this.checkInitialConfiguration();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNewBlockViolatesPrecondition() {
		// Call the unit under test
		RowBlockModel block = new RowBlockModel(null);
		// Check the post-conditions. See the @Test annotation.
	}

	public void testLegalMoveHelper(BlockIndex blockIndex) {
		// In the setUp method, the RowGameController constructor
		// created a new RowGameModel.
		//
		// Check the pre-conditions (i.e. class invariants)
		this.checkInitialConfiguration();
		// Call the unit under test: Execute a legal move
		humanGame.move(blockIndex);
		// Check the post-conditions (i.e. class invariants)
		assertEquals(Player.PLAYER_2, humanGame.gameModel.getPlayer());
		assertEquals(8, humanGame.gameModel.movesLeft);
		assertEquals("X", humanGame.gameModel.blocksData[blockIndex.getRow()][blockIndex.getColumn()].getContents());
		assertEquals(false, humanGame.gameModel.blocksData[blockIndex.getRow()][blockIndex.getColumn()].getIsLegalMove());
	}

	@Test
	public void testLegalMove() {
		BlockIndex blockIndex = new BlockIndex(0, 0);
		testLegalMoveHelper(blockIndex);
	}

	// Against a computer player: After performing a legal move, the game is updated appropriately.
	@Test
	public void testComputerUpdate() {
		BlockIndex move = new BlockIndex(0, 0);
		computerGame.move(move);
		// ensure 0, 0 == X
		assertEquals("X", computerGame.gameModel.blocksData[move.getRow()][move.getColumn()].getContents());
		// ensure some other block is now O
		boolean foundO = false;
		for (int row = 0; row < 3 && !foundO; row++) {
			for (int col = 0; col < 3 && !foundO; col++) {
				if ("O".equals(computerGame.gameModel.blocksData[row][col].getContents())) {
					foundO = true;
				}
			}
		}
		assertTrue(foundO);
	}


	@Test(expected = UnsupportedOperationException.class)
	public void testIllegalMove() {
		// Perform the setup and check the pre-conditions
		BlockIndex blockIndex = new BlockIndex(0, 0);
		testLegalMoveHelper(blockIndex);

		// Call the unit under test: Execute an illegal move
		humanGame.move(blockIndex);

		// Check the post-conditions (i.e. class invariants).
		// See the @Test annotation.
	}

	@Test
	public void testGameOverWin() {
		// Perform setup and check the pre-conditions
		humanGame.move(new BlockIndex(0, 0));
		humanGame.move(new BlockIndex(2, 2));
		humanGame.move(new BlockIndex(0, 1));
		humanGame.move(new BlockIndex(2, 1));
		for (int column = 0; column < 2; column++) {
			assertEquals("X", humanGame.gameModel.blocksData[0][column].getContents());
		} // end for column
		assertEquals(null, humanGame.gameModel.getFinalResult());
		// Call the unit under test: Player 1 has 3 Xs in a row
		BlockIndex player1WinsBlockIndex = new BlockIndex(0, 2);
		humanGame.move(player1WinsBlockIndex);
		// Check the post-conditions
		assertEquals("X", humanGame.gameModel.blocksData[player1WinsBlockIndex.getRow()][player1WinsBlockIndex.getColumn()]
				.getContents());
		assertEquals("Player 1 wins!", humanGame.gameModel.getFinalResult());
	}

	@Test
	public void testGameOverTie() {
		// Perform setup and check the pre-conditions
		for (int column = 0; column < 3; column++) {
			humanGame.move(new BlockIndex(0, column));
		} // end for column
		for (int column = 0; column < 3; column++) {
			humanGame.move(new BlockIndex(2, column));
		} // end for column
		for (int column = 0; column < 2; column++) {
			humanGame.move(new BlockIndex(1, column));
		} // end for column
		assertEquals(null, humanGame.gameModel.getFinalResult());
		// Call the unit under test: Neither player wins
		humanGame.move(new BlockIndex(1, 2));
		// Check the post-conditions
		assertEquals(RowGameModel.GAME_END_NOWINNER, humanGame.gameModel.getFinalResult());
	}

	@Test
	public void testReset() {
		// Perform the setup and check the pre-conditions
		BlockIndex blockIndex = new BlockIndex(0, 0);
		testLegalMoveHelper(blockIndex);

		// Call the unit under test: Execute reset
		humanGame.resetGame();

		// Check the post-conditions
		this.checkInitialConfiguration();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testUndoDisallowed() {
		// Perform the setup and check the pre-conditions
		this.checkInitialConfiguration();

		// Call the unit under test: Execute undo
		humanGame.undo();

		// Check the post-conditions. See the @Test annotation.
	}

	@Test
	public void testUndoAallowed() {
		// Perform the setup and check the pre-conditions
		BlockIndex blockIndex = new BlockIndex(0, 0);
		testLegalMoveHelper(blockIndex);

		// Call the unit under test: Execute undo
		humanGame.undo();

		// Check the post-conditions
		this.checkInitialConfiguration();
	}
}
