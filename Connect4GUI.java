package ui;
import core.*;

import javafx.scene.shape.Circle;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javafx.application.Platform;
import java.lang.Integer;
import java.util.List;


/**
 * This class is a GUI for Connect4
 * @author Marcus Miller
 * @version 1
 */
public class Connect4GUI extends Application {

  /**
   * The original height of the stage
   */ 
  private int HEIGHT = 400;
  /**
   * The original width of the stage
   */ 
  private ObjectInputStream in;
  private ObjectOutputStream out;
 
  private boolean running;
  private int port;
  private char player;
  private GridPane mainPane = new GridPane();
  private GridPane gridPane = new GridPane();
  private Button message = new Button();
  private Object inData = null;
  private char c = ' ';
  private char[][] board = null;
  private String text=Connect4Constants.WAITING_MESSAGE;

  
  private class ColumnClickHandler implements EventHandler<MouseEvent>{
	private int column;
	public ColumnClickHandler(int column){
      this.column = column;
	}
  	@Override
    public void handle(MouseEvent e){
      if((text.equals(Connect4Constants.PLAYER2_WINS_MESSAGE) ||
          text.equals(Connect4Constants.PLAYER2_TURN_MESSAGE) ||
    	  text.equals(Connect4Constants.PLAYER2_INVALID_TURN_MESSAGE)) && 
    	  player == Connect4Constants.PLAYER1){
        return;
      }
      else if((text.equals(Connect4Constants.PLAYER1_WINS_MESSAGE) ||
    		  text.equals(Connect4Constants.PLAYER1_TURN_MESSAGE) ||
    		  text.equals(Connect4Constants.PLAYER1_INVALID_TURN_MESSAGE)) && 
    		  player == Connect4Constants.PLAYER2){
        return;
      }
      else if (text.equals(Connect4Constants.WAITING_MESSAGE) || 
    		   text.equals(Connect4Constants.TIE_GAME_MESSAGE)) {
    	  return;  	  
      }
      try{
        out.writeObject(column);
      }
      catch(Exception ex){
        ex.printStackTrace();
      }
    } 
  }
  /**
   * This function controls how the GUI works
   * @param primaryStage the stage for the window
   * @throws Exception this function can throw a function
   */  
  @Override
  public void start(Stage primaryStage) throws Exception{
	  

    message.setText(text);
    message.setVisible(true);
    message.setFont(Font.font("verdan", FontWeight.BOLD,
                    FontPosture.REGULAR, .05*HEIGHT));
    

    
    //circles[Row][Column] 0,0 bottom left, 5,6 top right
    Button[][] circles = new Button[Connect4Constants.ROWS][Connect4Constants.COLUMNS];
    GridPane[] columns = new GridPane[Connect4Constants.COLUMNS];   
    for (int c=0; c<Connect4Constants.COLUMNS; c++){
      columns[c] = new GridPane();
      gridPane.add(columns[c],c,0);
    }
    for (int r=Connect4Constants.ROWS-1; r >= 0; r--){
      for(int c=0; c < Connect4Constants.COLUMNS; c++){
        Button circle = new Button();
        //circle.setShape(new Circle(10));
        int tmp = (5-r)%Connect4Constants.ROWS;
        circles[tmp][c] = circle;
        circles[tmp][c].setBorder(new Border(new BorderStroke(Color.YELLOW, 
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        circles[tmp][c].setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        columns[c].add(circle,0,r);
      }
    }        
    
    mainPane.add(gridPane,0,0);
    mainPane.add(message,0,1);
    Scene scene = new Scene(mainPane,300, 300);
    scene.setFill(Color.YELLOW);
    
  
    for (int r = 0; r < Connect4Constants.ROWS; r++){
      for(int c = 0; c < Connect4Constants.COLUMNS; c++){
        circles[r][c].prefWidthProperty().bind(gridPane.widthProperty());
        circles[r][c].prefHeightProperty().bind(gridPane.heightProperty());
      }
    }
    message.prefWidthProperty().bind(mainPane.widthProperty());
    message.prefHeightProperty().bind(mainPane.heightProperty());
    
    primaryStage.setTitle(Connect4Constants.WINDOW_TITLE);
    primaryStage.setScene(scene);
    primaryStage.sizeToScene();
    primaryStage.show();
    
    
    Application.Parameters params2 = getParameters();
    List<String> rawArguments2 = params2.getRaw();
    player = rawArguments2.get(1).charAt(0);
    System.out.println(player);
    port = Integer.parseInt(rawArguments2.get(0));
    String host = "localhost";
    
    ColumnClickHandler eventHandler0 = new ColumnClickHandler(1);
    columns[0].addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler0);
    
    ColumnClickHandler eventHandler1 = new ColumnClickHandler(2);
    columns[1].addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler1);
    
    ColumnClickHandler eventHandler2 = new ColumnClickHandler(3);
    columns[2].addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler2);
    
    ColumnClickHandler eventHandler3 = new ColumnClickHandler(4);
    columns[3].addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler3);
    
    ColumnClickHandler eventHandler4 = new ColumnClickHandler(5);
    columns[4].addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler4);
    
    ColumnClickHandler eventHandler5 = new ColumnClickHandler(6);
    columns[5].addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler5);
    
    ColumnClickHandler eventHandler6 = new ColumnClickHandler(7);
    columns[6].addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler6);
    
    primaryStage.setOnCloseRequest(e -> {
        try{
          if (running == true) {
            if (player == Connect4Constants.PLAYER1) {
              out.writeObject(Connect4Constants.PLAYER1_QUIT_MESSAGE);
            }
            else {
              out.writeObject(Connect4Constants.PLAYER2_QUIT_MESSAGE);
            }
          }
        }
        catch(Exception ex){
          ex.printStackTrace();
        }
        running = false;
    });

    Runnable updateBoardRunnable = ()->{
      for(int r = 0; r<Connect4Constants.ROWS;r++){
        for(int col = 0; col < Connect4Constants.COLUMNS; col++){
          c = board[r][col];
          if (c == ' '){
          circles[r][col].setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
          }
          else if (c == Connect4Constants.PLAYER1){
      	    circles[r][col].setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
          }  
          else{
      	    circles[r][col].setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
          }
        }
      }
    };

    Runnable updateMessageRunnable = ()->{
        System.out.println(text);
        if(player == Connect4Constants.PLAYER1 && text.equals(Connect4Constants.PLAYER1_WINS_MESSAGE)) {
          message.setText(Connect4Constants.YOU_PLAYER1_WINS_GUI);
          running = false; 
        }
        else if (player == Connect4Constants.PLAYER1 && text.equals(Connect4Constants.PLAYER2_WINS_MESSAGE)) {
          message.setText(Connect4Constants.OPPONENT_PLAYER2_WINS_GUI);
          running = false; 
        }
        else if (player == Connect4Constants.PLAYER2 && text.equals(Connect4Constants.PLAYER1_WINS_MESSAGE)) {
          message.setText(Connect4Constants.OPPONENT_PLAYER1_WINS_GUI);
          running = false; 
        }
        else if (player == Connect4Constants.PLAYER2 && text.equals(Connect4Constants.PLAYER2_WINS_MESSAGE)) {
          message.setText(Connect4Constants.YOU_PLAYER2_WINS_GUI);
          running = false; 
        }
        else if(text.equals(Connect4Constants.TIE_GAME_MESSAGE)){
          message.setText(text);
          running = false; 
        }
        else if((text.equals(Connect4Constants.PLAYER2_TURN_MESSAGE) 
        		|| text.equals(Connect4Constants.PLAYER2_INVALID_TURN_MESSAGE))
        		&& player == Connect4Constants.PLAYER2){
          message.setText(Connect4Constants.PLAYER2_YOUR_TURN_GUI);
        }
        else if((text.equals(Connect4Constants.PLAYER1_TURN_MESSAGE) 
        		|| text.equals(Connect4Constants.PLAYER1_INVALID_TURN_MESSAGE))
        		&& player == Connect4Constants.PLAYER1){
      	  message.setText(Connect4Constants.PLAYER1_YOUR_TURN_GUI);
        }
        else if((text.equals(Connect4Constants.PLAYER2_TURN_MESSAGE) 
        		|| text.equals(Connect4Constants.PLAYER2_INVALID_TURN_MESSAGE))
        		&& player != Connect4Constants.PLAYER2){
          message.setText(Connect4Constants.WAITING_FOR_PLAYER2_GUI); 
        }
        else if((text.equals(Connect4Constants.PLAYER1_TURN_MESSAGE) 
        		|| text.equals(Connect4Constants.PLAYER1_INVALID_TURN_MESSAGE))
        		&& player != Connect4Constants.PLAYER1){
      	  message.setText(Connect4Constants.WAITING_FOR_PLAYER1_GUI);
        }
        else if (text.equals(Connect4Constants.PLAYER1_QUIT_MESSAGE)) {
          running = false;
          message.setText(Connect4Constants.PLAYER1_QUIT_MESSAGE);
        }
        else if (text.equals(Connect4Constants.PLAYER2_QUIT_MESSAGE)) {
          running = false;
          message.setText(Connect4Constants.PLAYER2_QUIT_MESSAGE);
        }
    };

    new Thread(()->{
      Socket socket;
      try{
        socket = new Socket(host, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
      }
      catch(Exception ex){
        ex.printStackTrace();
        return;
      }

      running = true;
      while(running){
        try{
          inData = in.readObject();
        }
        catch(Exception ex){
          if (running == false){
            break;
          }
          ex.printStackTrace();
          break;
        }
        if (inData instanceof char[][]){
          board = (char[][]) inData;
          Platform.runLater(updateBoardRunnable);
        }
        else if (inData instanceof String){
          text = (String) inData;
      	  Platform.runLater(updateMessageRunnable);
        }
        else{
          System.out.println("Not Char[][] or String");
          break;
        }
        try {
          Thread.sleep(100);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
      }
      try{
        columns[0].removeEventFilter(MouseEvent.MOUSE_CLICKED,
                                     eventHandler0);
        columns[1].removeEventFilter(MouseEvent.MOUSE_CLICKED,
                                     eventHandler1);
        columns[2].removeEventFilter(MouseEvent.MOUSE_CLICKED,
                                     eventHandler2);
        columns[3].removeEventFilter(MouseEvent.MOUSE_CLICKED,
                                     eventHandler3);
        columns[4].removeEventFilter(MouseEvent.MOUSE_CLICKED,
                                     eventHandler4);
        columns[5].removeEventFilter(MouseEvent.MOUSE_CLICKED,
                                     eventHandler5);
        columns[6].removeEventFilter(MouseEvent.MOUSE_CLICKED,
                                     eventHandler6);
        out.close();
        in.close();
        socket.close();

      }
        catch(Exception ex){
          ex.printStackTrace();
      }
    }).start();




    
  }

  /**
   * This function launches a GUI window.
   * @param args command line arguments.
   */
  public static void main(String args[]){ 
    launch(args);
  }
}
