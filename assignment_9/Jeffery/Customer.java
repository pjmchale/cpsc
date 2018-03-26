import java.util.Random;

final public class Customer{
  private String name;
  private int customerID;

  /**
   * Constructor for class with no arguments
   */
  Customer(){
    name = "No Name";
    customerID = 0;
    generateID();
  }
  
  public void generateID() {
	  Random rng = new Random();
	  customerID = rng.nextInt(9999 - 1000 + 1) + 1000;
  }
  /**
   * Constructor for class, with anme and id arguments.
   * @param customerName name to set customer to
   * @param id id to set customerID to
   */
  Customer(String customerName){
    name = customerName;
    generateID();
  }
  Customer(String customerName, int ID){
	  name = customerName;
	  customerID = ID;
  }

  /**
   * Constructor for class with oldCustomer input
   * @param customerName name to set customer to
   * @param id id to set customerID to
   */
  Customer(Customer oldCustomer){
    name = oldCustomer.getName();
    customerID = oldCustomer.getID();
  }

  /**
   * Returns the customers name
   * @return name
   */
  public String getName(){
    return name;
  }

  /**
   * Sets the customer name
   * @param newName name to change customer name to
   * */
  public void setName(String newName){
    name = newName;
  }

  /**
   * Returns the customer ID
   * @return customerID
   */
  public int getID(){
    return customerID;
  }

  /**
   * Sets the customer ID
   * @param id input id to set customer ID to
   */
  public void setID(int id){
    customerID = id;
  }

  /**
   * Returns formatted string describing values of instance variables
   */
  public String toString(){
    return "Customer Name: " + name + ", Customer ID: " + customerID;
  }


}



