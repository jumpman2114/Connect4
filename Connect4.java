import java.util.Scanner;
import java.util.InputMismatchException;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.application.Platform;
import java.io.IOException;
import java.lang.Thread;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.FileInputStream;
import java.io.FileOutputStream;
/**
 * The Connect4 class is the logic for the Connect4 game.
 * @author Marcus Miller
 * @version 3
 */
public class Connect4{
  /**
   * board represents the Connect4 board
   */
  public char[][] board;
  /**
   * height represents the current height of each column
   */
  public int [] heights;
  /**
   * COLUMNS is the number of columns on the Checker board.
   */
  public static int COLUMNS = 7;
  /**
   * ROWS is the number of rows on the Checker board.
   */
  public static int ROWS = 6;
  private ObjectInputStream in1;
  private ObjectOutputStream out1;
  private ServerSocket server;
  private Socket socket1;


  /**
   * Runs a two player Connect4 game with two modes.
   * You can play another player or a computer.
   * You can chose between a GUI or console output.
   */
  public static void main(String[] args) throws Exception{
    Connect4 c4 = new Connect4();
    Connect4TextConsole display = new Connect4TextConsole();
    Connect4ComputerPlayer computer = new Connect4ComputerPlayer();
    Scanner s = new Scanner(System.in);
    int port = 8000;
    display.displayGetDisplay();
    char displayMode = 'a';
    while (!(displayMode == 'G' || displayMode == 'T')){ 
      displayMode = c4.getDisplayMode(s);
      if (!(displayMode == 'G' || displayMode == 'T')){
        display.displayWrongDisplay();
      }
    }
    display.displayStart();
    char mode = 'a';//invalid mode
    while (!(mode == 'C' || mode == 'P')){ 
      mode = c4.getGameMode(s);
      if (!(mode == 'C' || mode == 'P')){
        display.displayWrongMode();
      }
    }
    if (displayMode == 'T'){
      display.displayBoard(c4.board);
      display.displayStart2(mode);
    }    
    Runnable runnable = ()->{
      Application.launch(Connect4GUI.class);
      Platform.setImplicitExit(true);
    };
    Thread thread = new Thread(runnable);
    if (displayMode == 'G'){
      thread.start();
      try{
          c4.server = new ServerSocket(port);
          c4.socket1=c4.server.accept();
          c4.out1 = new ObjectOutputStream(c4.socket1.getOutputStream());
          c4.in1 = new ObjectInputStream(c4.socket1.getInputStream());
      }
      catch(Exception ex){
          System.out.println("Error setting up Server socket.");
      }
    }
    
      
    
    char turn = 'X';
    int move=0;
    int result = 0; //0=still playing, 1=player X wins, 2=player O wins, 3=tie
    int valid=1; //0=not valid move, 1-6= valid and the number represents the row
    //main loop
    while (result == 0){
      //display message
      if (displayMode == 'G'){
	try{
	  if (turn == 'X' && valid != 0){
            c4.out1.writeObject("Black's turn.");
	  }
	  else if (turn == 'X' && valid == 0){
            c4.out1.writeObject("Black invalid move. Black's turn.");
	  }
	  else if (turn == 'O' && valid != 0){
            c4.out1.writeObject("Red's turn.");
	  }
	  else{
            c4.out1.writeObject("Red invalid move. Red's turn.");
	  }
	}
	catch(Exception ex){
          System.out.println("Error displaying message");
	  break;
	}
      }	
      else{
        display.displayPlayerTurn(turn);
	if (valid == 0){
          display.invalidMove();//console
	}
      }

      //get move
      if (mode == 'C' && turn == 'O'){//get computer move
        move = computer.getMove(c4.board);
	if (displayMode == 'T'){
          display.displayMove(move);//console
	}
      }
      else{// get player move
	if (displayMode == 'G'){
	  try{
            move = (int) c4.in1.readObject();
	  }
	  catch(Exception ex){
            System.out.println("Error reading player move");
	    break;
	  }
	}
	else{
	  move = c4.getMove(s);//console
	}
      }
      
      //check move
      valid = c4.validMove(move);
      if (valid == 0){//invalid move
        continue;
      }

      //place move
      c4.placePiece(move, turn);
      if (displayMode == 'G'){
        try{
	  c4.out1.reset();
	  c4.out1.writeObject(c4.board);
	}
	catch(Exception ex){
          System.out.println("Error writing board to GUI");
	  break;
	}
      }
      else{
        display.displayBoard(c4.board);//console
      }

      //get current game state
      result = c4.isWin(valid, move, turn);
      if (turn == 'X') turn = 'O';
      else turn = 'X';
    }
    //end of main loop

    if (displayMode == 'G'){
      try{
        if (result == 1){
          c4.out1.writeObject("Black Wins!");
        }
        else if (result == 2){
          c4.out1.writeObject("Red Wins!");
        }
        else{
          c4.out1.writeObject("Tie Game!");
        }
      }
      catch (Exception ex){
        System.out.println("Error writing winner to GUI");
      }
    }
    else{
      display.displayWinner(result);//console
    }
    if (displayMode == 'G'){
      c4.out1.close();
      c4.in1.close();
    }
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
    heights = new int[COLUMNS]; 
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
   * This function gets the display mode from the user.
   * @param s This class is used to get keyboard input.
   * @return The Display mode. 'G'==GUI, 'T'=text console
   */
  private char getDisplayMode(Scanner s){
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
    heights[column-1] += 1;
    board[heights[column-1]-1][column-1] = turn;
  }

  /**
   * This function determines if a move is valid.
   * @param column represents the column the player wants to place a checker.
   * @return The row that the checker would be place or 0 if it is an invalid move.
   */
  public int validMove(int column){
    column -= 1;
    if (column < 0 || column >= COLUMNS) return 0;
    if (heights[column] >= ROWS) return 0;
    return heights[column]+1;
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
      if (heights[c] < ROWS) return 0; //still playing
    }
    return 3; //tie
  }
}
