import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CreateMenu{
	GridPane root;
	Scene scene;
	public CreateMenu(Stage stage) {
		displayCreateMenu(stage);
	}

	public void displayCreateMenu(Stage primaryStage) {
		GridPane root = new GridPane();
		root.setHgap(10);
		root.setVgap(10);
		root.setPadding(new Insets(0, 10, 0, 13));
		
		Label IDLabel = new Label("Customer ID: ");
		IDLabel.setFont(Font.font("Courier New", 13));
		root.add(IDLabel, 1, 1);
		
		Label nameLabel = new Label("Customer Name: ");
		nameLabel.setFont(Font.font("Courier New", 13));
		root.add(nameLabel, 1, 3);
		

		Label startBalanceLabel = new Label("Start balance: ");
		startBalanceLabel.setFont(Font.font("Courier New", 13));
		root.add(startBalanceLabel, 1, 5);
		
		TextField IDTextField = new TextField();
		root.add(IDTextField, 1, 2);
		TextField nameTextField = new TextField();
		root.add(nameTextField, 1, 4);
		TextField startBalanceTextField = new TextField();
		root.add(startBalanceTextField, 1, 6);
	
		Button createButton = new Button("Create");
		createButton.setOnAction(new HandleButtonClick("display",primaryStage));
		root.add(createButton, 1, 8);
		
		Scene scene = new Scene(root, 300, 300);
		primaryStage.setScene(scene);

	}
	
}