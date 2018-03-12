/**
 * This savings account class extends bank account.
 * it controls interest rates and fees for the account
 * withdraw and transfer functions
 */
public class SavingsAccount extends BankAccount{
  double annualInterestRate;

  SavingsAccount(){
    annualInterestRate = 0.0;
  }

  /**
   * Constructor for setting customer, start balance and overdraft fee
   * @param inAccountHolder customer to set account to
   * @param startBalance initial balance for the account
   * @param interestRate overdraft fee for the account
   */
  SavingsAccount(Customer inAccountHolder, double startBalance, double interestRate){
    setBalance(startBalance);
    setCustomer(inAccountHolder);
    annualInterestRate= interestRate/100.00;
  }

  /**
   * @return the annual interest rate
   */
  public double getAnnualInterestRate(){
    return annualInterestRate;
  }

  /**
   * set the annual interest rate
   * @param inFee input overdraft fee
   */
  public void setAnnualInterestRate(double interestRate){
    annualInterestRate = interestRate/100.00;
  }


  /**
   * Saving account implementation. Gets fees and interest of account
   * @return the total monthly fees and interest
   */
  protected double getMonthlyFeesAndInterest(){
    if(getBalance() < 1000.0){
      return (getBalance()*((1.0/12.0)*annualInterestRate) - 5);
    }
    return (getBalance()*((1.0/12.0)*annualInterestRate));
  }

}
