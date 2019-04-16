import java.net.Socket;
import java.net.ServerSocket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import javafx.application.Application;
import javafx.application.Platform;
/**
 * Runs a Connect4 game that can be play a computer or connect to another
 * through a Connect4Server
 * @author Marcus Miller
 * @version 4
 */
public class Connect4Client{
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
    char displayMode = 'a';
    display.displayGetDisplay();
    while(!(displayMode == 'G' || displayMode == 'T')){
      displayMode = display.getDisplayMode(s);
      if (!(displayMode == 'G' || displayMode == 'T')){
        display.displayWrongDisplay();
      }
    }
    display.displayGetGameMode();
    char mode = 'a';
    while(!(mode == 'C' || mode == 'P')){
      mode = display.getGameMode(s);
      if (!(mode == 'C' || mode == 'P')){
        display.displayWrongMode();
      }
    }
    try{
      c4c.socket = new Socket("localhost", 8000);
      c4c.out = new ObjectOutputStream(
		    c4c.socket.getOutputStream());
      c4c.in = new ObjectInputStream(c4c.socket.getInputStream());
    }
    catch (Exception ex){
      ex.printStackTrace();
    }
    int player = 1;
    Object obj = null;
    try{
      c4c.out.writeObject(mode);
      player = (int) c4c.in.readObject();
    }
    catch (Exception ex){
      ex.printStackTrace();
    }
    int ROWS = 6;
    int COLUMNS = 7;
    char[][] board = new char[ROWS][COLUMNS];
    for(int r = 0; r < ROWS; r++){
      for(int c = 0; c < COLUMNS; c++){
        board[r][c] = ' ';
      }
    }
    int portGUI = 0;
    try{
      portGUI = (int) c4c.in.readObject();
    }
    catch(Exception ex){
      ex.printStackTrace();
    }
    String playerString = Integer.toString(player);
    String portGUIString = Integer.toString(portGUI);
    Runnable runnable = ()->{
      Application.launch(Connect4GUI.class, portGUIString,
		      playerString);
      Platform.setImplicitExit(true);
    };
    Thread thread = null;
    if (displayMode == 'G'){
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
      }
    }
    if (displayMode == 'T'){
      display.displayBoard(board);
      display.displayBeginGame(mode);
    }
    String text = null;
    while(true){
      try{
        obj = c4c.in.readObject();
      }
      catch(Exception ex){
        System.out.println("Error reading object");
        ex.printStackTrace();
      }
      if (obj instanceof String){
        text = (String) obj;
	if (text.equals("Black Wins!") || text.equals("Red Wins!") ||
			text.equals("Tie Game!")){
	  if (displayMode == 'T'){
	    if(text.equals("Black Wins!")){
              display.displayWinner(1);
	    }
	    else if (text.equals("Red Wins!")){
              display.displayWinner(2);
	    }
	    else{
              display.displayWinner(3);
	    }
	  }
	  else{
	    try{
              c4c.outGUI.writeObject(text);
	    }
	    catch(Exception ex){
              ex.printStackTrace();
	    }
	  }
          break;
	}
        else if (mode == 'C' && (text.equals("Red's turn.") ||
	  text.equals("Red invalid move. Red's turn."))){
	  if (displayMode == 'T'){
	    if(text.equals("Red invalid move. Red's turn.")){
	      display.invalidMove();
	    }
	    display.displayPlayerTurn('O');
	  }
	  else{
	    try{
              c4c.outGUI.writeObject(text);
	    }
	    catch(Exception ex){
              ex.printStackTrace();
	    }
	  } 
	  continue;
        }	
        else if ((text.equals("Black's turn.") || 
		text.equals("Black invalid move. Black's turn.")) &&
                player == 1){
	  if (displayMode == 'T'){
            try{
	      if(text.equals("Black invalid move. Black's turn.")){
                display.invalidMove();
	      }
	      display.displayPlayerTurn('X');
              c4c.out.writeObject(display.getMove(s));
	    }
	    catch(Exception ex){
              System.out.println("Error writing to Connect4 from Client.");
              ex.printStackTrace();
	    } 
	  }
	  else{
	    try{
              c4c.outGUI.writeObject(text);
              c4c.out.writeObject(c4c.inGUI.readObject());
	    }
	    catch(Exception ex){
              System.out.println("Error writing to Connect4 from Client.");
              ex.printStackTrace();
	    }
	  }
        }	
        else if ((text.equals("Red's turn.") || 
		text.equals("Red invalid move. Red's turn.")) &&
                player == 2){
	  if (displayMode == 'T'){
	    try{
	      if(text.equals("Red invalid move. Red's turn.")){
                display.invalidMove();
	      }
	      display.displayPlayerTurn('O');
              c4c.out.writeObject(display.getMove(s));
	    }
	    catch(Exception ex){
              System.out.println("Error writing to Connect4.");
              ex.printStackTrace();
	    }
	  }
	  else{
	    try{
              c4c.outGUI.writeObject(text);
              c4c.out.writeObject(c4c.inGUI.readObject());
	    }
	    catch(Exception ex){
              System.out.println("Error reading from GUI.");
              ex.printStackTrace();
	    }
	  }
        }
        else{
	  String tmp = text + " Waiting for other player.";
          if(displayMode == 'T'){
	    display.displayMessage(tmp);
	  }
	  else{
            try{
              c4c.outGUI.writeObject(tmp);
	    }
	    catch(Exception ex){
              System.out.println("Error reading from GUI.");
              ex.printStackTrace();
	    }
	  }
	}	
      } 
      else if (obj instanceof char[][]){
        board = (char[][]) obj;
	if (displayMode == 'T'){
          display.displayBoard(board);
	}
	else{
          try{
            c4c.outGUI.writeObject(board);
	  }
	  catch(Exception ex){
            System.out.println("Error writing to GUI.");
            ex.printStackTrace();
	  }
	}
      }
      else{
        System.out.println("Not a String or char[][] type");
      }
    }
    try{
      if(displayMode == 'G'){
        c4c.outGUI.close();
        c4c.inGUI.close();
      }
      c4c.out.close();
      c4c.in.close();
    }
    catch(Exception ex){
      System.out.println("Closing streams in client.");
      ex.printStackTrace();
    }
  }
}
