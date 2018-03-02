import java.util.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.HashMap;
import javafx.geometry.Pos;

public class Country {
 // extends Application{
	private String name = "";
	private int countryID;
	private Player owner;
	private int numUnits;
	private ArrayList<Integer> neighbours;
	private Pane root;
	private Label amountOfUnitsLabel;
	private Label ownerLabel;
	private ImageView imageView;
	private boolean clickable = true;
	/**
	 * Constructor to set the id, neighbours, and name
	*/
	Country(int id, ArrayList<Integer> newNeighbours, String newName, Pane root, ArrayList<Integer> titleCordinates){
		neighbours = newNeighbours;
		name = newName;
		countryID = id;

		String imagePath = "mapImages/"+name+".png";
		Image image = new Image(imagePath);
		imageView = new ImageView();
		imageView.setImage(image);
		// imageView.setId(x);
		imageView.setPreserveRatio(true);
		imageView.setSmooth(true);
		imageView.setLayoutX(0);
		imageView.setLayoutY(0);
		imageView.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
		     public void handle(MouseEvent event) {
		     	if (clickable){
			        String imagePath = "mapImages/"+name+"-Hover.png";
					Image hoverImage = new Image(imagePath);
					imageView.setImage(hoverImage);
				}
		        event.consume();
		     }
		});
		imageView.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
		     public void handle(MouseEvent event) {
		     	if (clickable){
			        String imagePath = "mapImages/"+name+".png";
					Image nonHoverImage = new Image(imagePath);
					imageView.setImage(nonHoverImage);
				}
		        event.consume();
		     }
		});
		imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		        System.out.println(name + " Pressed");
		        if (clickable) {
		        	//************* TELL THE SOME OTHER CLASS THAT A COUNTRY WAS CLICKED ************
		        	MainMenu.setCountryClicked(Country.this);
		        }
		        event.consume();
		    }
		});

        root.getChildren().add(imageView);

        Label title = new Label(name);
		title.setFont(Font.font("Courier New", 12));
		int posx = titleCordinates.get(0);
		int posy = titleCordinates.get(1);
		title.setLayoutX(posx);
		title.setLayoutY(posy);

		// String ownerName = "";
		// if (owner == null){
		// 	String ownerName = "";
		// } else {
		// 	String ownerName = owner.getName();
		// }
		
		ownerLabel = new Label("");
		ownerLabel.setFont(Font.font("Courier New", 10));
		posx = posx;
		posy = posy + 10;
		ownerLabel.setLayoutX(posx);
		ownerLabel.setLayoutY(posy);

		String amountOfUnits = Integer.toString(numUnits); 
		amountOfUnitsLabel = new Label("Units: "+amountOfUnits);
		amountOfUnitsLabel.setFont(Font.font("Courier New", 10));
		posx = posx;
		posy = posy + 10;
		amountOfUnitsLabel.setLayoutX(posx);
		amountOfUnitsLabel.setLayoutY(posy);


		root.getChildren().add(title);
		root.getChildren().add(ownerLabel);
		root.getChildren().add(amountOfUnitsLabel);

		title.toFront();
		ownerLabel.toFront();
		amountOfUnitsLabel.toFront();
		imageView.toBack();
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
	}

	/**
	 * Used to set the number of units
	 * @param an int to set the number of units
	*/
	public void setUnits(int units){
		numUnits = units;
		// Update the map here
		amountOfUnitsLabel.setText("Units: "+numUnits);
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
		String ownerName = owner.getName();
		ownerLabel.setText(ownerName);
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