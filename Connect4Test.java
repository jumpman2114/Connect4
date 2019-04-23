package test;

import static org.junit.Assert.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import core.Connect4;

public class Connect4Test {
    public static core.Connect4 c4;
    public static ServerSocket socketServer;
    static Socket player1;
    static Socket player2;
    static Socket serverPlayer1;
    static Socket serverPlayer2;
    static ObjectOutputStream out1;
    static ObjectOutputStream out2;
    static ObjectOutputStream serverOut1;
    static ObjectOutputStream serverOut2;
    static ObjectInputStream in1;
    static ObjectInputStream in2;
    static ObjectInputStream serverIn1;
    static ObjectInputStream serverIn2;
    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	
	
	@Before
	public void setUp() throws Exception {
	 
      
	  Runnable runnable = ()->{	  
		try {
		  socketServer = new ServerSocket(8000);
		  
		  //black wins vertically
          serverPlayer1 = socketServer.accept();        
          serverPlayer2 = socketServer.accept();
          serverOut1 = new ObjectOutputStream(
 	             serverPlayer1.getOutputStream());
          serverOut2 = new ObjectOutputStream(
               serverPlayer2.getOutputStream());
          serverIn1 = new ObjectInputStream(serverPlayer1.getInputStream());
          serverIn2 = new ObjectInputStream(serverPlayer2.getInputStream());
     
          serverOut1.writeObject(-1);
          serverOut2.writeObject(-1);
          serverOut1.writeObject(0);
          serverOut2.writeObject(0);
          serverOut1.writeObject(8);
          serverOut2.writeObject(8);
          serverOut1.writeObject(1);
          serverOut2.writeObject(2);
          serverOut1.writeObject(1);
          serverOut2.writeObject(2);
          serverOut1.writeObject(1);
          serverOut2.writeObject(2);
          serverOut1.writeObject(1);

          //red wins horizontally
          serverPlayer1 = socketServer.accept();        
          serverPlayer2 = socketServer.accept();
          serverOut1 = new ObjectOutputStream(
  	      serverPlayer1.getOutputStream());
          serverOut2 = new ObjectOutputStream(
          serverPlayer2.getOutputStream());
          serverIn1 = new ObjectInputStream(serverPlayer1.getInputStream());
          serverIn2 = new ObjectInputStream(serverPlayer2.getInputStream());
          
          serverOut1.writeObject(1);
          serverOut2.writeObject(3);
          serverOut1.writeObject(1);
          serverOut2.writeObject(4);
          serverOut1.writeObject(2);
          serverOut2.writeObject(5);
          serverOut1.writeObject(2);
          serverOut2.writeObject(6);
          
          //black vs computer
          serverPlayer1 = socketServer.accept();        
          serverPlayer2 = socketServer.accept();
          serverOut1 = new ObjectOutputStream(
  	      serverPlayer1.getOutputStream());
          serverOut2 = new ObjectOutputStream(
          serverPlayer2.getOutputStream());
          serverIn1 = new ObjectInputStream(serverPlayer1.getInputStream());
          serverIn2 = new ObjectInputStream(serverPlayer2.getInputStream());
          
          serverOut1.writeObject(1);
          serverOut1.writeObject(1);
          serverOut1.writeObject(1);
          serverOut1.writeObject(1);
          serverOut1.writeObject(1);
          serverOut1.writeObject(1);

          serverOut1.writeObject(2);
          serverOut1.writeObject(2);
          serverOut1.writeObject(2);
          serverOut1.writeObject(2);
          serverOut1.writeObject(2);
          serverOut1.writeObject(2);
          
          serverOut1.writeObject(3);
          serverOut1.writeObject(3);
          serverOut1.writeObject(3);
          serverOut1.writeObject(3);
          serverOut1.writeObject(3);
          serverOut1.writeObject(3);
          
          serverOut1.writeObject(4);
          serverOut1.writeObject(4);
          serverOut1.writeObject(4);
          serverOut1.writeObject(4);
          serverOut1.writeObject(4);
          serverOut1.writeObject(4);
          
          serverOut1.writeObject(5);
          serverOut1.writeObject(5);
          serverOut1.writeObject(5);
          serverOut1.writeObject(5);
          serverOut1.writeObject(5);
          serverOut1.writeObject(5);
          
          serverOut1.writeObject(6);
          serverOut1.writeObject(6);
          serverOut1.writeObject(6);
          serverOut1.writeObject(6);
          serverOut1.writeObject(6);
          serverOut1.writeObject(6);
          
          serverOut1.writeObject(7);
          serverOut1.writeObject(7);
          serverOut1.writeObject(7);
          serverOut1.writeObject(7);
          serverOut1.writeObject(7);
          serverOut1.writeObject(7);
          
          //red wins \
          serverPlayer1 = socketServer.accept();        
          serverPlayer2 = socketServer.accept();
          serverOut1 = new ObjectOutputStream(
  	      serverPlayer1.getOutputStream());
          serverOut2 = new ObjectOutputStream(
          serverPlayer2.getOutputStream());
          serverIn1 = new ObjectInputStream(serverPlayer1.getInputStream());
          serverIn2 = new ObjectInputStream(serverPlayer2.getInputStream());
          
          serverOut1.writeObject(1);
          serverOut2.writeObject(4);
          serverOut1.writeObject(3);
          serverOut2.writeObject(3);
          serverOut1.writeObject(1);
          serverOut2.writeObject(2);
          serverOut1.writeObject(1);
          serverOut2.writeObject(1);
          serverOut1.writeObject(2);
          serverOut2.writeObject(2);
          
          System.out.println("leaving thread");
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	  };
	  
	  Thread threadServer = new Thread(runnable);
	  threadServer.start();
	  
	  //black wins vertically
	  player1 = new Socket("localhost", 8000);

      player2 = new Socket("localhost", 8000);
      
      out1 = new ObjectOutputStream(
	             player1.getOutputStream());
      out2 = new ObjectOutputStream(
              player2.getOutputStream());
      
      in1 = new ObjectInputStream(player1.getInputStream());
      in2 = new ObjectInputStream(player2.getInputStream());
	  
      c4 = new core.Connect4(player1, player2,
			            out1, out2, in1, in2, 2);
      c4.startGame();

      //red wins horizontally 
	  player1 = new Socket("localhost", 8000);

      player2 = new Socket("localhost", 8000);
      
      out1 = new ObjectOutputStream(
	             player1.getOutputStream());
      out2 = new ObjectOutputStream(
              player2.getOutputStream());
      
      in1 = new ObjectInputStream(player1.getInputStream());
      in2 = new ObjectInputStream(player2.getInputStream());
	  
      c4 = new core.Connect4(player1, player2,
			            out1, out2, in1, in2, 2);
      c4.startGame();
      
      //black vs computer 
	  player1 = new Socket("localhost", 8000);

      player2 = new Socket("localhost", 8000);
      
      out1 = new ObjectOutputStream(
	             player1.getOutputStream());
      out2 = new ObjectOutputStream(
              player2.getOutputStream());
      
      in1 = new ObjectInputStream(player1.getInputStream());
      in2 = new ObjectInputStream(player2.getInputStream());
	  
      c4 = new core.Connect4(player1, player2,
			            out1, out2, in1, in2, 1);
      c4.startGame();
      
      //red wins \
	  player1 = new Socket("localhost", 8000);

      player2 = new Socket("localhost", 8000);
      
      out1 = new ObjectOutputStream(
	             player1.getOutputStream());
      out2 = new ObjectOutputStream(
              player2.getOutputStream());
      
      in1 = new ObjectInputStream(player1.getInputStream());
      in2 = new ObjectInputStream(player2.getInputStream());
	  
      c4 = new core.Connect4(player1, player2,
			            out1, out2, in1, in2, 2);
      c4.startGame();

	}

	@After
	public void tearDown() throws Exception {
		try {
		  //serverOut1.close();
	      //serverOut2.close();
		  //serverIn1.close();
		  //serverIn2.close();
		  //serverPlayer1.close();
		  //serverPlayer2.close();
		  //out1.close();
		  //out2.close();
		  //in1.close();
		  //in2.close();
		  //player1.close();
		  //player2.close();
		  if(socketServer != null) {
		    if (!socketServer.isClosed()){
		      socketServer.close();
		    }
		  }
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void test() {
      
      assertFalse(c4.equals(null));

	}

}
