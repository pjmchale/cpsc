package PlayerPackage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import MapStage.*;
import GameEngine.*;
import CombatEngine.*;


/**
 * Class representing an AI player
 */
public class AiPlayerSimple extends Player{
    private ArrayList<Country> conflicts = new ArrayList<Country>();;
    private WorldMap map;
    private LinkedHashMap<Country,Double> turnValues = new LinkedHashMap<Country,Double>();
    private boolean turnOver;
    private int counter = 0;
    private AiMove move;
    /**
     * Basic Constructor for an AiPlayerSimple
     * @param name
     * @param map is required for the ai to make non-random decisions
     */
    public AiPlayerSimple(String name, WorldMap map){
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
        boolean turnOver = false;
        int unitsLost;
        Combat combat;
        counter = 0;
        System.out.print("Available Units:");
        System.out.println(getAvailableUnits());

        //Adds available units to the first country
        move = determineMove();
        move.getFromCountry().addUnits(getAvailableUnits());
        setAvailableUnits(0);

        //Test Print
        System.out.println("Stuck 1.1 \n");
        System.out.println("To Country:");
        System.out.println(move.getToCountry() + "\n");
        System.out.println("From Country:");
        System.out.println(move.getFromCountry()+ "\n");
        System.out.println("Move units:");
        System.out.println(move.getNumUnits()+ "\n");

        //Plays the turn until it can no longer do a valid move

        while (turnOver == false && counter < 5){
            counter++;
            System.out.println("Stuck 2.2 \n");
            if (move == null){
                System.out.println("Turn Over");
                turnOver = true;
            }
            else{
                counter = 0;
                while (move.getNumUnits() > 0 && move.getToCountry().getOwner() != this && counter < 10){

                    //Test Print
                    counter++;
                    System.out.println("Stuck 3.3 \n");
                    System.out.println("To Country:");
                    System.out.println(move.getToCountry() + "\n");
                    System.out.println("From Country:");
                    System.out.println(move.getFromCountry()+ "\n");
                    System.out.println("Move units:");
                    System.out.println(move.getNumUnits()+ "\n");

                    MainGUI.removeAttackGUIElements();
                    MainGUI.startAttack(move.getFromCountry(), move.getToCountry());

                    move.setNumUnits(move.getFromCountry().getUnits() - 1);
                }
                move = determineMove();
            }
        }
        //Fortifies if it can
        AiMove fortification = determineFortification();
        if (fortification != null){
            fortification.getFromCountry().addUnits(-(fortification.getNumUnits()));
            fortification.getToCountry().addUnits(fortification.getNumUnits());
        }
        MainGUI.nextPane();
        MainGUI.nextTurn();
    }

    /**
     * @param country is the country that it's attacking from
     * @return the amount of units sent to attack
     */
    public int getAttackingUnits(Country country){
        if (country.getUnits() >= 3){
            return 3;
        }
        return country.getUnits();
    }
    /**
     * @param country is the country that it's defending from
     * @return the amount of units sent to defend
     */
    public int getDefendingUnits(Country country){
        if (country.getUnits() >= 2){
            return 2;
        }
        return country.getUnits();
    }
    public void moveUnits(){
        move.getToCountry().addUnits(move.getNumUnits);
        move.getFromCOuntry().addUnits(-move.getNumUnits);
    }
    /**
     * Calculates a relative value for each country on the game board
     * Takes into account who owns it, how many neighbours this owns and player ranking
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
        if (turnValues.size()>0){
            turnValues.clear();
        }


        //Loops through every country on the map, assigning a value and adding the two to a unsorted LinkedHashMap
        for (Country country : countries){
            owner = country.getOwner();
            if (owner == this){
                value = 0;
            }
            else {
                value = baseValue;
                numNeighboursUnowned = 0;

                //Adds value based on how many units are in the country
                if ( country.getUnits() > 0 && country.getUnits() <= 5){
                    value += (1 / Math.pow(2, country.getUnits()-1));
                }

                //Adds value based on how many of a countries neighbour this AI owns
                for (Country neighbour : map.getNeighbours(country)) {
                    if (neighbour.getOwner() != this) {
                        numNeighboursUnowned++;
                    }
                }
                value += ((double)(map.getNeighbours(country).size() - numNeighboursUnowned)) / (map.getNeighbours(country).size());

                //Adds value based on the standing of the owner, Higher Standing = higher value, makes the AI go after the player in first place
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
        //Sorts the unsorted LinkedHashMap into a sorted one base on the values
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
        conflicts.clear();
        ArrayList<Country> neighbours;
        for (Country country : getCountriesOwned()) {
            neighbours = map.getNeighbours(country);
            for (Country neighbour : neighbours) {
                if (neighbour.getOwner() != this) {
                    conflicts.add(country);
                }
            }
        }
        //System.out.println(conflicts);
    }

    /**
     * Method used in the claiming of countries (before the actual game begins)
     * Currently uses the second method since TurnValues is not the correct way to calculate the value for a country
    public void claimCountry(){
        calculateTurnValues();
        for (Country key: turnValues.keySet()) {
            if (key.getOwner() == null) {
                key.setOwner(this);
                this.placeUnits(key,1);
                return;
            }
        }
    }
     **/


    /**
     * Method used in the claiming of countries (before the actual game begins)
     * Currently just claims countries in the order of the arraylist found in the map
     * Basically goes NA > SA > EU etc...
     */
    public void claimCountry2(){
        for (Country country: map.getCountries()) {
            if (country.getOwner() == null) {
                country.setOwner(this);
                this.placeUnits(country,1);
                return;
            }
        }
    }

    /**
     * Method that places units in the place unit face of the game
     * Perioritizes countries that are boarding a unowned country with more or equal units
     */
    public void placeUnit(){
        //Test Print
        System.out.println("Available Units: \n" + getAvailableUnits() + "\n");

        determineConflicts();
        ArrayList<Country> neighbours;
        for (Country conflict: conflicts) {
            neighbours = map.getNeighbours(conflict);
            for (Country neighbour : neighbours) {
                if (neighbour.getOwner() != this && neighbour.getUnits() >= conflict.getUnits()) {
                    //Test Print
                    System.out.println("Placed in: \n" + conflict.getName() + "\n");

                    this.placeUnits(conflict, 1);
                    return;
                }
            }
        }
        //Backup if somehow there is no neighbouring unowned country with more or even amount of units
        for (Country conflict: conflicts){
            System.out.println("Something's weird y'all");
            this.placeUnits(conflict, 1);
            return;
        }
    }

    /**
     * Determines if it should fortify
     * @return AiMove if fortification required or null if no fortification required
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
        System.out.println(turnValues);
        ArrayList<Country> neighbours = new ArrayList<Country>();
        double highestValue = 0.0;
        Country toCountry = null;
        Country fromCountry = null;
        AiMove move;

        //This is only during the first move of a turn
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
            move = new AiMove(fromCountry, toCountry, getAvailableUnits() + fromCountry.getUnits() - 1 );
            return move;
        }

        //This happens as long as the AI has a valid attack it can do
        else if (canAttack()){
            for (Country conflict: conflicts){
                if (conflict.getUnits() > 1){
                    neighbours = map.getNeighbours(conflict);
                    for (Country neighbour: neighbours){
                        if (turnValues.get(neighbour) > highestValue){
                            highestValue = turnValues.get(neighbour);
                            toCountry = neighbour;
                            fromCountry = conflict;
                        }
                    }
                }
            }
            move = new AiMove(fromCountry, toCountry, fromCountry.getUnits() - 1 );
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