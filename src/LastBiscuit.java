/* Level 3 Attempted */

import java.util.Scanner;

public class LastBiscuit {

	public static class Barrel {
		int id;
		int biscuits;

		public Barrel(int id, int biscuits) {
			this.id = id;
			this.biscuits = biscuits;
		}

		public boolean takeBiscuits(int amount) {
			if (amount > biscuits) {
				return false;
			}
			biscuits -= amount;
			return true;
		}
	}

	public static int take(int player) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Biscuits taken by player "+ player +": ");

		while (!scanner.hasNextInt()) {
			System.out.print("Please enter a valid integer: ");
			scanner.next();
		}

		int amount = scanner.nextInt();
		if (amount < 0) {
			System.out.println("Invalid integer choice, must be greater than 0");
			amount = take(player);
		}
		return amount;
	}

	public static String choose() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("From barrel1 (one), barrel2 (two), or both (both)? ");

		String choice = scanner.nextLine();
		if (choice.equals("one") || choice .equals("two") || choice.equals("both")) {
			return choice;
		}
		System.out.println("Invalid barrel selection please try again");
		choice = choose();
		return choice;
	}

    public static void main(String[] args) {
		Barrel barrel1 = new Barrel(1, 6);
		Barrel barrel2 = new Barrel(2, 8);

		System.out.println("Biscuits Left - Barrel 1: " + barrel1.biscuits);
		System.out.println("Biscuits Left - Barrel 2: " + barrel2.biscuits);

		int player = 1;
		while(barrel1.biscuits > 0 || barrel2.biscuits > 0) {
			boolean roundFinished = false;
			int amount = take(player);
			String choice = choose();

			if (choice.equals("one")) {
				if (barrel1.takeBiscuits(amount)) {
					roundFinished = true;
				} else {
					System.out.println("There are not that many biscuits left in Barrel 1, try again.");
				}
			} else if (choice.equals("two")) {
				if (barrel2.takeBiscuits(amount)) {
					roundFinished = true;
				} else {
					System.out.println("There are not that many biscuits left in Barrel 1, try again.");
				}
			} else {
				if (amount > barrel1.biscuits || amount > barrel2.biscuits) {
					System.out.println("There are not that many biscuits left in both Barrels, try again.");
				} else {
					barrel1.takeBiscuits(amount);
					barrel2.takeBiscuits(amount);
					roundFinished = true;
				}
			}

			System.out.println("Biscuits Left - Barrel 1: " + barrel1.biscuits);
			System.out.println("Biscuits Left - Barrel 2: " + barrel2.biscuits);

			if (barrel1.biscuits == 0 && barrel2.biscuits == 0) {
				System.out.println("Winner is player " + player);
			}

			if (roundFinished) {
				player = (player == 1) ? 2 : 1;
			}
		}
    }
}
