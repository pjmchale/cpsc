package CombatEngine;

import AnimatedSequences.*;
import MapStage.*;
import GameEngine.*;
import PlayerPackage.*;
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

public class CombatGUI {

	private Combat combat;
	private Dice dice;
	private Player attacker;
	private Player defender;
	private Country attackingCountry;
	private Country defendingCountry;
	private int numAttackers;
	private int numDefenders;
	private boolean displayOnMap = false;
	private Pane pane;

	public CombatGUI(Combat cb) {
		System.out.println("Combat Graphic");
		pane = new Pane();
		dice = cb.getDice();
		attacker = cb.getAttacker();
		defender = cb.getDefender();
		attackingCountry = cb.getAttackingCountry();
		defendingCountry = cb.getDefendingCountry();
		numAttackers = cb.getNumAttackers();
		numDefenders = cb.getNumDefenders();
		combat = cb;
		if(isHuman(attacker) || isHuman(defender)) {
				initPane();
		}else {
			initAiBattle();
		}
	}
	public boolean getDisplayOnMap() {
		return displayOnMap;
	}
	public void initAiBattle() {
		displayOnMap = true;
		AiPlayerSimple aiAtk = (AiPlayerSimple) attacker;
		AiPlayerSimple aiDef = (AiPlayerSimple) defender;
		numAttackers = aiAtk.getAttackingUnits(attackingCountry);
		numDefenders = aiDef.getDefendingUnits(defendingCountry);
		combat.setNumAttackers(numAttackers);
		combat.setNumDefenders(numDefenders);
		pane.getChildren().add(MainGUI.getMapPane());
	}
	
	public void displayMapChanges(int atkLose, int defLose) {
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), ae -> {
			System.out.println("AI finish combat");
			if(defendingCountry.getUnits() <= 0) {
				AiPlayerSimple aiAtk = (AiPlayerSimple) attacker;
				System.out.println(aiAtk.getName() + " is moving to new country");
				aiAtk.moveUnits();
			}
			pane.getChildren().clear();
			attacker.playTurn();
		}));
		displayToMap(atkLose,attackingCountry);
		displayToMap(defLose,defendingCountry);
		timeline.play();
	}
	private void displayToMap(int amount, Country c) {
		if(amount != 0) {
			double x = c.getCenterX();
			double y = c.getCenterY();
			AnimatedText at = new AnimatedText("-"+amount ,pane,x,y, 1);
			at.setDeltaY(-4);
			at.setFadeOut();
			at.start();
		
		}
	}

	private void displayTransition(Rectangle backDrop) {
		System.out.println("display transition");
		Pane transition = new Pane();
		pane.getChildren().add(transition);
		MyAnimation transitionAnimation = new MyAnimation(transition, false);
		transitionAnimation.setFrames(AnimationTransition.getToBattleTransition(transition));
		transitionAnimation.start();
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), ae -> {
			System.out.println("active");
			pane.getChildren().remove(MainGUI.getMapPane());
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
	private void initPane() {
		System.out.println("init combat gui pane");
		pane = new Pane();
		final double WIDTH = 960.0;
		final double HEIGHT = 600.0;
		final double paneX = (960 - WIDTH) / 2;
		final double paneY = (600 - HEIGHT) / 2;
		Rectangle backDrop = new Rectangle(paneX, paneY, WIDTH, HEIGHT);
		pane.getChildren().add(MainGUI.getMapPane());
		displayTransition(backDrop);
	}

	public Pane getPane() {
		System.out.println("getting the combat pane");
		if(!isHuman(attacker) && !isHuman(defender)) {
			combat.startBattle();
		}
		return pane;
	}

	/**
	 * draws any elements that should be constantly on the display
	 * 
	 * @param field
	 *            the field this will be drawn onto
	 */
	private void constantDisplayElements(Rectangle field) {
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
	private void displaySelection(Rectangle backDrop) {
		System.out.println("Displaying selection screen");
		constantDisplayElements(backDrop);
		CallAction displayResults = new CallAction() {
			public void use(int amount) {
				if (amount > 0 && amount <= defendingCountry.getUnits()) {

					if (amount >= 2)
						amount = 2;
					numDefenders = amount;
					combat.setNumDefenders(numDefenders);
					pane.getChildren().clear();
					constantDisplayElements(backDrop);
					combat.startBattle();
				}
			}
		};

		CallAction attackerDone = new CallAction() {
			
			public void use(int amount) {
				
				System.out.println("attacking amount: " + amount +"   "+ attackingCountry.getUnits());
				if (amount > 0 && amount < attackingCountry.getUnits()) {
					if (amount >= 3)
						amount = 3;
					numAttackers = amount;
					combat.setNumAttackers(numAttackers);
					pane.getChildren().clear();
					constantDisplayElements(backDrop);
					if (isHuman(defender)) {
						System.out.println("Human defending action");
						getUnits(defender.getName() + "(" + (defendingCountry.getUnits()) + ")",
								" how many units(DEFEND)", backDrop, displayResults);
					System.out.println("test points");
					}else {
						System.out.println("AI defending");
						defenderAI(displayResults);
					}
				}
			}
		};
		System.out.println("checking");
		if (isHuman(attacker)) {
			getUnits(attacker.getName() + "(" + (attackingCountry.getUnits() - 1) + ")", " how many units(ATTACK)",
					backDrop, attackerDone);
		} else {
			System.out.println("AI attacking point check");
			attackerAI(backDrop,displayResults);
		}

	}

	private void attackerAI(Rectangle backDrop, CallAction ca) {
		AiPlayerSimple ai = (AiPlayerSimple) attacker;
		int amount = ai.getAttackingUnits(attackingCountry);
		numAttackers = amount;
		combat.setNumAttackers(numAttackers);
		System.out.println("Ai decided to attack with: " +amount);
		getUnits(defender.getName() + "(" + (defendingCountry.getUnits()) + ")",
				" how many units(DEFEND)", backDrop, ca);
	}

	private void defenderAI(CallAction ca) {
		AiPlayerSimple ai = (AiPlayerSimple) defender;
		int amount = ai.getDefendingUnits(defendingCountry);
		ca.use(amount);
	}

	private boolean isHuman(Player p) {
		return p.getPlayerType().equalsIgnoreCase("HUMAN");
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
	private void displayNumber(Rectangle rect, int row, int number, Color color) {
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
		System.out.println("starting gui");
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
				if (combat.attackerWin(atkDice[i], defDice[i])) {
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
		nextBtn.setLayoutX((960 - image.getWidth()) - 60);
		nextBtn.setLayoutY(500);
		setNextBtnEvents(nextBtn);
		pane.getChildren().add(nextBtn);
	}
	private void displayVictoryTransition(CallAction ca) {
		if(isHuman(attacker)) {
			getUnits("Move " + attackingCountry.getName() + "(" + (attackingCountry.getUnits() - 1) + ")",
				"To " + defendingCountry.getName(), new Rectangle(0, 0, 960, 600), ca);
		}else {
			System.out.println("AI moving units to new country");
			AiPlayerSimple ai = (AiPlayerSimple) attacker;
			ai.moveUnits();
			ai.playTurn();
		}
		
	}

	/**
	 * sets the action events to a button
	 * 
	 * @param nextBtn
	 *            the button to set these new action events to
	 */
	private void setNextBtnEvents(Button nextBtn) {
		CallAction moveUnitAction = new CallAction() {
			public void use(int amount) {
				if (amount > 0 && amount < attackingCountry.getUnits()) {
					System.out.println("using");
					defender.loseCountry(defendingCountry);
					defendingCountry.setOwner(attacker);
					attacker.moveUnits(attackingCountry, defendingCountry, amount);
					pane.getChildren().clear();
					MainGUI.nextPane();
				}
			}
		};
		System.out.println("defending country has " + defendingCountry.getUnits() + " left");
		if (defendingCountry.getUnits() <= 0) {
			nextBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					pane.getChildren().clear();
					displayVictoryTransition(moveUnitAction);
				}
			});

		} else {
			nextBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					pane.getChildren().clear();
					if(isHuman(attacker)){
						MainGUI.nextPane();
					}else {
						AiPlayerSimple ai = (AiPlayerSimple) attacker; 
						ai.playTurn();
					}
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
	private void displayResultBox(double x, double y, boolean leftSide, String name, Color color) {
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
	private void setDividers(int amount, Rectangle rect) {
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
	private void getUnits(String title, String msg, Rectangle field, CallAction ca) {
		System.out.println("asking for units");
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
		Image bg = new Image("arts_assests/Combat_Background.png");
		ImageView iv = new ImageView(bg);
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
		pane.getChildren().add(iv);
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
	private Label centeredText(String msg, Rectangle field) {
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
	private Label centeredText(String msg, Rectangle field, int size) {
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
}