/**
 * The Connect4 output for the Connect4 class.
 * @author Marcus Miller
 * @version 1
 */

import java.lang.StringBuilder;

public class Connect4TextConsole{
  /**
   * @return The first message of the game.
   */
  public String displayStart(){
    String s = "Begin Game.\n";
    System.out.print(s);
    return s;
  }

  /**
   * @param board Prints a Checker board.
   * @return Returns a string representation of the board.
   */
  public String displayBoard(char[][] board){
    StringBuilder s = new StringBuilder();
    for (int r = 5; r >= 0; r--){
      s.append('|');
      for (int c = 0; c <= 6; c++){
        s.append(board[r][c]);
        s.append('|');
      }
      s.append('\n');
    }
    s.append('\n');
    System.out.print(s.toString());
    return s.toString();
  }

  /**
   * @param player x for playerX and O for playerO
   * @return returns a message saying whose turn it is.
   */
  public String displayPlayerTurn(char player){
    StringBuilder s = new StringBuilder();
    String first = "Player ";
    s.append(first);
    s.append(player);
    String last = " - your turn. Choose a column number 1-7.\n";
    s.append(last);
    System.out.print(s.toString());
    return s.toString();
  }

  /**
   * @param result contains the game status 
   * @return displays and returns the end of game message
   */
  public String displayWinner(int result){
    StringBuilder s = new StringBuilder();
    String win = "Player";
    String winEnd = " Won the Game\n";
    if (result == 1){
      s.append(win);
      s.append('X');
      s.append(winEnd);
    }
    else if (result == 2){
      s.append(win);
      s.append('O');
      s.append(winEnd); 
    }
    else {
      s.append("Tie Game\n");
    }
    System.out.print(s.toString()); 
    return s.toString();
  }

  /**
   * @return displays and returns a message that says the move is invalid
   */
  public String invalidMove(){
    String s = "Invalid move. Choose a column number from 1-7.\n"; 
    System.out.print(s);
    return s;
  }
}
