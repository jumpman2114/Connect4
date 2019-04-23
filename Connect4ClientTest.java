package test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import core.Connect4Client;

public class Connect4ClientTest {
	private ServerSocket server;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Socket gui;
	private ObjectOutputStream guiout;
	private ObjectInputStream guiin;
	private Socket gui2;
	private ObjectOutputStream guiout2;
	private ObjectInputStream guiin2;
	private Socket gui3;
	private ObjectOutputStream guiout3;
	private ObjectInputStream guiin3;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	  
      String data = "T\nC\n1\n1\n1n\1\n1\n1\n";
	  InputStream newIn = new ByteArrayInputStream( data.getBytes("UTF-8") );
	  System.setIn(newIn);

	  
	  Runnable runnable = ()->{	  
		try {
		  server = new ServerSocket(8000);
		  char[][] board = new char[6][7];

          socket = server.accept(); 
          out = new ObjectOutputStream(
  	                  socket.getOutputStream());
          in = new ObjectInputStream(socket.getInputStream());
          out.writeObject(new Integer(1));
          out.writeObject(new Integer(8005));
          out.writeObject("Red's turn.");
          out.writeObject("Red invalid move. Red's turn.");
          out.writeObject("Black's turn.");
          out.writeObject("Black invalid move. Black's turn.");
          out.writeObject(board);
          out.writeObject("Black Wins!");
          
          socket = server.accept(); 
          out = new ObjectOutputStream(
	                  socket.getOutputStream());
          in = new ObjectInputStream(socket.getInputStream());
          out.writeObject(new Integer(2));
          out.writeObject(new Integer(8001));         
          out.writeObject("Red's turn.");
          out.writeObject("Red invalid move. Red's turn.");
          out.writeObject("Black's turn.");
          out.writeObject("Black invalid move. Black's turn.");
          out.writeObject(board);
          out.writeObject("Red Wins!");
          
          socket = server.accept(); 
          out = new ObjectOutputStream(
	                  socket.getOutputStream());
          in = new ObjectInputStream(socket.getInputStream());
          
          out.writeObject(new Integer(2));
          out.writeObject(new Integer(8004));
          Thread.sleep(100);
          gui = new Socket("localhost", 8004);
          guiout = new ObjectOutputStream(
                  gui.getOutputStream());
          guiin = new ObjectInputStream(gui.getInputStream());
      
          out.writeObject("Red's turn.");
          guiout.writeObject(1);
          out.writeObject("Red invalid move. Red's turn.");
          guiout.writeObject(2);
          out.writeObject("Black's turn.");
          guiout.writeObject(3);
          out.writeObject("Black invalid move. Black's turn.");
          guiout.writeObject(4);
          out.writeObject(board);
          guiout.writeObject(5);
          out.writeObject("Tie Game!");
          
          socket = server.accept(); 
          out = new ObjectOutputStream(
	                  socket.getOutputStream());
          in = new ObjectInputStream(socket.getInputStream());
          
          out.writeObject(new Integer(1));
          out.writeObject(new Integer(8002));
          Thread.sleep(100);
          gui2 = new Socket("localhost", 8002);
          guiout2 = new ObjectOutputStream(
                  gui2.getOutputStream());
          guiin2 = new ObjectInputStream(gui2.getInputStream());
      
          out.writeObject("Red's turn.");
          guiout2.writeObject(1);
          out.writeObject("Red invalid move. Red's turn.");
          guiout2.writeObject(2);
          out.writeObject("Black's turn.");
          guiout2.writeObject(3);
          out.writeObject("Black invalid move. Black's turn.");
          guiout2.writeObject(4);
          out.writeObject(board);
          guiout2.writeObject(5);
          out.writeObject("Tie Game!");
          
          socket = server.accept(); 
          out = new ObjectOutputStream(
	                  socket.getOutputStream());
          in = new ObjectInputStream(socket.getInputStream());
          
          out.writeObject(new Integer(1));
          out.writeObject(new Integer(8003));
          Thread.sleep(100);
          gui3 = new Socket("localhost", 8003);
          guiout3 = new ObjectOutputStream(
                  gui3.getOutputStream());
          guiin3 = new ObjectInputStream(gui3.getInputStream());
      
          out.writeObject("Red's turn.");
          guiout3.writeObject(1);
          out.writeObject("Red invalid move. Red's turn.");
          guiout3.writeObject(2);
          out.writeObject("Black's turn.");
          guiout3.writeObject(3);
          out.writeObject("Black invalid move. Black's turn.");
          guiout3.writeObject(4);
          out.writeObject(board);
          guiout3.writeObject(5);
          out.writeObject("Tie Game!");
          
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	  };
	  
	  Thread thread = new Thread(runnable);
	  thread.start();
	  Connect4Client.main(null);
	  
	  
      data = "T\nP\n2\n2\n2\n2\n2\n2\n";
	  newIn = new ByteArrayInputStream( data.getBytes("UTF-8") );
	  System.setIn(newIn);
	  Connect4Client.main(null);
	  
      data = "G\nP\n";
	  newIn = new ByteArrayInputStream( data.getBytes("UTF-8") );
	  System.setIn(newIn);
	  Connect4Client.main(null);
	  
      data = "G\nP\n";
	  newIn = new ByteArrayInputStream( data.getBytes("UTF-8") );
	  System.setIn(newIn);
	  Connect4Client.main(null);
	  
      data = "G\nC\n";
	  newIn = new ByteArrayInputStream( data.getBytes("UTF-8") );
	  System.setIn(newIn);
	  Connect4Client.main(null);
	  
	  server.close();
	  
	  
	  
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
	}

}
