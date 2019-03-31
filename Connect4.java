/**
 * The Connect4 class is the logic for the connect4 game.
 * @author Marcus Miller
 * @version 1
 */
import java.util.Scanner;
public class Connect4{
  /**
   * board represents the checker board
   */
  public char[][] board; // game board
  

  /**
   * Runs a two player text console chess game.
   */
  public static void main(String[] args){
    Connect4 c4 = new Connect4();
    Connect4TextConsole display = new Connect4TextConsole();
    Scanner s = new Scanner(System.in);
    display.displayBoard(c4.board);
    display.displayStart();
    char turn = 'X';
    int move;
    int result = 0; //0 still playing 1 player x win 2 player o win 3 tie
    int valid; //0 not valid 1-6 row
    while (result == 0){
      display.displayPlayerTurn(turn);
      move = c4.getMove(s);
      valid = c4.validMove(move);
      if (valid == 0){
        display.invalidMove();
        continue;
      }
      c4.placePiece(valid, move, turn);
      display.displayBoard(c4.board);
      result = c4.isWin(valid, move, turn);
      if (turn == 'X') turn = 'O';
      else turn = 'X';
    }
    display.displayWinner(result);
  }
  
  /**
   * Initializes the board member
   */
  public Connect4(){
    board = new char[6][7];
    for (int r = 0; r < 6; r++){
      for (int c = 0; c < 7; c++){
        board[r][c] = ' ';
      }
    } 
  }
  

  /**
   * @throws InputMismatchException only int data type is accepted
   * @param Scanner s used to get keyboard input
   * @return int returns the column that the player wants to move to. 
   */
  private int getMove(Scanner s){
    int move = s.nextInt();
    return move;
  }
  
  /**
   * @param int row row to place piece on checker board
   * @param int column column to place piece on checker board
   * @param char turn represents the player
   */
  private void placePiece(int row, int column, char turn){
    board[row-1][column-1] = turn;
  }

  /**
   * @param column represents the column the player wants to play
   * @return int returns the row that corresponds to the column or 0 for bad move.
   */
  private int validMove(int column){
    column -= 1;
    if (column < 0 || column >= 7) return 0;
    int row = 0;
    while (board[row][column] != ' '){
      row++;
      if (row >5) return 0;
    }
    return row + 1;
  }
  /**
   * @param int row the row played
   * @param int column the column played
   * @param char turn represents the player
   * @return the game status 0 still playing 1 playerX wins 2 playerO wins 3 tie
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
    while(c <= 6){
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
    while(c >= 0 && r <= 5){
      if (board[r][c] == turn){
        total += 1;
        c -= 1;
        r += 1;
      }
      else break;
    }
    c = column + 1;
    r = row - 1;
    while(c <= 6 && r >= 0){
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
    while(r <= 5){
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
    while(c <= 6 && r <= 5){
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
    for (r = 0; r < 6; r++){
      for (c = 0; c < 7; c++){
        if (board[r][c] == ' ') return 0;//still playing
      }
    }
    return 3; //tie
  }
}
