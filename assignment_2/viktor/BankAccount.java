/**
 * Simulates a bank account with overdraft
 * Allows withdraws and deposits
 */
public class BankAccount{

    private double balance;
    private double overdraftAmount = 100.0;

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

}
