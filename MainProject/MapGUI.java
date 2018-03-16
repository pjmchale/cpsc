// package map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.*;
import javafx.scene.layout.Pane;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.*;
import javafx.scene.*;
import javafx.scene.effect.ColorAdjust;
import javafx.beans.binding.Bindings;
import javafx.scene.shape.Rectangle; 
import javafx.scene.shape.Circle;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.Label;
import javafx.scene.paint.*;

public class MapGUI {

	private Pane root = new Pane();

	/**
	 * Used to get the pane
	 * @return the root pane displaying the maps
	*/
	public Pane getPane() {
		return root;
	}

	MapGUI() {

		// Add the connections
		String imagePathConnections = "mapAssets/Connections.png";
		Image imageConnections = new Image(imagePathConnections);
		ImageView imageViewConnections = new ImageView();
		imageViewConnections.setImage(imageConnections);
		imageViewConnections.setPreserveRatio(true);
		imageViewConnections.setSmooth(true);
		imageViewConnections.setLayoutX(0);
		imageViewConnections.setLayoutY(0);
		imageViewConnections.toFront();

		root.getChildren().add(imageViewConnections);

		// Add the boarders
		String imagePathBoarders = "mapAssets/Boarders.png";
		Image imageBoarders = new Image(imagePathBoarders);
		ImageView imageViewBoarders = new ImageView();
		imageViewBoarders.setImage(imageBoarders);
		imageViewBoarders.setPreserveRatio(true);
		imageViewBoarders.setSmooth(true);
		imageViewBoarders.setLayoutX(0);
		imageViewBoarders.setLayoutY(0);
		imageViewBoarders.toFront();

		root.getChildren().add(imageViewBoarders);
	}

	public void showLegend() {
		// Get the players and create a rectange background
		Player[] players = MainGUI.getAllPlayers();

		double backgroundHeight = players.length*30 + 10;
		Rectangle background = new Rectangle(10, 310, 140, backgroundHeight);
		background.setFill(Color.rgb(255, 255, 255, 0.9));
		background.setArcHeight(10);
	    background.setArcWidth(10);

	    DropShadow backgroundShadow = new DropShadow();
	    backgroundShadow.setWidth(1);
	    backgroundShadow.setHeight(1);
	    backgroundShadow.setOffsetX(1);
	    backgroundShadow.setOffsetY(1);
	    backgroundShadow.setRadius(10);
	    background.setEffect(backgroundShadow);

	    root.getChildren().add(background);

	    int corY = 320;

	    for (Player i: players) {
	    	// Create a name label
	    	Label name = new Label(i.getName());
			name.setFont(Font.font("Arial", FontWeight.BOLD, 14));
			name.setLayoutX(20);
			name.setLayoutY(corY);

	    	// Create a circle icon dot
	    	Circle colorIcon = new Circle();
			colorIcon.setCenterX(125);
			colorIcon.setCenterY(corY+5);
			colorIcon.setRadius(10);
			colorIcon = iconBackgroundColor(i.getId(), colorIcon);

			// Add these to the view
			root.getChildren().add(name);
			root.getChildren().add(colorIcon);

			corY += 30;
	    }

	}

	/**
	 * Used to get the countries
	 * @param The player ID number to get the color
	 * @param The circle to change the color of
	 * @return The modified circle
	*/
	public Circle iconBackgroundColor(int playerNumber, Circle circle) {
		int R = 0;
		int G = 0;
		int B = 0;

		switch(playerNumber) {
			case 1:
				// Green RGB(178, 255, 102)
				R = 178;
				G = 255;
				B = 102;
			break;
			case 2:
				// Red RGB(255, 51, 51)
				R = 255;
				G = 51;
				B = 51;
			break;
			case 3:
				// Orange RGB(255, 148, 102)
				R = 255;
				G = 148;
				B = 102;
			break;
			case 4:
				// Purple RGB(120, 131, 239)
				R = 120;
				G = 131;
				B = 239;
			break;
			case 5:
				// Brown RGB(128, 56, 25)
				R = 128;
				G = 56;
				B = 25;
			break;
			case 6:
				// Ugly Purple RGB(128, 25, 117)
				R = 128;
				G = 25;
				B = 117;
			break;
			default:
				// White RGB(255, 255, 255)
				R = 255;
				G = 255;
				B = 255;
		}

		double alphaValue = 1.0;

		circle.setFill(Color.rgb(R, G, B, alphaValue));

		return circle;
	}

}