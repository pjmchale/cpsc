import java.util.concurrent.TimeUnit;

public class BankAccount{
  private double balance;
  private double overdraftAmount;

  /**
   * class constructor sets initial values for balance and overdraftAmount
   */
  BankAccount(){
    this.balance = 0.0;
    this.overdraftAmount = 100.0;
  }

  /**
   * deposits money into the account but updating the balance by input amount
   *
   * @param amount amount of money to increase balance by
   */
  void deposit(double amount){
    if(amount > 0.0){
      this.balance += amount;
    } else{
      System.out.println("[*] Cannot deposit a negative amount");
    }
  }

  /**
   * withdraws money from account by subtracting from balance
   * also checks that account has high enough balance to withdraw (balance + overdraftAmount)
   *
   * @param amount amount of money to withdraw from account
   */
  void withdraw(double amount){
    if(amount <= (balance + overdraftAmount) && amount > 0){
      this.balance -= amount;
    } else {
      System.out.println("[*] Cannot withdraw $" + amount + " (balance: " + this.balance 
          + ", overdraft amount = " + this.overdraftAmount + ")");
    }
  }

  /**
   * Return the current balance of the bank account
   * @return balance of the account
   */
  double getBalance(){
    return this.balance;
  }

  /**
   * sets the overdraft amount for the bank account
   *
   * @param overdraftAmount new overdraft amount to set for bank account
   */
  void setOverdraftAmount(double overdraftAmount){
    this.overdraftAmount = overdraftAmount;
  }


  static public void main(String[] args){
    new Trace();
  }

}


class Trace{

  Trace(){
    System.out.println("BankAccount b1 = new BankAccount();");
    BankAccount b1 = new BankAccount();

    sleepSeconds(1.5);
    System.out.println();

    System.out.println("b1.getBalance()\n" + b1.getBalance());

    sleepSeconds(1.5);
    System.out.println();

    System.out.println("b1.withdraw(200)");
    b1.withdraw(200);

    sleepSeconds(1.5);
    System.out.println();

    System.out.println("b1.deposit(100)");
    b1.deposit(100);
    
    sleepSeconds(1.5);
    System.out.println();

    System.out.println("b1.getBalance()\n" + b1.getBalance());

    sleepSeconds(1.5);
    System.out.println();

    System.out.println("b1.withdraw(200)");
    b1.withdraw(200);

    sleepSeconds(1.5);
    System.out.println();

    System.out.println("b1.getBalance()\n" + b1.getBalance());

    sleepSeconds(1.5);
    System.out.println();

    System.out.println("b1.deposit(-100)");
    b1.deposit(-100);

    sleepSeconds(1.5);
    System.out.println();

    System.out.println("b1.deposit(500)");
    b1.deposit(500);

    sleepSeconds(1.5);
    System.out.println();

    System.out.println("b1.getBalance()\n" + b1.getBalance());

    sleepSeconds(1.5);
    System.out.println();

    System.out.println("BankAccount b2 = new BankAccount();");
    BankAccount b2 = new BankAccount();

    sleepSeconds(1.5);
    System.out.println();

    System.out.println("b2 = b1;");
    b2 = b1;

    sleepSeconds(1.5);
    System.out.println();

    System.out.println("b2.getBalance()\n" + b2.getBalance());

    sleepSeconds(1.5);
    System.out.println();
    
    System.out.println("b1.deposit(500)");
    b1.deposit(500);

    sleepSeconds(1.5);
    System.out.println();

    System.out.println("b2.getBalance()\n" + b2.getBalance());

    sleepSeconds(1.5);
    System.out.println();
  }

  void sleepSeconds(double seconds){
    try {
      // thread to sleep for 1000 milliseconds
      seconds *= 1000;
      Thread.sleep((int) seconds);
    } catch (Exception e) {
        System.out.println(e);
    }
  }

}


