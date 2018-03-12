public class SavingsAccount extends BankAccount {
	
	private double annualInterestRate;

	SavingsAccount() {
		annualInterestRate = 0;
	}

	SavingsAccount(Customer accountHolder, double startBalance, double newAnnualInterestRate) {
		setCustomer(accountHolder);
		setBalance(startBalance);
		annualInterestRate = newAnnualInterestRate;
	}
	
	public double getAnnualInterestRate() {
		return annualInterestRate;
	}

	public void setAnnualInterestRate(double newRate) {
		annualInterestRate = newRate;
	} 

	@Override
	protected double getMonthlyFeesAndInterest() {
		double bal = getBalance();
		double output = 0.0;
		double interest = 0.0;
		if (bal < 1000.0) {
			// setBalance(bal-5.0);
			// output -= 5.0;
			interest = -5.0;
			interest += annualInterestRate/12;
		} else {
			// interest = 5.0;
			interest += annualInterestRate/12;
		}
		
		return bal*interest/100;
	}


}
