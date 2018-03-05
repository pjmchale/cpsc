/**
 * Simulates a chequing account with overdraft
 * Allows withdraws and deposits
 */
public class ChequingAccount extends BankAccount{
    private double overdraftAmount = 100.0;
    private double overdraftFee = 3;

    public ChequingAccount(double transactionFee){
        overdraftFee = transactionFee;
    }
    public ChequingAccount(Customer accountHolder,double startBalance, double transactionFee){
        setBalance(startBalance);
        setCustomer(accountHolder);
        overdraftFee = transactionFee;
    }

    public void setOverdraftAmount(double overdraftAmount) {
        this.overdraftAmount = overdraftAmount;
    }

    public double getOverdraftAmount() {
        return overdraftAmount;
    }

    public void setTransactionFee(double overdraftFee) {
        this.overdraftFee = overdraftFee;
    }

    public double getTransactionFee() {
        return overdraftFee;
    }

    public void withdraw(double amount){
        if (getBalance() + overdraftAmount - amount >= 0){
            setBalance(getBalance() - amount);
            if (getBalance() < 0){
                setBalance(getBalance() - overdraftFee) ;
            }
        }
    }
    public void transfer(double amount, BankAccount toAccount){
        if (amount <= (getBalance() + overdraftAmount)) {
            withdraw(amount);
            toAccount.deposit(amount);
        }
    }
}
