package MapStage;


import PlayerPackage.*;
import GameEngine.*;
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

public class WorldMapGUI {

	private Pane root = new Pane();
	private ImageView imageViewTurnIcon;
	private ArrayList<Text> nameLabels = new ArrayList<Text>();
	private  ArrayList<Circle> nameDots = new ArrayList<Circle>();

	/**
	 * Used to get the pane
	 * @return the root pane displaying the maps
	*/
	public Pane getPane() {
		return root;
	}

	/**
	 * Will build the GUI parts of the initial map
	 * The connections image and the boarders image
	*/
	public WorldMapGUI() {

		// Add the connections
		String imagePathConnections = "map_assets/Connections.png";
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
		String imagePathBoarders = "map_assets/Boarders.png";
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

	/**
	 * Will add the legend to the map
	 * Only called once all player are created
	*/
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
	    	Text name = new Text(i.getName());
			name.setFont(Font.font("Arial", FontWeight.BOLD, 14));
			name.setLayoutX(30);
			name.setLayoutY(corY+10);

	    	// Create a circle icon dot
	    	Circle colorIcon = new Circle();
			colorIcon.setCenterX(125);
			colorIcon.setCenterY(corY+5);
			colorIcon.setRadius(10);
			colorIcon = iconBackgroundColor(i.getId(), colorIcon);

			// Add these to the view
			root.getChildren().add(name);
			root.getChildren().add(colorIcon);

			nameLabels.add(name);
			nameDots.add(colorIcon);

			corY += 30;
	    }

	    // Add the turn icon in the correct spot
		imageViewTurnIcon = new ImageView();
		imageViewTurnIcon.setPreserveRatio(true);
		imageViewTurnIcon.setSmooth(true);
		imageViewTurnIcon.setLayoutX(15);
		imageViewTurnIcon.setLayoutY(351);
		imageViewTurnIcon.toFront();

		root.getChildren().add(imageViewTurnIcon);

	}

	/**
	 * Will show that a player is removed on the legend
	 * Will make the name red and strikethrough it
	 * @param The player to remove
	*/
	public void removePlayerFromLegend(Player playerToRemove) {
		int index = getPlayerIndex(playerToRemove);

		nameLabels.get(index).setStyle("-fx-strikethrough: true");
		nameLabels.get(index).setFill(Color.RED);
		nameLabels.get(index).setFont(Font.font("Arial", FontWeight.LIGHT, 14));

		nameDots.get(index).setOpacity(0.4);
	}

	/**
	 * Will move the turn icon 
	 * @param The current player's turn
	*/
	public void updateTurnIcon(Player currentPlayer) {
		int index = getPlayerIndex(currentPlayer);

	  	// Move the turn icon
	  	String imagePathTurnIcon = "map_assets/Turn_Icon.png";
		Image imageTurnIcon = new Image(imagePathTurnIcon);
	  	imageViewTurnIcon.setImage(imageTurnIcon);
	  	imageViewTurnIcon.toFront();
	    imageViewTurnIcon.setLayoutY(317 + (index*30));
	}

	/**
	 * Will get the index of a player in the legend
	 * @param The player to get the index of
	 * @return The index of the player
	*/
	public int getPlayerIndex(Player playerOfIndex) {
		Player[] players = MainGUI.getAllPlayers();
		int index = -1;

		for (int i = 0; (i < players.length) && (index == -1); i++) {
	        if (players[i] == playerOfIndex) {
	            index = i;
	        }
	    }
	    return index;
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