import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Start the program
 */
public class BankAccountGUI extends Application {
	Scene mainDisplay;
	final int HEIGHT = 300;
	final int WIDTH = 300;
	Scene createAccountDisplay;

	/**
	 * Build the GUI and link the buttons to the button handler
	 */
	@Override
	public void start(Stage primaryStage) {

		Customer customer = new Customer("John Smith", 22);
		BankAccount bankAccount = new BankAccount(customer, 100.0);
		mainDisplay = displayMainMenu(customer, bankAccount, primaryStage);
		primaryStage.setTitle("Bank Account");
		primaryStage.setScene(mainDisplay);
		primaryStage.show();

	}
	/**
	 * creates the display for the Create a new account display
	 * @param stage the stage this will be displayed on
	 */
	public Scene displayCreateAccount(Stage stage) {
		GridPane root = new GridPane();
		root.setHgap(10);
		root.setVgap(10);
		/* Customer ID label */

		Label customerIDLabel = new Label("Customer ID");
		customerIDLabel.setFont(Font.font("Courier New", 15));
		root.add(customerIDLabel, 1, 1);

		/* Customer ID text field */
		TextField customerIDTextField = new TextField();
		root.add(customerIDTextField, 1, 2);

		/* Customer Name label */
		Label customerNameLabel = new Label("Customer Name");
		customerNameLabel.setFont(Font.font("Courier New", 15));
		root.add(customerNameLabel, 1, 3);

		/* Customer name text field */
		TextField customerNameTextField = new TextField();
		root.add(customerNameTextField, 1, 4);

		/* Start Balance label */
		Label startBalanceLabel = new Label("Start Balance");
		startBalanceLabel.setFont(Font.font("Courier New", 15));
		root.add(startBalanceLabel, 1, 5);

		/* Customer start balance field */
		TextField startBalanceTextField = new TextField();
		root.add(startBalanceTextField, 1, 6);


		/* Create button */
		Button compileAccountBtn = new Button("Create Account");
		compileAccountBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String customerName = customerNameTextField.getText();
				int customerID;
				double startBalance;
				//check to see if user inputs correct values
				try {
					customerIDTextField.setStyle("-fx-text-fill: black;");
					customerID = Integer.parseInt(customerIDTextField.getText());
				} catch (NumberFormatException e) {
					customerIDTextField.setStyle("-fx-text-fill: red;");
					return;
				}

				//check to see if user inputs correct values
				try {
					startBalanceTextField.setStyle("-fx-text-fill: black;");
					startBalance = Double.parseDouble(startBalanceTextField.getText());
				} catch (NumberFormatException e) {
					startBalanceTextField.setStyle("-fx-text-fill: red;");
					return;
				}
				//sets up the new display for the new customer and bank account
				Customer customer = new Customer(customerName, customerID);
				BankAccount bankAccount = new BankAccount(customer, startBalance);
				mainDisplay = displayMainMenu(customer, bankAccount, stage);
				stage.setScene(mainDisplay);
				stage.show();
			}
		});
		root.add(compileAccountBtn , 1, 8);
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		return scene;
	}
	/**
	 * creates the bank account information, withdraw, and deposit
	 * @param customer the customer's information
	 * @param bankAccount the bank information
	 * @param stage the stage this scene will be displayed onto
	 */
	public Scene displayMainMenu(Customer customer, BankAccount bankAccount, Stage stage) {
		
		/* Create the grid to store the visual elements */
		GridPane root = new GridPane();
		root.setHgap(10);
		root.setVgap(10);

		/* Customer Name label */
		Label nameLabel = new Label(customer.getName());
		nameLabel.setFont(Font.font("Courier New", 15));
		root.add(nameLabel, 1, 1);

		/* Customer Balance label */
		Label balanceLabel = new Label("Balance: " + bankAccount.getBalance());
		balanceLabel.setFont(Font.font("Courier New", 15));
		root.add(balanceLabel, 1, 0);

		/* Input text field */
		TextField amountTextField = new TextField();
		root.add(amountTextField, 1, 2);

		/* Deposit Button */
		Button depositButton = new Button("Deposit");
		depositButton.setOnAction(new HandleButtonClick("deposit", bankAccount, amountTextField, balanceLabel));
		root.add(depositButton, 1, 3);

		/* Withdraw Button */
		Button withdrawButton = new Button("Withdraw");
		withdrawButton.setOnAction(new HandleButtonClick("withdraw", bankAccount, amountTextField, balanceLabel));
		root.add(withdrawButton, 2, 3);

		/* Create a new Account button */
		Button createAccount = new Button("Create New Account");
		createAccount.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				createAccountDisplay = displayCreateAccount(stage);
				stage.setScene(createAccountDisplay);
				stage.show();
			}
		});
		root.add(createAccount, 1, 5);
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		return scene;
	}
}
