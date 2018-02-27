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

public class initializeBoard{

  public Pane getPane(){
    return buildPane();
  } 
  

  private void buildPane(){

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

    /* Randomize turn order label */
    Label randomizeTurnLabel = new Label("Randomizing Turn Order....");
    randomizeTurnLabel.setTextFill(Color.RED);
    randomizeTurnLabel.layoutXProperty().bind(initializeBoardPane.widthProperty().subtract(randomizeTurnLabel.widthProperty()).divide(2));
    randomizeTurnLabel.setLayoutY(centerY);
    initializeBoardPane.getChildren().add(randomizeTurnLabel);


    return initializeBoardPane;
  }




}
