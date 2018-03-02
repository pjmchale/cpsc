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
import java.util.*;

public class InitializeBoard{
  private boolean clicked;

  public Pane getPane(){
    return buildPane();
  } 
  

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
    Pane initializeBoardPane = new Pane();
    initializeBoardPane.setPrefSize(resX, resY);
    initializeBoardPane.setLayoutX(0);
    initializeBoardPane.setLayoutY(0);

    /* Label displaying who gets first turn*/
    Label playerTurnLabel = new Label("");
    playerTurnLabel.setTextFill(Color.RED);
    playerTurnLabel.setFont(new Font("Times New Roman Bold", 18));
    playerTurnLabel.layoutXProperty().bind(initializeBoardPane.widthProperty().subtract(playerTurnLabel.widthProperty()).divide(2));
    playerTurnLabel.setLayoutY(centerY+50);
    initializeBoardPane.getChildren().add(playerTurnLabel);

    /* Pane for country selection phase */
    Pane countrySelectionPane = new Pane();
    countrySelectionPane.setPrefSize(resX, resY);
    countrySelectionPane.setLayoutX(0);
    countrySelectionPane.setLayoutY(0);

    /* Label indicating to select the country */
    Label selectedCountryLabel = new Label("");
    selectedCountryLabel.setTextFill(Color.RED);
    selectedCountryLabel.setFont(new Font("Times New Roman Bold", 18));
    selectedCountryLabel.setLayoutX(50);
    selectedCountryLabel.setLayoutY(50);
    countrySelectionPane.getChildren().add(selectedCountryLabel);




    /* Randomize turn order label */
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
            countrySelectionPane.getChildren().add(MainMenu.getMapPane());
            selectedCountryLabel.setText(MainMenu.getCurrentPlayer().getName() + " Please Choose A Country To Own");
            initializeBoardPane.getChildren().add(countrySelectionPane);
            //MainMenu.nextPane();
          }else {
            randomizeTurnButton.setText("Click to continue ...");
            clicked = true;
          }
          MainMenu.gameManager.initializeTurn();
          playerTurnLabel.setText(MainMenu.getCurrentPlayer().getName() + " Goes First!");

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




}
