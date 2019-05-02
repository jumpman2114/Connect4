package ui;
import core.*;
import java.util.Scanner;
import java.lang.StringBuilder;
/**
 * The Connect4 console output for the Connect4 class.
 * @author Marcus Miller
 * @version 4
 */
public class Connect4TextConsole extends Connect4Constants{
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
    return move;
  }
  /**
   * gets the game mode: C=computer, P=player
   * @param s used to get keyboard input
   * @return the game mode
   */
  public char getGameMode(Scanner s){
    String mode = s.nextLine();
    if (mode.length() == 0) return 0;
    return mode.charAt(0);
  }
  /**
   * gets the display mode: T=text console, G=GUI
   * @param s used to get keyboard input
   * @return the display mode
   */
  public char getDisplayMode(Scanner s){
    String mode = s.nextLine();
    if(mode.length() == 0) return 0;
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
	String s = GET_GAME_MODE_TERMINAL;
    System.out.println(s);
    return s;
  }
  /**
   * This function prints a get display mode message.
   * @return The get display mode message.
   */
  public String displayGetDisplay(){
    String s = GET_DISPLAY_TERMINAL;
    System.out.println(s);
    return s;
  } 
  /**
   * This function prints an invalid display mode message.
   * @return The invalid display mode message.
   */
  public String displayWrongDisplay(){
    String s = INVALID_DISPLAY_TERMINAL;
    System.out.println(s);
    return s;
  } 

  /**
   * This function displays the computers move.
   * @param move The move that the computer made.
   * @return A string of the computer's move.
   */
  public String displayMove(int move){
    String s = "" + move;
    System.out.println(s);
    return s;
  }
  /**
   * This functions displays an error message if the player selects an invalid mode.
   * @return A string error message.
   */ 
  public String displayWrongMode(){
    String s = INVALID_DISPLAY_TERMINAL;
    System.out.println(s);
    return s;
  }
  /**
   * This function displays the game mode selected.
   * @param option The game mode selected.
   * @return A string of the game mode selected.
   */
  public String displayBeginGame(char option) throws IllegalArgumentException{
    if (option == COMPUTER){
      String s = START_COMPUTER_GAME_TERMINAL;
      System.out.println(s);
      return s;
    }
    else if (option == 'P'){
      String s = START_PLAYER_GAME_TERMINAL;
      System.out.println(s);
      return s;
    }
    else{
      throw new IllegalArgumentException("option must be 'P' or 'C'");
    }
  }

  /**
   * This function prints the checker board.
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
    
    System.out.println(s.toString());
    return s.toString();
  }

  /**
   * This function displays a message indicating whose turn it is.
   * @param player 'X'=PlayerX's turn, 'O'=PlayerO's turn.
   * @return returns a message saying whose turn it is.
   */
  public String displayPlayerTurn(char player){
    String s;
    if (player == PLAYER1) {
      s= PLAYER1_YOUR_TURN_TERMINAL;
    }
    else {
      s= PLAYER2_YOUR_TURN_TERMINAL;
    }
    System.out.println(s);
    return s.toString();
  }

  /**
   * This function displays the winning message.
   * @param result contains the game status.
   * @return The end of game message.
   */
  public String displayWinner(int result){
    String s;

    if (result == 1){
      s = PLAYER_1_WINS_TERMINAL;
    }
    else if (result == 2){
      s = PLAYER_2_WINS_TERMINAL;
    }
    else {
      s = TIE_GAME_TERMINAL;
    }
    System.out.println(s); 
    return s.toString();
  }

  /**
   * This function displays an invalid move message.
   * @return An invalid move message.
   */
  public String invalidMove(){
    String s = INVALID_MOVE_TERMINAL; 
    System.out.println(s);
    return s;
  }
}
