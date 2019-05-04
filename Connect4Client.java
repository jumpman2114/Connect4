package core;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import javafx.application.Application;
import javafx.application.Platform;
import ui.Connect4GUI;
import ui.Connect4TextConsole;
/**
 * Runs a Connect4 game that can be play a computer or connect to another
 * through a Connect4Server
 * @author Marcus Miller
 * @version 4
 */
public class Connect4Client extends Connect4Constants{
  private int port = 8000;
  private Socket socket; /**socket that talks to server */
  private ObjectOutputStream out; /**output to server */
  private ObjectInputStream in; /**input from server */
  private Socket socketGUI; /**socket that talks to GUI*/
  private ObjectOutputStream outGUI;/**output to GUI*/
  private ObjectInputStream inGUI;/**input from GUI*/
  private ServerSocket serverGUI;/**GUI server*/
  /**
   * Runs connect4 game that can play a computer or find a
   * player through Connect4Server
   */
  public static void main(String[] args){
    Connect4Client c4c = new Connect4Client();
    Connect4TextConsole display = new Connect4TextConsole();
    Scanner s = new Scanner(System.in);
    char displayMode = 0;
    display.displayGetDisplay();
    while(!(displayMode == GUI || displayMode == TERMINAL)){
      displayMode = display.getDisplayMode(s);
      if (!(displayMode == GUI || displayMode == TERMINAL)){
        display.displayWrongDisplay();
      }
    }
    display.displayGetGameMode();
    char mode = 0;
    while(!(mode == COMPUTER || mode == PLAYER)){
      mode = display.getGameMode(s);
      if (!(mode == COMPUTER || mode == PLAYER)){
        display.displayWrongMode();
      }
    }
    try{
      c4c.socket = new Socket("localhost", c4c.port);
      c4c.out = new ObjectOutputStream(c4c.socket.getOutputStream());
      c4c.in = new ObjectInputStream(c4c.socket.getInputStream());
    }
    catch (Exception ex){
      ex.printStackTrace();
      return;
    }
    char player = PLAYER1;
    Object obj = null;
    int portGUI =0;
    try{
      c4c.out.writeObject(mode);
      player = (char) c4c.in.readObject();
      portGUI = (int) c4c.in.readObject();
     
    }
    catch (Exception ex){
      ex.printStackTrace();
      return;
    }

    char[][] board = new char[ROWS][COLUMNS];
    for(int r = 0; r < ROWS; r++){
      for(int c = 0; c < COLUMNS; c++){
        board[r][c] = ' ';
      }
    }

    String playerString = Character.toString(player);
    String portGUIString = Integer.toString(portGUI);
    Runnable runnable = ()->{
      try {
        Application.launch(Connect4GUI.class, portGUIString,
		                   playerString);
        Platform.setImplicitExit(true);
      }
      catch(Exception ex){
    	ex.printStackTrace();
    	return;
      }
    };
    Thread thread = null;
    if (displayMode == GUI){
      thread = new Thread(runnable);
      thread.start();
      try{
        c4c.serverGUI = new ServerSocket(portGUI);
	    c4c.socketGUI = c4c.serverGUI.accept();
	    c4c.outGUI = new ObjectOutputStream(
	    c4c.socketGUI.getOutputStream());
	    c4c.inGUI = new ObjectInputStream(
		c4c.socketGUI.getInputStream());
      }
      catch (Exception ex){
        ex.printStackTrace();
        return;
      }
    }
    if (displayMode == TERMINAL){
      display.displayBoard(board);
      display.displayBeginGame(mode);
    }
    String text = null;
    while(true){
      try{
        obj = c4c.in.readObject();
      }
      catch(Exception ex){
        ex.printStackTrace();
        break;
      }
      if (obj instanceof String){
        text = (String) obj;
        if (text.equals(PLAYER1_QUIT_MESSAGE)) {
          try {
            c4c.outGUI.writeObject(PLAYER1_QUIT_MESSAGE);
          }
          catch(Exception ex) {
            ex.printStackTrace();
          }
          break;
        }
        else if (text.equals(PLAYER2_QUIT_MESSAGE)) {
          try {
            c4c.outGUI.writeObject(PLAYER2_QUIT_MESSAGE);
          }
          catch(Exception ex) {
            ex.printStackTrace();
          }
          break;
        }
        else if (text.equals(PLAYER1_WINS_MESSAGE) || text.equals(PLAYER2_WINS_MESSAGE) ||
			text.equals(TIE_GAME_MESSAGE)){
	      if (displayMode == TERMINAL){
	        if(text.equals(PLAYER1_WINS_MESSAGE)){
              display.displayWinner(PLAYER1_WINS);
	        }
	        else if (text.equals(PLAYER2_WINS_MESSAGE)){
              display.displayWinner(PLAYER2_WINS);
	        }
	        else{
              display.displayWinner(TIE_GAME); // Tie game
	        }
	      } 
	      else{
	        try{
              c4c.outGUI.writeObject(text);
	        }
	        catch(Exception ex){
              ex.printStackTrace();
              break;
	        }
	      }
          break;
	    }
        else if (mode == COMPUTER && (text.equals(PLAYER2_TURN_MESSAGE) ||
	             text.equals(PLAYER2_INVALID_TURN_MESSAGE))){
	      if (displayMode == TERMINAL){
	        if(text.equals(PLAYER2_INVALID_TURN_MESSAGE)){
	          display.invalidMove();
	        }
	        display.displayPlayerTurn(PLAYER2);
	      }
	      else{
	        try{
              c4c.outGUI.writeObject(text);
	        }
	        catch(Exception ex){
              ex.printStackTrace();
              break;
	        }
	      } 
	      continue;
        }	
        else if ((text.equals(PLAYER1_TURN_MESSAGE) || 
		          text.equals(PLAYER1_INVALID_TURN_MESSAGE)) &&
                  player == PLAYER1){
	      if (displayMode == TERMINAL){
            try{
	          if(text.equals(PLAYER1_INVALID_TURN_MESSAGE)){
                display.invalidMove();
	          }
	          display.displayPlayerTurn(PLAYER1);
              c4c.out.writeObject(display.getMove(s));
	        }
	        catch(Exception ex){
              ex.printStackTrace();
              break;
	        } 
	      }
	      else{
	        try{
              c4c.outGUI.writeObject(text);
              Object inData = c4c.inGUI.readObject();
              if (inData instanceof String) {
                c4c.out.writeObject(inData);
                c4c.outGUI.writeObject(inData);
                break;
              }
              c4c.out.writeObject(inData);
	        }
	        catch(Exception ex){
              ex.printStackTrace();
              break;
	        }
	      }
        }	
        else if ((text.equals(PLAYER2_TURN_MESSAGE) || 
		          text.equals(PLAYER2_INVALID_TURN_MESSAGE)) &&
                  player == PLAYER2){
	      if (displayMode == TERMINAL){
	        try{
	          if(text.equals(PLAYER2_INVALID_TURN_MESSAGE)){
                display.invalidMove();
	          }
	          display.displayPlayerTurn(PLAYER2);
              c4c.out.writeObject(display.getMove(s));
	        }
	        catch(Exception ex){
              ex.printStackTrace();
              break;
	        }
	      }
	      else{
		    try{
		      c4c.outGUI.writeObject(text);
		      Object inData = c4c.inGUI.readObject();
		      if (inData instanceof String) {
		        c4c.out.writeObject(inData);
		        c4c.outGUI.writeObject(inData);
		        break;
		      }
		      c4c.out.writeObject(inData);
			}
			catch(Exception ex){
		      ex.printStackTrace();
		      break;
			}
	      }
        }
        else{	      
          if(displayMode == TERMINAL){
            if (player != PLAYER1) {
              display.displayMessage(WAITING_FOR_PLAYER1_TERMINAL);
            }
            else {
              display.displayMessage(WAITING_FOR_PLAYER2_TERMINAL);
            }
	      }
	      else{
            try{
              if (player != PLAYER1) {
                c4c.outGUI.writeObject(PLAYER1_TURN_MESSAGE);  
              }
              else {
                c4c.outGUI.writeObject(PLAYER2_TURN_MESSAGE);
              }
	        }
	        catch(Exception ex){    
              ex.printStackTrace();
              break;
	        }
	      }
	    }	
      } 
      else if (obj instanceof char[][]){
        board = (char[][]) obj;
	    if (displayMode == TERMINAL){
          display.displayBoard(board);
	    }
	    else{
          try{
            c4c.outGUI.writeObject(board);
	      }
	      catch(Exception ex){
            ex.printStackTrace();
            break;
	      }
	    }
      }
      else{
        System.out.println("Not a String or char[][] type");
      }
    } 
    try{
      if(displayMode == GUI){
        c4c.outGUI.close();
        c4c.inGUI.close();
      }
      c4c.out.close();
      c4c.in.close();
    }
    catch(Exception ex){
      ex.printStackTrace();
      return;
    }
  }
}
