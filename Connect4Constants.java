package core;

public class Connect4Constants {
  public static final char PLAYER1 = 'X';
  public static final char PLAYER2 = 'O';
  public static final String WINDOW_TITLE = "Connect4";
  

  public static final String PLAYER1_QUIT_MESSAGE = "Player1 quit";
  public static final String PLAYER2_QUIT_MESSAGE = "Player2 quit";
  public static final String WAITING_MESSAGE = "Waiting for opponent to join";
  public static final String PLAYER1_WINS_MESSAGE = "Black wins!";
  public static final String PLAYER2_WINS_MESSAGE = "Red wins!";
  public static final String PLAYER1_TURN_MESSAGE = "Black's turn.";
  public static final String PLAYER2_TURN_MESSAGE = "Red's turn.";
  public static final String PLAYER1_INVALID_TURN_MESSAGE = "Black invalid move. Black's turn.";
  public static final String PLAYER2_INVALID_TURN_MESSAGE = "Red invalid move. Red's turn.";
  public static final String TIE_GAME_MESSAGE = "Tie game!";
  
  public static final String TIE_GAME_GUI = "Tie game!";
  public static final String OPPONENT_PLAYER1_WINS_GUI = "Black wins!";
  public static final String OPPONENT_PLAYER2_WINS_GUI = "Red wins!";
  public static final String YOU_PLAYER1_WINS_GUI = "Black you win!";
  public static final String YOU_PLAYER2_WINS_GUI = "Red you win!";
  public static final String WAITING_FOR_PLAYER1_GUI = "Waiting for Black's move";
  public static final String WAITING_FOR_PLAYER2_GUI = "Waiting for Red's move";
  public static final String PLAYER1_YOUR_TURN_GUI = "Black your turn. Click a column to place your checker.";
  public static final String PLAYER2_YOUR_TURN_GUI = "Red your turn. Click a column to place your checker.";
  
  public static final String GET_GAME_MODE_TERMINAL = "Enter 'P' if you want to play against another player; enter 'C' to play against computer.";
  public static final String GET_DISPLAY_TERMINAL = "Begin Game. Enter 'G' if you want a GUI display; enter 'T' to have a text console.";
  public static final String INVALID_GAME_MODE_TERMINAL = "Invalid! Enter 'G' if you want a GUI display; enter 'T' to have a text console.";
  public static final String INVALID_DISPLAY_TERMINAL = "Invalid! Enter 'P' if you want to play against another player; enter 'C' to play against computer.";
  public static final String START_COMPUTER_GAME_TERMINAL = "Start game against computer.";
  public static final String START_PLAYER_GAME_TERMINAL = "Start game against player.\nWaiting for another player.";
  public static final String PLAYER1_YOUR_TURN_TERMINAL = "Player " + PLAYER1 + " - your turn. Choose a column number 1-7.";
  public static final String PLAYER2_YOUR_TURN_TERMINAL = "Player " + PLAYER2 + " - your turn. Choose a column number 1-7.";
  public static final String PLAYER_1_WINS_TERMINAL = "Player " + PLAYER1 + " Won the Game.";
  public static final String PLAYER_2_WINS_TERMINAL = "Player " + PLAYER2 + " Won the Game.";
  public static final String INVALID_MOVE_TERMINAL = "Invalid move. Choose a column number from 1-7.";
  public static final String TIE_GAME_TERMINAL = "Tie Game!";
  public static final String WAITING_FOR_PLAYER1_TERMINAL = "Waiting for Player X's move";
  public static final String WAITING_FOR_PLAYER2_TERMINAL = "Waiting for Player O's move";
  
  
  public static final int COLUMNS = 7;
  public static final int ROWS = 6;
  public static final int ACTIVE = 0;
  public static final int PLAYER1_WINS = 1;
  public static final int PLAYER2_WINS = 2;
  public static final int TIE_GAME = 3;


  public static final char GUI = 'G';
  public static final char TERMINAL = 'T';
  public static final char COMPUTER = 'C';
  public static final char PLAYER = 'P';
  
  
}
