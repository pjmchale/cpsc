/**
 * This is the function for running the risk game
 * it maintains the state of the game and contains
 * are the functions for the game high level game logic
 */

package GameEngine;

import PlayerPackage.*;
import CombatEngine.*;
import MapStage.*;
import java.util.*;

public class GameManager{ 

  private boolean usingGUI = false;
  private int numPlayers;
  private Player[] players;
  private WorldMap map;
  private Player currentPlayer;
  private Country countryClicked;
  private Country toCountry;
  private Country fromCountry;
  private int turnIndex;
  private Player firstTurn;
  private int firstTurnIndex;
  private boolean distributeUnits = false;
  private boolean attacking = false;
  private boolean fortify = false;
  private boolean placeUnits = false;
  private boolean turn = false;

  /* Consstructor initializes the map*/
  GameManager(){
    initializeMap(usingGUI);
  }

  /* Constructor initilaize map with gui */
  GameManager(boolean withGUI){
    usingGUI = withGUI;
    initializeMap(usingGUI);
  }

  /**
   * getter for game map
   */
  public WorldMap getMap(){
    return map;
  }

  /**
   * creates the map for the game
   */
  private void initializeMap(boolean withGUI){
    map = new WorldMap(withGUI);
  }

  /**
   * getter for current player
   */
  public Player getCurrentPlayer(){
    return currentPlayer;
  }

  /**
   * setter for current player
   */
  public void setCurrentPlayer(Player player){
    currentPlayer = player;
  }

  /**
   * getter for all players
   */
  public Player[] getAllPlayers(){
    return players;
  }

  /**
   * getter for country clicked
   */
  public void setCountryClicked(Country country){
    countryClicked = country;
    processCountryClick();
  }

  /**
   * setter for country clicked
   * @return country
   */
  public Country getCountryClicked(){
    return countryClicked;
  }

  /**
   * gets the from country selected
   */
  public Country getFromCountry(){
    return fromCountry;
  }

  /**
   * Determines what to do with country click based off 4 possible game states:
   *  Distributing Units: Inital distribution of units at beginning of game
   *  Attacking: Current player attacking from owned country to other players country
   *  Fortifying: Move units from owned country to another owned country (also ends turn)
   *  Placing Units: At beginning of each turn player gets to place new units onto board
   * 
   */
  public void processCountryClick(){

    /* Distributing units phase, set country owner */
    if(distributeUnits) distributeUnitsState();

    /* For when player is attacking */
    if(attacking) attackState();

    /* For when player is fortifying*/
    if(fortify) fortifyState();

    /* For when player is placing new units */
    if(placeUnits) placeUnitsState();
   
  }

  /**
   * Code to run when distribute units state is set at beginning of game
   * after country is clicked
   */
  private void distributeUnitsState(){
    // if Country is not owned, set owner and place unit on country
    if(countryClicked.getOwner() == null){
      countryClicked.setOwner(currentPlayer);
      currentPlayer.placeUnits(countryClicked, 1);
      nextTurn();
      MainGUI.continueCountrySelection();
      return;
    // if all countries owned just place a unit
    }else if(allCountriesOwned()){
      if(countryClicked.getOwner() == currentPlayer){
        currentPlayer.placeUnits(countryClicked, 1);
        nextTurn();
        MainGUI.continueCountrySelection();
      }
    }
  }

  /**
   * Code to run when attack state is set after country click
   */
  private void attackState(){
    // if country they area attacking from is not set, ensure they own the country and their is enough units
    if(fromCountry == null){
      if(countryClicked.getOwner() == currentPlayer && countryClicked.getUnits() > 1){
        fromCountry = countryClicked;
        getMap().showNeighbours(countryClicked);
        MainGUI.setCountrySelectionLabel("Please Select Country To Attack");
      }else{
        return;
      }
    // if country they area attacking from is not set, ensure they own the country and their is enough units
    }else{
      if(countryClicked.getOwner() != currentPlayer && countryClicked.isNeighbour(fromCountry)){
        toCountry = countryClicked;
        getMap().hideNeighbours();
        MainGUI.removeAttackGUIElements();
        MainGUI.startAttack(fromCountry, toCountry);
      }else{
        return;
      }
    }
  }

  /**
   * Code to run when attack state is set after country click
   */
  private void fortifyState(){
    // Select country to move units from 
    if(fromCountry == null){
      if(countryClicked.getOwner() == currentPlayer){
        fromCountry = countryClicked;
        getMap().showNeighboursOwner(countryClicked, currentPlayer);
        MainGUI.setCountrySelectionLabel("Please Select Country To Move Units To");
      }else{
        return;
      }
    // Select country to move units too
    }else if(toCountry == null)
      if(countryClicked.getOwner() == currentPlayer && countryClicked.isNeighbour(fromCountry)){
        toCountry = countryClicked;
        getMap().hideNeighbours();
        MainGUI.setCountrySelectionLabel("How Many Units Would You Like To Move? (" + (fromCountry.getUnits()-1) + " available)");
        MainGUI.addFortifyGUIElements();
      }else{
        return;
      }
  }
  
  /**
   * Code to run when place units state is set (begginning of turn) after country click
   */
  private void placeUnitsState(){
   // Select country to place a unit
    if(countryClicked.getOwner() == currentPlayer){
      currentPlayer.placeUnits(countryClicked, 1);
      MainGUI.setGainedUnitsLabel(currentPlayer.getName() + " Select Country To Place Gained Units! (" + currentPlayer.getAvailableUnits() + " available)");
      if(currentPlayer.getAvailableUnits() == 0){
        MainGUI.removePlaceUnitsGUIElements();
        clearState();
        MainGUI.nextPane();
      }
    }
  }



  /**
   * determines if all countries are owned by someone
   * @return true is all countries are owned false if not
   */
  public boolean allCountriesOwned(){
	  ArrayList<Country> countries;

    countries = getMap().getCountries();
    for(int i=0; i < countries.size();i++){
      if(countries.get(i).getOwner() == null){
        return false;
      }
    }

    return true;
  }

  /**
   * sets boolean for distributing units phase
   * @state distributing units state (true false)
   */
  public void setDistributeUnits(boolean state){
    distributeUnits = state;
  } 

  /**
   * Checks if player has won the game i.e. owns every territory on the map
   * if there is a winner it tells MainGUI to end the game
   */
  public void checkIfGameOver(){
    ArrayList<Country> allCountries = map.getCountries();
    for(int i=0; i < players.length ; i++){
      if(players[i].getCountriesOwned().size() >= allCountries.size()){
        MainGUI.gameOver(players[i]);
      }
    }
  }

  /**
   * Creates all players for the game
   */
  public void initializePlayers(int numHuman, int numAI, String[] names){
    int i;
    int k=0;

    numPlayers = numHuman + numAI;
    players = new Player[numPlayers];
    for(i=0; i < numHuman; i++){
      players[i] = new Player(names[i]);
    }
    for(; i < numPlayers; i++){
      players[i] = new AiPlayerSimple("AI #" + k, getMap());
      k++;
    }
    
    if(usingGUI){
      MainGUI.showLegend();
    }
  }

  /**
   * getter for number of players
   */
  public int getNumPlayers(){
    return numPlayers;
  }

  /**
   * For running turn when an AI is the current player
   */
  public void AITurn(){

    // if still in initial board distribution
    if(distributeUnits){
      // claim country if still open countries
      // else place a unit on country owned
      if(!allCountriesOwned()){
        currentPlayer.claimCountry2();
        nextTurn();
        return;
      }else{
        currentPlayer.placeUnit();
      }
      nextTurn();
      return;
    }

    // Play regular turn
    currentPlayer.playTurn();

  }


  /**
   * Switches turn to next player. 
   * If during regular turn, turn distribution of units is called
   */
  public void nextTurn(){
    turnIndex++;
    turnIndex %= numPlayers;
    currentPlayer = players[turnIndex];
    MainGUI.setPlayerTurnLabel(currentPlayer.getName() + "'s Turn");

    if(turn){
      calcDistributeUnits();
    }

    // If current player is AI player then allow to run turn
    if(currentPlayer.getPlayerType().equals("AI")){
      AITurn();
      return;
    }

  }

  /**
   * initializes turn order randomly
   */
  public void initializeTurn(){

    // sleep for 1 second
    /*
    try{
      Thread.sleep(1000);
    } catch (Exception e) {
      System.out.println(e);
    }
    */

    Random rand = new Random();
    turnIndex = rand.nextInt(numPlayers);
    currentPlayer = players[turnIndex];
    firstTurn = currentPlayer;
    firstTurnIndex = turnIndex;

  }

  /**
   * calculates and distributes new units at beginning of turn
   */
  public void calcDistributeUnits(){
    int numNewUnits = 3;
    int numUnits;
    int userChoice;
    ArrayList<Country> countriesOwned;
    Country country;

    countriesOwned = currentPlayer.getCountriesOwned();

    if(countriesOwned.size() > numNewUnits){
      numNewUnits = countriesOwned.size()/3;
    }

    currentPlayer.setAvailableUnits(numNewUnits);

    if(usingGUI && !currentPlayer.getPlayerType().equals("AI")){
      setPlaceUnits();
      MainGUI.distributeUnitsTurn(currentPlayer);
    }
  }


  /**
   * Fortify, send units from owned country to another owned country
   */
  public void fortify(int numUnits){
    currentPlayer.moveUnits(fromCountry, toCountry, numUnits);
    nextTurn();
  }

  /**
   * Clears the current state of the game manager
   */
  public void clearState(){
    attacking = false;
    distributeUnits = false;
    fortify = false;
    placeUnits = false;
    toCountry = null;
    fromCountry = null;
  }

  /**
   * sets the turn state to true. For when regular gameplay begins
   */
  public void setTurnState(){
    turn = true;
  }

  /**
   * Set attacking state for game manager
   */
  public void setAttacking(){
    clearState();
    attacking = true;
  }

  /**
   * Get attacking state for game manager
   */
  public boolean getAttacking(){
    return attacking;
  }

  /**
   * Set ditribute units state for game manager
   */
  public void setDistributeUnits(){
    clearState();
    distributeUnits = true;
  }

  /**
   * Get distribute units state for game manager
   */
  public boolean getDistributeUnits(){
    return distributeUnits;
  }

  /**
   * Set fortifying state for game manager
   */
  public void setFortify(){
    clearState();
    fortify = true;
  }

  /**
   * Get fortifying state for game manager
   */
  public boolean getFortify(){
    return fortify;
  }

  /**
   * Set place units state for game manager
   */
  public void setPlaceUnits(){
    clearState();
    placeUnits = true;
  }

  /**
   * Get place units state for game manager
   */
  public boolean getPlaceUnits(){
    return placeUnits;
  }

  /**
   * automatically sets up the game.
   * Creates players and sets all countries owned
   * to one player except one country
   */
  public void autoSetup(){
    ArrayList<Country> countries;
    String[] names = new String[2];

    // Create players
    names[0] = "Alice";
    names[1] = "Bob";
    initializePlayers(2, 0, names);

    // start turn
    initializeTurn();

    // set owners of all except one country to player 
    countries = getMap().getCountries();
    for(int i=0; i < countries.size()-1; i++){
      countries.get(i).setOwner(currentPlayer);
      currentPlayer.setAvailableUnits(5);
      currentPlayer.placeUnits(countries.get(i), 5);
    }

    // go to next players turn and set them to own last country
    nextTurn();
    countries.get(countries.size()-1).setOwner(currentPlayer);
    currentPlayer.setAvailableUnits(1);
    currentPlayer.placeUnits(countries.get(countries.size()-1), 1);

    // begin regular turn play
    nextTurn();
    setTurnState();

  }

}

