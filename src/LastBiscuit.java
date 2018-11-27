/* Level 3 Attempted */

import java.util.Scanner;

public class LastBiscuit {
	private static final int[] BARREL_STOCK = { 6, 8 }; // Fixed initial number of biscuits in each barrel

	// Messages displayed to the user during the game
	private static final String MESSAGE_WINNER = "Winner is player ";
	private static final String MESSAGE_TAKEN = "Biscuits taken by player ";
	private static final String MESSAGE_REMAINING = "Biscuits Left - Barrel ";
	private static final String MESSAGE_CHOICE = "From barrel1 (one), barrel2 (two), or both (both)? ";
	private static final String MESSAGE_VALID_ENOUGH = "There are not that many biscuits left in barrel";
	private static final String MESSAGE_VALID_CHOICE = "Please enter a valid choice (one, two or both): ";
	private static final String MESSAGE_VALID_AMOUNT = "Please enter a valid integer (above 0 and below the max): ";

	private static class Game {
		private final Scanner input = new Scanner(System.in); // Not static because Scanner has state (if two instances of Game classes were used issues would occur)
		private boolean finished = false;
		private int[] barrels = BARREL_STOCK;
		private int playerTurn = 1;
		private int winner;

		// Prints remainder of biscuits in each barrel
		public void printRemaining() {
			System.out.println(MESSAGE_REMAINING + "1: " + barrels[0]);
			System.out.println(MESSAGE_REMAINING + "2: " + barrels[1]);
		}

		// Gets the amount to be taken from the barrel(s)
		public int getAmount() {
			int max = barrels[0] > barrels[1] ? barrels[0] : barrels[1]; // Compares the stock of both barrels to get the max

			// Ensures the input is an integer otherwise prompts again
			while(!input.hasNextInt()) {
				System.out.print(MESSAGE_VALID_AMOUNT);
				input.next();
			}

			int amount = input.nextInt();
			input.nextLine();

			// Ensures the input is above 0 and not above the max otherwise prompts again
			if (amount < 0 || amount > max) {
				System.out.print(MESSAGE_VALID_AMOUNT);
				amount = getAmount();
			}
			return amount;
		}

		// Gets the barrel(s) the player wishes to remove from
		public String getChoice() {
			String choice = input.nextLine();
			// Ensures the input is valid by comparing with the valid choices otherwise prompts again
			if (!choice.matches("one|two|both")) {
				System.out.print(MESSAGE_VALID_CHOICE);
				choice = getChoice();
			}
			return choice;
		}

		public void take() {
			System.out.print(MESSAGE_TAKEN + playerTurn + ": ");
			int amount = getAmount();
			System.out.print(MESSAGE_CHOICE);
			String choice = getChoice();

			// Remove the biscuits from the selected barrel(s) if the amount is valid otherwise take() is called again
			switch(choice) { // Switch automatically compares String using .equals()
				case "one":
					if (amount > barrels[0]) {
						System.out.println(MESSAGE_VALID_ENOUGH + "1.");
						take();
						return;
					}
					barrels[0] -= amount;
					break;
				case "two":
					if (amount > barrels[1]) {
						System.out.println(MESSAGE_VALID_ENOUGH + "2.");
						take();
						return;
					}
					barrels[1] -= amount;
					break;
				case "both":
					if (amount > barrels[0] || amount > barrels[1]) {
						System.out.println(MESSAGE_VALID_ENOUGH + "1+2.");
						take();
						return;
					}
					barrels[0] -= amount;
					barrels[1] -= amount;
					break;
				default:
					// No default code as the input was already checked upon being inputted
					break;
			}

			// Checks if the game is finished by checking if both barrels are empty
			if (barrels[0] == 0 && barrels[1] == 0) {
				finished = true;
				winner = playerTurn;
				return;
			}
			playerTurn = playerTurn == 1 ? 2 : 1; // Alternates player turns
		}
	}

    public static void main(String[] args) {
		Game game = new Game(); // Creates a new instance of the game
		game.printRemaining();
		while(!game.finished) {
			game.take();
			game.printRemaining();
			if (game.finished) {
				System.out.println(MESSAGE_WINNER + game.winner);
			}
		}
    }
}