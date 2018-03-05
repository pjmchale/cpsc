
public class ChequingAccount extends BankAccount{
  double overdraftFee;
  double overdraftAmount = 100.00;

  ChequingAccount(double transactionFee){
    overdraftFee = transactionFee;
  }

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
    System.out.println("Amount = " + (amount - getBalance()));
    if (amount < 0) {
      System.out.println("Cannot withdraw a negative amount");
    }
    else if ((amount - getBalance()) <= overdraftAmount){
      setBalance(getBalance() - amount);
      if(getBalance() < 0){
        setBalance(getBalance() - overdraftFee);
      }
    } else{
      System.out.println("Not enough money in account");
    }
  }
}
