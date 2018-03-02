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
  static private Pane currentPane;
  static private Pane nextPane;
  static private Pane root;
  static private Pane turnPane;
  //static public GameManager gameManager;

  static private InitializeBoard initializeBoard;
  static private Label countrySelectionLabel;

  static private int numPlayers;
  static private Player[] players;
  static private Map map;
  static private Player currentPlayer;
  static private Country countryClicked;
  static private Country toCountry;
  static private Country fromCountry;
  static private int turnIndex;
  static private Player firstTurn;
  static int firstTurnIndex;
  static boolean distributeUnits;
  static boolean attacking;
  static boolean fortify;

  /**
   * moves the scene to the next pane
   */
  static public void nextPane(){
    setPane(nextPane);
    if(nextPane == getMapPane()){
      root.getChildren().add(turnPane);
    }
    nextPane = getMapPane();
  }

  /* sets the scene to the input pane
   * 
   */
  static private void setPane(Pane pane){
    root.getChildren().remove(currentPane);
    if(currentPane == getMapPane()){
      root.getChildren().remove(turnPane);
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
      root.getChildren().remove(turnPane);
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
        System.out.println(currentPlayer.getName());
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
        if(countryClicked.getOwner() == currentPlayer){
          fromCountry = countryClicked;
          countrySelectionLabel.setText("Please Select Country To Attack");
        }else{
          return;
        }
      }else{
        if(countryClicked.getOwner() != currentPlayer && countryClicked.isNeighbour(fromCountry)){
          toCountry = countryClicked;
          root.getChildren().remove(getMapPane());
          root.getChildren().remove(countrySelectionLabel);
          startAttack(toCountry, fromCountry);
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
          countrySelectionLabel.setText("Please Select Country To Move Units To");
        }else{
          return;
        }
      }else if(toCountry == null)
        if(countryClicked.getOwner() == currentPlayer && countryClicked.isNeighbour(fromCountry)){
          toCountry = countryClicked;
          countrySelectionLabel.setText("How Many Units Would You Like To Move? (" + fromCountry.getUnits()-1 + " available)");
                  }else{
          return;
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
   */
  static public void setDistributeUnits(boolean state){
    distributeUnits = state;
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
   * initiates the combat
   */
  static private void startAttack(Country toCountry, Country fromCountry){
    //Combat combat = new Combat(toCountry, fromCountry);
    //setPane(combat.getPane(), getMapPane());
    toCountry = null;
    fromCountry = null;
    attacking = false;
  }

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

    /* initialize turn pane */
    turnPane = new Pane();
    turnPane.setPrefSize(resX, resY);
    turnPane.setLayoutX(0);
    turnPane.setLayoutY(0);
    turnPane.getChildren().add(getMapPane());

    /* VBox for user selection during turn */
    HBox selectionHBox = new HBox();
    selectionHBox.layoutXProperty().bind(turnPane.widthProperty().subtract(selectionHBox.widthProperty()).divide(2));
    selectionHBox.setLayoutY(30);

    /* Country selection label */
    countrySelectionLabel = new Label("");
    countrySelectionLabel.setFont(new Font("Times New Roman Bold", 20));
    countrySelectionLabel.setTextFill(Color.RED);
    countrySelectionLabel.layoutXProperty().bind(root.widthProperty().subtract(countrySelectionLabel.widthProperty()).divide(2));
    countrySelectionLabel.setLayoutY(30);

    /* Number of units text box */
    TextField numUnitsTextField = new TextField();
    numUnitsTextField.layoutXProperty().bind(root.widthProperty().divide(2).add(countrySelectionLabel.widthProperty()));
    numUnitsTextField.setLayoutY(30);


    /* attack selection button */
    Button attackButton = new Button("Attack");
    attackButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        attacking = true;
        toCountry = null;
        fromCountry = null;
        root.getChildren().remove(turnPane);
        countrySelectionLabel.setText("Please Select Country To Attack From");
        root.getChildren().add(countrySelectionLabel);
      }
    });


    /* fortify selection button */
    Button fortifyButton = new Button("Fortify");
    fortifyButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        fortify = true;
        toCountry = null;
        fromCountry = null;
        root.getChildren().remove(turnPane);
        countrySelectionLabel.setText("Please Select Country To Move Units From");
        root.getChildren().add(countrySelectionLabel);

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

    Button confirmButton = new Button("Confirm");
    confirmButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        root.getChildren().remove(getMapPane());
        root.getChildren().remove(countrySelectionLabel);
        currentPlayer.moveUnits(toCountry, fromCountry, ?*jfdsiajdfsiod)
        nextTurn();
      }
    });

    
    selectionHBox.getChildren().addAll(attackButton, fortifyButton, endTurnButton);
    turnPane.getChildren().add(selectionHBox);

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
}

