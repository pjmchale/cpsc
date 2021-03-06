/**
 * This is the main function for running the risk game GUI.
 * Controls the main stage and loading of the different panes
 * onto the root pane.
 */

package GameEngine;
import PlayerPackage.*;
import CombatEngine.*;
import MapStage.*;
import java.util.*;
import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javax.swing.JFileChooser;
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



public class MainGUI extends Application { 

  // Instance variables
  static private boolean autoSetUpGame = false;
  static private GameManager gameManager;
  static private WorldMapGUI mapGUI;

  // Pane instance variables
  static private Pane currentPane;
  static private Pane nextPane;
  static private Pane root;
  static private HBox turnHBox;

  // GUI instance variables
  static private InitializeBoard initializeBoard;
  static private Label countrySelectionLabel;
  static private Label gainedUnitsLabel;
  static private Button cancelButton;
  static private Button confirmButton;
  static private TextField numUnitsTextField;
  static private ImageView ivBackground;

  /**
   * moves the scene to the next pane
   * Default is Map Pane shown at each turn
   */
  static public void nextPane(){
    setPane(nextPane);

    // If next pane is default map pane add the turn option HBox
    if(nextPane == getMapPane()){
      getMapPane().toFront();
      root.getChildren().add(turnHBox);
    }

    // Check if game over/player eliminated
    gameManager.checkGameState();

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
   * No privacy leaks concern (we want to be able to modify)
   */
  static public GameManager getGameManager(){
    return gameManager;
  }

  /**
   * Initializes the map gui
   */
  static public void initializeMapGUI(){
    mapGUI = new WorldMapGUI();
  }

  /**
   * Initialize game manager with gui
   */
  static public void initializeGameManager(){
    gameManager = new GameManager(true);
  }

  /**
   * Call for continuing country selection during initializeBoard
   * method during distribution of initial units at beginning of game
   */
  static public void continueCountrySelection(){
    initializeBoard.continueCountrySelection();
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
   * No privacy leaks concern (we want to be able to modify)
   */
  static public WorldMapGUI getMapGUI(){
    return mapGUI;
  }
  /**
   * getter for country clicked
   */
  static public void setCountryClicked(Country country){
    gameManager.setCountryClicked(country);
  }

  /**
   * will move to next turn
   */
  static public void nextTurn(){
    gameManager.nextTurn();
  }

  
  /**
   * sets the country selection label
   */
  static public void setCountrySelectionLabel(String inputString){
    countrySelectionLabel.setText(inputString);
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
   *  Called when game is over and displays winner 
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
   * Updates the legend (usually when player eliminated)
   */
  static public void updateLegend(){
    mapGUI.updateLegend();
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
    Image backgroundImage = new Image("GameEngine/BlueRadialGradient.jpg");
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

    /* set title text */
    Label title = new Label("RISK");
    title.setStyle("-fx-font: 175 timesnewroman; -fx-base: #ee2211;");
    title.setTextFill(Color.RED);
    title.layoutXProperty().bind(menuPane.widthProperty().subtract(title.widthProperty()).divide(2));
    title.setLayoutY(50);
    menuPane.getChildren().add(title);

    /* HBox for user selection during turn */
    turnHBox = new HBox();
    turnHBox.layoutXProperty().bind(root.widthProperty().subtract(turnHBox.widthProperty()).divide(2));
    turnHBox.setLayoutY(20);

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
    attackButton.setStyle("-fx-font: 20 arial; -fx-base: #ee2211;");
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
    fortifyButton.setStyle("-fx-font: 20 arial; -fx-base: #ee2211;");
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
    endTurnButton.setStyle("-fx-font: 20 arial; -fx-base: #ee2211;");
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

    
    // Add all created buttons to Hbox
    turnHBox.getChildren().addAll(attackButton, fortifyButton, endTurnButton);

    /* pane for initialize board units */
    Pane initializeBoardPane = new Pane();
    menuPane.setPrefSize(resX, resY);
    menuPane.setLayoutX(0);
    menuPane.setLayoutY(0);

    
    /* Save location text */
    String currSaveLocation = gameManager.getSaveLocation();
    if(currSaveLocation.length() > 35){
      currSaveLocation = "..." + currSaveLocation.substring(currSaveLocation.length()-35);
    }
    Label saveLocationLabel = new Label("Current Save Location: " + currSaveLocation); 
    saveLocationLabel.setFont(new Font("Times New Roman Bold", 15));
    saveLocationLabel.setTextFill(Color.BLACK);
    saveLocationLabel.setLayoutX(65);
    saveLocationLabel.setLayoutY(10);
    menuPane.getChildren().add(saveLocationLabel);

    /* Change save location button */
    Button changeSaveLocationButton = new Button ("Change");
    changeSaveLocationButton.setStyle("-fx-font: 10 arial; -fx-base: #ee2211;");
    changeSaveLocationButton.setLayoutX(10);
    changeSaveLocationButton.setLayoutY(10);
    changeSaveLocationButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          FileChooser fileChooser = new FileChooser();
          fileChooser.setTitle("Select File To Save Game");
          FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("RISK files (*.risk)", "*.risk");
          fileChooser.getExtensionFilters().add(extFilter);
          File location = fileChooser.showSaveDialog(primaryStage);
          if(location == null){
            return;
          }else{
            gameManager.setSaveLocation(location.getAbsolutePath());
          }
          String currSaveLocation = gameManager.getSaveLocation();
          if(currSaveLocation.length() > 35){
            currSaveLocation = "..." + currSaveLocation.substring(currSaveLocation.length()-35);
          }
          saveLocationLabel.setText(currSaveLocation);
        }
    });
    menuPane.getChildren().add(changeSaveLocationButton);

    /* Load saved game button */
    Button loadSavedGameButton = new Button ("Load Saved Game");
    loadSavedGameButton.setStyle("-fx-font: 10 arial; -fx-base: #ee2211;");
    loadSavedGameButton.setLayoutX(10);
    loadSavedGameButton.setLayoutY(30);
    loadSavedGameButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          FileChooser fileChooser = new FileChooser();
          fileChooser.setTitle("Please Select Saved Game To Load");
          FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("RISK files (*.risk)", "*.risk");
          fileChooser.getExtensionFilters().add(extFilter);
          File file = fileChooser.showOpenDialog(primaryStage);
          if(file == null){
            return;
          }else{
            if(!gameManager.loadGame(file)){
              return;
            }else{
              nextPane = getMapPane();
              nextPane();
              return;
            }
          }
        }
    });
    menuPane.getChildren().add(loadSavedGameButton);

    /* start game button */
    Button startGame = new Button("Click Here To Begin Game");
    startGame.setStyle("-fx-font: 30 arial; -fx-base: #ee2211;");
    startGame.layoutXProperty().bind(menuPane.widthProperty().subtract(startGame.widthProperty()).divide(2));
    startGame.setLayoutY(centerY);
    startGame.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          if(autoSetUpGame){
            gameManager.autoSetup();
            nextPane = getMapPane();
            nextPane();
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

