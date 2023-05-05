import controller.RowGameController;
import controller.AbstractController;
import java.util.Scanner;

public class RowGameApp 
{
    /**
     * Starts a new game in the GUI.
     */
    public static void main(String[] args) {
        
        AbstractController game = null;

        Scanner opp = new Scanner(System.in); 
        System.out.println("Would you like to play a human or computer?");

        String pref = opp.nextLine(); 

        while (!(pref.equals("human") || pref.equals("computer"))) {
            System.out.println("Invalid input. Try again.");
            opp = new Scanner(System.in); 
            System.out.println("Would you like to play a human or computer?");
            pref = opp.nextLine(); 
        }

        if (pref.equals("human")) {
            game = new RowGameController();
        } else if (pref.equals("computer")) {
            game = new RowGameController();
        } 
        
        game.gameView.gui.setVisible(true);
    }
}