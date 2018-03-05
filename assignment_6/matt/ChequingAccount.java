
public class ChequingAccount extends BankAccount{

	private double overdraftFee;
	private double overdraftAmount = 100.0;

	/**
     * Creates a new chequing acount
     * @param The overdraft fee
    */
	ChequingAccount(double transactionFee) {
		overdraftFee = transactionFee;
	}

	/**
     * Creates a new chequing acount and will assign a customer and start balance
     * @param The account holder
     * @param The starting balance
     * @param The overdraft fee
    */
	ChequingAccount(Customer accountholder, double starBalance, double transactionFee) {
		overdraftFee = transactionFee;
		setCustomer(accountholder);
		setBalance(starBalance);

	}

	/**
     * Gets the overdraft fee
     * @return The overdraft fee
    */
	public double getOverdraftFee() {
		return overdraftFee;
	}

	/**
     * Sets the overdraft fee
     * @param The new overdraft fee
    */
	public void setOverdraftFee(double fee) {
		overdraftFee = fee;
	}

	/**
     * Sets the overdraft amount
     * @param The new overdraft amount
    */
	public void setOverdraftAmount(double amount) {
		overdraftAmount = amount;
	}

	/**
     * Gets the overdraft amount
     * @return The overdraft amount
    */
	public double getOverdraftAmount() {
		return overdraftAmount;
	}

	/**
     * Sends money from one bank account to another
     * @param The transfer amount
     * @param The account to send the money to
    */
	@Override
	public void transfer(double amount, BankAccount toAccount) {
        double bal = getBalance();
        if (bal+overdraftFee >= 0){
            withdraw(amount);
            toAccount.deposit(amount);
        }    
    }

    /**
     * Will withdraw from the chequing account with the overdraft accounted for
	 * @param The withdraw amount 
    */
	@Override
	public void withdraw(double amount) {

		double bal = getBalance();
		// bal += amount;
		double balAndOverdraftAmount = bal + overdraftAmount;
		if (amount < 0) {
            System.out.println("Cannot withdraw a negative amount");
        } else if (balAndOverdraftAmount >= amount && amount > bal) {
            bal = bal - amount;
            bal = bal - overdraftFee;
            System.out.println("Transfer successfull, overdraft fee encured");
        } else if (balAndOverdraftAmount < amount) {
        	System.out.println("This exceed your limit");
        } else {
        	bal = bal - amount;
        	System.out.println("Transfer successfull.");
        }

        setBalance(bal);
	}
}