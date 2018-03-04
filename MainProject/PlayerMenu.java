/**
 * This class (called by MainMenu.java) builds the players
 * for the RISK game. It receives the number of human and AI 
 * players from the user and creates them
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



public class PlayerMenu{
  int numPlayers;
  int currPlayer;
  int numAIPlayers;
  int maxAIPlayers;
  Pane playerMenu;
  int resX, resY;
  int centerX, centerY;


  /**
   * returns the pane for use by MainMenu
   */
  public Pane getPane(){
    return buildPane();
  }

  /**
   * creates the pane
   */
  private Pane buildPane(){

    /* Set screen size/resolution */
    resX = 960;
    resY = 600;
    centerX = resX/2;
    centerY = resY/2;

    /* Pane for the player selection menu */
    playerMenu = new Pane();
    playerMenu.setPrefSize(resX, resY);

    /* Enter number of players label */
    Label numPlayerLabel = new Label();
    numPlayerLabel.setText("Please Enter Number of Human Players (2-6)");
    numPlayerLabel.setFont(new Font("Times New Roman Bold", 18));
    numPlayerLabel.setTextFill(Color.RED);
    numPlayerLabel.layoutXProperty().bind(playerMenu.widthProperty().subtract(numPlayerLabel.widthProperty()).divide(3));
    numPlayerLabel.setLayoutY(centerY);
    playerMenu.getChildren().add(numPlayerLabel);

    /* number of players text field*/
    TextField numPlayersTextField= new TextField();
    numPlayersTextField.layoutXProperty().bind(numPlayerLabel.layoutXProperty().add(numPlayerLabel.widthProperty()).add(5));
    numPlayersTextField.setLayoutY(centerY);
    numPlayersTextField.setStyle("-fx-text-fill: black;");
    playerMenu.getChildren().add(numPlayersTextField);

    /* confirm AI players button */
    Button confirmAIPlayersButton = new Button(" Continue ");
    confirmAIPlayersButton.layoutXProperty().bind(playerMenu.widthProperty().subtract(confirmAIPlayersButton.widthProperty()).divide(2));
    confirmAIPlayersButton.setLayoutY(centerY + 50);
    confirmAIPlayersButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          numPlayersTextField.setStyle("-fx-text-fill: black;");
          numAIPlayers = Integer.parseInt(numPlayersTextField.getText());
        } catch (NumberFormatException e) {
          numPlayersTextField.setStyle("-fx-text-fill: red;");
          return;
        }

        if(numAIPlayers >= 0 && numAIPlayers <= maxAIPlayers){
          playerMenu.getChildren().clear();
          createPlayers();
          return;
        }else{
          numPlayersTextField.setStyle("-fx-text-fill: red;");
        }
      }
    });

    /* confirm human players button */
    Button confirmPlayersButton = new Button(" Continue ");
    confirmPlayersButton.layoutXProperty().bind(playerMenu.widthProperty().subtract(confirmPlayersButton.widthProperty()).divide(2));
    confirmPlayersButton.setLayoutY(centerY + 50);
    confirmPlayersButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          numPlayersTextField.setStyle("-fx-text-fill: black;");
          numPlayers = Integer.parseInt(numPlayersTextField.getText());
        } catch (NumberFormatException e) {
          numPlayersTextField.setStyle("-fx-text-fill: red;");
          return;
        }

        if(numPlayers >= 2 && numPlayers <= 6){
          maxAIPlayers = 6-numPlayers;
          numPlayerLabel.setText("Please Enter Number of AI Players (max " + maxAIPlayers + ")");
          playerMenu.getChildren().remove(confirmPlayersButton);
          playerMenu.getChildren().add(confirmAIPlayersButton);
          numPlayersTextField.setText("");
        } else if (numPlayers == 4){
          numAIPlayers = 0;
        }else{
          numPlayersTextField.setStyle("-fx-text-fill: red;");
        }
      }
    });
    playerMenu.getChildren().add(confirmPlayersButton);

    return playerMenu;

  }

  /* Create number of players selected by user */
  private void createPlayers(){
    Label playerNameLabel = new Label();
    playerNameLabel.setText("Player 1 Please Enter Your Name");
    playerNameLabel.setFont(new Font("Times New Roman Bold", 18));
    playerNameLabel.setTextFill(Color.RED);
    playerNameLabel.layoutXProperty().bind(playerMenu.widthProperty().subtract(playerNameLabel.widthProperty()).divide(3));
    playerNameLabel.setLayoutY(centerY);
    playerMenu.getChildren().add(playerNameLabel);
    
    /* player name text field*/
    TextField playerNameTextField= new TextField();
    playerNameTextField.layoutXProperty().bind(playerNameLabel.layoutXProperty().add(playerNameLabel.widthProperty()).add(5));
    playerNameTextField.setLayoutY(centerY);
    playerNameTextField.setStyle("-fx-text-fill: black;");
    playerMenu.getChildren().add(playerNameTextField);

    /* Create Player Button */
    currPlayer = 0;
    String[] playerNames = new String[numPlayers];
    Button createPlayerButton = new Button(" Create Player ");
    createPlayerButton.layoutXProperty().bind(playerMenu.widthProperty().subtract(createPlayerButton.widthProperty()).divide(2));
    createPlayerButton.setLayoutY(centerY + 50);
    createPlayerButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {

        /* get input name and capitalize first letter */
        String name = playerNameTextField.getText();
        playerNames[currPlayer] = name.substring(0, 1).toUpperCase() + name.substring(1);
        currPlayer++;
        playerNameTextField.setText("");
        playerNameLabel.setText("Player " + (currPlayer+1) + " Please Enter Your Name");

        if(currPlayer == numPlayers){
          MainMenu.initializePlayers(numPlayers, numAIPlayers, playerNames);
          playerMenu.getChildren().clear();
          MainMenu.nextPane();
        }
      }
    });
    playerMenu.getChildren().add(createPlayerButton);

  }


}

