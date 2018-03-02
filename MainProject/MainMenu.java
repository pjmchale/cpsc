import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import java.util.*;



public class MainMenu extends Application { 
  static private Pane currentPane;
  static private Pane nextPane;
  static private Pane root;
  static public GameManager gameManager;

  static public void nextPane(){
    root.getChildren().remove(currentPane);
    root.getChildren().add(nextPane);
    currentPane = nextPane;
  }

  /* sets the scene to the input pane
   * 
   */
  static private void setPane(Pane pane){
    root.getChildren().remove(currentPane);
    root.getChildren().add(pane);
    currentPane = pane;
  }
  
  /* sets the current scene to the input pane and also sets the next pane
   *
   */
  static private void setPane(Pane pane, Pane nPane){
    root.getChildren().remove(currentPane);
    root.getChildren().add(pane);
    currentPane = pane;
    nextPane = nPane;
  } 

  static public Pane getMapPane(){
    return gameManager.getMap().getPane();
  }

  static public Player getCurrentPlayer(){
    return gameManager.getCurrentPlayer();
  }

  static public void setCountryClicked(Country country){
    gameManager.setCountryClicked(country);
  }
  
  @Override
  public void start(Stage primaryStage){
    int resX;
    int resY;
    double gapSize, centerX, centerY;

    /* Create the game manager */
    gameManager = new GameManager();

    /* Set screen size/resolution */
    resX = 960;
    resY = 600;
    gapSize = 10;
    centerX = resX/2;
    centerY = resY/2;

    /* Main pane from which all other panes are branches */
		root = new Pane();
    root.setPrefSize(resX,resY);

    /* Set the background image for the game */
    Image backgroundImage = new Image("BlueRadialGradient.jpg");
    ImageView ivBackground = new ImageView();
    ivBackground.setImage(backgroundImage);
    ivBackground.setFitWidth(resX);
    ivBackground.setPreserveRatio(true);
    ivBackground.setSmooth(true);
    ivBackground.setCache(true);
    root.getChildren().add(ivBackground);

    /* Display main screen pane */
    Pane menuPane = new Pane();
    menuPane.setPrefSize(resX, resY);
    menuPane.setLayoutX(0);
    menuPane.setLayoutY(0);
    setPane(menuPane);

    /* pane for initialize board units */
    Pane initializeBoardPane = new Pane();
    menuPane.setPrefSize(resX, resY);
    menuPane.setLayoutX(0);
    menuPane.setLayoutY(0);


    /* set title text */
    Label title = new Label("RISK");
    title.setFont(new Font("Times New Roman Bold", 175));
    title.setTextFill(Color.RED);
    //title.setLayoutX(centerX - title.widthProperty());
    title.layoutXProperty().bind(menuPane.widthProperty().subtract(title.widthProperty()).divide(2));
    title.setLayoutY(50);
    menuPane.getChildren().add(title);

    /* prints all fonts to stdout */
    /*
    List<String> allFonts = f.getFontNames();
    for(int i=0;i<allFonts.size();i++){
      System.out.println(allFonts.get(i));
    }
    */

    /* start game button */
    Button startGame = new Button("Click Here To Begin Game");
    startGame.getStyleClass().add("start_button");
    startGame.setFont(new Font("Times New Roman Bold", 45));
    startGame.layoutXProperty().bind(menuPane.widthProperty().subtract(startGame.widthProperty()).divide(2));
    startGame.setLayoutY(centerY);
    startGame.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          PlayerMenu playerMenu = new PlayerMenu();
          Pane playerPane = playerMenu.getPane();
          InitializeBoard initializeBoard = new InitializeBoard();
          Pane initializeBoardPane = initializeBoard.getPane();
          setPane(playerPane, initializeBoardPane);
          //primaryStage.setScene(createAccountScene);
        }
    });
    menuPane.getChildren().add(startGame);

    /* Create the stage */
    ivBackground.toBack(); 
		Scene scene = new Scene(root, resX, resY);
    scene.getStylesheets().add("css_styles.css");
		primaryStage.setTitle("RISK Game");
		primaryStage.setScene(scene);
    primaryStage.setResizable(false);
	  primaryStage.show();

    

  }
}

