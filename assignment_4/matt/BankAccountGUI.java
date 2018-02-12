import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Start the program
*/
public class BankAccountGUI extends Application {

	/**
	 * Build the GUI and link the buttons to the button handler
	*/
	@Override
	public void start(Stage primaryStage) {

		Customer customer = new Customer("John Smith", 22);
		BankAccount bankAccount = new BankAccount(customer, 100.0);

		/* Create the grid to store the visual elements */
		GridPane root = new GridPane();
		root.setHgap(10);
		root.setVgap(10);
		root.setPadding(new Insets(0, 10, 0, 10));

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

		/* Create the stage */
		Scene scene = new Scene(root, 300, 300);
		primaryStage.setTitle("Bank Account");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
