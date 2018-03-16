import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Class representing an AI player
 */
public class AiPlayerSimple extends Player{
    private ArrayList<Country> conflicts;
    private Map map;
    private LinkedHashMap<Country,Double> turnValues;
    private boolean turnOver;

    /**
     * Basic Constructor for an AiPlayerSimple
     * @param name
     * @param map is required for the ai to make non-random decisions
     */
    public AiPlayerSimple(String name, Map map){
        super(name);
        this.map = map;
    }

    /**
     * @return a string signifying that this player is AI
     */
    public String getPlayerType(){return "AI";}

    /**
     * Main method that plays the turn
     * Since this is the simple version of the ai, it only looks at the most valuable move it can and does it.
     * This method will simulate the whole turn until it can no longer do an action
     */
    public void playTurn(){
        turnOver = false;
        AiMove move = determineMove();
        move.getFromCountry().addUnits(getAvailableUnits());
        setAvailableUnits(0);
        int unitsLost;
        Combat combat;
        while (turnOver == false){
            if (move == null){
                turnOver = true;
            }
            else{
                while (move.getNumUnits() > 0 && move.getToCountry().getOwner() != this){
                    combat = new Combat(move.getFromCountry(), move.getToCountry());
                    move.setNumUnits(move.getFromCountry().getUnits() - 1);
                }
                move = determineMove();
            }
        }
        AiMove fortification = determineFortification();
        if (fortification != null){
            fortification.getFromCountry().addUnits(-(fortification.getNumUnits()));
            fortification.getToCountry().addUnits(fortification.getNumUnits());
        }
    }
    public int getAttackingUnits(Country country){
        if (country.getUnits() >= 3){
            return 3;
        }
        return country.getUnits();
    }
    public int getDefendingUnits(Country country){
        if (country.getUnits() >= 2){
            return 2;
        }
        return country.getUnits();
    }

    /**
     * Calculates a relative value for each country on the game board
     * Takes into account who owns it, how many neighbours this owns, player ranking
     */
    public void calculateTurnValues(){
        Player owner;
        double baseValue = 1.0/3.0;
        double value;
        int numUnowned;
        int numNeighboursUnowned;
        ArrayList<Country> countries = map.getCountries();
        int ownerStanding;
        Player[] players = MainGUI.getAllPlayers();
        LinkedHashMap<Country,Double> unsortedValues = new LinkedHashMap<Country,Double>();
        turnValues.clear();

        for (Country country : countries){
            owner = country.getOwner();
            if (owner == this){
                value = 0;
            }
            else {
                value = baseValue;
                numNeighboursUnowned = 0;
                if ( country.getUnits() > 0 && country.getUnits() <= 10){
                    value += (1 / Math.pow(2, country.getUnits()-1));
                }
                for (Country neighbour : map.getNeighbours(country)) {
                    if (neighbour.getOwner() != this) {
                        numNeighboursUnowned++;
                    }
                }
                value += ((double)(map.getNeighbours(country).size() - numNeighboursUnowned)) / (map.getNeighbours(country).size());

                ownerStanding = 1;
                for (Player player : players) {
                    if (owner.getTotalUnits() < player.getTotalUnits()) {
                        ownerStanding++;
                    }
                }
                value += ((double)(players.length - ownerStanding + 1) / players.length );
            }
            unsortedValues.put(country,value);
        }

        double highest;
        Country country = null;
        while (unsortedValues.size() > 0){
            highest = 0;
            for (Country key : unsortedValues.keySet()){
                if (unsortedValues.get(key) >= highest){
                    highest = unsortedValues.get(key);
                    country = key;
                }
            }
            turnValues.put(country,highest);
            unsortedValues.remove(country);
        }
    }

    /**
     * Determines what countries owned are bordering countries owned by other players
     */
    public void determineConflicts(){
        conflicts = new ArrayList<Country>();
        ArrayList<Country> neighbours;
        for (Country country : getCountriesOwned()) {
            neighbours = map.getNeighbours(country);
            for (Country neighbour : neighbours) {
                if (neighbour.getOwner() != this) {
                    conflicts.add(country);
                }
            }
        }
    }

    /**
     * Method used in the claiming of countries (before the actual game begins)
     * @return a country to be claimed , null if no countries are available
     */
    public Country claimCountry(){
        calculateTurnValues();
        for (Country key: turnValues.keySet()){
            if (key.getOwner() == null){
                return key;
            }
        }
        return null;
    }

    /**
     * Determines if it should fortify
     * @return an  AiMove or null if no fortification is required
     */
    public AiMove determineFortification(){
        determineConflicts();
        ArrayList<Country> neighbours;
        for (Country country : getCountriesOwned()) {
            if (country.getUnits() > 1 && !(conflicts.contains(country))){
                neighbours = map.getNeighbours(country);
                for (Country neighbour : neighbours) {
                    if (neighbour.getOwner() == this &&(conflicts.contains(neighbour))) {
                        return new AiMove(country,neighbour,country.getUnits()-1);
                    }
                }

            }

        }
        return null;
    }

    /**
     * Determines what move it shoud make, based on the values of the countries
     * @return an AiMove corresponding to that attack
     */
    public AiMove determineMove(){
        determineConflicts();
        calculateTurnValues();
        ArrayList<Country> neighbours = new ArrayList<Country>();
        double highestValue = 0.0;
        Country toCountry = null;
        Country fromCountry = null;
        AiMove move;

        if (getAvailableUnits() > 0){
            for (Country conflict: conflicts){
                neighbours = map.getNeighbours(conflict);
                for (Country neighbour: neighbours){
                    if (turnValues.get(neighbour) > highestValue){
                        highestValue = turnValues.get(neighbour);
                        toCountry = neighbour;
                        fromCountry = conflict;
                    }
                }
            }
            move = new AiMove(toCountry, fromCountry, getAvailableUnits() + fromCountry.getUnits() - 1 );
            return move;
        }

        else if (canAttack()){
            for (Country conflict: conflicts){
                neighbours = map.getNeighbours(conflict);
                for (Country neighbour: neighbours){
                    if (turnValues.get(neighbour) > highestValue){
                        highestValue = turnValues.get(neighbour);
                        toCountry = neighbour;
                        fromCountry = conflict;
                    }
                }
            }
            move = new AiMove(toCountry, fromCountry, fromCountry.getUnits() - 1 );
            return move;
        }
        else {
            return null;
        }
    }

    /**
     * Checks to see if it can still attack
     * @return true if it can, false if not
     */
    public boolean canAttack(){
        if (conflicts.size() > 0){
            for (Country country : conflicts){
                if (country.getUnits() > 1){
                    return true;
                }
            }
        }
        return false;

    }
}