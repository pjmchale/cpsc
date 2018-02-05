public class Customer {
	private String name; 
	private int custromerID;

	Customer(){
		name = "None";
		custromerID = 0;
	}

	Customer(String custromerName, int id){
		name = custromerName;
		custromerID = id;
	}

	Customer(Customer returningCustomer){
		name = returningCustomer.getName();
		custromerID = returningCustomer.getID();
	}

	public String getName(){
		return name;
	}
	public void setName(String input){
		name = input;
	}

	public int getID(){
		return custromerID;
	}
	public void setID(int input){
		custromerID = input;
	}

	public String toString(){
		// System.out.println();
		String strOfID = Integer.toString(custromerID);
		return "Hello, " + name + " your ID is: " + strOfID;
	}

}