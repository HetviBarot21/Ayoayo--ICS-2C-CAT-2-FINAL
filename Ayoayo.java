import java.util.Arrays;

class Ayoayo {
    private String player1Name, player2Name; //store names of player 1 and 2
    private int[] board; // an integer array to represent the board: pits and stores, for both players

    public Ayoayo() { //initializes the game board with 14 positions, each players pits (0-5 for player 1 and 7-12 for plater 2)
        board = new int[14];
        Arrays.fill(board, 4); // Initialize all pits with 4 seeds
        board[6] = board[13] = 0; // Stores start empty
    }

    public void createPlayer(String name, int player) { //assigns names to players based on player number
        if (player == 1) player1Name = name;
        else if (player == 2) player2Name = name;
    }
    //display current state of the board, including players names, their stores and seeds in their pits
    public void printBoard() {
        printPlayerBoard(1, player1Name, board[6], Arrays.copyOfRange(board, 0, 6));
        printPlayerBoard(2, player2Name, board[13], Arrays.copyOfRange(board, 7, 13));
    }

    private void printPlayerBoard(int player, String playerName, int store, int[] pits) {
        System.out.println("Player " + player + " (" + playerName + "):");
        System.out.println("Store: " + store + " Pits: " + Arrays.toString(pits));
    }
    //check if the game is over and dertemine player based on scores
    public String returnWinner() {
        if (isGameOver()) {
            int p1Score = board[6], p2Score = board[13];
            if (p1Score > p2Score) return "Winner is Player 1: " + player1Name;
            else if (p2Score > p1Score) return "Winner is Player 2: " + player2Name;
            else return "It's a tie";
        }
        else{
        return "Game has not ended";}
    }

    public String playGame(int player, int pit) {
        if (pit < 1 || pit > 6) return "Invalid pit index!";
        int startPit = (player == 1) ? pit - 1 : pit + 6;
        if (board[startPit] == 0) return "Pit is empty!";

        int seeds = board[startPit];
        board[startPit] = 0;
        int currentPit = startPit;

        // Distribute seeds
        while (seeds > 0) {
            currentPit = (currentPit + 1) % 14;
            if ((player == 1 && currentPit == 13) || (player == 2 && currentPit == 6)) continue; // Skip opponent's store
            board[currentPit]++;
            seeds--;
        }

        // Special Rule 1: Last seed in own store
        if ((player == 1 && currentPit == 6) || (player == 2 && currentPit == 13)) {
            return "Player " + player + " takes another turn!";
        }

        // Special Rule 2: Last seed in own empty pit
        if (board[currentPit] == 1 && isOwnPit(player, currentPit)) {
            int oppositePit = 12 - currentPit;
            board[getStoreIndex(player)] += board[oppositePit] + 1; // Add seeds from opposite pit and current pit
            board[oppositePit] = 0;
            board[currentPit] = 0;
        }

        // Check game end
        if (isGameOver()) {
            collectRemainingSeeds();
            return "Game has ended!";
        }

        return Arrays.toString(board);
    }

    private boolean isOwnPit(int player, int pit) {
        return (player == 1 && pit >= 0 && pit <= 5) || (player == 2 && pit >= 7 && pit <= 12);
    }

    private int getStoreIndex(int player) {
        return (player == 1) ? 6 : 13;
    }

    public boolean isGameOver() {
        boolean player1Empty = Arrays.stream(board, 0, 6).sum() == 0;
        boolean player2Empty = Arrays.stream(board, 7, 13).sum() == 0;
        return player1Empty || player2Empty;
    }

    private void collectRemainingSeeds() {
        for (int i = 0; i < 6; i++) board[6] += board[i]; // Collect Player 1 seeds
        for (int i = 7; i < 13; i++) board[13] += board[i]; // Collect Player 2 seeds
        Arrays.fill(board, 0, 6, 0);
        Arrays.fill(board, 7, 13, 0);
    }
}
