package MapStage;

import GameEngine.*;
import PlayerPackage.*;
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

public class CountryGUI {

	private Pane root;
	private ImageView imageView;
	private boolean clickable = true;
	private infoView popUp;
	private String name = "";
	private int countryID;
	private Player owner;
	private int numUnits;
	private String ownerName;
	private int ownerID;
	private Country countryLogic;

	/**
	 * Constructor to add the country image to the view, set the id, neighbours, name, ownerID, ownerName, amount of units, and pane
	 * @param The country's name
	 * @param The number of units
	 * @param The owners name
	 * @param The owners id
	 * @param The country that holds the logic for this GUI
	 * @param Wether to build the GUI or not
	*/
	CountryGUI(String newName, int newNumUnits, String newOwnerName, int newOwnerID, Country country) {
		WorldMapGUI mapGUI = MainGUI.getMapGUI();
		root = mapGUI.getPane();
		name = newName;
		numUnits = newNumUnits;
		ownerName = newOwnerName;
		ownerID = newOwnerID;
		countryLogic = country;

		String path = "mapAssets/"+name+".png";
		Image img = new Image(path);
		imageView = new ImageView();
		imageView.setImage(img);
		imageView.setOpacity(1);
		imageView.setPreserveRatio(true);
		imageView.setSmooth(true);
		imageView.setLayoutX(0);
		imageView.setLayoutY(0);

		// Based on hover or click create or destroy the infoView
		imageView.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
		     public void handle(MouseEvent event) {
		     	if (clickable){
		     		clearInfoView();
		     		pullDateForInfoView();
			        popUp = new infoView(event.getX()+10, event.getY()+10, ownerName, numUnits, name, ownerID, root);    
			    }
			    event.consume();
		     }
		});
		imageView.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
		     public void handle(MouseEvent event) {
		     	clearInfoView();
			    event.consume();
		     }
		});
		imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	if (clickable){
		    		// Tell the MainGUI the country was clicked, remove all popUps and show a new one
			        MainGUI.setCountryClicked(countryLogic);
			        clearInfoView();
			        pullDateForInfoView();
			        popUp = new infoView(event.getX()+10, event.getY()+10, ownerName, numUnits, name, ownerID, root);
			    }
		        event.consume();
		    }
		});

		root.getChildren().add(colorView(ownerID, imageView));
	}


	/**
	 * Used to get the color of a country imageView
	 * @param the player number to determine what color to use
	 * @param the imageView to modify
	 * @return the modified imageView
	*/
	public ImageView colorView(int playerNumber, ImageView iV){
		double con = 0;
		double hue = 0;
		double sat = 0;
		double bri = 0;

		switch(playerNumber) {
			case 1:
				// Green
				con = 0;
				hue = 0.5;
				sat = 0.6;
				bri = 0;
			break;
			case 2:
				// Red
				con = 0;
				hue = 0;
				sat = 0.8;
				bri = 0;
			break;
			case 3:
				// Orange
				con = 0;
				hue = 0.1;
				sat = 0.6;
				bri = 0;
			break;
			case 4:
				// Purple
				con = 0;
				hue = -0.7;
				sat = 0.5;
				bri = 0;
			break;
			case 5:
				// Brown
				con = 0;
				hue = 0.1;
				sat = 0.8;
				bri = -0.5;
			break;
			case 6:
				// Magenta
				con = 0;
				hue = -0.3;
				sat = 0.8;
				bri = -0.5;
			break;
		}
		
		ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setContrast(con);
		colorAdjust.setHue(hue);
		colorAdjust.setBrightness(bri);
		colorAdjust.setSaturation(sat);

		iV.setEffect(colorAdjust);

		return iV;
	}


	/**
	 * Used to clear in info pop up view
	*/
	public void clearInfoView() {
 		if (popUp != null){
	        popUp.clear();
	        popUp = null;
	    }
	}

	/**
	 * Will update the colors of the country
	 * @param The owners name
	 * @param The owners ID number
	*/
	public void updateOwnerVisual(String ownerName, int ownerID) {
		colorView(ownerID, imageView);
	}

	/**
	 * Sets the countries opacity
	 * @param The image opactiy to set
	*/
	public void setImageOpacity(double alpha){
		imageView.setOpacity(alpha);
	}

	/**
	 * Will pull data to update the hover view from the logic country
	*/
	public void pullDateForInfoView() {
		ownerName = countryLogic.getOwnerName();
		numUnits = countryLogic.getUnits();
		name = countryLogic.getName();
		ownerID = countryLogic.getOwnerID();
	}

}




