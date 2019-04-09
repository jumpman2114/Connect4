import java.util.concurrent.ThreadLocalRandom;
/**
 * This class is a computer player for the Connect4 class.
 * @author Marcus Miller
 * @version 1
 */
public class Connect4ComputerPlayer{
  /**
   * This function gets a random move from the computer.
   * @param board Represents the checker board state.
   * @return The move the computer picked.
   */
  public int getMove(char[][] board){
    int move = ThreadLocalRandom.current().nextInt(1, 8);
    return move;  
  }
}
