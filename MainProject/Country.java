import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.Map;
import java.util.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.Group; 
import javafx.scene.effect.ColorAdjust;
import javafx.beans.binding.Bindings;
import javafx.scene.effect.*;
import javafx.scene.image.*;

public class Country {
 // extends Application{
	private String name = "";
	private int countryID;
	private Player owner;
	private int numUnits;
	private ArrayList<Integer> neighbours;
	private Pane root;
	// private Label amountOfUnitsLabel;
	// private Label ownerLabel;
	private ImageView imageView;
	private boolean clickable = true;
	private infoView popUp;
	private String ownerName;
	private int ownerID;
	/**
	 * Constructor to set the id, neighbours, and name
	*/
	Country(int id, ArrayList<Integer> newNeighbours, String newName, int newOwnerID, String newOwnerName, int newNumUnits, Pane newRoot){
		root = newRoot;
		name = newName;
		numUnits = newNumUnits;
		ownerName = newOwnerName;
		ownerID = newOwnerID;
		countryID = id;
		neighbours = newNeighbours;

		String path = "mapAssets/"+name+".png";
		Image img = new Image(path);
		imageView = new ImageView();
		imageView.setImage(img);
		imageView.setOpacity(1);
		imageView.setPreserveRatio(true);
		imageView.setSmooth(true);
		imageView.setLayoutX(0);
		imageView.setLayoutY(0);
		imageView.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
		     public void handle(MouseEvent event) {
		     	if (clickable){
		     		if (popUp != null){
				        popUp.clear();
				        popUp = null;
				    }
			        popUp = new infoView(event.getX()+10, event.getY()+10, ownerName, numUnits, name, ownerID, root);    
			    }
			    event.consume();
		     }
		});
		imageView.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
		     public void handle(MouseEvent event) {
		     	if (clickable){
		     		if (popUp != null){
				        popUp.clear();
				        popUp = null;
				    }
			    }
			    event.consume();
		     }
		});
		imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	if (clickable){
		    		if (popUp != null){
		    			popUp.clear();
				        popUp = null;
		    		}
			        MainMenu.setCountryClicked(Country.this);
			        popUp = new infoView(event.getX()+10, event.getY()+10, ownerName, numUnits, name, ownerID, root);
			    }
		        event.consume();
		    }
		});

		root.getChildren().add(colorView(ownerID, imageView));
	}

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

			case 5:
				// Color
				con = 0;
				hue = -0.7;
				sat = 0.5;
				bri = 0;

			case 6:
				// Color
				con = 0;
				hue = -0.7;
				sat = 0.5;
				bri = 0;
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
	 * Used to get the name of the country
	 * @return the name
	*/
	public String getName(){
		return name;
	}

	/**
	 * Used to set the name of the country
	 * @param a String to set the name
	*/
	public void setName(String newName){
		name = newName;
	}

	/**
	 * Used to add or remove units 
	 * @param an int to change the units by passing a positive or negative number
	*/
	public void addUnits(int units) {
		numUnits = numUnits + units;
		// amountOfUnitsLabel.setText("Units: "+numUnits);
	}

	/**
	 * Used to set the number of units
	 * @param an int to set the number of units
	*/
	public void setUnits(int units){
		numUnits = units;
		// Update the map here

	}

	/**
	 * Used to get the number of units in the country.
	 * @return the number of units
	*/
	public int getUnits(){
		return numUnits;
	}

	/**
	 * Used to set the owner of the country
	 * @param a Player to set the owner
	*/
	public void setOwner(Player player) {
		owner = player;
		ownerName = player.getName();
		ownerID = player.getId();
		colorView(ownerID, imageView);
		// ownerID = new Player(GameManager.getPlayers()).indexOf(player);
		// **********
	}

	/**
	 * Used to get the owner of the country
	 * @return the owner
	*/
	public Player getOwner(){
		return owner;
	}

	/**
	 * Used to set the id of the country
	 * @param an int to set the id
	*/
	public void setCountryID(int id) {
		countryID = id;
	}

	/**
	 * Used to get the id of the country
	 * @return the id
	*/
	public int getCountryID(){
		return countryID;
	}

	public ArrayList<Integer> getNeighbours(){
		return neighbours;
	}

	public void setClickable(boolean state){
		clickable = state;
		if (clickable) {
			imageView.setOpacity(1);
		} else {
			imageView.setOpacity(0.1);
		}
	}

	/**
	 * Used to check who the owner of the country is
	 * @param a Player to check if they are the owner
	 * @return a boolean if the player is the owner or not
	*/
	public boolean isOwner(Player player) {
		return owner == player;
	}

	/**
	 * Used to check if the country is a neighbour to another
	 * @param a Country to see if it is a neighbour to the current one
	 * @return a boolean if the country passed is a neighbour or not
	*/
	public boolean isNeighbour(Country country) {
		int possibleNeighbour = country.getCountryID();
		return neighbours.contains(possibleNeighbour);
	}




	/**
	 * Used to ask user how many units they want to send
	 * @return int the amount of units that have chosen to send
	*/
	public int selectUnitAmount(){
		System.out.println("How many units do you want to send? ");
		Scanner kb = new Scanner(System.in);
		int input = kb.nextInt();
		// int input;

		// /* Instructions label */
		// Label instructionsLabel = new Label("How many units do you want to send?");
		// instructionsLabel.setFont(Font.font("Courier New", 15));
		// root.getChildren().add(instructionsLabel);

		// /* Input text field */
		// TextField amountTextField = new TextField();
		// root.getChildren().add(amountTextField);

		// /* Submit Button */
		// Button submitButton = new Button("Submit");
		// submitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		// 	@Override
		//      public void handle(MouseEvent event) {
		//         // Get text from user and check it and change the label
		//         input = Integer.parseInt(amountTextField.getText());
		//      	if (input > numUnits){
		// 			// System.out.println("Not enough units.");
		// 			instructionsLabel = new Label("Not enough units.");
		// 			root.getChildren().add(instructionsLabel);

		// 			input = 0;
		// 		}
				return input;
		//         event.consume();
		//      }
		// });

		// root.getChildren().add(submitButton);
	}

	/**
	 * Used to get the countries stats and present them
	 * @return String of nicely formated information
	*/
	public String toString(){
		String numUnitsString = Integer.toString(numUnits);
		String countryIDString = Integer.toString(countryID);
		return "Name: " + name + "(" + countryIDString +  ")" + "\n" + "Owner: " + owner.getName() + "\n" + "Units: " + numUnitsString;
	}

}