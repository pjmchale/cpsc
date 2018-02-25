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
  Customer customer;
  BankAccount bankAccount;
  Scene rootScene;

	/**
	 * Build the GUI and link the buttons to the button handler
	*/
	@Override
	public void start(Stage primaryStage) {


		customer = new Customer("John Smith", 22);
		bankAccount = new BankAccount(customer, 100.0);

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

    /* Gridpane for create account scene */
    GridPane createAccountGrid = new GridPane();
		createAccountGrid.setHgap(10);
		createAccountGrid.setVgap(10);
		createAccountGrid.setPadding(new Insets(0, 10, 0, 10));

    /* Create account button on root scene */
    Button createAccount = new Button("Create Account");
    createAccount.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          Scene scene = new Scene(createAccountGrid, 400, 300);
          primaryStage.setScene(scene);
          primaryStage.show();
        }
    });
		root.add(createAccount, 1, 5);

    /* Create the stage */
    rootScene = new Scene(root, 400, 300);
		primaryStage.setTitle("Bank Account");
		primaryStage.setScene(rootScene);
		primaryStage.show();

    /**
     * Build create bank account scene
     **/

    
		/* Customer ID label */
		Label customerIDLabel = new Label("Customer ID");
		customerIDLabel.setFont(Font.font("Courier New", 15));
		createAccountGrid.add(customerIDLabel, 1, 1);

    /* Customer ID text field */
		TextField customerIDTextField = new TextField();
		createAccountGrid.add(customerIDTextField, 2, 1);
    
		/* Customer Name label */
		Label customerNameLabel = new Label("Customer Name");
		customerNameLabel.setFont(Font.font("Courier New", 15));
		createAccountGrid.add(customerNameLabel, 1, 2);

    /* Customer name text field */
		TextField customerNameTextField = new TextField();
		createAccountGrid.add(customerNameTextField, 2, 2);

    /* Start Balance label */
		Label startBalanceLabel = new Label("Start Balance");
		startBalanceLabel.setFont(Font.font("Courier New", 15));
		createAccountGrid.add(startBalanceLabel, 1, 3);

    /* Customer start balance field */
		TextField startBalanceTextField = new TextField();
		createAccountGrid.add(startBalanceTextField, 2, 3);

    /* Create button */
    Button createAccountButton = new Button("Create Account");
		createAccountGrid.add(createAccountButton, 1, 5);
    createAccountButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          String customerName = customerNameTextField.getText();
          int customerID;
          double startBalance;

          try {
            customerID =  Integer.parseInt(customerIDTextField.getText());
          } catch (NumberFormatException e){
            return;
          }

          try {
            startBalance =  Double.parseDouble(startBalanceTextField.getText());
          } catch (NumberFormatException e){
            return;
          }

          customer = new Customer(customerName, customerID);
          bankAccount = new BankAccount(customer, startBalance);

          /* HAVE TO UPDATE DEPOSIT AND WITHDRAW HANDLE CLICK */

          nameLabel.setText(customerName);
          balanceLabel.setText("Balance: " + bankAccount.getBalance());
          primaryStage.setScene(rootScene);
          primaryStage.show();
        }
    });

    
		
    
    
	}

}
