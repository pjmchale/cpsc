import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class representing a player
 */
public class Player{
    private String name;
    private int id;
    private ArrayList<Country> countriesOwned;
    private int totalUnits;
    private int availableUnits;
    private static int numPlayer = 1;

    /**
     * Constructors for a player
     * @param name is the name of the player
     */
    Player(String name){
        this.name = name;
        id = numPlayer;
        numPlayer++;
        totalUnits = 0;
        availableUnits = 0;
        countriesOwned = new ArrayList<Country>();
    }

    /**
     * Getter and Setters for needed instance variables
     */
    public String getName(){
        return name;
    }

    public int getId() { return id; }

    public ArrayList<Country> getCountriesOwned(){
        return countriesOwned;
    }

    public void setTotalUnits(int units){
        totalUnits = units;
    }

    public int getTotalUnits(){
        return totalUnits;
    }

    public void setAvailableUnits(int units) {
        availableUnits = units;
        totalUnits += units;
    }

    public int getAvailableUnits() {
        return availableUnits;
    }

    public void loseUnits(int units){totalUnits -= units;}
    /**
     * @return a string signifying that this player is Human
     */
    public String getPlayerType(){return "HUMAN";}

    /**
     * Methods to gain and lose a country
     * @param countryWon is a country to be added to the countries owned by player
     * @param countryLost is a country to be lost by the player
     */
    public void gainCountry(Country countryWon){
        countriesOwned.add(countryWon);
    }

    public void loseCountry(Country countryLost){
        countriesOwned.remove(countryLost);
    }


    /**
     * Places a number of units at a owned country
     * @param country represents what country the units will be placed at
     * @param units represents how many units to be added
     */
    public void placeUnits(Country country, int units){
        if (country.getOwner() == this){
            if (units <= availableUnits && units >= 0) {
                country.addUnits(units);
                availableUnits -= units;
            }
            else if (units < 0){
                System.out.println("Can't place a negative amount");
            }
            else {
                System.out.println("You do not have that many units available.");
            }
        }
        else {
            System.out.println("Please select a country you own");
        }
    }


    /**
     * Attack a country from an owned country, simulate the battle
     * @param attackingCountry represents the attacking country
     * @param defendingCountry represents the defending country
     * @return a boolean to signify if the attack was a valid attack
     */

    public void attack(Country attackingCountry, Country defendingCountry){
        if (attackingCountry.getOwner() == this ){
            if (attackingCountry.isNeighbour(defendingCountry)){
                Combat battle;
                battle = new Combat(attackingCountry, defendingCountry);
                //battle.simulateBattle();
                //return true;
            }
            else {
                System.out.println("Something went wrong 113");
            }
        }
        else {
            System.out.println("Something went wrong 117");
        }
        //return false;
    }



    /**
     * Takes two countries as variables and moves units between the two.
     * @param fromCountry represents the country the units are moving from
     * @param toCountry represents the country the units are moving to
     */
    public void moveUnits(Country fromCountry, Country toCountry){
        //
        if (fromCountry.isNeighbour(toCountry)){
            int units;
            do{
                units = receiveInt("How many units would you like to move?");
                if (fromCountry.getUnits() - units <= 0){
                    System.out.println("1 unit has to be left behind");
                }
                else if (units < 0){
                    System.out.println("Can't move a negative amount");
                }

            } while(fromCountry.getUnits() - units <= 0 || units < 0);
            toCountry.addUnits(units);
            fromCountry.addUnits(-units);
        }
        else{
            System.out.println("Countries are not neighbours");
        }
    }

    public void moveUnits(Country fromCountry, Country toCountry, int units){
        toCountry.addUnits(units);
        fromCountry.addUnits(-units);

    }

    /**
     * Temporary method for getting an int from user
     * @param displayString is the string displayed for input
     * @return the next Int inputed into the console
     */
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