import controller.HumanRowGameController;
import controller.ComputerRowGameController;
import controller.RowGameController;

public class RowGameApp 
{
    /**
     * Starts a new game in the GUI.
     */
    public static void main(String[] args) {

        // game is either HumanRowGameController or ComputerRowGameController. Ask the user which one they want.
        RowGameController game = null;
        if (args.length == 0) {
            System.out.println("Usage: java RowGameApp <human|computer>");
            System.exit(1);
        }
        else if (args[0].equals("human")) {
            game = new HumanRowGameController();
        }
        else if (args[0].equals("computer")) {
            game = new ComputerRowGameController();
        }
        else {
            System.out.println("Usage: java RowGameApp <human|computer>");
            System.exit(1);
        }
        game.gameView.gui.setVisible(true);
    }
}