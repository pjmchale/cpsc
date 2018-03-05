/**
 * Simulates a bank account with overdraft
 * Allows withdraws and deposits
 */
public class BankAccount{

    private double balance;
    private Customer customer;

    /**
     * Constructor with no input arguments. Sets the balance to 0.0
     */
    BankAccount(){
      balance = 0.0;
    }

    /**
     * Constructor with input arguments of Customer and balance and sets the corresponding instance variables
     * @param inputCustomer the input Customer class
     * @param @inputBalance the input balance to set the account balance to
     */
    BankAccount(Customer inputCustomer, double inputBalance){
      customer = inputCustomer;
      if(inputBalance < 0){
        System.out.println("Cannot initialize with negative balance");
      } else{
        balance = inputBalance;
      }
    }


    /**
     * sets the Customer class instance variable
     * @param inputCustomer
     */
    public void setCustomer(Customer inputCustomer){
      customer = inputCustomer;
    }

    /**
     * return the customer Class associated with the account
     * @return customer
     */
    public Customer getCustomer(){
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
        if (balance-amount >= 0){
            balance -= amount;
           // return true;
        } 
        // else {
        //     return false;
        // }
        // if (amount < 0) {
        //     System.out.println("Cannot withdraw a negative amount");
        // }
        // else if ((amount - balance) <= overdraftAmount){
        //     balance -= amount;
        // } else{
        //   System.out.println("Not enough money in account");
        // }
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
    // public void setOverdraftAmount(double value){
    //     overdraftAmount = value;
    // }


    public void transfer(double amount, BankAccount toAccount) {
        if (balance-amount >= 0){
            withdraw(amount);
            toAccount.deposit(amount);
        }
        
    }

    protected void setBalance(double amount) {
        balance = amount;
    }

}

