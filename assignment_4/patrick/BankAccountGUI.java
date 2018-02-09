import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class BankAccountGUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
      primaryStage.setTitle("Bank Account");
      
      Text bankAccountText = new Text("Bank Account"); 
      Button btnDeposit = new Button();
      btnDeposit.setText("Deposit");
      btnDeposit.setOnAction(new EventHandler<ActionEvent>() {

          @Override
          public void handle(ActionEvent event) {
              System.out.println("Hello World!");
          }
      });

      StackPane root = new StackPane();
      root.getChildren().add(btn);
      primaryStage.setScene(new Scene(root, 300, 250));
      primaryStage.show();
    }
}
