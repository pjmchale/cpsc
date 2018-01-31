/**
 * simulates a customer
 */
public class Customer{
	private String name;
	private Int customerID;
	/**
	 * 
	 * @param name 
	 * The name of the customer
	 * @param customerID
	 * the unique ID of the customer
	 */
	Customer(String name, int customerID){
		this.name = name;
		this.customerID = customerID;
	}
	/**
	 * returns the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * sets a new name
	 * @param name
	 * the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * returns the customerID
	 */
	public int getCustomerID() {
		return customerID;
	}
	/**
	 * sets a new customerID
	 * @param customerID
	 * the new customerID
	 */
	public void setCUstomerID(int customerID) {
		this.customerID = customerID;
	}
	/**
	 * default message 
	 */
	public void toString() {
		System.out.println("name: " + name + "\ncustomer ID: "+customerID);
	}
	
}