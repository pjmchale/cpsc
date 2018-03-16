/**
 * This is the function for running the risk game
 * it maintains the state of the game and contains
 * are the functions for the game high level game logic
 */

import java.util.*;

public class GameManager{ 

  private int numPlayers;
  private Player[] players;
  private Map map;
  private MapGUI mapGUI;
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
    initializeMap();
  }

  /**
   * getter for game map
   */
  public Map getMap(){
    return map;
  }

  /**
   * getter for game mapGUI
   */
  public MapGUI getMapGUI(){
    return mapGUI;
  }
  /**
   * creates the map for the game
   */
  private void initializeMap(){
    map = new Map();
    mapGUI = new MapGUI();
  }

  /**
   * getter for current player
   */
  public Player getCurrentPlayer(){
    return currentPlayer;
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
    if(distributeUnits){
      // if Country is not owned, set owner and place unit on country
      if(countryClicked.getOwner() == null){
        countryClicked.setOwner(currentPlayer);
        currentPlayer.placeUnits(countryClicked, 1);
        distributeUnits = false;
        nextTurn();
        return;
      // if all countries owned just place a unit
      }else if(allCountriesOwned()){
        if(countryClicked.getOwner() == currentPlayer){
          currentPlayer.placeUnits(countryClicked, 1);
          distributeUnits = false;
          nextTurn();
        }
      }
    }

    /* For when player is attacking */
    if(attacking){
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

    /* For when player is fortifying*/
    if(fortify){
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

    /* For when player is placing new units */
    if(placeUnits){
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
  }

  /**
   * determines if all countries are owned by someone
   * @return true is all countries are owned false if not
   */
  public boolean allCountriesOwned(){
	  ArrayList<Country> countries;

    countries = MainMenu.getMap().getCountries();
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
      System.out.println(players[i].getName() + ":" + players[i].getCountriesOwned().size() + "/" + allCountries.size());
      if(players[i].getCountriesOwned().size() >= allCountries.size()){
        MainGUI.gameOver(players[i]);
      }
    }
  }

  /**
   * Creates all players for the game
   */
  public void initializePlayers(int numHuman, int numAI, String[] names){

    numPlayers = numHuman + numAI;
    players = new Player[numPlayers];
    for(int i=0; i < numHuman; i++){
      players[i] = new Player(names[i]);
    }
    //for(; i < numPlayers; i++){
    //  players[i] = new AIPlayer();
    //}
    
    MainGUI.showLegend();
  }

  /**
   * getter for number of players
   */
  public int getNumPlayers(){
    return numPlayers;
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
  private void calcDistributeUnits(){
    int numNewUnits = 3;
    int numUnits;
    int userChoice;
    ArrayList<Country> countriesOwned;
    Country country;

    countriesOwned = currentPlayer.getCountriesOwned();

    if(countriesOwned.size() > numNewUnits){
      numNewUnits = countriesOwned.size();
    }
    currentPlayer.setAvailableUnits(numNewUnits);

    setPlaceUnits();
    MainGUI.distributeUnitsTurn(currentPlayer);
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


}

