package test;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import core.Connect4Server;

import static org.junit.Assert.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Connect4ServerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	  String data = "q\n";
	  InputStream newIn = new ByteArrayInputStream( data.getBytes("UTF-8") );
	  System.setIn(newIn);	
		
	  Runnable runnable = ()->{
	    Connect4Server.main(null);
	  };
      Thread threadServer = new Thread(runnable);
	  threadServer.start();
	  
	  Thread.sleep(100);
	  //game 1
	  Socket player1 = new Socket("localhost", 8000);
      ObjectOutputStream out1 = new ObjectOutputStream(player1.getOutputStream());
      ObjectInputStream in1 = new ObjectInputStream(player1.getInputStream());
      out1.writeObject('P');
      
      Socket player2 = new Socket("localhost", 8000);
      ObjectOutputStream out2 = new ObjectOutputStream(player2.getOutputStream());
      ObjectInputStream in2 = new ObjectInputStream(player2.getInputStream());
      out2.writeObject('P');
      
      //player 1 game 2
	  player1 = new Socket("localhost", 8000);
      out1 = new ObjectOutputStream(player1.getOutputStream());
      in1 = new ObjectInputStream(player1.getInputStream());
      out1.writeObject('P');
      
      //game 3
      player2 = new Socket("localhost", 8000);
      out2 = new ObjectOutputStream(player2.getOutputStream());
      in2 = new ObjectInputStream(player2.getInputStream());
      out2.writeObject('C');
      
      //player 2 game 2
      player2 = new Socket("localhost", 8000);
      out2 = new ObjectOutputStream(player2.getOutputStream());
      in2 = new ObjectInputStream(player2.getInputStream());
      out2.writeObject('P');
      
      //player 1 game 4
      player2 = new Socket("localhost", 8000);
      out2 = new ObjectOutputStream(player2.getOutputStream());
      in2 = new ObjectInputStream(player2.getInputStream());
      out2.writeObject('C');
      
      threadServer.join();
      
      
      
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
	}

}
