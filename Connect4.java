package core;

//import java.util.Scanner;
//import java.util.InputMismatchException;
//import javafx.application.Application;
//import javafx.stage.Stage;
//import javafx.application.Platform;
//import java.io.IOException;
//import java.lang.Thread;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.net.ServerSocket;
import java.net.Socket;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
/**
 * The Connect4 class is the logic for the Connect4 game.
 * @author Marcus Miller
 * @version 4
 */
public class Connect4 extends Connect4Constants{
  /**
   * board represents the Connect4 board
   */
  public char[][] board;
  /**
   * height represents the current height of each column
   */
  public int [] heights;

  private ObjectInputStream in1;/**input from player 1*/
  private ObjectOutputStream out1;/**output from player 1*/
  private ObjectInputStream in2;/**input from player 2*/
  private ObjectOutputStream out2;/**output from player2*/
  //private Socket socket1;/**player 1 socket*/
 // private Socket socket2;/**player 2 socket*/
  private int numPlayers;/**number of human players*/

  /**
   * Runs a two player Connect4 game with two modes.
   * You can play another player or a computer.
   * You can chose between a GUI or console output.
   */
  public void startGame(){
    Connect4ComputerPlayer computer = new Connect4ComputerPlayer();
      
    char mode = COMPUTER;
    if (numPlayers == 2){
      mode = PLAYER;
    } 
    char turn = PLAYER1;
    int move=0;
    int result = 0; //0=still playing, 1=player X wins, 2=player O wins, 3=tie
    int valid=1; //0=not valid move, 1-6= valid and the number represents the row
    //main loop
    while (result == ACTIVE){
      try{
        if (turn == PLAYER1 && valid != 0){
          out1.writeObject(PLAYER1_TURN_MESSAGE);
	      if (numPlayers == 2){
            out2.writeObject(PLAYER1_TURN_MESSAGE);
	      }
	    }
	    else if (turn == PLAYER1 && valid == 0){
          out1.writeObject(PLAYER1_INVALID_TURN_MESSAGE);
	      if (numPlayers == 2){
            out2.writeObject(PLAYER1_INVALID_TURN_MESSAGE);
	      }
	    }
	    else if (turn == PLAYER2 && valid != 0){
          out1.writeObject(PLAYER2_TURN_MESSAGE);
	      if (numPlayers == 2){
            out2.writeObject(PLAYER2_TURN_MESSAGE);
	      }
	    }
	    else if (turn == PLAYER1 && valid == 0){
          out1.writeObject(PLAYER2_INVALID_TURN_MESSAGE);
          if (numPlayers == 2){
	        out2.writeObject(PLAYER2_INVALID_TURN_MESSAGE);
	      }
	    }
      }
      catch(Exception ex){
    	ex.printStackTrace();
	    break;
      }	

      //get move
      if (mode == COMPUTER && turn == PLAYER2){//get computer move
        move = computer.getMove(board);
      }
      else{// get player move
    	Object obj;
        try{
	      if (turn == PLAYER1){
	    	obj = in1.readObject();
	    	if(obj instanceof String) {
	    	  if (numPlayers == 2) {
	    	    out2.writeObject(PLAYER1_QUIT_MESSAGE);
	    	  }
	    	  break;
	    	}
	    	else {
	    	  move = (int) obj;
	    	}
	      }
	      else{
		    obj = in2.readObject();
		    if(obj instanceof String) {
		      out1.writeObject(PLAYER2_QUIT_MESSAGE);
		      break;
		    }
		    else {
		      move = (int) obj;
		    }
	      }
	    }
	    catch(Exception ex){
	      ex.printStackTrace();
	      break;
      	}
      }
      
      //check move
      valid = validMove(move);
      if (valid == 0){//invalid move
        continue;
      }

      //place move
      placePiece(move, turn);
      try{
        out1.reset();
	    out1.writeObject(board);
        if (numPlayers == 2){
          out2.reset();
	      out2.writeObject(board);
	    }
      }
      catch(Exception ex){
        ex.printStackTrace();
	    break;
      }

      //get current game state
      result = isWin(valid, move, turn);
      if (turn == PLAYER1) turn = PLAYER2;
      else turn = PLAYER1;
    }
    //end of main loop

    try{
      if (result == PLAYER1_WINS){
        out1.writeObject(PLAYER1_WINS_MESSAGE);
	    if (numPlayers == 2){
          out2.writeObject(PLAYER1_WINS_MESSAGE);
	    }
      }
      else if (result == PLAYER2_WINS){
        out1.writeObject(PLAYER2_WINS_MESSAGE);
	    if (numPlayers == 2){
          out2.writeObject(PLAYER2_WINS_MESSAGE);
	    }
      }
      else{
        out1.writeObject(TIE_GAME_MESSAGE);
	    if (numPlayers == 2){
          out2.writeObject(TIE_GAME_MESSAGE);
	    }
      }
      out1.close();
      in1.close();
      if (numPlayers == 2){
        out2.close();
        in2.close();
      }
    }
    catch (Exception ex){
      ex.printStackTrace();
      return;
    }
  }
  
  /**
   * Initializes the board and height fields.
   * @param s1 player 1 socket
   * @param s2 player 2 socket
   * @param o1 output to player1
   * @param o2 output to player2
   * @param i1 input from player1
   * @param i2 input from player2
   * @param nPlayers number of human players
   */
  public Connect4(Socket s1, Socket s2, ObjectOutputStream o1,
		          ObjectOutputStream o2, ObjectInputStream i1,
		          ObjectInputStream i2, int nPlayers){
    //socket1 = s1;
    //socket2 = s2;
    in1 = i1;
    in2 = i2;
    out1 = o1;
    out2 = o2;
    numPlayers = nPlayers;
    board = new char[ROWS][COLUMNS];
    for (int r = 0; r < ROWS; r++){
      for (int c = 0; c < COLUMNS; c++){
        board[r][c] = ' ';
      }
    }
    heights = new int[COLUMNS]; 
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
      if (turn == PLAYER1) return PLAYER1_WINS;
      else return PLAYER2_WINS;
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
      if (turn == PLAYER1) return PLAYER1_WINS;
      else return PLAYER2_WINS;
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
      if (turn == PLAYER1) return PLAYER1_WINS;
      else return PLAYER2_WINS;
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
      if (turn == PLAYER1) return PLAYER1_WINS;
      else return PLAYER2_WINS;
    }
    //check full
    for (c = 0; c < COLUMNS; c++){
      if (heights[c] < ROWS) return ACTIVE; //still playing
    }
    return TIE_GAME; //tie
  }
}
