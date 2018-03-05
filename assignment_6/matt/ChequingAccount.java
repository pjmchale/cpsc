
public class ChequingAccount extends BankAccount{

	private double overdraftFee;
	private double overdraftAmount = 100.0;

	ChequingAccount(double transactionFee) {
		overdraftFee = transactionFee;
	}

	ChequingAccount(Customer accountholder, double starBalance, double transactionFee) {
		overdraftFee = transactionFee;
		setCustomer(accountholder);
		setBalance(starBalance);

	}

	public double getOverdraftFee() {
		return overdraftFee;
	}

	public void setOverdraftFee(double fee) {
		overdraftFee = fee;
	}

	public void setOverdraftAmount(double amount) {
		overdraftAmount = amount;
	}

	public double getOverdraftAmount() {
		return overdraftAmount;
	}

	@Override
	public void transfer(double amount, BankAccount toAccount) {
        double bal = getBalance();
        if (bal+overdraftFee >= 0){
            withdraw(amount);
            toAccount.deposit(amount);
        }    
    }

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
        }  else if (balAndOverdraftAmount < amount) {
        	System.out.println("No U");
            // bal -= amount;
            // bal -= overdraftFee;
        } else {
        	bal = bal - amount;
        }

        setBalance(bal);
	}
}