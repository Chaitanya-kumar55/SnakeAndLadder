import java.util.*;

class Player {
    int id;
    String name;
    int position;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        this.position = 0; // Starting position
    }

    public void move(int steps) {
        this.position += steps;
    }
}

class Mover {
    private final Random random;
    private final int sides;

    public Mover(int sides) {
        this.sides = sides;
        this.random = new Random();
    }

    public int roll() {
        return random.nextInt(sides) + 1;
    }
}

class Board {
    private final int size;
    private final Map<Integer, Integer> snakes;
    private final Map<Integer, Integer> ladders;

    public Board(int size, Map<Integer, Integer> snakes, Map<Integer, Integer> ladders) {
        this.size = size;
        this.snakes = snakes;
        this.ladders = ladders;
    }

    public int getNewPosition(int currentPosition) {
        if (snakes.containsKey(currentPosition)) {
            System.out.println("Oops! Snake at " + currentPosition);
            return snakes.get(currentPosition);
        }
        if (ladders.containsKey(currentPosition)) {
            System.out.println("Yay! Ladder at " + currentPosition);
            return ladders.get(currentPosition);
        }
        return currentPosition;
    }

    public int getSize() {
        return size;
    }
}

class GameStatus {
    private final List<Player> players;
    private final Board board;
    private boolean isFinished;

    public GameStatus(List<Player> players, Board board) {
        this.players = players;
        this.board = board;
        this.isFinished = false;
    }

    public boolean isGameOver() {
        return isFinished;
    }

    public void checkGameStatus(Player player) {
        if (player.position >= board.getSize()) {
            System.out.println(player.name + " wins!");
            isFinished = true;
        }
    }
}

class Game {
    private final Board board;
    private final List<Player> players;
    private final Mover dice;
    private final GameStatus gameStatus;

    public Game(Board board, List<Player> players) {
        this.board = board;
        this.players = players;
        this.dice = new Mover(6);
        this.gameStatus = new GameStatus(players, board);
    }

    public void startGame() {
        System.out.println("Starting the Snake and Ladder Game!");

        while (!gameStatus.isGameOver()) {
            for (Player player : players) {
                int roll = dice.roll();
                System.out.println(player.name + " rolled a " + roll);
                player.move(roll);
                player.position = board.getNewPosition(player.position);

                System.out.println(player.name + " is at position " + player.position);

                gameStatus.checkGameStatus(player);
                if (gameStatus.isGameOver()) break;
            }
        }
    }
}

public class SnakeAndLadder {
    public static void main(String[] args) {
        // Define snakes and ladders
        Map<Integer, Integer> snakes = new HashMap<>();
        snakes.put(16, 6);
        snakes.put(47, 26);
        snakes.put(49, 11);

        Map<Integer, Integer> ladders = new HashMap<>();
        ladders.put(2, 15);
        ladders.put(19, 38);
        ladders.put(36, 44);

        // Create the board
        Board board = new Board(50, snakes, ladders);

        // Create players
        List<Player> players = new ArrayList<>();
        players.add(new Player(1, "Chaitanya"));
        players.add(new Player(2, "Hari"));

        // Start the game
        Game game = new Game(board, players);
        game.startGame();
    }
}
