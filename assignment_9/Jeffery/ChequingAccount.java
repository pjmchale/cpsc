/**
 * This chequing account class extends bank account.
 * it controls overdraft limits and fees and overrides
 * withdraw and transfer functions
 */
public class ChequingAccount extends BankAccount{
  double overdraftFee;
  double overdraftAmount = 100.00;

  /**
   * Constructor
   * @param transactionFee sets the overdraft fee for the account
   */
  ChequingAccount(double transactionFee){
    overdraftFee = transactionFee;
  }

  /**
   * Constructor for setting customer, start balance and overdraft fee
   * @param inAccountHolder customer to set account to
   * @param startBalance initial balance for the account
   * @param transactionFee overdraft fee for the account
   */
  ChequingAccount(Customer inAccountHolder, double startBalance, double transactionFee){
    setBalance(startBalance);
    setCustomer(inAccountHolder);
    overdraftFee = transactionFee;
  }

  /**
   * @return the overdraft fee
   */
  public double getOverdraftFee(){
    return overdraftFee;
  }

  /**
   * set the overdraft fee
   * @param inFee input overdraft fee
   */
  public void setOverdraftFee(double inFee){
    overdraftFee = inFee;
  }

  /**
   * @return the overdraftAmount
   */
  public double getOverdraftAmount(){
    return overdraftAmount;
  }

  /**
   * sets the overdraft amount
   * @param inAmount overdraft amount for account
   */
  public void setOverdraftAmount(double inAmount){
    overdraftAmount = inAmount;
  }

  /**
   * withdraws the requested amount from the balance without exceeding the overdraftAmount
   * @param amount is the desired withdraw amount
   */
  @Override
  public void withdraw(double amount){
    if (amount < 0) {
      System.out.println("Cannot withdraw a negative amount");
    }
    else if (canWithdraw(amount)){
      setBalance(getBalance() - amount);
      if(getBalance() < 0){
        setBalance(getBalance() - overdraftFee);
      }
    } else{
      System.out.println("Not enough money in account");
    }
  }
  public boolean canWithdraw(double amount) {
	  return (amount - getBalance()) <= overdraftAmount;
  }
	
	@Override
	protected double getMonthlyFeesAndInterest() {
    double bal = getBalance();
    if (bal > 0) {
      return 0.0;
    } else {
      return 0.2*bal;
    }
	}

  /**
   * Transfers money from this account to another
   * @param amount amount to transfer
   * @param toAccount Account to send money too
   */
  // @Override
  // public void transfer(double amount, BankAccount toAccount){
  //   if((amount - getBalance()) <= overdraftAmount){
  //     withdraw(amount);
  //     toAccount.deposit(amount);
  //   }
  // }
}
