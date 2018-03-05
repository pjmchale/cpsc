public class Assign6Trace {
	public static void main(String[] args){
		System.out.println("Customer c = new Customer(\"John Doe\", 321);");
		Customer c = new Customer("John Doe", 321);

    sleepPrint();

		System.out.println("BankAccount b1 = new BankAccount(c,100.0);");
		BankAccount b1 = new BankAccount(c,100.0);

    sleepPrint();

		System.out.println("ChequingAccount b2 = new ChequingAccount(c, 200.0, 12.0);");
		ChequingAccount b2 = new ChequingAccount(c, 200.0, 12.0);

    sleepPrint();

		System.out.println("b2.setOverdraftAmount(150.0);");
		b2.setOverdraftAmount(150.0);

    sleepPrint();

    System.out.println("BankAccount b3 = b2");
		BankAccount b3 = b2;

    sleepPrint();
		
		System.out.println("b1.getBalance() = " + b1.getBalance() + ", b3.getBalance() = " +  b3.getBalance());

    sleepPrint();

		System.out.println("b1.withdraw(110);");
		b1.withdraw(110);

    sleepPrint();

		System.out.println("b1.getBalance() = " + b1.getBalance() + ", b3.getBalance() = " +  b3.getBalance());

    sleepPrint();
		
		System.out.println("b2.withdraw(300.0);");
		b2.withdraw(300.0);
		System.out.println("b1.getBalance() = " + b1.getBalance() + ", b3.getBalance() = " +  b3.getBalance());

    sleepPrint();
		
		System.out.println("b1.transfer(50.0, b2);");
		b1.transfer(50.0, b2);
		System.out.println("b1.getBalance() = " + b1.getBalance() + ", b3.getBalance() = " +  b3.getBalance());

    sleepPrint();
		
		System.out.println("b2.transfer(88,b1);");
		b2.transfer(88,b1);
		System.out.println("b1.getBalance() = " + b1.getBalance() + ", b3.getBalance() = " +  b3.getBalance());
	}

  static void sleepSeconds(double seconds){
    try {
      // thread to sleep for 1000 milliseconds
      seconds *= 1000;
      Thread.sleep((int) seconds);
    } catch (Exception e) {
        System.out.println(e);
    }
  }

  static void sleepPrint(){
    System.out.println();
    sleepSeconds(1.5);
  }
}
