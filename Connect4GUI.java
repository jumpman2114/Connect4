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
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import javafx.animation.AnimationTimer;
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
		            FontPosture.REGULAR, .05*HEIGHT));
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
    EventHandler<MouseEvent> eventHandler0 = new 
	EventHandler<MouseEvent>(){
	@Override
	public void handle(MouseEvent e){
      try{
        FileWriter fileWriter = 
        new FileWriter("data.txt");
        fileWriter.write(1);
        fileWriter.close();
      }
      catch(IOException ex){
        System.out.println("Error writing to 'data.txt' from column1"); 
      }	
    }};
    columns[0].addEventFilter(MouseEvent.MOUSE_CLICKED,
			     eventHandler0);

    EventHandler<MouseEvent> eventHandler1 = new 
  	  EventHandler<MouseEvent>(){
	  @Override
	  public void handle(MouseEvent e){
      try{
        FileWriter fileWriter = 
        new FileWriter("data.txt");
        fileWriter.write(2);
        fileWriter.close();
      }
      catch(IOException ex){
        System.out.println("Error writing to 'data.txt' from column2");
      }
    }};
    columns[1].addEventFilter(MouseEvent.MOUSE_CLICKED,
			     eventHandler1);
    EventHandler<MouseEvent> eventHandler2 = new 
	EventHandler<MouseEvent>(){
	@Override
	public void handle(MouseEvent e){
      try{
        FileWriter fileWriter = new FileWriter("data.txt");
        fileWriter.write(3);
        fileWriter.close();
      }
      catch(IOException ex){
        System.out.println("Error writing to 'data.txt' from column3");
      }
    }};
    columns[2].addEventFilter(MouseEvent.MOUSE_CLICKED,
			     eventHandler2);
    EventHandler<MouseEvent> eventHandler3 = new 
  	EventHandler<MouseEvent>(){
	@Override
	public void handle(MouseEvent e){
      try{
        FileWriter fileWriter = new FileWriter("data.txt");
        fileWriter.write(4);
        fileWriter.close();
      }
      catch(IOException ex){
        System.out.println("Error writing to 'data.txt' from column4");
      }
    }};
    columns[3].addEventFilter(MouseEvent.MOUSE_CLICKED,
 			     eventHandler3);
    EventHandler<MouseEvent> eventHandler4 = new 
        EventHandler<MouseEvent>(){
	@Override
	public void handle(MouseEvent e){
      try{
        FileWriter fileWriter = new FileWriter("data.txt");
        fileWriter.write(5);
        fileWriter.close();
      }
      catch(IOException ex){
        System.out.println("Error writing to 'data.txt' from column5");
      }
    }};
    columns[4].addEventFilter(MouseEvent.MOUSE_CLICKED,
			     eventHandler4);
    EventHandler<MouseEvent> eventHandler5 = new 
	EventHandler<MouseEvent>(){
	@Override
	public void handle(MouseEvent e){
      try{
        FileWriter fileWriter = new FileWriter("data.txt");
        fileWriter.write(6);
        fileWriter.close();
      }
      catch(IOException ex){
        System.out.println("Error writing to 'data.txt' from column6");
      }
    }};
    columns[5].addEventFilter(MouseEvent.MOUSE_CLICKED,
			     eventHandler5);
    EventHandler<MouseEvent> eventHandler6 = new 
	EventHandler<MouseEvent>(){
	@Override
	public void handle(MouseEvent e){
      try{
        FileWriter fileWriter = new FileWriter("data.txt");
        fileWriter.write(7);
        fileWriter.close();
      }
      catch(IOException ex){
        System.out.println("Error writing to 'data.txt' from column7");
      }
    }};
    columns[6].addEventFilter(MouseEvent.MOUSE_CLICKED,
			     eventHandler6);
    new AnimationTimer(){
	@Override
	public void handle(long now){
      try{
        FileReader fileReader = new FileReader("message.txt");
        StringBuilder sb = new StringBuilder();
        int c;
        while((c=fileReader.read())!=-1){
          sb.append((char)c);
        }
        fileReader.close();
        message.setText(sb.toString());
        fileReader = new FileReader("board.txt");
        for(int r = 0; r<ROWS;r++){
          for(int col = 0; col < COLUMNS; col++){
	    c = fileReader.read();
            if ((char)c == ' '){
              circles[r][col].setFill(Color.BLUE);
	    }
	    else if ((char)c == 'X'){
	      circles[r][col].setFill(Color.BLACK);
	    }
	    else{
	      circles[r][col].setFill(Color.RED);
	    }
          }
        }
        fileReader.close();
      }
      catch(IOException ex){
        System.out.println("Error reading 'message.txt' or 'board.txt'");
      }
    }}.start();
  
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
  }
  /**
   * This function launches a GUI window.
   * @param args command line arguments.
   */
  public static void main(String args[]){
    launch(args);
  }
}
