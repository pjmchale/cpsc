/**
 * This class (called by MainMenu) initialized the board.
 * it allows player to choose the countries they own and
 * distribute the initial units
 */


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import java.util.*;

public class InitializeBoard{
  private boolean clicked;
  private Pane initializeBoardPane;
  private Pane countrySelectionPane;
  private Label playerTurnLabel;
  private Label selectedCountryLabel;
  Player currPlayer;


  /**
   * returns the pane for use by MainMenu
   */
  public Pane getPane(){
    return buildPane();
  } 

  /**
   * builds the actual pane for initializing the board 
   */
  private Pane buildPane(){
    int resX;
    int resY;
    int centerX;
    int centerY;

    /* Set screen size/resolution */
    resX = 960;
    resY = 600;
    centerX = resX/2;
    centerY = resY/2;

    /* pane for initialize board units */
    initializeBoardPane = new Pane();
    initializeBoardPane.setPrefSize(resX, resY);
    initializeBoardPane.setLayoutX(0);
    initializeBoardPane.setLayoutY(0);

    /* Label displaying who gets first turn*/
    playerTurnLabel = new Label("");
    playerTurnLabel.setTextFill(Color.RED);
    playerTurnLabel.setFont(new Font("Times New Roman Bold", 18));
    playerTurnLabel.layoutXProperty().bind(initializeBoardPane.widthProperty().subtract(playerTurnLabel.widthProperty()).divide(2));
    playerTurnLabel.setLayoutY(centerY+50);
    initializeBoardPane.getChildren().add(playerTurnLabel);

    /* Pane for country selection phase */
    countrySelectionPane = new Pane();
    countrySelectionPane.setPrefSize(resX, resY);
    countrySelectionPane.setLayoutX(0);
    countrySelectionPane.setLayoutY(0);

    /* Label indicating to select the country */
    selectedCountryLabel = new Label("");
    selectedCountryLabel.setTextFill(Color.RED);
    selectedCountryLabel.setFont(new Font("Times New Roman Bold", 25));
    selectedCountryLabel.layoutXProperty().bind(countrySelectionPane.widthProperty().subtract(selectedCountryLabel.widthProperty()).divide(2));
    //selectedCountryLabel.setLayoutX(50);
    selectedCountryLabel.setLayoutY(35);
    countrySelectionPane.getChildren().add(selectedCountryLabel);

    /* Button for continuing after selection */
    Button continueSelectionButton = new Button("Continue ...");
    continueSelectionButton.setTextFill(Color.RED);
    continueSelectionButton.setFont(new Font("Times New Roman Bold", 18));
    continueSelectionButton.layoutXProperty().bind(countrySelectionPane.widthProperty().subtract(continueSelectionButton.widthProperty()).divide(2));
    continueSelectionButton.layoutYProperty().bind(countrySelectionPane.heightProperty().subtract(50));
    continueSelectionButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          /* if all units distributed we're done */
          if(allUnitsDistributed()){
              MainMenu.setDistributeUnits(false);
              initializeBoardPane.getChildren().clear();
              MainMenu.nextPane();
          }
          
          if(MainMenu.getCurrentPlayer() == currPlayer){
              return;
          }else{
            if(!MainMenu.allCountriesOwned()){
              currPlayer = MainMenu.getCurrentPlayer();
              selectedCountryLabel.setText(currPlayer.getName() + " Please Choose A Country To Take Ownership");
              MainMenu.setDistributeUnits(true);
            }else{
              currPlayer = MainMenu.getCurrentPlayer();
              selectedCountryLabel.setText(currPlayer.getName() + " Please Choose A Country To Place Unit (" + MainMenu.getCurrentPlayer().getAvailableUnits() + " available)");
              MainMenu.setDistributeUnits(true);
            }

          }
        }
    });
    countrySelectionPane.getChildren().add(continueSelectionButton);


    /* Randomize turn order button */
    clicked = false;
    Button randomizeTurnButton = new Button("Click to Randomize Turn Order....");
    //randomizeTurnButton.setTextFill(Color.RED);
    randomizeTurnButton.setFont(new Font("Times New Roman Bold", 18));
    randomizeTurnButton.layoutXProperty().bind(initializeBoardPane.widthProperty().subtract(randomizeTurnButton.widthProperty()).divide(2));
    randomizeTurnButton.setLayoutY(centerY);
    randomizeTurnButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          if(clicked){
            initializeBoardPane.getChildren().clear();
            distributeUnits();
            return;
          }else {
            randomizeTurnButton.setText("Click to continue ...");
            clicked = true;
          }
          MainMenu.initializeTurn();
          currPlayer = MainMenu.getCurrentPlayer();
          playerTurnLabel.setText(currPlayer.getName() + " Goes First!");

          /* sleep for 1 second */
          /*
          try{
            Thread.sleep(1000);
          } catch (Exception e) {
            System.out.println(e);
          }
          */

        }
    });
    initializeBoardPane.getChildren().add(randomizeTurnButton);


    return initializeBoardPane;
  }

  /**
   * Method for initial distribution of units on the board
   */
  private void distributeUnits(){
    int i;
    int numUnits;
    int userChoice;
    Player players[];
    
    /* CHANGE THIS */
    int numCountries = 24;
    switch(MainMenu.getNumPlayers()){
      case 2: 
        //numUnits = 7;
        numUnits = (numCountries/MainMenu.getNumPlayers()) + 2;
        break;
      case 3: 
        //numUnits = 7;
        numUnits = (numCountries/MainMenu.getNumPlayers()) + 2;
        break;
      case 4: 
        //numUnits = 5;
        numUnits = (numCountries/MainMenu.getNumPlayers()) + 2;
        break;
      case 5:
        numUnits = (numCountries/MainMenu.getNumPlayers()) + 2;
      case 6:
        numUnits = (numCountries/MainMenu.getNumPlayers()) + 2;
      default: 
        numUnits = 5;

      /*
      case 2: numUnits = 40;
      case 3: numUnits = 35;
      case 4: numUnits = 30;
      default: numUnits = 30;
      */

    }

    /* Set initial number of units for each player */
    players = MainMenu.getAllPlayers();
    for(i=0; i < players.length; i++){
      players[i].setAvailableUnits(numUnits);
    }

    countrySelectionPane.getChildren().add(MainMenu.getMapPane());
    MainMenu.getMapPane().toBack();
    selectedCountryLabel.setText(currPlayer.getName() + " Please Choose A Country To Take Ownership");
    initializeBoardPane.getChildren().add(countrySelectionPane);
    MainMenu.setDistributeUnits(true);

  }

  /**
   * checks if all units for all players have been distributed
   * @return true if all units have been distributed, false otherwise
   */
   private boolean allUnitsDistributed(){
     Player[] players;
     players = MainMenu.getAllPlayers();

     for(int i=0; i < players.length ; i++){
       if(players[i].getAvailableUnits() > 0){
         return false;
       }
     }

     return true;
   }


}
