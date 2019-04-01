/**
 * The Connect4 class is the logic for the Connect4 game.
 * @author Marcus Miller
 * @version 2
 */
import java.util.Scanner;
import java.util.InputMismatchException;
public class Connect4{
  /**
   * board represents the Connect4 board
   */
  public char[][] board;
  /**
   * height represents the current height of each column
   */
  public int [] height;
  /**
   * COLUMNS is the number of columns on the Checker board.
   */
  public static int COLUMNS = 7;
  /**
   * ROWS is the number of rows on the Checker board.
   */
  public static int ROWS = 6;
  /**
   * Runs a two player text console Connect4 game with two modes.
   * You can play another player or a computer.
   */
  public static void main(String[] args){
    Connect4 c4 = new Connect4();
    Connect4TextConsole display = new Connect4TextConsole();
    Connect4ComputerPlayer computer = new Connect4ComputerPlayer();
    Scanner s = new Scanner(System.in);
    display.displayBoard(c4.board);
    display.displayStart();
    char mode = 'a';
    while (!(mode == 'C' || mode == 'P')){ 
      mode = c4.getGameMode(s);
      if (!(mode == 'C' || mode == 'P')){
        display.displayWrongMode();
      }
    }
    display.displayStart2(mode);
    char turn = 'X';
    int move;
    int result = 0; //0=still playing, 1=player X wins, 2=player O wins, 3=tie
    int valid; //0=not valid move, 1-6= valid and the number represents the row
    while (result == 0){
      display.displayPlayerTurn(turn);
      if (mode == 'C' && turn == 'O'){
        move = computer.getMove(c4.board);
        display.displayMove(move);
      }
      else{
	move = c4.getMove(s);
      }
      valid = c4.validMove(move);
      if (valid == 0){
        display.invalidMove();
        continue;
      }
      c4.placePiece(move, turn);
      display.displayBoard(c4.board);
      result = c4.isWin(valid, move, turn);
      if (turn == 'X') turn = 'O';
      else turn = 'X';
    }
    display.displayWinner(result);
  }
  
  /**
   * Initializes the board and height fields.
   */
  public Connect4(){
    board = new char[ROWS][COLUMNS];
    for (int r = 0; r < ROWS; r++){
      for (int c = 0; c < COLUMNS; c++){
        board[r][c] = ' ';
      }
    }
    height = new int[COLUMNS]; 
  }
  

  /**
   * This function gets the user's move.
   * @param s This class is used to get keyboard input.
   * @return The column that the player wants to place a checker. 
   */
  private int getMove(Scanner s){
    int move = 0;
    try{
      move = s.nextInt();
    }
    catch (InputMismatchException e){
      s.nextLine();
      return 0;
    }
    finally{
      return move;
    }
  }
  /**
   * This function gets the game mode from the user.
   * @param s This class is used to get keyboard input.
   * @return The game mode. 'C'=play computer, 'P'=play player
   */
  private char getGameMode(Scanner s){
    String mode = s.nextLine();
    if (mode.length() == 0) return 'a';
    return mode.charAt(0);
  }
  /**
   * This function places a checker on the checker board
   * @param column The column to place a checker on the checker board.
   * @param turn represents the player.
   */
  private void placePiece(int column, char turn){
    height[column-1] += 1;
    board[height[column-1]-1][column-1] = turn;
  }

  /**
   * This function determines if a move is valid.
   * @param column represents the column the player wants to place a checker.
   * @return The row that the checker would be place or 0 if it is an invalid move.
   */
  public int validMove(int column){
    column -= 1;
    if (column < 0 || column >= COLUMNS) return 0;
    if (height[column] >= ROWS) return 0;
    return height[column]+1;
  }
  /**
   * This function determines the state of the game.
   * @param row The row played.
   * @param column The column played.
   * @param turn Represents the player.
   * @return The game status: 0=still playing, 1=playerX wins, 2=playerO wins, 3=tie
   */
  private int isWin(int row, int column, char turn){
    row -= 1;
    column -= 1;
    //check horizontal
    int c = column-1;
    int total = 1;
    while(c >= 0){
      if (board[row][c] == turn){
        total += 1;
        c -= 1;
      }
      else break;
    }
    c = column + 1;
    while(c < COLUMNS){
      if (board[row][c] == turn){
        total += 1;
        c += 1;
      }
      else break;
    }
    if (total >= 4){
      if (turn == 'X') return 1;
      else return 2;
    }
    //check \ 
    int r = row + 1;
    c = column - 1;
    total = 1;
    while(c >= 0 && r < ROWS){
      if (board[r][c] == turn){
        total += 1;
        c -= 1;
        r += 1;
      }
      else break;
    }
    c = column + 1;
    r = row - 1;
    while(c < COLUMNS && r >= 0){
      if (board[r][c] == turn){
        total += 1;
        c += 1;
        r -= 1;
      }
      else break;
    }
    if (total >= 4){
      if (turn == 'X') return 1;
      else return 2;
    }
    //check vertical
    r = row-1;
    total = 1;
    while(r >= 0){
      if (board[r][column] == turn){
        total += 1;
        r -= 1;
      }
      else break;
    }
    r = row + 1;
    while(r < ROWS){
      if (board[r][column] == turn){
        total += 1;
        r += 1;
      }
      else break;
    }
    if (total >= 4){
      if (turn == 'X') return 1;
      else return 2;
    }
    //check /
    r = row - 1;
    c = column - 1;
    total = 1;
    while(c >= 0 && r >= 0){
      if (board[r][c] == turn){
        total += 1;
        c -= 1;
        r -= 1;
      }
      else break;
    }
    c = column + 1;
    r = row + 1;
    while(c < COLUMNS && r < ROWS){
      if (board[r][c] == turn){
        total += 1;
        c += 1;
        r += 1;
      }
      else break;
    }
    if (total >= 4){
      if (turn == 'X') return 1;
      else return 2;
    }
    //check full
    for (c = 0; c < COLUMNS; c++){
      if (height[c] < ROWS) return 0; //still playing
    }
    return 3; //tie
  }
}
