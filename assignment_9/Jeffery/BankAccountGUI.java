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
import java.io.*;

/**
 * Start the program
 */
public class BankAccountGUI extends Application {
	Scene mainDisplay;
	final int HEIGHT = 300;
	final int WIDTH = 300;
	private String accountType = "UNKOWN";
	Customer customer;
	private SavingsAccount bankAccount;
	private ChequingAccount chequeAccount;
	Scene createAccountDisplay;
	private String fileName = "Account.txt";
	private File file = new File(fileName);

	/**
	 * Build the GUI and link the buttons to the button handler
	 */
	@Override
	public void start(Stage primaryStage) {

		// Customer customer = new Customer("John Smith");
		// SavingsAccount bankAccount = new SavingsAccount(customer, 100.0);
		// mainDisplay = displayMainMenu(customer, bankAccount, primaryStage);
		checkForFile(primaryStage);
		primaryStage.setTitle("Bank Account");
		primaryStage.setScene(mainDisplay);
		primaryStage.show();

	}

	@Override
	public void stop() {
		if(bankAccount != null) {
			updateFile(customer, bankAccount);
		}else {
			updateFile(customer, chequeAccount);
		}
	}

	public void updateFile(Customer c, BankAccount ba) {
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			DataOutputStream dos = new DataOutputStream(fos);
			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(accountType);
			bufferedWriter.newLine();
			bufferedWriter.write(c.getName());
			bufferedWriter.newLine();
			bufferedWriter.write(c.getID()+"");
			bufferedWriter.newLine();
			bufferedWriter.write(ba.getBalance()+"");
			bufferedWriter.close();
		} catch (IOException ex) {
			System.out.println("Error writing to file '" + fileName + "'");
		}
	}

	public void checkForFile(Stage primaryStage) {
		if (file.exists() && file.length() > 0) {
			String name = "ERROR";
			int ID = 0;
			double amount = 0.0;
			try {
				FileReader fileReader = new FileReader(fileName);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				accountType = bufferedReader.readLine();
				name = bufferedReader.readLine();
				ID = Integer.parseInt(bufferedReader.readLine());
				amount = Double.parseDouble(bufferedReader.readLine());
				bufferedReader.close();
			} catch (FileNotFoundException ex) {
				System.out.println("Unable to open file '" + fileName + "'");
			} catch (IOException ex) {
				System.out.println(ex);
			}

			customer = new Customer(name, ID);
			if(accountType.equals("SAVING")) {
				bankAccount = new SavingsAccount(customer, amount);
				mainDisplay = displayMainMenu(customer, bankAccount, primaryStage);
			}else if(accountType.equals("CHEQUING")){
				chequeAccount = new ChequingAccount(amount);
				mainDisplay = displayMainMenu(customer, chequeAccount, primaryStage);
				
			}
		} else {
			mainDisplay = savingOrChequng(primaryStage);
		}
	}

	/**
	 * creates the display for the Create a new account display
	 * 
	 * @param stage
	 *            the stage this will be displayed on
	 */
	public Scene savingOrChequng(Stage stage) {
		GridPane root = new GridPane();
		root.setHgap(10);
		root.setVgap(10);
		
		Button createSavingBtn = new Button("Create Saving Account");
		createSavingBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				accountType = "SAVING";
				System.out.println("creating a savings account");
				mainDisplay = displayCreateAccount(stage);
				stage.setScene(mainDisplay);
				stage.show();
			}
		});
		root.add(createSavingBtn, 1, 2);

		Button createChequingBtn = new Button("Create Chequing Account");
		createChequingBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				accountType = "CHEQUING";
				System.out.println("creating a chequing account");
				mainDisplay = displayCreateAccount(stage);
				stage.setScene(mainDisplay);
				stage.show();
			}
		});
		root.add(createChequingBtn, 1, 4);
		
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		return scene;
	}
	public Scene displayCreateAccount(Stage stage) {
		GridPane root = new GridPane();
		root.setHgap(10);
		root.setVgap(10);

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

				// check to see if user inputs correct values
				try {
					startBalanceTextField.setStyle("-fx-text-fill: black;");
					startBalance = Double.parseDouble(startBalanceTextField.getText());
				} catch (NumberFormatException e) {
					startBalanceTextField.setStyle("-fx-text-fill: red;");
					return;
				}
				// sets up the new display for the new customer and bank account
				customer = new Customer(customerName);
				if(accountType.equals("CHEQUING")) {
					chequeAccount = new ChequingAccount(startBalance);
					mainDisplay = displayMainMenu(customer, chequeAccount, stage);
					updateFile(customer, chequeAccount);
				}else if(accountType.equals("SAVING")){
					bankAccount = new SavingsAccount(customer, startBalance);
					mainDisplay = displayMainMenu(customer, bankAccount, stage);
					updateFile(customer, bankAccount);
					
				}
				stage.setScene(mainDisplay);
				stage.show();
			}
		});
		root.add(compileAccountBtn, 1, 8);
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		return scene;
	}

	/**
	 * creates the bank account information, withdraw, and deposit
	 * 
	 * @param customer
	 *            the customer's information
	 * @param bankAccount
	 *            the bank information
	 * @param stage
	 *            the stage this scene will be displayed onto
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
