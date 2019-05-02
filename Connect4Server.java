package core;

import java.lang.Thread;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


/**
 * Server for Connect4 games
 * @author Marcus Miller
 * @version 4
 */
public class Connect4Server extends Connect4Constants{
	
  public boolean running;
  public ServerSocket serverSocket;
  
  /**
   * Connect4Server constructor
   */
  public Connect4Server() {
	  this.running = true;
  }
  
  /**
   * Thread class for a Connect4 game
   */
  private static class Game implements Runnable{
    private Connect4 game;/** Connect4 game*/
    /**
     * starts a Connect4 game
     * */
    public void run(){
      game.startGame(); 
    }
    /**
     * Constructor for game thread
     * @param game to be played
     */
    public Game(Connect4 game){
      this.game = game;
    }
  }
  private static class KeyboardListen implements Runnable{
	  
    private Connect4Server server; /** Connect4Server*/
    
    /**
	 * Constructor for listen to keyboard to close server thread
	 * @param game to be played
	 */
	public KeyboardListen(Connect4Server server){
       this.server = server;
	}
	 
	/**
	 * listen to close server
     */
	public void run(){

      Scanner s = new Scanner(System.in); 
	  while(server.running) {
		String text = "";
		try {
	      text = s.nextLine();
		}
		catch(Exception ex) {
		  ex.printStackTrace();
		  break;
		}
	    if (!text.equals("q")) continue;
	    server.running = false;
	    try {
	      System.out.println("Closing Server");
	      server.serverSocket.close();
	    }
	    catch(Exception ex) {
	      ex.printStackTrace();
	    }
	  }
	  s.close();
    }
  }
  
  /**
   * Matchmaking for Connect4 games
   * @param args isn't used
   */
  public static void main(String[] args){
	int portServer = 8000;
    int portGUI = portServer + 1; //starting GUI server 
    int player = 1; //current player being assigned a game
    Socket player1 = null; //client 1
    Socket player2 = null; //client 2
    ObjectOutputStream out1 = null; //output to player 1
    ObjectOutputStream out2 = null; //output to player 2
    ObjectInputStream in1 = null;  //input from player 1
    ObjectInputStream in2 = null;  //input from player 2
    Connect4 game = null; //game being assigned
    Thread threadGame = null;
    Connect4Server c4s = new Connect4Server();
    Thread kbListen = new Thread(new KeyboardListen(c4s));
    kbListen.start();
    char mode = 0;
    
    
    
    System.out.println("Starting Server");
    try{
      c4s.serverSocket = new ServerSocket(portServer);
    }
    catch(Exception ex){
      ex.printStackTrace();
      c4s.running = false;
    }
    while(c4s.running){
	  if(player == 1){
	    System.out.println("Finding Player 1");
	    try {
          player1 = c4s.serverSocket.accept(); //wait for clients
	    }
	    catch(Exception e){
	      if (c4s.running == false) {
	    	  break;
	      }
	      else {
	        e.printStackTrace();
	        break;
	      }
	    }
	    try {
          out1 = new ObjectOutputStream(player1.getOutputStream());
          in1 = new ObjectInputStream(player1.getInputStream());
	    }
	    catch(Exception ex) {
	      ex.printStackTrace();
	      continue;
	    }
	    try {
	      mode = (char) in1.readObject();
		  out1.writeObject(PLAYER1); //assign player number to client
		  out1.writeObject(portGUI++); //assign port for GUI
	    }
	    catch(Exception ex){
		  ex.printStackTrace();
		  continue;
		}
	    System.out.println("Found Player:player 1 Port: " + (portGUI-1));
        if (mode == PLAYER){
	      player = 2; // two player game
	    }
	    else{
          game = new Connect4(player1, null, out1, null,
		   	                  in1, null, 1); // initialize game against the computer
	      Game runnable = new Game(game);
          threadGame = new Thread(runnable);
	      threadGame.start(); //start one player game
	      System.out.println("Starting game against computer.");
	    }
	  }  
	  else{
	    try{
          player2 = c4s.serverSocket.accept(); // wait for player 2
	    }
	    catch(Exception e){
		  e.printStackTrace();
		  continue;
	    }
	    try {
          out2 = new ObjectOutputStream(player2.getOutputStream());
          in2 = new ObjectInputStream(player2.getInputStream());
	    }
	    catch(Exception ex){
		  ex.printStackTrace();
		  continue;
		}
	    try {
	      mode = (char) in2.readObject();
	    }
	    catch(Exception ex){
		  ex.printStackTrace();
		  continue;
		}
        if (mode == PLAYER){
          try {
	        out2.writeObject(PLAYER2); // assign player 2 to player 2
	        out2.writeObject(portGUI++); //assign gui port and increment the gui port
          }
  	      catch(Exception ex){
		    ex.printStackTrace();
		    continue;
		  }
	      System.out.println("Found Player:player 2 Port: " + (portGUI-1));
	      game = new Connect4(player1, player2, out1,
		                        out2, in1, in2,  2);
	      Game runnable = new Game(game);
          threadGame = new Thread(runnable);
	      threadGame.start();
	      player = 1; //start matching for a new game
	      System.out.println("Starting two player game.");
	    }
	    else{
	      try {
	        out2.writeObject(PLAYER1); //assign player number 1 for computer game
	        out2.writeObject(portGUI++);
	      }
		  catch(Exception ex){
		    ex.printStackTrace();
		    continue;
	      }
	      System.out.println("Found Player:player 1 Port: " + (portGUI-1));
          game = new Connect4(player2, null, out2, null,
			                    in2, null, 1);
	      Game runnable = new Game(game);
          threadGame = new Thread(runnable);
	      threadGame.start(); //start game against a computer
	      System.out.println("Starting game against computer.");
	    }
      }
    }
  }
}

