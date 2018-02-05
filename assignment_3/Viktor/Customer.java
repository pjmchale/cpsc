
public class Customer{
    private String name;
    private int customerID;

    public String getName(){
        return name;
    }
    public void setName(String newName){
        name = newName;
    }
    public int getID(){
        return customerID;
    }
    public void setCustomerID(int newID){
        customerID = newID;
    }
    public String toString(){
        return ("Name: "); //+ name + "\n" + "ID: " + Integer.toString(customerID));
    }
    public Customer(String startName, int startCustomerID){
        name = startName;
        customerID = startCustomerID;
    }
    public Customer() {
        name = "Not Set";
        customerID = 0;
    }
    public Customer(Customer customer){
        name = customer.name;
        customerID = customer.customerID;
    }
}