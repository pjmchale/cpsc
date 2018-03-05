/**
 * Simulates a bank account
 * Allows withdraws and deposits
 */
public class BankAccount{

    private double balance;
    private Customer accountHolder;

    public BankAccount(Customer customer, double startBalance) {
        balance = startBalance;
        accountHolder = customer;
    }
    public BankAccount(){
        balance = 0.0;
        accountHolder = new Customer();
    }
    public void setCustomer(Customer customer){
        accountHolder = customer;
    }

    public Customer getCustomer(){
        return accountHolder;
    }

    public double getBalance(){
        return balance;

    }
    protected void setBalance(double newBalance){
        balance = newBalance;
    }
    public void deposit(double amount){
        if (amount < 0){
            System.out.println("Cannot deposit a negative amount");
        }
        else if (amount >= 0){
            balance += amount;
        }
    }

    public void withdraw(double amount){
        if ((balance - amount) >= 0){
            balance -= amount;
        }
    }

    public void transfer(double amount, BankAccount toAccount){
        if (amount <= balance) {
            withdraw(amount);
            toAccount.deposit(amount);
        }
    }

}
