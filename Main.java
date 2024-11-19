import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Ayoayo game = new Ayoayo();
        game.createPlayer("Jensen", 1);
        game.createPlayer("Brian", 2);
        
        Scanner scanner = new Scanner(System.in);
        int currentPlayer = 1;
        while (!game.isGameOver()) {
            game.printBoard();
            System.out.println("Player " + currentPlayer + ", choose a pit (0-5): ");
            int pit = scanner.nextInt();

            // Play the game for the current player
            if (Boolean.parseBoolean(game.playGame(currentPlayer, pit))) {2
                // Check if the player gets another turn
                if (currentPlayer == 1) {
                    currentPlayer = 2; // Switch to Player 2
                } else {
                    currentPlayer = 1; // Switch back to Player 1
                }
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }

        // Game has ended, display the winner
        game.printBoard();
        System.out.println("Game has ended. " + game.returnWinner());
        scanner.close();
    }
}

