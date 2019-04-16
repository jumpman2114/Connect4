import java.util.Scanner;
import java.lang.StringBuilder;
/**
 * The Connect4 console output for the Connect4 class.
 * @author Marcus Miller
 * @version 4
 */
public class Connect4TextConsole{
  /**
   * Gets players move
   * @param s Scanner used to read input
   * @return the column that the player selected
   */
  public int getMove(Scanner s){
    int move = 0;
    try{
      move = s.nextInt();
    }
    catch(Exception ex){
      s.nextLine();
      return 0;
    }
    finally{
      return move;
    }
  }
  /**
   * gets the game mode: C=computer, P=player
   * @param s used to get keyboard input
   * @return the game mode
   */
  public char getGameMode(Scanner s){
    String mode = s.nextLine();
    if (mode.length() == 0) return 'a';
    return mode.charAt(0);
  }
  /**
   * gets the display mode: T=text console, G=GUI
   * @param s used to get keyboard input
   * @return the display mode
   */
  public char getDisplayMode(Scanner s){
    String mode = s.nextLine();
    if(mode.length() == 0) return 'a';
    return mode.charAt(0);
  }
  /**
   * displays message from connect4client
   * @param text to be printed to screen
   */
  public void displayMessage(String text){
    System.out.println(text);
  }
  
  /**
   * This function prints the get game mode message of the game.
   * @return The game mode message of the game.
   */
  public String displayGetGameMode(){
    String s = "Enter 'P' if you want to play against another player; enter 'C' to play against computer.\n";
    System.out.print(s);
    return s;
  }
  /**
   * This function prints a get display mode message.
   * @return The get display mode message.
   */
  public String displayGetDisplay(){
    String s = "Begin Game. Enter 'G' if you want a GUI display; enter 'T' to have a text console.\n";
    System.out.print(s);
    return s;
  } 
  /**
   * This function prints an invalid display mode message.
   * @return The invalid display mode message.
   */
  public String displayWrongDisplay(){
    String s = "Invalid! Enter 'G' if you want a GUI display; enter 'T' to have a text console.\n";
    System.out.print(s);
    return s;
  } 

  /**
   * This function displays the computers move.
   * @param move The move that the computer made.
   * @return A string of the computer's move.
   */
  public String displayMove(int move){
    String s = "" + move + "\n";
    System.out.print(s);
    return s;
  }
  /**
   * This functions displays an error message if the player selects an invalid mode.
   * @return A string error message.
   */ 
  public String displayWrongMode(){
    String s = "Invalid! Enter 'P' if you want to play against another player; enter 'C' to play against computer.\n";
    System.out.print(s);
    return s;
  }
  /**
   * This function displays the game mode selected.
   * @param option The game mode selected.
   * @return A string of the game mode selected.
   */
  public String displayBeginGame(char option) throws IllegalArgumentException{
    if (option == 'C'){
      String s = "Start game against computer.\n";
      System.out.print(s);
      return s;
    }
    else if (option == 'P'){
      String s = "Start game against player.\n";
      System.out.print(s);
      s = "Waiting for another player.\n";
      System.out.print(s);
      return s;
    }
    else{
      throw new IllegalArgumentException("option must be 'P' or 'C'");
    }
  }

  /**
   * This funtion prints the checker board.
   * @param board A Checker board.
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
   * This function displays a message indicating whose turn it is.
   * @param player 'X'=PlayerX's turn, 'O'=PlayerO's turn.
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
   * This function displays the winning message.
   * @param result contains the game status.
   * @return The end of game message.
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
   * This function displays an invalid move message.
   * @return An invalid move message.
   */
  public String invalidMove(){
    String s = "Invalid move. Choose a column number from 1-7.\n"; 
    System.out.print(s);
    return s;
  }
}
