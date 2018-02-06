import java.util.Arrays;
import java.util.Scanner;
/**
 * Class representing a player
 */
public class Player{
    private String name;
    private ArrayList<Country>countriesOwned;
    private int totalUnits;
    private int availableUnits;

    /**
     * Constructors for a player
     * @param name
     */

    public void Player(String name){
        this.name = name;
        totalUnits = 0;
        availableUnits = 0;
        countriesOwned = new ArrayList<Country>();
    }

    public String getName(){
        return name;
    }

    public ArrayList<Country> getCountriesOwned(){
        return countriesOwned;
    }

    public void gainCountry(Country countryWon){
        countriesOwned.add(countryWon);
    }

    public void loseCountry(Country countryLost){
        countriesOwned = countriesOwned.remove(countryLost);
    }



    //public void placeUnits(Country country, int units){
        //add units, remove from available units
    //    if (hasCountry(country)){
    //        if (units <= availableUnits) {
    //            country.addUnits(units);
    //            availableUnits -= units;
    //        }
    //    }
    //}

    public void placeUnits(Country country, int units){
        if (country.getOwner() == this){
            if (units <= availableUnits && units >= 0) {
                            country.addUnits(units);
                            availableUnits -= units;
            }
        }
    }
    public boolean attack(Country fromCountry, Country toCountry){
        if (fromCountry.getOwner() == this ){
            if (fromCountry.isNeighbour(toCountry)){
                Combat battle = new Combat(fromCountry, toCountry);
                battle.simulateCombat();
                return True;
        //        }
            }
        }
        else {
            return False;
        }
    }

    public void moveUnits(Country fromCountry, Country toCountry){
        //
        do{
            int units = receiveInt("How many units would you like to move?");
            if (fromcountry.getNumUnits() - units > 0 %% units >= 0){
                toCountry.addUnits(units);
                fromCountry.addUnits(-units);
                }
                else if (units < 0){
                    System.out.println("Can't move a negative amount");
                }
                else {
                    System.out.println("1 unit has to be left behind");
                }
            } while(fromcountry.getNumUnits() - units > 0);
    }


//    public boolean hasCountry(Country country){
//        for (Country n : countriesOwned) { if (country == n) { return true; } }
//        return false;
//    }

    public void setAvailableUnits(int availableUnits) {
        this.availableUnits = availableUnits;
        totalUnits += availableUnits;
    }

    public int getAvailableUnits() {
        return availableUnits;
    }

    void public setTotalUnits(int units){
        totalUnits = units;
    }

    int public getTotalUnits(){
        return totalUnits;
    }

    private int receiveInt(String displayString){
        Scanner kb = new Scanner(System.in);

        System.out.print(displayString);
        while(!kb.hasNextInt()){
            System.out.println("Please enter a valid integer...");
            System.out.print(displayString);
            kb.next();
        }
        return kb.nextInt();
    }

}