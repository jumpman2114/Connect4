import java.lang.Thread;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Server for Connect4 games
 * @author Marcus Miller
 * @version 4
 */
public class Connect4Server{
  /**
   * Thread class for a Connect4 game
   */
  private static class Game implements Runnable{
    private Connect4 game;/** Connect4 game*/
    /**
     * starts a Connect4 game
     * */
    public void run(){
      try{
        game.startGame(); 
      }
      catch(Exception ex){
        ex.printStackTrace();
      }
    }
    /**
     * Constructor for game thread
     * @param game to be played
     */
    public Game(Connect4 game){
      this.game = game;
    }
  }
  /**
   * Connects players to each other
   * @param args isn't used
   */
  public static void main(String[] args){
    int portGUI = 8001; 
    int player = 1;
    Socket player1 = null;
    Socket player2 = null;
    ObjectOutputStream out1 = null;
    ObjectOutputStream out2 = null;
    ObjectInputStream in1 = null;
    ObjectInputStream in2 = null;
    Connect4 game = null;
    ServerSocket serverSocket = null;
    Thread thread = null;
    try{
      serverSocket = new ServerSocket(8000);
    }
    catch(Exception ex){
      ex.printStackTrace();
    }
    while(true){
      try{
	if(player == 1){
          player1 = serverSocket.accept();
          out1 = new ObjectOutputStream(player1.getOutputStream());
          in1 = new ObjectInputStream(player1.getInputStream());
	  out1.writeObject(1);
	  out1.writeObject(portGUI++);
          if ((char) in1.readObject() == 'P'){
	    player = 2;
	  }
	  else{
            game = new Connect4(player1, null, out1, null,
			  in1, null, 1);
	    Game runnable = new Game(game);
            thread = new Thread(runnable);
	    thread.start();
	  }
	}
	else{
          player2 = serverSocket.accept();
          out2 = new ObjectOutputStream(player2.getOutputStream());
          in2 = new ObjectInputStream(player2.getInputStream());
          if ((char) in2.readObject() == 'P'){
	    out2.writeObject(2);
	    out2.writeObject(portGUI++);
            game = new Connect4(player1, player2, out1,
		      out2, in1, in2,  2);
	    Game runnable = new Game(game);
            thread = new Thread(runnable);
	    thread.start();
	    player = 1;
	  }
	  else{
	    out2.writeObject(1);
	    out2.writeObject(portGUI++);
            game = new Connect4(player2, null, out2, null,
			  in2, null, 1);
	    Game runnable = new Game(game);
            thread = new Thread(runnable);
	    thread.start();
	  }
        }
      }
      catch(Exception ex){
        ex.printStackTrace();
      }
    }
  }
}
