public class SavingsAccount extends BankAccount {
	
	static private double annualInterestRate = 0.0;

	/**
     * Constructor for creating a savings account
    */
	SavingsAccount(){}

	/**
     * Constructor for setting customer, start balance and overdraft fee
     * @param inAccountHolder customer to set account to
     * @param startBalance initial balance for the account
     * @param interestRate overdraft fee for the account
    */
	SavingsAccount(Customer accountHolder, double startBalance) {
		setCustomer(accountHolder);
		setBalance(startBalance);
	}
	
	/**
     * @return the annual interest rate
    */
	static public double getAnnualInterestRate() {
		return annualInterestRate;
	}

    /**
     * set the annual interest rate
     * @param inFee input overdraft fee
    */
	static public void setAnnualInterestRate(double newRate) {
		annualInterestRate = newRate;
	} 

	/**
     * Saving account implementation. Gets fees and interest of account
     * @return the total monthly fees and interest
    */
	protected double getMonthlyFeesAndInterest() {
		double bal = getBalance();
		double interest = 0.0;
		if (bal < 1000.0) {
			interest = -5.0;
			interest += annualInterestRate/12;
		} else {
			interest += annualInterestRate/12;
		}
		return bal*interest/100;
	}


}
