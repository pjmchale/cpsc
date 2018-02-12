import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class BankAccountGUI extends Application {
  Customer customer;
  BankAccount bankAccount;
  Button btnDeposit;
  Button btnWithdraw;
  TextField valueTextField;
  Text accountBalance;


  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Bank Account");
    customer = new Customer("John Smith", 123);
    bankAccount = new BankAccount(customer, 100);

    /* Bank account title text */
    Text bankAccountTitle = new Text();
    bankAccountTitle.setText("Bank Account:");
    bankAccountTitle.setX(25);
    bankAccountTitle.setY(25);

    /* Bank Account Name Text */
    Text accountName = new Text();
    accountName.setText("Name: " + customer.getName());
    accountName.setX(25);
    accountName.setY(40);

    /* Bank Account Balance Text */
    accountBalance = new Text();
    accountBalance.setText("Balance: $" + bankAccount.getBalance());
    accountBalance.setX(25);
    accountBalance.setY(55);

    /* Input text field */
    valueTextField = new TextField();
    valueTextField.setText("0.0");
    //valueTextField.setAlignment(Pos.CENTER);
    valueTextField.setLayoutX(100);
    valueTextField.setLayoutY(75);
    //valueTextField.layoutY(50);


    /* Deposit button */
    btnDeposit = new Button();
    btnDeposit.setText("Deposit");
    btnDeposit.setLayoutX(75);
    btnDeposit.setLayoutY(125);
    btnDeposit.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        double value;
        try{
          value = Double.parseDouble(valueTextField.getText());
        } catch(NumberFormatException e) {
          value = 0;
        }
        bankAccount.deposit(value);
        accountBalance.setText("Balance: $" + bankAccount.getBalance());

      }
    });
    
    /* Withdraw button */
    btnWithdraw= new Button();
    btnWithdraw.setLayoutX(200);
    btnWithdraw.setLayoutY(125);
    btnWithdraw.setText("Withdraw");
    btnWithdraw.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          double value;
          try{
            value = Double.parseDouble(valueTextField.getText());
          } catch(NumberFormatException e) {
            value = 0;
          }
          bankAccount.withdraw(value);
          accountBalance.setText("Balance: $" + bankAccount.getBalance());
   
        }
    });

  
    Pane root = new Pane();
    root.getChildren().add(btnDeposit);
    root.getChildren().add(btnWithdraw);
    root.getChildren().add(bankAccountTitle);
    root.getChildren().add(accountName);
    root.getChildren().add(accountBalance);
    root.getChildren().add(valueTextField);
    primaryStage.setScene(new Scene(root, 400, 250));
    primaryStage.setResizable(false);
    primaryStage.show();
  }


}
