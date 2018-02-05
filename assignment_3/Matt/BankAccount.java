public class BankAccount {

	private double balance;
	private double overdraftAmount = 100.0;
	private Customer customer;

	BankAccount(){
		balance = 0.0;
	}

	BankAccount(Customer newCustomer, double newBalance){
		customer = newCustomer;
		balance = newBalance;
	}

	public void setCustomer(Customer newCustomer){
		customer = newCustomer;
	}

	public Customer getCustomer(){
		return customer;
	}


	/**
	 * Used to get the users current balance
	 * @return the current balance
	*/
	public double getBalance() {
		return balance;
	}

	/**
	 * Used to set the users overdraft amount
	 * @param double: The desired overdraft amount
	*/
	public void setOverdraftAmount(double overdraftAmount) {
		this.overdraftAmount = overdraftAmount;
	}

	/**
	 * Checks to see if deposit amount is valid, and will deposit the amount
	 * @param double: The deposit amount
	*/
	public void deposit(double depositAmount) {
		if (depositAmount > 0.0) {
			balance += depositAmount;
		} else {
			System.out.println("You can't deposit an amount less than 0");
		}
	}

	/** 
	 * Check to see if withdraw amount if valid, and will withdraw the amount 
	 * @param double: The withdraw amount
	*/
	public void withdraw(double withdrawAmount) {
		double withdrawLimit = balance + overdraftAmount;
		if (withdrawAmount <= withdrawLimit) {
			balance -= withdrawAmount;
		} else {
			System.out.println("You ain't got that kind of cash!");
		}
	}

}
