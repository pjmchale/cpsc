/**
 * This class (called by MainGUI) initialized the board.
 * it allows player to choose the countries they own and
 * distribute the initial units
 */

package GameEngine;
import PlayerPackage.*;
import CombatEngine.*;
import MapStage.*;


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
  GameManager gameManager;
  private Pane initializeBoardPane;
  private Pane countrySelectionPane;
  private Label playerTurnLabel;
  private Label selectedCountryLabel;
  Player currPlayer;


  /**
   * returns the pane for use by MainGUI
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

    /* get the game manager */
    gameManager = MainGUI.getGameManager();

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

    
    /* Randomize turn order button */
    clicked = false;
    Button randomizeTurnButton = new Button("Click to Randomize Turn Order....");
    randomizeTurnButton.setStyle("-fx-font: 15 arial; -fx-base: #ee2211;");
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
          gameManager.initializeTurn();
          currPlayer = gameManager.getCurrentPlayer();
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
   * Function for checking if taking ownership fo countries at beginning
   * of game has completed (called by GameManager)
   */
  public void continueCountrySelection(){
    /* if all units distributed we're done */
    if(allUnitsDistributed()){
        gameManager.clearState();
        initializeBoardPane.getChildren().clear();
        gameManager.setTurnState();
        MainGUI.nextPane();
        gameManager.nextTurn();
        return;
    }
    
    /* if AI allow them to place unit or take country */
    if(gameManager.getCurrentPlayer().getPlayerType().equals("AI")){
      return;
    }
    
    if(gameManager.getCurrentPlayer() == currPlayer){
        return;
    }else{
      if(!gameManager.allCountriesOwned()){
        currPlayer = gameManager.getCurrentPlayer();
        selectedCountryLabel.setText(currPlayer.getName() + " Please Choose A Country To Take Ownership");
        gameManager.setDistributeUnits();
      }else{
        currPlayer = gameManager.getCurrentPlayer();
        selectedCountryLabel.setText(currPlayer.getName() + " Please Choose A Country To Place Unit (" + gameManager.getCurrentPlayer().getAvailableUnits() + " available)");
        gameManager.setDistributeUnits();
      }

    }
  }


  /**
   * Method for initial distribution of units on the board
   * calculates number of units each player gets at beginning of game
   */
  private void distributeUnits(){
    int i;
    int numUnits;
    int userChoice;
    Player players[];
    
    /* CHANGE THIS */
    int numCountries = 24;
    switch(gameManager.getNumPlayers()){
      case 2: 
        //numUnits = 7;
        numUnits = (numCountries/gameManager.getNumPlayers()) + 2;
        break;
      case 3: 
        //numUnits = 7;
        numUnits = (numCountries/gameManager.getNumPlayers()) + 2;
        break;
      case 4: 
        //numUnits = 5;
        numUnits = (numCountries/gameManager.getNumPlayers()) + 2;
        break;
      case 5:
        numUnits = (numCountries/gameManager.getNumPlayers()) + 2;
      case 6:
        numUnits = (numCountries/gameManager.getNumPlayers()) + 2;
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
    players = gameManager.getAllPlayers();
    for(i=0; i < players.length; i++){
      players[i].setAvailableUnits(numUnits);
    }

    countrySelectionPane.getChildren().add(MainGUI.getMapPane());
    MainGUI.getMapPane().toBack();
    selectedCountryLabel.setText(currPlayer.getName() + " Please Choose A Country To Take Ownership");
    initializeBoardPane.getChildren().add(countrySelectionPane);
    gameManager.setDistributeUnits(true);
    if(gameManager.getCurrentPlayer().getPlayerType().equals("AI")){
      gameManager.AITurn();
    }

  }

  /**
   * checks if all units for all players have been distributed
   * @return true if all units have been distributed, false otherwise
   */
   private boolean allUnitsDistributed(){
     Player[] players;
     players = gameManager.getAllPlayers();

     for(int i=0; i < players.length ; i++){
       if(players[i].getAvailableUnits() > 0){
         return false;
       }
     }

     return true;
   }


}
