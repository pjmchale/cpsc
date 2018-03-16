/**
 * This is the main function for running the risk game.
 * it maintains the state of the game and It builds the 
 * stage and gets panes from other classes to add to the
 * scene.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
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



public class MainGUI extends Application { 
  static private boolean autoSetUpGame = false;
  static private GameManager gameManager;
  static private MapGUI mapGUI;

  static private Pane currentPane;
  static private Pane nextPane;
  static private Pane root;
  static private HBox turnHBox;

  static private InitializeBoard initializeBoard;
  static private Label countrySelectionLabel;
  static private Label playerTurnLabel;
  static private Label gainedUnitsLabel;
  static private Button cancelButton;
  static private Button confirmButton;
  static private TextField numUnitsTextField;
  static private ImageView ivBackground;

  /**
   * moves the scene to the next pane
   */
  static public void nextPane(){
    setPane(nextPane);
    if(nextPane == getMapPane()){
      getMapPane().toFront();
      root.getChildren().add(turnHBox);
      gameManager.setTurnState();
      playerTurnLabel.setVisible(true);
    }

    gameManager.checkIfGameOver();

    nextPane = getMapPane();
  }

  /**
   * sets the scene to the input pane
   */
  static private void setPane(Pane pane){
    root.getChildren().remove(currentPane);
    if(currentPane == getMapPane()){
      root.getChildren().remove(turnHBox);
    }
    root.getChildren().add(pane);
    currentPane = pane;
  }
  
  /**
   * sets the current scene to the input pane and also sets the next pane
   */
  static private void setPane(Pane pane, Pane nPane){
    root.getChildren().remove(currentPane);
    if(currentPane == getMapPane()){
      root.getChildren().remove(turnHBox);
    }
    root.getChildren().add(pane);
    currentPane = pane;
    nextPane = nPane;
  } 

  /**
   * gets the game manager
   */
  static public GameManager getGameManager(){
    return gameManager;
  }

  /**
   * Initializes the map gui
   */
  public void initializeMapGUI(){
    mapGUI = new MapGUI();
  }

  /**
   * Initialize game manager
   */
  public void initializeGameManager(){
    gameManager = new GameManager();
  }

  /**
   * gets all the players (needed by Map.java)
   */
  static public Player[] getAllPlayers(){
    return gameManager.getAllPlayers();
  }

  /**
   * getter for getting map pane
   */
  static public Pane getMapPane(){
    return mapGUI.getPane();
  }

  /**
   * getter for getting map GUI
   */
  static public MapGUI getMapGUI(){
    return mapGUI;
  }
  /**
   * getter for country clicked
   */
  static public void setCountryClicked(Country country){
    gameManager.setCountryClicked(country);
  }

  
  /**
   * sets the country selection label
   */
  static public void setCountrySelectionLabel(String inputString){
    countrySelectionLabel.setText(inputString);
  }

  /** 
   * sets the player current turn label
   */
  static public void setPlayerTurnLabel(String inputString){
    playerTurnLabel.setText(inputString);
  }

  /**
   * sets the country selection label
   */
  static public void setGainedUnitsLabel(String inputString){
    gainedUnitsLabel.setText(inputString);
  }

  /**
   * removes the GUI elements present during attack setup
   */
  static public void removeAttackGUIElements(){
    root.getChildren().remove(getMapPane());
    root.getChildren().remove(countrySelectionLabel);
    root.getChildren().remove(cancelButton);
  }
  
  /**
   * adds the GUI elements present during fortify setup
   */
  static public void addFortifyGUIElements(){
    root.getChildren().add(numUnitsTextField);
    root.getChildren().add(confirmButton);
  }

  /**
   * removes the GUI elements present during placing units setup at beginning of turn
   */
  static public void removePlaceUnitsGUIElements(){
    root.getChildren().remove(gainedUnitsLabel);
  }

  /**
   *  Called when game is over and dsiplays winner 
   * @return gameOver true if player owns every territory otherwise false
   */
  static public void gameOver(Player winningPlayer){
      root.getChildren().clear();
      root.getChildren().add(ivBackground);
      Label winnerLabel = new Label();
      winnerLabel.setFont(new Font("Times New Roman Bold", 45));
      winnerLabel.setTextFill(Color.RED);
      winnerLabel.setText("Congratulations " + winningPlayer.getName() + " You Won The Game!");
      winnerLabel.layoutXProperty().bind(root.widthProperty().subtract(winnerLabel.widthProperty()).divide(2));
      winnerLabel.layoutYProperty().bind(root.heightProperty().divide(2));
      root.getChildren().add(winnerLabel);
  }

  /**
   * Shows the legend on the map
   */
  static public void showLegend(){
    mapGUI.showLegend();
  }

  /**
   * Distributes new units at beginning of turn
   * amount determined by GameManager
   */
  static public void distributeUnitsTurn(Player currentPlayer){
    root.getChildren().remove(turnHBox);
    root.getChildren().remove(getMapPane());
    root.getChildren().add(getMapPane());

    gainedUnitsLabel.setText(currentPlayer.getName() + " Select Country To Place Gained Units! (" + currentPlayer.getAvailableUnits() + " available)");
    root.getChildren().add(gainedUnitsLabel);

  }

  

  /**
   * initiates the combat
   */
  static public void startAttack(Country fromCountry, Country toCountry){
    Combat combat = new Combat(fromCountry, toCountry);
    setPane(combat.getPane(), getMapPane());
    gameManager.clearState();
  }

  /**
   * Main function builds the stage for the game
   */
  @Override
  public void start(Stage primaryStage){
    int resX;
    int resY;
    double gapSize, centerX, centerY;

    /* Create the game manager and map gui*/
    initializeMapGUI();
    initializeGameManager();


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
    ivBackground = new ImageView();
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

    /* PLayer turn label */
    playerTurnLabel = new Label();
    playerTurnLabel.setFont(new Font("Times New Roman Bold", 30));
    playerTurnLabel.setTextFill(Color.RED);
    playerTurnLabel.setLayoutX(30);
    playerTurnLabel.layoutYProperty().bind(root.heightProperty().subtract(30));
    root.getChildren().add(playerTurnLabel);
    playerTurnLabel.setVisible(false);

    /* VBox for user selection during turn */
    turnHBox = new HBox();
    turnHBox.layoutXProperty().bind(root.widthProperty().subtract(turnHBox.widthProperty()).divide(2));
    turnHBox.setLayoutY(30);

    /* Country selection label */
    countrySelectionLabel = new Label("");
    countrySelectionLabel.setFont(new Font("Times New Roman Bold", 20));
    countrySelectionLabel.setTextFill(Color.RED);
    countrySelectionLabel.layoutXProperty().bind(root.widthProperty().subtract(countrySelectionLabel.widthProperty()).divide(2));
    countrySelectionLabel.setLayoutY(30);

    /* Number of units text box */
    numUnitsTextField = new TextField();
    numUnitsTextField.layoutXProperty().bind(root.widthProperty().subtract(numUnitsTextField.widthProperty()).divide(2));
    numUnitsTextField.setLayoutY(60);

    /* Label for displaying number of untis gained at begginning of turn */
    gainedUnitsLabel = new Label();
    gainedUnitsLabel.setFont(new Font("Times New Roman Bold", 20));
    gainedUnitsLabel.setTextFill(Color.RED);
    gainedUnitsLabel.layoutXProperty().bind(root.widthProperty().subtract(gainedUnitsLabel.widthProperty()).divide(2));
    gainedUnitsLabel.setLayoutY(30);

    /* attack selection button */
    Button attackButton = new Button("Attack");
    attackButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        gameManager.setAttacking();
        root.getChildren().remove(turnHBox);
        countrySelectionLabel.setText("Please Select Country To Attack From");
        root.getChildren().add(countrySelectionLabel);
        root.getChildren().add(cancelButton);
      }
    });


    /* fortify selection button */
    Button fortifyButton = new Button("Fortify");
    fortifyButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        gameManager.setFortify();
        root.getChildren().remove(turnHBox);
        countrySelectionLabel.setText("Please Select Country To Move Units From");
        root.getChildren().add(countrySelectionLabel);
        root.getChildren().add(cancelButton);
      }
    });



    /* end turn selection button */
    Button endTurnButton = new Button("End Turn");
    endTurnButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        gameManager.nextTurn();
      }
    });

    /**
     * button to confirm num of units selection
     */
    confirmButton = new Button("Confirm");
    confirmButton.layoutXProperty().bind(root.widthProperty().divide(2).add(numUnitsTextField.widthProperty().divide(2)));
    confirmButton.setLayoutY(60);
    confirmButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        int numUnits;
        try {
          numUnitsTextField.setStyle("-fx-text-fill: black;");
          numUnits = Integer.parseInt(numUnitsTextField.getText());
        } catch (NumberFormatException e) {
          numUnitsTextField.setStyle("-fx-text-fill: red;");
          return;
        }       
        if(numUnits < gameManager.getFromCountry().getUnits() && numUnits >= 0){
          root.getChildren().remove(getMapPane());
          root.getChildren().remove(countrySelectionLabel);
          root.getChildren().remove(numUnitsTextField);
          root.getChildren().remove(confirmButton);
          root.getChildren().remove(cancelButton);
          gameManager.fortify(numUnits);
        }else{
          numUnitsTextField.setStyle("-fx-text-fill: red;");
          return;
        }
      }
    });

    /**
     * button to cancel turn selection choice
     */
    cancelButton = new Button("Cancel");
    cancelButton.layoutXProperty().bind(root.widthProperty().subtract(cancelButton.widthProperty()).divide(2));
    cancelButton.layoutYProperty().bind(root.heightProperty().subtract(30));
    cancelButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        gameManager.clearState();
        gameManager.getMap().hideNeighbours();
        root.getChildren().remove(cancelButton);
        root.getChildren().remove(confirmButton);
        root.getChildren().remove(numUnitsTextField);
        root.getChildren().remove(countrySelectionLabel);
        root.getChildren().remove(getMapPane());
        nextPane();
      }
    });

    
    turnHBox.getChildren().addAll(attackButton, fortifyButton, endTurnButton);

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
    //startGame.getStyleClass().add("start_button");
    startGame.setFont(new Font("Times New Roman Bold", 20));
    startGame.layoutXProperty().bind(menuPane.widthProperty().subtract(startGame.widthProperty()).divide(2));
    startGame.setLayoutY(centerY);
    startGame.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          if(autoSetUpGame){
            //gameManager.autoSetup();
            return;
          }
          PlayerMenu playerMenu = new PlayerMenu();
          Pane playerPane = playerMenu.getPane();
          initializeBoard = new InitializeBoard();
          Pane initializeBoardPane = initializeBoard.getPane();
          setPane(playerPane, initializeBoardPane);
        }
    });
    menuPane.getChildren().add(startGame);

    /* Create the stage */
    ivBackground.toBack(); 
		Scene scene = new Scene(root, resX, resY);
		primaryStage.setTitle("RISK Game");
		primaryStage.setScene(scene);
    primaryStage.setResizable(false);
	  primaryStage.show();

  }

}

