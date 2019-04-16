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
 * @version 4
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
  private ObjectInputStream in1;/**input from player 1*/
  private ObjectOutputStream out1;/**output from player 1*/
  private ObjectInputStream in2;/**input from player 2*/
  private ObjectOutputStream out2;/**output from player2*/
  private Socket socket1;/**player 1 socket*/
  private Socket socket2;/**player 2 socket*/
  private int numPlayers;/**number of human players*/

  /**
   * Runs a two player Connect4 game with two modes.
   * You can play another player or a computer.
   * You can chose between a GUI or console output.
   */
  public void startGame() throws Exception{
    Connect4ComputerPlayer computer = new Connect4ComputerPlayer();
      
    char mode = 'C';
    if (numPlayers == 2){
      mode = 'P';
    } 
    char turn = 'X';
    int move=0;
    int result = 0; //0=still playing, 1=player X wins, 2=player O wins, 3=tie
    int valid=1; //0=not valid move, 1-6= valid and the number represents the row
    //main loop
    while (result == 0){
      try{
        if (turn == 'X' && valid != 0){
          out1.writeObject("Black's turn.");
	  if (numPlayers == 2){
            out2.writeObject("Black's turn.");
	  }
	}
	else if (turn == 'X' && valid == 0){
          out1.writeObject("Black invalid move. Black's turn.");
	  if (numPlayers == 2){
            out2.writeObject("Black invalid move. Black's turn.");
	  }
	}
	else if (turn == 'O' && valid != 0){
          out1.writeObject("Red's turn.");
	  if (numPlayers == 2){
            out2.writeObject("Red's turn.");
	  }
	}
	else{
          out1.writeObject("Red invalid move. Red's turn.");
          if (numPlayers == 2){
	    out2.writeObject("Red invalid move. Red's turn.");
	  }
	}
      }
      catch(Exception ex){
        System.out.println("Error displaying message");
	break;
      }	

      //get move
      if (mode == 'C' && turn == 'O'){//get computer move
        move = computer.getMove(board);
      }
      else{// get player move
        try{
	  if (turn == 'X'){
            move = (int) in1.readObject();
	  }
	  else{
            move = (int) in2.readObject();
	  }
	}
	catch(Exception ex){
          System.out.println("Error reading player move");
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
        System.out.println("Error writing board to GUI");
        ex.printStackTrace();
	break;
      }

      //get current game state
      result = isWin(valid, move, turn);
      if (turn == 'X') turn = 'O';
      else turn = 'X';
    }
    //end of main loop

    try{
      if (result == 1){
        out1.writeObject("Black Wins!");
	if (numPlayers == 2){
          out2.writeObject("Black Wins!");
	}
      }
      else if (result == 2){
        out1.writeObject("Red Wins!");
	if (numPlayers == 2){
          out2.writeObject("Red Wins!");
	}
      }
      else{
        out1.writeObject("Tie Game!");
	if (numPlayers == 2){
          out2.writeObject("Tie Game!");
	}
      }
    }
    catch (Exception ex){
      System.out.println("Error writing winner to GUI");
      ex.printStackTrace();
    }
    out1.close();
    in1.close();
    if (numPlayers == 2){
      out2.close();
      in2.close();
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
    socket1 = s1;
    socket2 = s2;
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
