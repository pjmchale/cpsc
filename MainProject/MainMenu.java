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



public class MainMenu extends Application { 
  static private boolean autoSetUpGame = false;

  static private Pane currentPane;
  static private Pane nextPane;
  static private Pane root;
  static private HBox turnHBox;
  //static public GameManager gameManager;

  static private InitializeBoard initializeBoard;
  static private Label countrySelectionLabel;
  static private Label playerTurnLabel;
  static private Label gainedUnitsLabel;
  static private Button cancelButton;
  static private Button confirmButton;
  static private TextField numUnitsTextField;

  static private int numPlayers;
  static private Player[] players;
  static private Map map;
  static private Player currentPlayer;
  static private Country countryClicked;
  static private Country toCountry;
  static private Country fromCountry;
  static private int turnIndex;
  static private Player firstTurn;
  static private int firstTurnIndex;
  static private boolean distributeUnits = false;
  static private boolean attacking = false;
  static private boolean fortify = false;
  static private boolean placeUnits = false;
  static private boolean turn = false;

  /**
   * moves the scene to the next pane
   */
  static public void nextPane(){
    setPane(nextPane);
    if(nextPane == getMapPane()){
      getMapPane().toFront();
      root.getChildren().add(turnHBox);
      turn = true;
      playerTurnLabel.setVisible(true);
    }

    checkIfGameOver();

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
  
  /* sets the current scene to the input pane and also sets the next pane
   *
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
   * getter for getting map pane
   */
  static public Pane getMapPane(){
    return getMap().getPane();
  }

  /**
   * getter for game map
   */
  static public Map getMap(){
    return map;
  }

  /**
   * creates the map for the game
   */
  private void initializeMap(){
    map = new Map();
  }

  /**
   * getter for current player
   */
  static public Player getCurrentPlayer(){
    return currentPlayer;
  }

  /**
   * getter for all players
   */
  static public Player[] getAllPlayers(){
    return players;
  }

  /**
   * getter for country clicked
   */
  static public void setCountryClicked(Country country){
    countryClicked = country;
    processCountryClick();
    
  }

  /**
   * setter for country clicked
   * @return country
   */
  static public Country getCountryClicked(){
    return countryClicked;
  }

  /**
   * Determines what to do with country click based off game state
   */
  static public void processCountryClick(){

    /* Distributing units phase, set country owner */
    if(distributeUnits){
      if(countryClicked.getOwner() == null){
        countryClicked.setOwner(currentPlayer);
        currentPlayer.placeUnits(countryClicked, 1);
        distributeUnits = false;
        nextTurn();
        return;
      }else if(allCountriesOwned()){
        if(countryClicked.getOwner() == currentPlayer){
          currentPlayer.placeUnits(countryClicked, 1);
          distributeUnits = false;
          nextTurn();
        }
      }
    }

    /* For when player is attacking */
    if(attacking){
      if(fromCountry == null){
        if(countryClicked.getOwner() == currentPlayer && countryClicked.getUnits() > 1){
          fromCountry = countryClicked;
          getMap().showNeighbours(countryClicked);
          countrySelectionLabel.setText("Please Select Country To Attack");
        }else{
          return;
        }
      }else{
        if(countryClicked.getOwner() != currentPlayer && countryClicked.isNeighbour(fromCountry)){
          toCountry = countryClicked;
          getMap().hideNeighbours();
          root.getChildren().remove(getMapPane());
          root.getChildren().remove(countrySelectionLabel);
          root.getChildren().remove(cancelButton);
          startAttack(fromCountry, toCountry);
        }else{
          return;
        }
      }
    }

    /* For when player is fortifying*/
    if(fortify){
      if(fromCountry == null){
        if(countryClicked.getOwner() == currentPlayer){
          fromCountry = countryClicked;
          getMap().showNeighboursOwner(countryClicked, currentPlayer);
          countrySelectionLabel.setText("Please Select Country To Move Units To");
        }else{
          return;
        }
      }else if(toCountry == null)
        if(countryClicked.getOwner() == currentPlayer && countryClicked.isNeighbour(fromCountry)){
          toCountry = countryClicked;
          getMap().hideNeighbours();
          countrySelectionLabel.setText("How Many Units Would You Like To Move? (" + (fromCountry.getUnits()-1) + " available)");
          root.getChildren().add(numUnitsTextField);
          root.getChildren().add(confirmButton);
        }else{
          return;
        }
    }

    /* For when player is placing new units */
    if(placeUnits){
      if(countryClicked.getOwner() == currentPlayer){
        currentPlayer.placeUnits(countryClicked, 1);
        gainedUnitsLabel.setText(currentPlayer.getName() + " Select Country To Place Gained Units! (" + currentPlayer.getAvailableUnits() + " available)");
        if(currentPlayer.getAvailableUnits() == 0){
          root.getChildren().remove(gainedUnitsLabel);
          placeUnits = false;
          nextPane();
        }
      }
    }
  }

  /**
   * determines if all countries are owned by someone
   * @return true is all countries are owned false if not
   */
  static public boolean allCountriesOwned(){
	  ArrayList<Country> countries;

    countries = MainMenu.getMap().getCountries();
    for(int i=0; i < countries.size();i++){
      if(countries.get(i).getOwner() == null){
        return false;
      }
    }

    return true;
  }

  /**
   * sets boolean for distributing units phase
   * @state distributing units state (true false)
   */
  static public void setDistributeUnits(boolean state){
    distributeUnits = state;
  } 

  /**
   * Checks if player has won the game i.e. owns every territory on the map
   * @return gameOver true if player owns every territory otherwise false
   */
  static public boolean checkIfGameOver(){
    ArrayList<Country> allCountries = map.getCountries();

    for(int i=0; i < players.length ; i++){
      System.out.println(players[i].getName() + ":" + players[i].getCountriesOwned().size() + "/" + allCountries.size());
      if(players[i].getCountriesOwned().size() >= allCountries.size()){
        root.getChildren().clear();
        //root.getChildren().add(ivBackground);
        Label winnerLabel = new Label();
        winnerLabel.setFont(new Font("Times New Roman Bold", 45));
        winnerLabel.setTextFill(Color.RED);
        winnerLabel.setText("Congratulations " + players[i].getName() + " You Won The Game!");
        winnerLabel.layoutXProperty().bind(root.widthProperty().subtract(winnerLabel.widthProperty()).divide(2));
        winnerLabel.layoutYProperty().bind(root.heightProperty().divide(2));
        root.getChildren().add(winnerLabel);
        return true;
      }
    }
    return false;
  }

  /**
   * Creates all players for the game
   */
  static public void initializePlayers(int numHuman, int numAI, String[] names){

    numPlayers = numHuman + numAI;
    players = new Player[numPlayers];
    for(int i=0; i < numHuman; i++){
      players[i] = new Player(names[i]);
    }
    //for(; i < numPlayers; i++){
    //  players[i] = new AIPlayer();
    //}
  }

  /**
   * getter for number of players
   */
  static public int getNumPlayers(){
    return numPlayers;
  }

  /**
   * Switches turn to next player
   */
  static public void nextTurn(){
    turnIndex++;
    turnIndex %= numPlayers;
    currentPlayer = players[turnIndex];
    playerTurnLabel.setText(currentPlayer.getName() + "'s Turn");

    if(turn){
      calcDistributeUnits();
    }
  }

  /**
   * initializes turn order randomly
   */
  static public void initializeTurn(){

    // sleep for 1 second
    /*
    try{
      Thread.sleep(1000);
    } catch (Exception e) {
      System.out.println(e);
    }
    */

    Random rand = new Random();
    turnIndex = rand.nextInt(numPlayers);
    currentPlayer = players[turnIndex];
    firstTurn = currentPlayer;
    firstTurnIndex = turnIndex;

  }

  /**
   * calculates and distributes new units at beginning of turn
   */
  static private void calcDistributeUnits(){
    int numNewUnits = 3;
    int numUnits;
    int userChoice;
    ArrayList<Country> countriesOwned;
    Country country;

    root.getChildren().remove(turnHBox);
    root.getChildren().remove(getMapPane());
    root.getChildren().add(getMapPane());

    countriesOwned = currentPlayer.getCountriesOwned();

    if(countriesOwned.size() > numNewUnits){
      numNewUnits = countriesOwned.size();
    }
    currentPlayer.setAvailableUnits(numNewUnits);

    gainedUnitsLabel.setText(currentPlayer.getName() + " Select Country To Place Gained Units! (" + currentPlayer.getAvailableUnits() + " available)");
    root.getChildren().add(gainedUnitsLabel);
    
    attacking = false;
    fortify = false;
    distributeUnits = false;
    placeUnits = true;

  }

  /**
   * initiates the combat
   */
  static private void startAttack(Country fromCountry, Country toCountry){
    Combat combat = new Combat(fromCountry, toCountry);
    setPane(combat.getPane(), getMapPane());
    toCountry = null;
    fromCountry = null;
    attacking = false;
  }

  /**
   * Main function builds the stage for the game
   */
  @Override
  public void start(Stage primaryStage){
    int resX;
    int resY;
    double gapSize, centerX, centerY;

    /* Create the game manager */
    initializeMap();


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
        attacking = true;
        distributeUnits = false;
        fortify = false;
        toCountry = null;
        fromCountry = null;
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
        fortify = true;
        distributeUnits = false;
        attacking = false;
        toCountry = null;
        fromCountry = null;
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
        nextTurn();
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
        if(numUnits < fromCountry.getUnits()){
          root.getChildren().remove(getMapPane());
          root.getChildren().remove(countrySelectionLabel);
          root.getChildren().remove(numUnitsTextField);
          root.getChildren().remove(confirmButton);
          root.getChildren().remove(cancelButton);
          currentPlayer.moveUnits(fromCountry, toCountry, numUnits);
          nextTurn();
          //nextPane();
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
        attacking = false;
        fortify = false;
        toCountry = null;
        fromCountry = null;
        getMap().hideNeighbours();
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
            autoSetup();
            return;
          }
          PlayerMenu playerMenu = new PlayerMenu();
          Pane playerPane = playerMenu.getPane();
          initializeBoard = new InitializeBoard();
          Pane initializeBoardPane = initializeBoard.getPane();
          setPane(playerPane, initializeBoardPane);
          //primaryStage.setScene(createAccountScene);
        }
    });
    menuPane.getChildren().add(startGame);

    /* Create the stage */
    ivBackground.toBack(); 
		Scene scene = new Scene(root, resX, resY);
    //scene.getStylesheets().add("css_styles.css");
		primaryStage.setTitle("RISK Game");
		primaryStage.setScene(scene);
    primaryStage.setResizable(false);
	  primaryStage.show();

    

  }

  /**
   * automatically sets up the game so that one player owns majority
   * used for testing
   */
  static private void autoSetup(){
    String[] names = new String[2];
    names[0] = "Alice";
    names[1] = "Bob";
    initializePlayers(2,0, names);
    initializeTurn();

    ArrayList<Country> countries;
    countries = MainMenu.getMap().getCountries();
    for(int i=0; i < countries.size()-1;i++){
      currentPlayer.setAvailableUnits(5);
      countries.get(i).setOwner(currentPlayer);
      currentPlayer.placeUnits(countries.get(i), 5);
    }

    nextTurn();
    turn = true;
    countries.get(countries.size()-1).setOwner(currentPlayer);
    currentPlayer.setAvailableUnits(5);
    currentPlayer.placeUnits(countries.get(countries.size()-1), 5);
    nextPane = getMapPane();
    nextPane();
  }
}

