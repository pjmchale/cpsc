/**
 * Simulates a bank account with overdraft
 * Allows withdraws and deposits
 */
public class BankAccount{

    private double balance;
    private double overdraftAmount = 100.0;
    private Customer customer;

    /**
     * Constructor with input arguments of Customer and balance and sets the corresponding instance variables
     * @param inputCustomer the input Customer class
     * @param @inputBalance the input balance to set the account balance to
     */
    BankAccount(Customer inputCustomer, double inputBalance){
      setCustomer(inputCustomer);
      if(inputBalance < 0){
        System.out.println("Cannot initialize with negative balance");
      } else{
        balance = inputBalance;
      }
    }

    /**
     * Constructor with no input arguments. Sets the balance to 0.0
     */
    BankAccount(){
      balance = 0.0;
    }

    /**
     * sets the Customer class instance variable
     * @param inputCustomer
     */
    void setCustomer(Customer inputCustomer){
      customer = inputCustomer;
    }

    /**
     * return the customer Class associated with the account
     * @return customer
     */
    Customer getCustomer(){
      return customer;
    }

    /**
     * Adds the requested deposit to the balance
     * @param amount is the desired deposit amount
     */
    public void deposit(double amount){
        if (amount < 0){
            System.out.println("Cannot deposit a negative amount");
        }
        else if (amount >= 0){
            balance += amount;
        }
    }

    /**
     * withdraws the requested amount from the balance without exceeding the overdraftAmount
     * @param amount is the desired withdraw amount
     */
    public void withdraw(double amount){
        if (amount < 0) {
            System.out.println("Cannot withdraw a negative amount");
        }
        else if ((amount - balance) <= overdraftAmount){
            balance -= amount;
        } else{
          System.out.println("Not enough money in account");
        }
    }

    /**
     *
     * @return the current balance
     */
    public double getBalance(){
        return balance;

    }

    /**
     * Sets the overdraftAmount
     * @param value is the maximum overdraftAmount
     */
    public void setOverdraftAmount(double value){
        overdraftAmount = value;
    }


    public static void main(String[] args){
      new Trace();
    }
}

class Trace{

  Trace(){
    System.out.println("Customer c1 = new Customer(\"Alison Brown\", 123);");
    Customer c1 = new Customer("Alison Brown", 123);

    sleepPrint();
    
    System.out.println("BankAccount b1 = new BankAccount(c1, 100.00);");
    BankAccount b1 = new BankAccount(c1, 100.00);

    sleepPrint();

    System.out.println("Customer c2 = b1.getCustomer();");
    Customer c2 = b1.getCustomer();

    sleepPrint();
    
    System.out.println("c2.setName(\"Charles Green\");");
    c2.setName("Charles Green");

    sleepPrint();
    
    System.out.println("System.out.println(c1);");
    System.out.println(c1);

    sleepPrint();

    System.out.println("System.out.println(c2);");
    System.out.println(c2);

    sleepPrint();

    System.out.println("Customer c3 = c1;");
    Customer c3 = c1;

    sleepPrint();

    System.out.println("c3.setName(\"Eva White\");");
    c3.setName("Eva White");

    sleepPrint();

    System.out.println("System.out.println(b1.getCustomer());");
    System.out.println(b1.getCustomer());

    sleepPrint();

    System.out.println("System.out.println(c3);");
    System.out.println(c3);

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

  void sleepPrint(){
    System.out.println();
    sleepSeconds(1.5);
  }

}
