import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class BankAccount extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		VBox root = new VBox();

		Label balanceLabel = new Label("Balance: ");
		balanceLabel.setFont(Font.font("Courier New", 15));
		root.getChildren().add(balanceLabel);

		Label nameLabel = new Label("NAME");
		nameLabel.setFont(Font.font("Courier New", 15));
		root.getChildren().add(nameLabel);

		Button depositButton = new Button("Deposit");
		depositButton.setOnAction(new HandleButtonClick("deposit"));
		root.getChildren().add(depositButton);

		Button withdrawButton = new Button("Withdraw");
		withdrawButton.setOnAction(new HandleButtonClick("withdraw"));
		root.getChildren().add(withdrawButton);

		TextField amountTextField = new TextField();
		root.getChildren().add(amountTextField);

		Scene scene = new Scene(root, 600, 600);
		primaryStage.setTitle("Bank Account");
		primaryStage.setScene(scene);
		primaryStage.show();
	}


}