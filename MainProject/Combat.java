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
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.control.Label;
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
	 * starts the battle between 2 countries
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

	public Pane getPane() {
		pane = new Pane();

		// MyAnimation ma = new MyAnimation(pane,true);
		// ma.addFrames("test",3);
		// ma.start();
		final double WIDTH = 960.0;
		final double HEIGHT = 600.0;
		final double paneX = (960 - WIDTH) / 2;
		final double paneY = (600 - HEIGHT) / 2;
		Rectangle backDrop = new Rectangle(paneX, paneY, WIDTH, HEIGHT);
		constantDisplayElements(backDrop);
		displaySelection(backDrop);
		// displayBattle(a, b);
		return pane;
	}
	
	public void constantDisplayElements(Rectangle field) {
		String message = attacker.getName() + " is attacking " + defender.getName();
		final int fontSize = 30;
		Label title = centeredText(message, field, fontSize);
		title.setFont(Font.font("Courier New", fontSize));
		title.setTextFill(Color.RED);
		pane.getChildren().add(title);

	}

	public void displaySelection(Rectangle backDrop) {
		CallAction displayResults = new CallAction() {
			public void use(int amount) {
				if (amount <= defendingCountry.getUnits()) {
					numDefenders = amount;
					pane.getChildren().clear();
					constantDisplayElements(backDrop);
					startBattle();
				}
			}
		};
		CallAction attackerDone = new CallAction() {
			public void use(int amount) {
				if (amount <= attackingCountry.getUnits()) {
					numAttackers = amount;
					pane.getChildren().clear();
					constantDisplayElements(backDrop);
					getUnits(defender.getName() + "(" + (defendingCountry.getUnits()) + ")", " how many units(DEFEND)",
							backDrop, displayResults);
				}
			}
		};
		getUnits(attacker.getName() + "(" + (getAttackingCountry().getUnits()-1) + ")", " how many units(ATTACK)", backDrop,
				attackerDone);

	}

	public void displayNumber(Rectangle rect, int row, int number, Color color) {
		double rectX = rect.getX();
		double rectY = rect.getY();
		double height = rect.getHeight();
		double spacing = height / 4;
		Label numberLabel = centeredText(number + "", rect, 30);
		numberLabel.setLayoutY(rectY + spacing * row + 5);
		pane.getChildren().add(numberLabel);
	}

	public void displayBattle(int[] atkDice, int[] defDice) {
		double alignY = 100.0;
		int minimumDice = Math.min(atkDice.length, defDice.length);
		Rectangle box = new Rectangle(300, 400);
		double divider = box.getHeight() / 4;
		displayResultBox(100, alignY, true, attacker.getName(), Color.RED);
		displayResultBox(560, alignY, false, defender.getName(), Color.BLUE);
		for (int i = 0; i < minimumDice; i++) {
			MyAnimation cross = new MyAnimation(pane, false);
			cross.addFrames("cross", 13, 29);
			Label atkLabel = centeredText(atkDice[i] + "", box, 50);
			double spacing = alignY + divider * (i + 1) + 15;
			double atkX = atkLabel.getLayoutX() + 100;
			atkLabel.setLayoutY(spacing);
			atkLabel.setLayoutX(atkX);
			Label defLabel = centeredText(defDice[i] + "", box, 50);
			defLabel.setLayoutY(spacing);
			double defX = defLabel.getLayoutX() + 560;
			defLabel.setLayoutX(defX);
			pane.getChildren().addAll(defLabel, atkLabel);

			if (attackerWin(atkDice[i], defDice[i])) {
				cross.setMiddleCoord(710, spacing + 25);
			} else {
				cross.setMiddleCoord(250, spacing + 25);
			}
			cross.start();
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
					getUnits("Move " + attackingCountry.getName() + "("+(attackingCountry.getUnits()-1)+")", "To " + defendingCountry.getName(),
							new Rectangle(0, 0, 960, 600), moveUnitAction);
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

	public void displayResultBox(double x, double y, boolean leftSide, String name, Color color) {
		Image panelImage;
		Rectangle box = new Rectangle(250,400);
		box.setX(x+15);
		box.setY(y);
		ImageView backPanel;
		if(leftSide) {
			panelImage = new Image("arts_assests/banner_attacker.png");
			backPanel = new ImageView(panelImage);
			backPanel.setLayoutX(x-355.4);
		}else {
			panelImage = new Image("arts_assests/banner_defender.png");
			backPanel = new ImageView(panelImage);
			backPanel.setLayoutX(x-30);
		}
		backPanel.setLayoutY(y-10);
		Label nameLabel = centeredText(name, box, 50);
		nameLabel.setTextFill(color);
		nameLabel.setLayoutY(y + 20);
		pane.getChildren().add(backPanel);
		pane.getChildren().add(nameLabel);
		setDividers(4, box);
	}

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

	public void getUnits(String title, String msg, Rectangle field, CallAction ca) {
		final double WIDTH = 300.0;
		final double HEIGHT = 100.0;
		double x = field.getX() + (field.getWidth() - WIDTH) / 2;
		double y = field.getY() + (field.getHeight() - HEIGHT) / 2;
		TextField input = new TextField();
		Rectangle backDrop = new Rectangle(x, y, WIDTH, HEIGHT);
		Image panelImage = new Image("arts_assests/backDrop_small.png");
		ImageView panelView = new ImageView(panelImage);
		panelView.setX(x-30);
		panelView.setY(y-10);
		Label message = centeredText(msg, backDrop);
		message.setLayoutY(message.getLayoutY() + 30);
		message.setTextFill(Color.rgb(5,5,5));

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

	public Label centeredText(String msg, Rectangle field) {
		Label message = new Label(msg);
		int totalChar = msg.length();
		final int fontSize = 20;
		message.setFont(Font.font("Courier New", FontWeight.BOLD,fontSize));
		message.setTextFill(Color.BLACK);
		message.relocate(field.getX() + (field.getWidth() - (fontSize / 1.7 * totalChar)) / 2, field.getY());
		return message;
	}

	public Label centeredText(String msg, Rectangle field, int size) {
		Label message = new Label(msg);
		int totalChar = msg.length();
		int fontSize = size;
		message.setFont(Font.font("Courier New", FontWeight.BOLD,fontSize));
		message.setTextFill(Color.BLACK);
		message.relocate(field.getX() + (field.getWidth() - (fontSize / 1.7 * totalChar)) / 2, field.getY());
		return message;
	}



	private class CallAction {
		public void use(int amount) {
			System.out.println(amount);
		}
	}

	private class inputNumberHandler implements EventHandler<ActionEvent> {
		TextField input;
		CallAction call;

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