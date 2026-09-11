import java.util.Scanner;

/**
 * Create an escape room game where the player must navigate
 * to the other side of the screen in the fewest steps, while
 * avoiding obstacles and collecting prizes.
 */
public class EscapeRoom
{
  /* TO-DO: Process game commands from user input:
      right, left, up, down: move player size of move, m, if player try to go off grid or bump into wall, score decreases
      jump over 1 space: player cannot jump over walls
      pick up prize: score increases, if there is no prize, penalty
      help: display all possible commands
      end: reach the far right wall, score increase, game ends, if game ends without reaching far right wall, penalty
      replay: shows number of player steps and resets the board, player or another player can play the same board
        
      if player land on a trap, spring a trap to increase score: the program must first check if there is a trap, if none exists, penalty
      Note that you must adjust the score with any method that returns a score
      Optional: create a custom image for player - use the file player.png on disk
    */

  public static void main(String[] args) 
  {      
    // welcome message
    System.out.println("Welcome to EscapeRoom!");
    System.out.println("Get to the other side of the room, avoiding walls and invisible traps,");
    System.out.println("pick up all the prizes.\n");
    
    GameGUI game = new GameGUI();
    game.createBoard();

    // size of move
    int m = 60; 
    // individual player moves
    int px = 0;
    int py = 0; 
    
    int score = 0;
    int totalCoins = 3;
    int collectedCoins = 0;
    boolean wonByCoins = false;

    Scanner in = new Scanner(System.in);
    String[] validCommands = { "right", "left", "up", "down", "r", "l", "u", "d",
    "jump", "jr", "jumpleft", "jl", "jumpup", "ju", "jumpdown", "jd",
    "pickup", "p", "quit", "q", "replay", "help", "?"};
  
    // set up game
    boolean play = true;
    while (play)
    {

      // get user command and validate
      System.out.print("Enter command:");
      String input = UserInput.getValidInput(validCommands);

      /* process user commands*/
      
      if (input.equals("right")){
        game.movePlayer(60, 0);
        score -= 1;
        System.out.println("Score:" + score);
      }
      else if (input.equals("left")){
        game.movePlayer(-60, 0);
        score -= 1;
        System.out.println("Score:" + score);
      }
      else if (input.equals("up")){
        game.movePlayer(0, -60);
        score -= 1;
        System.out.println("Score:" + score);
      }
      else if (input.equals("down")){
        game.movePlayer(0, 60);
        score -= 1;
        System.out.println("Score:" + score);
      }
      else if (input.equals("jumpleft")){
        game.movePlayer(-120, 0);
        score -= 1;
        System.out.println("Score:" + score);
      }
      else if (input.equals("jumpup")){
        game.movePlayer(0, -120);
        score -= 1;
        System.out.println("Score:" + score);
      }
      else if (input.equals("jumpdown")){
        game.movePlayer(0, 120);
        score -= 1;
        System.out.println("Score:" + score);
      }
      else if (input.equals("jump")){
        game.movePlayer(120, 0);
        score -= 1;
        System.out.println("Score:" + score);
      }
      else if (input.equals("help")){
        System.out.println("Help menu:");
        System.out.println("right: move the player 1 square right");
        System.out.println("left: move the player 1 square left");
        System.out.println("up: move the player 1 square up");
        System.out.println("down: move the player 1 square down");
        System.out.println("jump: jump the player 2 squares right");
        System.out.println("jumpleft: jump the player 2 squares left");
        System.out.println("jumpup: jumps the player 2 squares up");
        System.out.println("jumpdown: jump the player 2 squares down");
        System.out.println("quit: quit the game");
        System.out.println("replay: restart the game");
        System.out.println("pickup: pickup the prize"); 
      }
      else if (input.equals("pickup")){
        int pickupScore = game.pickupPrize();
        if (pickupScore > 0) {
          collectedCoins++;
        }
        score += pickupScore;
        System.out.println("Score:" + score);

        if (collectedCoins >= totalCoins) {
          wonByCoins = true;
          System.out.println("You collected all the coins! You win!");
          play = false;
        }
      }
      else if (input.equals("replay")){
        game.replay();
        score -= 2;
        System.out.println("Score:" + score);
      }
      else if (input.equals("quit")){
        /* uncomment when user quits */
        play = false;
      }
      else{
        System.out.println("Invalid command, try again.");
        score -= 3;
        System.out.println("Score:" + score);
      }

      if (game.isTrap(0, 0)) {
        score -= 5;
        System.out.println("You stepped on a trap! You lose 5 points.");
        System.out.println("Score:" + score);
      }

      if (game.isTrap(60, 0) || game.isTrap(-60, 0) || game.isTrap(0, 60) || game.isTrap(0, -60)) {
        int trapX = 0;
        int trapY = 0;

        if (game.isTrap(60, 0)) {
          trapX = 60;
          trapY = 0;
        }
        else if (game.isTrap(-60, 0)) {
          trapX = -60;
          trapY = 0;
        }
        else if (game.isTrap(0, 60)) {
          trapX = 0;
          trapY = 60;
        }
        else if (game.isTrap(0, -60)) {
          trapX = 0;
          trapY = -60;
        }

        System.out.print("There's a trap nearby! Do you want to spring it? (yes/no): ");
        String springTrap = UserInput.getValidInput(new String[]{"yes", "no"});
        if (springTrap.equals("yes")) {
          score += game.springTrap(trapX, trapY);
          System.out.println("Score:" + score);
        }
      }
    }

    if (!wonByCoins) {
      score += game.endGame();
      System.out.println("You won");
      System.out.println("steps=" + game.getSteps());
      System.out.println("Score:" + score);
    }

  }  
}


  

        
