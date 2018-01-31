
public class Customer{
  String name;
  int customerID;

  /**
   * Constructor for class. Sets name and id of customer
   * @param customerName name to set customer to
   * @param id id to set customerID to
   */
  Customer(String customerName, int id){
    name = customerName;
    customerID = id;
  }

  /**
   * Returns the customers name
   * @return name
   */
  String getName(){
    return name;
  }

  /**
   * Sets the customer name
   * @param newName name to change customer name to
   * */
  void setName(String newName){
    name = newName;
  }

  /**
   * Returns the customer ID
   * @return customerID
   */
  int getCustomerID(){
    return customerID;
  }

  /**
   * Sets the customer ID
   * @param id input id to set customer ID to
   */
  void setCustomerID(int id){
    customerID = id;
  }

  public String toString(){
    return "Customer Name: " + name + ", Customer ID: " + customerID;
  }

  public static void main(String[] args){
    new Trace();
  }

}


class Trace{

  Trace(){
    BankAccount b1 = new BankAccount();
  }

}
