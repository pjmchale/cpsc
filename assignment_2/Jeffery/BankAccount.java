
public class BankAccount {
	private double balance = 0;
	private double overdraftAmount = 100.0;
	
	
	public double getOverdraftAmount() {
		return overdraftAmount;
	}
	public void setOverdraftAmount(double overdraftAmount) {
		this.overdraftAmount = overdraftAmount;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public void deposit(double in) {
		if(in > 0) {
		balance += in;
		System.out.println("you have deposite: " +in+"$\n current balance is: " + balance);
		}
	}
	public void withdraw(double out) {
		double minimum = -overdraftAmount;
		if(out < 0) {
			System.out.println("cannont withdraw a negative number");
		}else if(balance-out >= minimum) {
			balance -= out;
			System.out.println("you are withdrawing: " +out+"$\n current balance is: " + balance);
		}
		
	}
	
}
