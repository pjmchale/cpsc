import java.util.Arrays;
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

    public void getCountriesOwned(){
        return countriesOwned;
    }

    public void gainCountry(Country countryWon){
        countriesOwned.add(countryWon);
    }

    public void loseCountry(Country countryLost){
        countriesOwned = ArrayUtils.remove(countriesOwned, countryLost);
    }



    public void placeUnits(Country country, int units){
        //add units, remove from available units
        if (hasCountry(country)){
            if (units <= availableUnits) {
                country.addUnits(units);
                availableUnits -= units;
            }
        }
    }
    public void attack(Country fromCountry, Country toCountry){
        //if (hasCountry(fromCountry)){
        //    if (!hasCountry(toCountry)){
        //        if fromcountry.isNeighbhour(toCountry){
        Combat battle = new Combat(fromCountry, toCountry);
        battle.simulateCombat();
        //        }
        //    }
        //}
    }
    public void moveUnits(Country fromCountry, Country toCountry, int units){
        //
        if (hasCountry(fromCountry)){
            if fromcountry.isNeighbhour(toCountry){
                if (fromcountry.getNumUnits() - units > 0){
                    toCountry.addUnits(units);
                    fromCountry.addUnits(-units);
                }
            }
        }
    }

    public boolean hasCountry(Country country){
        for (Country n : countriesOwned) { if (country == n) { return true; } }
        return false;
    }

    public void setAvailableUnits(int availableUnits) {
        this.availableUnits = availableUnits;
    }

    public int getAvailableUnits() {
        return availableUnits;
    }

    void public setTotalUnits(int units){
        totalUnits = units;
    }

}