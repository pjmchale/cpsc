/**
 * This is the main logic class for running the risk game
 * It maintains the state of the game and contains
 * all the functions for the game high level game logic
 */

package GameEngine;

import PlayerPackage.*;
import CombatEngine.*;
import MapStage.*;
import java.util.*;
import java.io.*;

public class GameManager {

  // Instance Variables
  private boolean usingGUI = false;

  // Player variables 
  private int numPlayers;
  private Player[] players;
  private Player currentPlayer;
  private WorldMap map;

  // Country variables for map clicks 
  private Country countryClicked;
  private Country toCountry;
  private Country fromCountry;

  // Turn variables 
  private int turnIndex;
  private Player firstTurn;
  private int firstTurnIndex;

  // Game state variables
  private boolean distributeUnits = false;
  private boolean attacking = false;
  private boolean fortify = false;
  private boolean placeUnits = false;
  private boolean turn = false;
  private String saveLocation;

  /**
   * Constructor initializes the map
   */
  public GameManager(){
    initializeMap(usingGUI);
    initDefaultSaveLocation();
  }

  /**
   * Constructor initilaize map with gui
   */
  public GameManager(boolean withGUI){
    usingGUI = withGUI;
    initializeMap(usingGUI);
    initDefaultSaveLocation();
  }


  /**
   * creates the map for the game
   */
  private void initializeMap(boolean withGUI){
    map = new WorldMap(withGUI);
  }

  /**
   * getter for game map
   * No privacy leaks concern (we want other classes to be able to modify the map)
   */
  public WorldMap getMap(){
    return map;
  }

  /**
   * Initialzies the default save location
   */
  private void initDefaultSaveLocation(){
    saveLocation = System.getProperty("user.dir") + "/Save/GameSave.risk";
  }

  /**
   * Set save location ensuring saved as .risk file
   */
  public void setSaveLocation(String inSaveLocation){
    if(!inSaveLocation.substring(inSaveLocation.length()-5).equals(".risk")){
      inSaveLocation += ".risk";
    }
    saveLocation = inSaveLocation;
  }

  /**
   * Get save location
   */
  public String getSaveLocation(){
    return saveLocation;
  }

  /**
   * getter for current player
   * No privacy leaks concern (we want to be able to modify)
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
   * No privacy leaks concern (we want to be able to modify)
   */
  public Player[] getAllPlayers(){
    return players;
  }

  /**
   * getter for number of players
   */
  public int getNumPlayers(){
    return numPlayers;
  }

  /**
   * getter for country clicked
   */
  public void setCountryClicked(Country country){
    countryClicked = country;
    processCountryClick();
  }

  /**
   * getter for country clicked
   * No privacy leaks concern (we want to be able to modify)
   * @return country
   */
  public Country getCountryClicked(){
    return countryClicked;
  }

  /**
   * gets the from country selected
   * No privacy leaks concern (we want to be able to modify)
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
   * Code to run when fortify state is set after country click
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
   * checks the game state during regular turn
   * Removes player if eliminated or checks if game is over (player has won)
   */
  public void checkGameState(){
    if(turn){
      checkIfPlayerEliminated();
      checkIfGameOver();
    }
  }

  /**
   * Checks if player has won the game i.e. owns every territory on the map
   * if there is a winner it tells MainGUI to end the game
   */
  public void checkIfGameOver(){
    ArrayList<Country> allCountries = map.getCountries();
    boolean gameWon;
    for(int i=0; i < numPlayers; i++){
      gameWon = true;
      for(int k=0;k < allCountries.size();k++){
        if(allCountries.get(k).getOwner() != players[i]){
          gameWon = false;
          break;
        }
      }
      if(gameWon) MainGUI.gameOver(players[i]);
    }
  }

  /**
   * Checks if player has been eliminated
   * i.e. they no longer own any countries and removes them form the game
   */
  public void checkIfPlayerEliminated(){
    for(int i=0;i < numPlayers; i++){
      if(players[i] != null){
        if(players[i].getCountriesOwned().size() == 0){
          players[i] = null;
          MainGUI.updateLegend();
        }
      }
    }
  }


  /**
   * Creates all players for the game
   */
  public void initializePlayers(int numHuman, int numAI, String[] names){
    int i;
    int k=0;

    // Create AI and Human players
    numPlayers = numHuman + numAI;
    players = new Player[numPlayers];
    for(i=0; i < numHuman; i++){
      players[i] = new Player(names[i]);
    }
    for(; i < numPlayers; i++){
      players[i] = new AiPlayerSimple("AI #" + k, getMap());
      k++;
    }
    
    // Turn on legend after players have been created
    if(usingGUI){
      MainGUI.showLegend();
    }
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
      }else{
        currentPlayer.placeUnit();
      }
      MainGUI.continueCountrySelection();
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

    // if player was eliminated they were set to null
    if(currentPlayer == null){
      nextTurn();
      return;
    }
    // Change turn icon on legend indicating current player
    MainGUI.getMapGUI().updateTurnIcon(currentPlayer);

    // Save the game and calculate how many units player gets at beginning of turn
    if(turn){
      saveGame();
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
    if(usingGUI){
      MainGUI.getMapGUI().updateTurnIcon(currentPlayer);
    }

  }

  /**
   * calculates and distributes new units at beginning of turn
   */
  public void calcDistributeUnits(){
    int numNewUnits;
    int numUnits;
    int userChoice;
    ArrayList<Country> countriesOwned;
    Country country;

    countriesOwned = currentPlayer.getCountriesOwned();

    // New units (minimum of 3) + continent bonus
    numNewUnits = countriesOwned.size()/3;
    if(numNewUnits < 3) numNewUnits = 3;
    numNewUnits += getMap().getContinentBonus(currentPlayer);

    currentPlayer.setAvailableUnits(numNewUnits);

    if(usingGUI && !currentPlayer.getPlayerType().equals("AI")){
      MainGUI.removePlaceUnitsGUIElements();
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
   * Saves the state of the game to disk
   */
  public void saveGame(){
    try {
      // open file to save
      File f = new File(saveLocation);
      if(f.exists()) f.delete();
      f.getParentFile().mkdirs();
      f.createNewFile();

      FileOutputStream fileStream = new FileOutputStream(saveLocation);
      ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);

      // Create saveState object and write to file
      SaveState saveState = new SaveState(this);
      objectStream.writeObject(saveState);

      objectStream.close();
      fileStream.close();

    } catch (Exception e) {
      e.printStackTrace();
      System.out.println(e);
    }
  }
  
  /**
   * Load the game for a gamesave file
   * @param saveFile file object containing path to load saved game
   * @return true if game loaded succesfully, false otherwise
   */
  public boolean loadGame(File saveFile){
    try{
      FileInputStream fileStream;
      ObjectInputStream objectStream;
      fileStream = new FileInputStream(saveFile.getAbsolutePath());
      objectStream = new ObjectInputStream(fileStream);

      // load saved SaveState object
      SaveState saveState = (SaveState) objectStream.readObject();

      loadGameFromSaveState(saveState);

      objectStream.close();
      fileStream.close();
    }catch(Exception e){
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * Loads the game from the loaded save state
   * Initializes players and assigns country owners and units
   * @param saveState object containgin all saved game info
   */
  private void loadGameFromSaveState(SaveState saveState){
    String[] playerNames;
    int numHumanPlayers = 0;
    int numAIPlayers = 0;
    int numPlayers;
    int currentPlayerIndex;
    int[] countriesOwnedID;
    int[] numUnitsPerCountry;
    ArrayList<Country> allCountries;

    // Initialize saved players
    numPlayers = saveState.getNumSavedPlayers();
    playerNames = new String[numPlayers];
    ArrayList<PlayerSave> allSavedPlayers = saveState.getAllSavedPlayers();
    for(int i=0; i < numPlayers; i++){
      playerNames[i] = allSavedPlayers.get(i).getName();
      if(allSavedPlayers.get(i).isAI()){
        numAIPlayers += 1;
      }else{
        numHumanPlayers += 1;
      }
    }
    initializePlayers(numHumanPlayers, numAIPlayers, playerNames);

    // Intitialize turn
    currentPlayerIndex = saveState.getCurrentPlayerIndex();
    currentPlayer = players[currentPlayerIndex];

    // Set country to correct owners
    allCountries = map.getCountries();
    for(int i=0; i < numPlayers; i++){
      countriesOwnedID = allSavedPlayers.get(i).getCountriesOwned();
      numUnitsPerCountry = allSavedPlayers.get(i).getNumUnitsPerCountry();
      for(int k=0; k < countriesOwnedID.length;k++){
        for(int j=0; j < allCountries.size(); j++){
          if(allCountries.get(j).getCountryID() == countriesOwnedID[k]){
            allCountries.get(j).setOwner(players[i]);
            allCountries.get(j).setUnits(numUnitsPerCountry[k]);
            break;
          }
        }
      }
    }

    // Begin the game
    setTurnState();
    turnIndex--;
    nextTurn();
  }


  /**
   * automatically sets up the game (was used for testing)
   * Creates players and sets all countries owned
   * to one player except one country
   */
  public void autoSetup(){
    ArrayList<Country> countries;
    String[] names = new String[3];

    // Create players
    names[0] = "Alice";
    names[1] = "Bob";
    names[2] = "Charlie";
    initializePlayers(3, 0, names);

    // start turn
    initializeTurn();

    // set owners of all except one country to player 
    countries = getMap().getCountries();
    for(int i=0; i < countries.size()-2; i++){
      countries.get(i).setOwner(currentPlayer);
      currentPlayer.setAvailableUnits(5);
      currentPlayer.placeUnits(countries.get(i), 5);
    }

    // go to next players turn and set them to own last country
    nextTurn();
    countries.get(countries.size()-2).setOwner(currentPlayer);
    currentPlayer.setAvailableUnits(1);
    currentPlayer.placeUnits(countries.get(countries.size()-2), 1);
    nextTurn();
    countries.get(countries.size()-1).setOwner(currentPlayer);
    currentPlayer.setAvailableUnits(1);
    currentPlayer.placeUnits(countries.get(countries.size()-1), 1);


    // begin regular turn play
    nextTurn();
    setTurnState();
    //saveGame();

  }

}

