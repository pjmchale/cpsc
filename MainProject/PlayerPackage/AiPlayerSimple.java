package PlayerPackage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import MapStage.*;
import GameEngine.*;
import CombatEngine.*;


/**
 * Class representing an AI player
 * extends Player
 */
public class AiPlayerSimple extends Player{
    private ArrayList<Country> conflicts = new ArrayList<Country>();;
    private WorldMap map;
    private LinkedHashMap<Country,Double> turnValues = new LinkedHashMap<Country,Double>();
    private boolean turnOver;
    private int counter = 0;
    private AiMove move;
    private AiMove fortification;

    /**
     * Basic Constructor for an AiPlayerSimple
     * @param name
     * @param map is required for the ai to make non-random decisions
     */
    public AiPlayerSimple(String name, WorldMap map){
        super(name);
        this.map = map;
    }
    private boolean checkIfWon(){
        for (Country country : map.getCountries()){
            if (country.getOwner() != this){
                return false;
            }
        }
        return true;
    }

    /**
     * @return a string signifying that this player is AI
     */
    public String getPlayerType(){return "AI";}

    /**
     * Main method that plays the turn
     * Since this is the simple version of the ai, it only looks at the most valuable move it can and does it.
     * This method will simulate a single move, is called until no move can be made anymore
     */
    public void playTurn(){
        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException ex)
        {
            System.out.println(ex);
        }
        if (checkIfWon()) {
            MainGUI.nextPane();
            return;
        }
        move = determineMove();
        //This happens only at the start of the turn, when the Ai can fortify.
        if (getAvailableUnits()>0){
            System.out.printf("Attacking %s from %s", move.getToCountry().getName(),move.getFromCountry().getName());
            move.getFromCountry().addUnits(getAvailableUnits());
            setAvailableUnits(0);
            MainGUI.removeAttackGUIElements();
            MainGUI.startAttack(move.getFromCountry(), move.getToCountry());
        }

        else if (move != null){
            System.out.printf("Attacking %s from %s", move.getToCountry().getName(),move.getFromCountry().getName());
            MainGUI.removeAttackGUIElements();
            MainGUI.startAttack(move.getFromCountry(), move.getToCountry());
        }
        //Fortifications at end
        else if(determineFortification() != null){
            fortification = determineFortification();
            fortification.getFromCountry().addUnits(-(fortification.getNumUnits()));
            fortification.getToCountry().addUnits(fortification.getNumUnits());
            MainGUI.nextPane();
            MainGUI.nextTurn();
        }
        else {
            MainGUI.nextPane();
            MainGUI.nextTurn();
        }
    }

    /**
     * @param country is the country that it's attacking from
     * @return the amount of units sent to attack
     */
    public int getAttackingUnits(Country country){
        if (country.getUnits() > 3){
            return 3;
        }
        return country.getUnits() - 1;
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
        if (move == null) return;
        move.getToCountry().setOwner(this);
        move.getToCountry().addUnits(move.getNumUnits());
        move.getFromCountry().addUnits(-move.getNumUnits());
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
            else if (owner == null){
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
        //Test Print
        //System.out.println(turnValues);
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
        //Test Print
        //System.out.println(conflicts);
    }

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
        //System.out.println("Available Units: \n" + getAvailableUnits() + "\n");

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
            System.out.println("Something's weird y'all, AiPlayer - 234");
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
        //System.out.println(turnValues);
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