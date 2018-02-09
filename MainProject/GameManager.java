import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

/**
 * This is the class which runs the entire game
 *
 */
public class GameManager{
  private int numPlayers;
  private Player[] players;
  private Map map;
  private Player currentPlayer;
  private int turnIndex;

  /**
   * Constructor, initializes and starts the game
   */
  GameManager(){
    initGameState();
    playGame();
  }

  /**
   * initializes game, creates players, map, turn order and distributes board units
   */
  private void initGameState(){
    Player firstTurn;
    int firstTurnIndex;

    printAsciiArt();

    initializePlayers();

    initializeMap();

    initializeTurn();
    firstTurn = currentPlayer;
    firstTurnIndex = turnIndex;

    distributeUnits();

    currentPlayer = firstTurn;
    turnIndex = firstTurnIndex;
  }

  /**
   * Checks if player has won the game i.e. owns every territory on the map
   * @return gameOver true if player owns every territory otherwise false
   */
  public void checkIfGameOver(){
    ArrayList<Country> allCountries = map.getCountries();

    for(int i=0; i < players.length ; i++){
      if(players[i].getCountriesOwned().size() == allCountries.size()){
        System.out.println("Congratulations " + currentPlayer.getName() + "! You have won the game!");
        System.exit(0);
        break;
      }
    }

    

  }

  /**
   * Exits the game
   */
  public void exitGame(){
    System.out.println("Exiting game. Bye!");
    System.exit(0);
  }
 

  /**
   * Creates all players for the game
   */
  private void initializePlayers(){
    boolean success = false;
    String name;
    Scanner kb;

    while (!success){
      numPlayers = receiveInt("Please enter number of players (2-4): ");
      if(numPlayers > 1 && numPlayers <= 4) success = true;
      else numPlayers = 0;
    }
    
    kb = new Scanner(System.in);
    players = new Player[numPlayers];
    for(int i=0; i < numPlayers; i++){
      System.out.print("Player " + (i+1) + " what is your name: ");
      name = kb.next();
      players[i] = new Player(name);
    }
  }

  /**
   * initializes turn order randomly
   */
  private void initializeTurn(){
    System.out.println("\nRandomizing first turn...\n");

    // sleep for 1 second
    try{
      Thread.sleep(1000);
    } catch (Exception e) {
      System.out.println(e);
    }

    Random rand = new Random();
    turnIndex = rand.nextInt(numPlayers);
    currentPlayer = players[turnIndex];

  }

  /**
   * creates the map for the game
   */
  private void initializeMap(){
    map = new Map();
  }

  /**
   * Distributes the intial units for the game. Allows players to choose the initial countries they own. 
   * Then allows the players to ditribute the initial units on their countries before beginning the game.
   */
  private void distributeUnits(){
    int i;
    int numUnits;
    int userChoice;
    ArrayList<Country> allCountries;
    Country selectedCountry;
    ArrayList<Integer> countryIndex;
    boolean validChoice, allUnitsDistributed;

    switch(numPlayers){
      case 2: 
        numUnits = 10;
        break;
      case 3: 
        numUnits = 7;
        break;
      case 4: 
        numUnits = 5;
        break;
      default: 
        numUnits = 5;

      /*
      case 2: numUnits = 40;
      case 3: numUnits = 35;
      case 4: numUnits = 30;
      default: numUnits = 30;
      */

    }

    for(i=0; i < players.length; i++){
      players[i].setAvailableUnits(numUnits);
    }

    // This loop distributes the initial units allowing players to select their initial countries
    allCountries = map.getCountries();
    for(i=0; i < allCountries.size(); i++){
        printTurn();
        printMap();
        countryIndex = printCountriesAvailable();

        validChoice = false;
        while(!validChoice){
          userChoice = receiveInt("\nEnter Country # to place unit: ");
          if(userChoice <= countryIndex.size() && userChoice > 0){
            selectedCountry = allCountries.get(countryIndex.get(userChoice-1));
            currentPlayer.gainCountry(selectedCountry);
            selectedCountry.setOwner(currentPlayer);
            currentPlayer.placeUnits(selectedCountry, 1);
            validChoice = true;
          }
        }

        nextTurn();
        
    }

    // This loop allows players to fortify their selected countries with the rest
    // of their available units
    allUnitsDistributed = false;
    while(!allUnitsDistributed){

      // Check if any player has available units
      allUnitsDistributed = true;
      for(i=0; i < players.length; i++){
        if(players[i].getAvailableUnits() != 0){
          allUnitsDistributed = false;
          break;
        }
      }

      if(currentPlayer.getAvailableUnits() == 0){
        nextTurn();
        continue;
      }

      printTurn();
      System.out.println("Units left: " + currentPlayer.getAvailableUnits());
      printCountriesOwned();
      allCountries = currentPlayer.getCountriesOwned();
      validChoice = false;
      while(!validChoice){
        userChoice = receiveInt("Select country # to place unit: ");
        if(userChoice <= allCountries.size() && userChoice > 0){
          selectedCountry = allCountries.get(userChoice-1);
          currentPlayer.placeUnits(selectedCountry, 1);
          validChoice = true;
        }

      }

      nextTurn();
    }

  }

  /**
   * Switches turn to next player
   */
  private void nextTurn(){
    turnIndex++;
    turnIndex %= numPlayers;
    currentPlayer = players[turnIndex];
  }

  /**
   * Function for playing turn
   */
  private void playGame(){
    int userChoice;

    while(true){
      printTurn();
      playTurn();
      nextTurn();
    }
        
  }

  /**
   * Allows play to play his turn with several menu options until player
   * decides to pass the turn onto the next player
   */
  private void playTurn(){
    boolean turnOver = false;
    int userChoice;

    placeNewTurnUnits();

    while(!turnOver){

      printTurnMenu();
      userChoice = receiveInt("Please select menu option: ");

      switch(userChoice){
        case 1:
          attack();
          checkIfGameOver();
          break;
        case 2:
          fortify();
          turnOver = true;
          break;
        case 3:
          printPlayerInfo();
          break;
        case 4:
          printMap();
          printMapInfo();
          break;
        case 5:
          turnOver = true;
          break;
        case 6:
          exitGame();
          break;

        default:
          System.out.println("invalid menu option");
    }

  }

  }

  private void placeNewTurnUnits(){
    int numNewUnits = 3;
    int numUnits;
    int userChoice;
    ArrayList<Country> countriesOwned;
    Country country;

    countriesOwned = currentPlayer.getCountriesOwned();

    if(countriesOwned.size() > numNewUnits){
      numNewUnits = countriesOwned.size();
    }

    System.out.println("Gained " + numNewUnits + " new units!");
    currentPlayer.setAvailableUnits(numNewUnits);
    
    while(currentPlayer.getAvailableUnits() != 0){
      printCountriesOwned();
      userChoice = receiveInt("Select country to place units: ");
      country = countriesOwned.get(userChoice-1);
      numUnits = receiveInt("How many units? (" + currentPlayer.getAvailableUnits() + " available): ");

      currentPlayer.placeUnits(country, numUnits);
    }


  }

  /**
   * Prints the menu for available options during players turn
   */
  private void printTurnMenu(){
    System.out.println("\n----------------------------\n"
                      +"Turn Menu:                  \n"
                      +"\t [1] Attack               \n"
                      +"\t [2] Fortify              \n"
                      +"\t [3] Display Player Info  \n"
                      +"\t [4] Display Map          \n"
                      +"\t [5] End Turn             \n"
                      +"\t [6] Exit Game            \n"
                      +"----------------------------\n");
  }
  
  /**
   * Prints to stdout the players whos turn it currently is
   */
  private void printTurn(){
    System.out.println("\n--------------------------------------------------------------------------------");
    System.out.println(currentPlayer.getName() + "'s turn.");
    System.out.println("--------------------------------------------------------------------------------");
  }

  /**
   * Sets up combat between two countries, current players selects attacking and defending country
   */
  private void attack(){
    int userChoice;
    ArrayList<Country> countries;
    Country attackingCountry, defendingCountry;

    printMap();
    System.out.println("Attack");
    System.out.println("--------------------------------------------------------------------------------");

    printCountriesOwned();
    userChoice = receiveInt("Select country to attack from: ");

    countries = currentPlayer.getCountriesOwned();
    attackingCountry = countries.get(userChoice-1);

    countries = printCountriesNeighbours(attackingCountry);
    if(countries.size() == 0){
      System.out.println("No enemy owned bordering countries");
      return;
    }

    userChoice = receiveInt("Select country to attack: ");

    defendingCountry = countries.get(userChoice-1);

    currentPlayer.attack(attackingCountry, defendingCountry);

  }

  /**
   * Allows user to move units from one country they own to another neighboring country
   */
  private void fortify(){
    int userChoice;
    int numUnits;
    ArrayList<Country> countries;
    Country fromCountry, toCountry;

    printMap();
    System.out.println("Fortify");
    System.out.println("--------------------------------------------------------------------------------");
    countries = currentPlayer.getCountriesOwned();

    printCountriesOwned();
    userChoice = receiveInt("Select country to move units from: ");
    fromCountry = countries.get(userChoice-1);

    userChoice = receiveInt("Select country to move units to: ");
    toCountry = countries.get(userChoice-1);

    currentPlayer.moveUnits(fromCountry, toCountry);

  }

  /**
   * Displays the Map for the game
   */
  private void printMap(){
    System.out.println();
    map.printMap();
  }

  /**
   * Displays the Map for the game
   */
  private void printMapInfo(){
    ArrayList<Country> allCountries;
    allCountries = map.getCountries();

    System.out.println("\nMap Information");
    System.out.println("--------------------------------------------------------------------------------");
    System.out.println("\t Countries: ");
    for(int i=0;i < allCountries.size(); i++){
      System.out.println("\t\t" + allCountries.get(i).getName() + " (Owner: "
                         + allCountries.get(i).getOwner().getName() + ", " 
                         + allCountries.get(i).getUnits() + " Units)");
    }

  }

  /**
   * Displays various information relating to the current players status in the game
   */
  private void printPlayerInfo(){
    ArrayList<Country> countriesOwned;
    countriesOwned = currentPlayer.getCountriesOwned();

    System.out.println("\nPlayer Info");
    System.out.println("--------------------------------------------------------------------------------");
    System.out.println("\t Player Name: " + currentPlayer.getName());
    //System.out.println("\t Total Number of Units: " + currentPlayer.getTotalUnits());
    System.out.println("\t Countries: ");
    for(int i=0;i < countriesOwned.size(); i++){
      System.out.println("\t\t" + countriesOwned.get(i).getName() + " (" + countriesOwned.get(i).getUnits() + " Units)");
    }

  }

  /**
   * Function for recieving an integer from the unit
   * @param displayString string to display to the user when requesting for an integer
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

  

  /**
   * Prints a list of the countries available i.e. Countries which currently have no owner
   * @return ArrayList which is an index of available countries
   */
  private ArrayList<Integer> printCountriesAvailable(){
    int k=1;
    ArrayList<Country> allCountries;
    allCountries = map.getCountries();
    ArrayList<Integer> availableIndex = new ArrayList<Integer>();

    System.out.println("Available countries:\n");
    for(int i=0; i < allCountries.size(); i++){
      if(allCountries.get(i).getOwner() == null){
        System.out.println("\t" + k + ". " + allCountries.get(i).getName());
        k++;
        availableIndex.add(i);
      }
    }

    return availableIndex;

  }

  /**
   * Prints the countries owned by the current player
   */
  private void printCountriesOwned(){
    ArrayList<Country> countriesOwned;
    countriesOwned = currentPlayer.getCountriesOwned();

    System.out.println(currentPlayer.getName() + "'s countries:");
    for(int i=0; i < countriesOwned.size(); i++){
        System.out.println("\t" + (i+1) + ". " + countriesOwned.get(i).getName()
                           + " (" + countriesOwned.get(i).getUnits() + " Units)");
    }

  }

  /**
   * Prints all the countries in the entire map
   * @return returns ArrayList of countries not owned by current player
   */
  private ArrayList<Country> printCountriesNeighbours(Country country){
    ArrayList<Country> countriesNeighbours, allCountries;
    allCountries = map.getCountries();

    countriesNeighbours = new ArrayList<Country>();

    System.out.println("Neighbouring Countries:");
    for(int i=0; i < allCountries.size(); i++){
      if(allCountries.get(i).getOwner() != currentPlayer && allCountries.get(i).isNeighbour(country)){
        countriesNeighbours.add(allCountries.get(i));
        System.out.println("\t" + countriesNeighbours.size() + ". " + allCountries.get(i).getName() + " (Owner: "
                           + allCountries.get(i).getOwner().getName() + ", " 
                           + allCountries.get(i).getUnits() + " Units)");
      }
    }

    return countriesNeighbours;
  }


  /**
   * Prints ascii art for the game
   */
  private void printAsciiArt(){
    System.out.println("\n" 
      + "                 _______     _____   ______   ___  ____\n"
      + "                |_   __ \\   |_   _|.' ____ \\ |_  ||_  _|\n"
      + "                  | |__) |    | |  | (___ \\_|  | |_/ /    \n"
      + "                  |  __ /     | |   _.____`.   |  __'.    \n"
      + "                 _| |  \\ \\_  _| |_ | \\____) | _| |  \\ \\_  \n"
      + "                |____| |___||_____| \\______.'|____||____| \n"
      + "\n"
      + "                 ,_   .  ._. _.  .\n"
      + "             , _-\\','|~\\~      ~/      ;-'_   _-'     ,;_;_,    ~~-\n"
      + "    /~~-\\_/-'~'--' \\~~| ',    ,'      /  / ~|-_\\_/~/~      ~~--~~~~'--_\n"
      + "    /              ,/'-/~ '\\ ,' _  , '|,'|~                   ._/-, /~\n"
      + "    ~/-'~\\_,       '-,| '|. '   ~  ,\\ /'~                /    /_  /~\n"
      + "  .-~      '|        '',\\~|\\       _\\~     ,_  ,               /|\n"
      + "            '\\        /'~          |_/~\\\\,-,~  \\ \"         ,_,/ |\n"
      + "             |       /            ._-~'\\_ _~|              \\ ) /\n"
      + "              \\   __-\\           '/      ~ |\\  \\_          /  ~\n"
      + "    .,         '\\ |,  ~-_      - |          \\\\_' ~|  /\\  \\~ ,\n"
      + "                 ~-_'  _;       '\\           '-,   \\,' /\\/  |\n"
      + "                   '\\_,~'\\_       \\_ _,       /'    '  |, /|'\n"
      + "                     /     \\_       ~ |      /         \\  ~'; -,_.\n"
      + "                     |       ~\\        |    |  ,        '-_, ,; ~ ~\\\n"
      + "                      \\,      /        \\    / /|            ,-, ,   -,\n"
      + "                       |    ,/          |  |' |/          ,-   ~ \\   '.\n"
      + "                      ,|   ,/           \\ ,/              \\       |\n"
      + "                      /    |             ~                 -~~-, /   _\n"
      + "                      |  ,-'                                    ~    /\n"
      + "                      / ,'                                      ~\n"
      + "                      ',|  ~\n"
      + "                        ~'\n");
  }


}
