import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.event.Event;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.ColumnConstraints;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.ServerSocket;
import javafx.application.Platform;
import java.lang.Integer;
import java.util.List;
/**
 * This class is a GUI for Connect4
 * @author Marcus Miller
 * @version 1
 */
public class Connect4GUI extends Application{
  /**
   * The number of columns on the board
   */ 
  private int COLUMNS = 7;
  /**
   * The number of rows on the board
   */ 
  private int ROWS = 6;
  /**
   * The original height of the stage
   */ 
  private int HEIGHT = 300;
  /**
   * The original width of the stage
   */ 
  private int WIDTH = 300;
  private ObjectInputStream in;
  private ObjectOutputStream out;
  private Thread thread;
  private boolean running;
  private static String port;
  /**
   * This function controls how the GUI works
   * @param primaryStage the stage for the window
   * @throws Exception this function can throw a function
   */  
  @Override
  public void start(Stage primaryStage) throws Exception{
	  
		    
    GridPane mainPane = new GridPane();
    GridPane gridPane = new GridPane();
    mainPane.setHgap(0);
    mainPane.setVgap(0);
    gridPane.setHgap(0);
    gridPane.setVgap(0);
    Text message = new Text();
    message.setText(" ");
    message.setFont(Font.font("verdan", FontWeight.BOLD,
		            FontPosture.REGULAR, .03*HEIGHT));
    RowConstraints r1Constraint = new RowConstraints();
    r1Constraint.setPercentHeight(90);
    mainPane.getRowConstraints().add(r1Constraint);
    RowConstraints r2Constraint = new RowConstraints();
    r2Constraint.setPercentHeight(10);
    mainPane.getRowConstraints().add(r2Constraint);
    ColumnConstraints c0Constraint = new ColumnConstraints();
    c0Constraint.setPercentWidth(5);
    mainPane.getColumnConstraints().add(c0Constraint);
    ColumnConstraints c1Constraint = new ColumnConstraints();
    c1Constraint.setPercentWidth(90);
    mainPane.getColumnConstraints().add(c1Constraint);
    ColumnConstraints c2Constraint = new ColumnConstraints();
    c2Constraint.setPercentWidth(5);
    
    mainPane.getColumnConstraints().add(c2Constraint);
    mainPane.add(gridPane,1,0);
    mainPane.add(message,1,1);
    
    mainPane.setValignment(gridPane,VPos.CENTER);
    mainPane.setValignment(message,VPos.CENTER);
    mainPane.setHalignment(gridPane,HPos.CENTER);
    mainPane.setHalignment(message,HPos.CENTER);
    //circles[Row][Column] 0,0 bottom left, 5,6 top right
    Circle[][] circles = new Circle[ROWS][COLUMNS];
    GridPane[] columns = new GridPane[COLUMNS]; 	
    for (int c=0; c<COLUMNS; c++){
      ColumnConstraints column = new ColumnConstraints();
      column.setPercentWidth(100.0/COLUMNS);
      gridPane.getColumnConstraints().add(column);
      columns[c] = new GridPane();
      gridPane.add(columns[c],c,0);
    }
    for (int r=ROWS-1; r >= 0; r--){
      for(int c=0; c < COLUMNS; c++){
        RowConstraints row = new RowConstraints();
          row.setPercentHeight(100.0/ROWS);
	  columns[c].getRowConstraints().add(row);
          Circle circle = new Circle();
	  int tmp = (5-r)%ROWS;
	  circles[tmp][c] = circle;
	  circle.setFill(Color.WHITE);
	  circle.setRadius(((.9*WIDTH-(1+COLUMNS)*
			  gridPane.getHgap())/COLUMNS)/2);
	  columns[c].setHalignment(circle,HPos.CENTER);
	  columns[c].setValignment(circle,VPos.CENTER);
          columns[c].add(circle,0,r);
      }
    }        
  
    Group root = new Group(mainPane);
    Scene scene = new Scene(root,WIDTH,HEIGHT);
    scene.setFill(Color.YELLOW);
    primaryStage.widthProperty().addListener((obs, oldVal,
	newVal)->{
    mainPane.setPrefWidth(newVal.doubleValue());  
    mainPane.setPrefHeight(newVal.doubleValue());  
    for (int r = 0; r < ROWS; r++){
      for(int c = 0; c < COLUMNS; c++){
	circles[r][c].setRadius(((.9*newVal.doubleValue()-
			(1+COLUMNS)*gridPane.getHgap())/COLUMNS)/2);
      }
    }
    message.setFont(Font.font("verdan", FontWeight.BOLD,
			FontPosture.REGULAR, .05*newVal.doubleValue()));
    });
    primaryStage.heightProperty().addListener((obs, oldVal,
	newVal)->{
      double h = newVal.doubleValue();
      mainPane.setPrefHeight(h);
      message.setFont(Font.font("verdan", FontWeight.BOLD,
			FontPosture.REGULAR, .05*h));
    });
    primaryStage.setTitle("Connect4");
    primaryStage.setScene(scene);
    primaryStage.sizeToScene();
    primaryStage.show();
    

    Application.Parameters params2 = getParameters();
    List<String> rawArguments2 = params2.getRaw();
    int player = Integer.parseInt(rawArguments2.get(1));

    EventHandler<MouseEvent> eventHandler0 = new 
        EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent e){
	if(message.getText().charAt(0) == 'R' && player == 1){
		return;
	}
	if(message.getText().charAt(0) == 'B' && player == 2){
		return;
	}
        try{
          out.writeObject(1);
	}
	catch(Exception ex){
          System.out.println("Error");
	  
	}
      }  	
    };
    columns[0].addEventFilter(MouseEvent.MOUSE_CLICKED,
			     eventHandler0);

    EventHandler<MouseEvent> eventHandler1 = new 
        EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent e){
	if(message.getText().charAt(0) == 'R' && player == 1){
		return;
	}
	if(message.getText().charAt(0) == 'B' && player == 2){
		return;
	}
        try{
          out.writeObject(2);
	}
	catch(Exception ex){
          System.out.println("Error");
	}
      }  	
    };
    columns[1].addEventFilter(MouseEvent.MOUSE_CLICKED,
			     eventHandler1);

    EventHandler<MouseEvent> eventHandler2 = new 
        EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent e){
	if(message.getText().charAt(0) == 'R' && player == 1){
		return;
	}
	if(message.getText().charAt(0) == 'B' && player == 2){
		return;
	}
        try{
          out.writeObject(3);
	}
	catch(Exception ex){
          System.out.println("Error");
	}
      }  	
    };
    columns[2].addEventFilter(MouseEvent.MOUSE_CLICKED,
			     eventHandler2);

    EventHandler<MouseEvent> eventHandler3 = new 
        EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent e){
	if(message.getText().charAt(0) == 'R' && player == 1){
		return;
	}
	if(message.getText().charAt(0) == 'B' && player == 2){
		return;
	}
        try{
          out.writeObject(4);
	}
	catch(Exception ex){
          System.out.println("Error");
	}
      }  	
    };
    columns[3].addEventFilter(MouseEvent.MOUSE_CLICKED,
			     eventHandler3);

    EventHandler<MouseEvent> eventHandler4 = new 
        EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent e){
	if(message.getText().charAt(0) == 'R' && player == 1){
		return;
	}
	if(message.getText().charAt(0) == 'B' && player == 2){
		return;
	}
        try{
          out.writeObject(5);
	}
	catch(Exception ex){
          System.out.println("Error");
	}
      }  	
    };
    columns[4].addEventFilter(MouseEvent.MOUSE_CLICKED,
			     eventHandler4);

    EventHandler<MouseEvent> eventHandler5 = new 
        EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent e){
	if(message.getText().charAt(0) == 'R' && player == 1){
		return;
	}
	if(message.getText().charAt(0) == 'B' && player == 2){
		return;
	}
        try{
          out.writeObject(6);
	}
	catch(Exception ex){
          System.out.println("Error");
	}
      }  	
    };
    columns[5].addEventFilter(MouseEvent.MOUSE_CLICKED,
			     eventHandler5);

    EventHandler<MouseEvent> eventHandler6 = new 
        EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent e){
	if(message.getText().charAt(0) == 'R' && player == 1){
		return;
	}
	if(message.getText().charAt(0) == 'B' && player == 2){
		return;
	}
        try{
          out.writeObject(7);
	}
	catch(Exception ex){
          System.out.println("Error");
	}
      }  	
    };
    columns[6].addEventFilter(MouseEvent.MOUSE_CLICKED,
			     eventHandler6);
    thread = new Thread(() ->{
      Application.Parameters params = getParameters();
      List<String> rawArguments = params.getRaw();
      int port = Integer.parseInt(rawArguments.get(0));
      String host = "localhost";
      Socket socket;
      try{
        socket = new Socket(host, port);
        out = new ObjectOutputStream(
		    socket.getOutputStream());
        in = new ObjectInputStream(
		    socket.getInputStream());
      }
      catch(Exception ex){
        System.out.println("Error setting up socket");
      }
      char c = ' ';
      char[][] board= null;
      String text="";
      Object obj = null;
      running = true;
      while(running){
        try{
          obj = in.readObject();
        }
        catch(Exception ex){
          System.out.println("Error reading object");
        }
         
        if (obj instanceof char[][]){
	  board = (char[][]) obj;
          for(int r = 0; r<ROWS;r++){
            for(int col = 0; col < COLUMNS; col++){
              c = board[r][col];
              if (c == ' '){
                circles[r][col].setFill(Color.BLUE);
	      }
	      else if (c == 'X'){
	        circles[r][col].setFill(Color.BLACK);
	      }
	      else{
	        circles[r][col].setFill(Color.RED);
	      }
            }
          }
        }
        else if (obj instanceof String){
	  text = (String) obj;
          message.setText(text);
	  if (text.equals("Black Wins!") || text.equals("Red Wins!") ||
			  text.equals("Tie Game!") ){
            break;  
	  }
        }
	else{
          System.out.println("Not a String or char[][] type");
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
      }
      catch(Exception ex){
        System.out.println("Error closing streams");
	Platform.exit();
      }
    });
    thread.start();
    primaryStage.setOnCloseRequest(e -> {
      running = false;
      /*
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
      }
      catch(Exception ex){
        System.out.println("Error closing streams");
	Platform.exit();
      }
      */
    });
  }

  /**
   * This function launches a GUI window.
   * @param args command line arguments.
   */
  public static void main(String args[]){ 
    launch(args);
  }
}
