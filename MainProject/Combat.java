
import java.lang.Math;
import java.util.Arrays;
import java.util.Scanner;
import javafx.scene.layout.Pane;
import javafx.scene.*;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.control.Label;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * Simulates a Combat between 2 country
 */
public class Combat {
	private Dice dice;
	private Player attacker;
	private Player defender;
	private Country attackingCountry;
	private Country defendingCountry;
	private int numAttackers;
	private int numDefenders;
	private Pane pane;

	Combat(Country attackingCountry, Country defendingCountry) {
		dice = new Dice();
		this.attackingCountry = attackingCountry;
		this.defendingCountry = defendingCountry;
		attacker = attackingCountry.getOwner();
		defender = defendingCountry.getOwner();
		initPane();

	}

	/**
	 * checks to see if the attacking number is higher
	 * 
	 * @param attackerDice
	 *            the attacker's number value
	 * @param defenderDice
	 *            the defender's number value
	 */
	public boolean attackerWin(int attackerDice, int defenderDice) {
		boolean attackWin = false;
		if (attackerDice > defenderDice) {
			attackWin = true;
		}
		return attackWin;
	}

	/**
	 * Roll's a number of dice
	 * 
	 * @param numDice
	 *            how much dice to roll
	 */
	public int[] rollDice(int numDice) {
		int dices[] = dice.rollDices(numDice);
		return dices;

	}

	/**
	 * decrease one unit from a country. checks if the country has any units left
	 * 
	 * @param country
	 *            the country that will lose (1) unit
	 * @param attacker
	 *            the player who has won the attack
	 */
	public void countryLose(Country country, Player attacker) {
		Player player = country.getOwner();
		System.out.println("-[===> ---------------------- <===]-");
		System.out.println(country.getName() + "[" + player.getName() + "] lost a unit");
		System.out.println("-[===> ---------------------- <===]-");

		if (country.getUnits() > 0)
			country.addUnits(-1);

		/*
		 * if (country.getUnits() <= 0) { System.out.println( player.getName() +
		 * " has lost " + country.getName() + "/n New owner is " + attacker.getName());
		 * 
		 * System.out.println("-[===> ---------------------- <===]-");
		 * player.loseCountry(country); attacker.gainCountry(country);
		 * country.setOwner(attacker); attacker.moveUnits(attackingCountry,
		 * defendingCountry); }
		 */
	}

	/**
	 * sorts an array from highest to lowest
	 * 
	 * @param arr
	 *            the unsorted array
	 */
	public int[] sortFromHighest(int[] arr) {
		Arrays.sort(arr);
		int arrLength = arr.length;
		int[] desendingArray = new int[arrLength];
		for (int i = 0; i < desendingArray.length; i++) {
			desendingArray[i] = arr[arrLength - i - 1];
		}
		return desendingArray;

	}

	/**
	 * simulates the battle between 2 countries
	 */
	public void simulateBattle() {
		System.out.println(
				attackingCountry.getName().toUpperCase() + " ATTACKING " + defendingCountry.getName().toUpperCase());
		// prompt attacker and defender to select amount of units
		System.out.print(attacker.getName() + " (attacker): ");
		numAttackers = attackingCountry.selectUnitAmount();

		System.out.print(defender.getName() + " (defender): ");
		numDefenders = defendingCountry.selectUnitAmount();
		startBattle();

	}

	/**
	 * starts the battle
	 */
	public void startBattle() {
		int[] atkDices = rollDice(numAttackers);
		int[] defDices = rollDice(numDefenders);
		int defending[] = sortFromHighest(defDices);
		int attacking[] = sortFromHighest(atkDices);
		int minimumDice = Math.min(defending.length, attacking.length);
		for (int i = 0; i < minimumDice; i++) {
			System.out.println("-[===> ---------------------- <===]-");
			System.out.println(attacker.getName() + " roles: " + attacking[i]);
			System.out.println(defender.getName() + " roles: " + defending[i]);
			if (attackerWin(attacking[i], defending[i])) {
				countryLose(defendingCountry, attacker);
			} else {
				countryLose(attackingCountry, defender);
			}
		}
		displayBattle(attacking, defending);

	}

	public void displayTransition(Rectangle backDrop) {
		Pane transition = new Pane();

		pane.getChildren().add(transition);
		MyAnimation transitionAnimation = new MyAnimation(transition, false);
		transitionAnimation.addFrames("battleStarting", 43);
		transitionAnimation.start();
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), ae -> {
			pane.getChildren().remove(MainMenu.getMapPane());
			displaySelection(backDrop);
			transition.toFront();
		}));
		Timeline clearTransition = new Timeline(new KeyFrame(Duration.seconds(3.7), ae -> {
			transition.getChildren().clear();
			pane.getChildren().remove(transition);
		}));
		clearTransition.play();
		timeline.play();

	}

	/**
	 * intializes the display for the battle
	 */
	public void initPane() {
		pane = new Pane();
		final double WIDTH = 960.0;
		final double HEIGHT = 600.0;
		final double paneX = (960 - WIDTH) / 2;
		final double paneY = (600 - HEIGHT) / 2;
		Rectangle backDrop = new Rectangle(paneX, paneY, WIDTH, HEIGHT);
		pane.getChildren().add(MainMenu.getMapPane());
		displayTransition(backDrop);
		// displaySelection(backDrop);
	}

	public Pane getPane() {
		return pane;
	}

	/**
	 * draws any elements that should be constantly on the display
	 * 
	 * @param field
	 *            the field this will be drawn onto
	 */
	public void constantDisplayElements(Rectangle field) {
		String message = attacker.getName() + " is attacking " + defender.getName();
		final int fontSize = 30;
		Label title = centeredText(message, field, fontSize);
		title.setFont(Font.font("Courier New", fontSize));
		title.setTextFill(Color.RED);
		pane.getChildren().add(title);

	}

	/**
	 * displays the selection phase, allowing the attacker and defender to choose
	 * how many units to send to battle
	 * 
	 * @param backDrop
	 *            the area these elements will be drawn onto
	 */
	public void displaySelection(Rectangle backDrop) {
		constantDisplayElements(backDrop);
		CallAction displayResults = new CallAction() {
			public void use(int amount) {
				if (amount <= defendingCountry.getUnits()) {
					if (amount >= 2)
						numDefenders = 2;
					else
						numDefenders = amount;
					pane.getChildren().clear();
					constantDisplayElements(backDrop);
					startBattle();
				}
			}
		};
		CallAction attackerDone = new CallAction() {
			public void use(int amount) {
				if (amount > 0 && amount < attackingCountry.getUnits()) {
					if (amount >= 3)
						numAttackers = 3;
					else
						numAttackers = amount;
					pane.getChildren().clear();
					constantDisplayElements(backDrop);
					getUnits(defender.getName() + "(" + (defendingCountry.getUnits()) + ")", " how many units(DEFEND)",
							backDrop, displayResults);
				}
			}
		};
		getUnits(attacker.getName() + "(" + (getAttackingCountry().getUnits() - 1) + ")", " how many units(ATTACK)",
				backDrop, attackerDone);

	}

	/**
	 * displays a number on a specific row relative to the field it will be drawn on
	 * 
	 * @param rect
	 *            the area these elements will be drawn onto
	 * @param row
	 *            the row value that will determine its y-value
	 * @param number
	 *            the number that will be displayed
	 * @param color
	 *            the font color of the number
	 */
	public void displayNumber(Rectangle rect, int row, int number, Color color) {
		double rectX = rect.getX();
		double rectY = rect.getY();
		double height = rect.getHeight();
		double spacing = height / 4;
		Label numberLabel = centeredText(number + "", rect, 30);
		numberLabel.setLayoutY(rectY + spacing * row + 5);
		pane.getChildren().add(numberLabel);
	}

	/**
	 * displays the battle results
	 * 
	 * @param atkDice
	 *            an array of values that will represent the attackers units
	 * @param defDice
	 *            an array of values that will represent the defenders units
	 */
	public void displayBattle(int[] atkDice, int[] defDice) {
		double alignY = 100.0;
		int maxDice = Math.max(atkDice.length, defDice.length);
		int minDice = Math.min(atkDice.length, defDice.length);
		Rectangle box = new Rectangle(300, 400);
		double divider = box.getHeight() / 4;
		displayResultBox(100, alignY, true, attacker.getName(), Color.RED);
		displayResultBox(560, alignY, false, defender.getName(), Color.BLUE);

		for (int i = 0; i < maxDice; i++) {
			double spacing = alignY + divider * (i + 1) + 15;
			if (i < atkDice.length) {
				Label atkLabel = centeredText(atkDice[i] + "", box, 50);
				double atkX = atkLabel.getLayoutX() + 100;
				atkLabel.setLayoutY(spacing);
				atkLabel.setLayoutX(atkX);
				pane.getChildren().add(atkLabel);
			}
			if (i < defDice.length) {
				Label defLabel = centeredText(defDice[i] + "", box, 50);
				defLabel.setLayoutY(spacing);
				double defX = defLabel.getLayoutX() + 560;
				defLabel.setLayoutX(defX);
				pane.getChildren().add(defLabel);
			}
			if (i < minDice) {
				MyAnimation cross = new MyAnimation(pane, false);
				cross.addFrames("cross", 13, 29);
				if (attackerWin(atkDice[i], defDice[i])) {
					cross.setMiddleCoord(710, spacing + 25);
				} else {
					cross.setMiddleCoord(250, spacing + 25);
				}
				cross.start();
			}
		}
		Button nextBtn = new Button();
		Image image = new Image("arts_assests/btn_next.png");
		ImageView iv = new ImageView(image);
		nextBtn.setStyle("-fx-background-color: transparent;");
		double ratio = image.getWidth() / image.getHeight();
		double btnHeight = 90;
		iv.setFitHeight(btnHeight);
		iv.setFitWidth(btnHeight * ratio);
		nextBtn.setGraphic(iv);
		pane.getChildren().add(nextBtn);
		nextBtn.setLayoutX((960 - image.getWidth()) - 60);
		nextBtn.setLayoutY(500);
		setNextBtnEvents(nextBtn);
	}

	/**
	 * sets the action events to a button
	 * 
	 * @param nextBtn
	 *            the button to set these new action events to
	 */
	public void setNextBtnEvents(Button nextBtn) {
		CallAction moveUnitAction = new CallAction() {
			public void use(int amount) {
				if (amount > 0 && amount < attackingCountry.getUnits()) {
					defender.loseCountry(defendingCountry);
					attacker.gainCountry(defendingCountry);
					defendingCountry.setOwner(attacker);
					attacker.moveUnits(attackingCountry, defendingCountry, amount);
					pane.getChildren().clear();
					MainMenu.nextPane();
				}
			}
		};
		System.out.println("defending country has " + defendingCountry.getUnits() + " left");
		if (defendingCountry.getUnits() <= 0) {
			nextBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					pane.getChildren().clear();
					getUnits("Move " + attackingCountry.getName() + "(" + (attackingCountry.getUnits() - 1) + ")",
							"To " + defendingCountry.getName(), new Rectangle(0, 0, 960, 600), moveUnitAction);
				}
			});

		} else {
			nextBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					pane.getChildren().clear();
					MainMenu.nextPane();
				}
			});
		}
		nextBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				nextBtn.setEffect(new DropShadow());
			}
		});
		nextBtn.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				nextBtn.setEffect(null);
			}
		});
	}

	/**
	 * displays the back drop of a player's results
	 * 
	 * @param x
	 *            the x-value
	 * @param y
	 *            the y-value
	 * @param leftSide
	 *            if the display is on the left side of the display
	 * @param name
	 *            the name of the player
	 * @param color
	 *            the font color
	 */
	public void displayResultBox(double x, double y, boolean leftSide, String name, Color color) {
		Image panelImage;
		Rectangle box = new Rectangle(250, 400);
		box.setX(x + 15);
		box.setY(y);
		ImageView backPanel;
		if (leftSide) {
			panelImage = new Image("arts_assests/banner_attacker.png");
			backPanel = new ImageView(panelImage);
			backPanel.setLayoutX(x - 355.4);
		} else {
			panelImage = new Image("arts_assests/banner_defender.png");
			backPanel = new ImageView(panelImage);
			backPanel.setLayoutX(x - 30);
		}
		backPanel.setLayoutY(y - 10);
		Label nameLabel = centeredText(name, box, 50);
		nameLabel.setTextFill(color);
		nameLabel.setLayoutY(y + 20);
		pane.getChildren().add(backPanel);
		pane.getChildren().add(nameLabel);
		setDividers(4, box);
	}

	/**
	 * displays dividers on a rectangle
	 * 
	 * @param amount
	 *            how many dividers is needed
	 * @param rect
	 *            the area the dividers will be drawn relative to
	 */
	public void setDividers(int amount, Rectangle rect) {
		double rectX = rect.getX();
		double rectY = rect.getY();
		double spacing = rect.getHeight() / amount;
		for (int i = 1; i < amount; i++) {
			double x = rectX;
			double y = rectY + spacing * i;
			Rectangle temp = new Rectangle(x, y, rect.getWidth(), 7);
			temp.setOpacity(0.3);
			pane.getChildren().add(temp);
		}
	}

	/**
	 * displays an input with a header at the top and a message below that
	 * 
	 * @param title
	 *            the header
	 * @param msg
	 *            the message to display
	 * @param field
	 *            the area these elements will be drawn relative to
	 * @ca the action that will happen once the user presses "CONFIRM"
	 */
	public void getUnits(String title, String msg, Rectangle field, CallAction ca) {
		final double WIDTH = 300.0;
		final double HEIGHT = 100.0;
		double x = field.getX() + (field.getWidth() - WIDTH) / 2;
		double y = field.getY() + (field.getHeight() - HEIGHT) / 2;
		TextField input = new TextField();
		Rectangle backDrop = new Rectangle(x, y, WIDTH, HEIGHT);
		Image panelImage = new Image("arts_assests/backDrop_small.png");
		ImageView panelView = new ImageView(panelImage);
		panelView.setX(x - 30);
		panelView.setY(y - 10);
		Label message = centeredText(msg, backDrop);
		message.setLayoutY(message.getLayoutY() + 30);
		message.setTextFill(Color.rgb(5, 5, 5));

		Label name = centeredText(title, backDrop);
		name.setTextFill(Color.WHITE);
		backDrop.setFill(Color.BLACK);
		input.setLayoutX(x + (backDrop.getWidth() - input.getWidth()) / 5);
		input.setLayoutY(y + 68);

		Button submitUnitsBtn = new Button("CONFIRM");
		submitUnitsBtn.setOnAction(new inputNumberHandler(input, ca));
		submitUnitsBtn.setLayoutX(x + WIDTH - 80);
		submitUnitsBtn.setLayoutY(y + 68);

		pane.getChildren().addAll(panelView, message, name);
		pane.getChildren().add(input);
		pane.getChildren().add(submitUnitsBtn);
		submitUnitsBtn.toFront();
	}

	/**
	 * returns a label with a message that is centered relative to a field
	 * 
	 * @param msg
	 *            the message to display
	 * @param field
	 *            the area this message will be drawn relative to
	 * 
	 */
	public Label centeredText(String msg, Rectangle field) {
		Label message = new Label(msg);
		int totalChar = msg.length();
		final int fontSize = 20;
		message.setFont(Font.font("Courier New", FontWeight.BOLD, fontSize));
		message.setTextFill(Color.BLACK);
		message.relocate(field.getX() + (field.getWidth() - (fontSize / 1.7 * totalChar)) / 2, field.getY());
		return message;
	}

	/**
	 * returns a label with a message that is centered relative to a field
	 * 
	 * @param msg
	 *            the message to display
	 * @param field
	 *            the area this message will be drawn relative to
	 * @param size
	 *            the font size
	 * 
	 */
	public Label centeredText(String msg, Rectangle field, int size) {
		Label message = new Label(msg);
		int totalChar = msg.length();
		int fontSize = size;
		message.setFont(Font.font("Courier New", FontWeight.BOLD, fontSize));
		message.setTextFill(Color.BLACK);
		message.relocate(field.getX() + (field.getWidth() - (fontSize / 1.7 * totalChar)) / 2, field.getY());
		return message;
	}

	/**
	 * acts as a custom action that will do something when the "use" method is
	 * called the "use" method is meant to be overrided whenever creating a new
	 * instance of this class
	 */
	private class CallAction {
		/**
		 * by default will print the amount to console
		 * 
		 * @param amount
		 *            the amount being passed through
		 */
		public void use(int amount) {
			System.out.println(amount);
		}
	}

	/**
	 * acts as a custom event handler that takes an integer if the input is not an
	 * integer it will change the text to red
	 */
	private class inputNumberHandler implements EventHandler<ActionEvent> {
		TextField input;
		CallAction call;

		/**
		 * the constructor that passes through a TextField and a CallAction
		 * 
		 * @param in
		 *            the input that will be used
		 * @param ca
		 *            the action that will happen once the input is correct
		 */
		public inputNumberHandler(TextField in, CallAction ca) {
			input = in;
			call = ca;
		}

		@Override
		public void handle(ActionEvent event) {
			int amount;
			try {
				input.setStyle("-fx-text-fill: black;");
				amount = Integer.parseInt(input.getText());
				call.use(amount);
			} catch (NumberFormatException e) {
				input.setStyle("-fx-text-fill: red;");
				return;
			}
		}

	}

	// getters and setters
	/**
	 * returns the dice
	 */
	public Dice getDice() {
		return dice;
	}

	/**
	 * sets the dice
	 * 
	 * @param dice
	 *            the dice to set
	 */
	public void setDice(Dice dice) {
		this.dice = dice;
	}

	/**
	 * returns the attacker
	 */
	public Player getAttacker() {
		return attacker;
	}

	/**
	 * sets who is the attacker
	 * 
	 * @param the
	 *            player who will become the attacker
	 */
	public void setAttacker(Player attacker) {
		this.attacker = attacker;
	}

	/**
	 * returns the defender
	 */
	public Player getDefender() {
		return defender;
	}

	/**
	 * sets who is the defender
	 * 
	 * @param defender
	 *            the player who will become the defender
	 */
	public void setDefender(Player defender) {
		this.defender = defender;
	}

	/**
	 * return the country that is attacking
	 */
	public Country getAttackingCountry() {
		return attackingCountry;
	}

	/**
	 * sets the attacking country
	 * 
	 * @param attackingCountry
	 *            the country that is attacking
	 */
	public void setAttackingCountry(Country attackingCountry) {
		this.attackingCountry = attackingCountry;
	}

	/**
	 * returns the country that is defending
	 */
	public Country getDefendingCountry() {
		return defendingCountry;
	}

	/**
	 * sets the defending country
	 * 
	 * @param defendingCountry
	 *            the country that is defending
	 */
	public void setDefendingCountry(Country defendingCountry) {
		this.defendingCountry = defendingCountry;
	}

	/**
	 * returns the amount of attackers
	 */
	public int getNumAttackers() {
		return numAttackers;
	}

	/**
	 * sets the amount of attackers
	 * 
	 * @param numAttackers
	 *            how much units are attacking
	 */
	public void setNumAttackers(int numAttackers) {
		this.numAttackers = numAttackers;
	}

	/**
	 * returns the number of defenders
	 */
	public int getNumDefenders() {
		return numDefenders;
	}

	/**
	 * sets the amount of defenders
	 * 
	 * @param numDefenders
	 *            how much units are attacking
	 */
	public void setNumDefenders(int numDefenders) {
		this.numDefenders = numDefenders;
	}

}