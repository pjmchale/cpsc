
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
	public void withdraw(double amount) {

		double bal = getBalance();
		// bal += amount;

		if (amount < 0) {
            System.out.println("Cannot withdraw a negative amount");
        } else 
        if ((bal + overdraftAmount) >= amount) {
            bal -= amount;
            bal -= overdraftFee;
        } else {
        	bal -= amount;
          // System.out.println("Overdraft Fee!!");
        }

        setBalance(bal);
	}
}