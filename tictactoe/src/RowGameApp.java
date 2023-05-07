import controller.HumanRowGameController;
import controller.ComputerRowGameController;
import controller.RowGameController;

public class RowGameApp 
{
    /**
     * Starts a new game in the GUI.
     */
    public static void main(String[] args) {
        // Give the user a text prompt instead of a command line argument
        RowGameController game = null;
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.println("Would you like to play against a human or a computer?");
        String input = scanner.nextLine();

        if (input.equals("human")) {
            game = new HumanRowGameController();
        }
        else if (input.equals("computer")) {
            game = new ComputerRowGameController();
        }
        else {
            System.out.println("Usage: java RowGameApp <human|computer>");
            System.exit(1);
        }

        game.gameView.gui.setVisible(true);
        scanner.close();
    }
}