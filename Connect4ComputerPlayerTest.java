package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import core.Connect4ComputerPlayer;

public class Connect4ComputerPlayerTest {
    static private char[][] board;
    static private Connect4ComputerPlayer cp;
    static int ROWS = 6;
    static int COLUMNS = 7;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		board = new char[ROWS][COLUMNS];
		for(int r=0;r<ROWS;r++) {
			for(int c=0;c<COLUMNS;c++) {
				board[r][c] = ' ';
			}
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		cp = new Connect4ComputerPlayer();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		boolean test = 0<cp.getMove(board) && cp.getMove(board)<=COLUMNS;
		System.out.println(test);
		assertTrue(test);
		
		test = 0<cp.getMove(board) && cp.getMove(board)<=COLUMNS;
		System.out.println(test);
		assertTrue(test);
		
		test = 0<cp.getMove(board) && cp.getMove(board)<=COLUMNS;
		System.out.println(test);
		assertTrue(test);
		
		test = 0<cp.getMove(board) && cp.getMove(board)<=COLUMNS;
		System.out.println(test);
		assertTrue(test);
			
		test = 0<cp.getMove(board) && cp.getMove(board)<=COLUMNS;
		System.out.println(test);
		assertTrue(test);
		
		
		test = 0<cp.getMove(board) && cp.getMove(board)<=COLUMNS;
		System.out.println(test);
		assertTrue(test);
		
		
		test = 0<cp.getMove(board) && cp.getMove(board)<=COLUMNS;
		System.out.println(test);
		assertTrue(test);
		
		test = 0<cp.getMove(board) && cp.getMove(board)<=COLUMNS;
		System.out.println(test);
		assertTrue(test);
		
	}

}
