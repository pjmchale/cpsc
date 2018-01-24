public class BankAccount{

    /** These are instance variables **/
    public double balance;
    public double overdraftAmount = 100.0;

    /**
     * Adds the requested deposit to the balance
     * @param dep is the desired deposit amount
     */
    public void deposit(double dep){
        getBalance();
        if (dep >= 0){
            balance += dep;
        }
    }

    /**
     * withdraws the requested amount from the balance without exceeding the overdraftAmount
     * @param wit is the desired withdraw amount
     */
    public void withdraw(double wit){
        getBalance();
        if ((wit - balance) <= overdraftAmount){
            balance -= wit;
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
