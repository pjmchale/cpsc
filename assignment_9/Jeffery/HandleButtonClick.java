import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Handle the button click and act upon it
 */
public class HandleButtonClick implements EventHandler<ActionEvent> {

	private String actionType = "";
	private BankAccount account;
	private TextField actionAmountTextField;
	private Label actionBalanceLabel;
	private GridPane gp;
	private Stage Pstage;

	/**
	 * Used get the
	 * 
	 * @param buttonName:
	 *            a String to check between deposit and withdraw
	 * @param bankAccount:
	 *            a BankAccount that is of the customers
	 * @param amountTextField:
	 *            a TextField of the withdraw or deposit amount
	 * @param balanceLabel:
	 *            a Label of the customers balance
	 */
	public HandleButtonClick(String buttonName, BankAccount bankAccount, TextField amountTextField,
			Label balanceLabel) {
		actionType = buttonName;
		account = bankAccount;
		actionAmountTextField = amountTextField;
		actionBalanceLabel = balanceLabel;
	}

	public HandleButtonClick(String buttonName, Stage stage) {
		actionType = buttonName;
		Pstage = stage;
	}

	/**
	 * Used to handle the button click Will take the value from the balanceLabel and
	 * apply a withdraw or deposit
	 * 
	 * @param event:
	 *            a ActionEvent
	 */
	@Override
	public void handle(ActionEvent event) {

		double changeAmount;
		try {
			changeAmount = Double.parseDouble(actionAmountTextField.getText());
			actionAmountTextField.setStyle("-fx-text-fill: black;");
		if(changeAmount < 0) {
			actionAmountTextField.setText("Negative input");
			actionAmountTextField.setStyle("-fx-text-fill: red;");
		}else if(changeAmount == 0){
			actionAmountTextField.setText("(0) invalid");
			actionAmountTextField.setStyle("-fx-text-fill: red;");			
		}else if(account.canWithdraw(changeAmount) == false){
			actionAmountTextField.setText("insufficient funds");
			actionAmountTextField.setStyle("-fx-text-fill: red;");						
		}else {			
			if (actionType == "withdraw") {
				account.withdraw(changeAmount);
			} else if (actionType == "deposit") {
				account.deposit(changeAmount);
			}
		}
		} catch (NumberFormatException e) {
			changeAmount = 0;
			actionAmountTextField.setText("String input");
			actionAmountTextField.setStyle("-fx-text-fill: red;");
		}
		actionBalanceLabel.setText("Balance: " + account.getBalance());
	}
}
