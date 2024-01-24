package ie.atu.sw;

import java.util.Scanner;

/**
 * The Menu class provides methods for displaying a menu and obtaining user choices.
 */
public class Menu {

    /**
     * Displays the main menu for the Sentiment Analysis application.
     */
    public static void displayMenu() {
        System.out.println(ConsoleColour.WHITE);
        System.out.println("***********************************************************");
        System.out.println("* ATU - Higher Diploma in Science in Software Development *");
        System.out.println("*                                                         *");
        System.out.println("*             Virtual Threaded Sentiment Analyser         *");
        System.out.println("*                                                         *");
        System.out.println("***********************************************************");
        System.out.println("(1) Configure Lexicons");
        System.out.println("(2) Execute, Analyse Text File and Report");
        System.out.println("(3) Quit");
        System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
        System.out.print("Select Option [1-3]>");
        System.out.println();
    }

    /**
     * Gets the user's choice from the menu.
     *
     * @param scanner The Scanner object for reading user input.
     * @return The user's choice.
     */
    public static int getUserChoice(Scanner scanner) {
        scanner = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.print("Select option: ");
            String input = scanner.nextLine();

            try {
                choice = Integer.parseInt(input);
                if (isValidChoice(choice)) {
                    break;
                } else {
                    System.out.println("Please, select a valid option (1-3).");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please, enter a valid number.");
            }
        }

        return choice;
    }

    // Private helper method to check the validity of the user's choice
    private static boolean isValidChoice(int choice) {
        return choice >= 1 && choice <= 3;
    }
}
