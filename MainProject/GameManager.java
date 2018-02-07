import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

//// TEMP
//class Player{
//  String name = "temp name";
//  ArrayList<Country> countriesOwned;
//  int availableUnits;
//  Player(){
//    //System.out.println("Name?:");
//    //Scanner kb = new Scanner(System.in);
//    //name = kb.next();
//  }
//
//  public String getName(){
//    return name;
//  }
//
//  public void setAvailableUnits(int num){
//    availableUnits = num;
//  }
//
//  public void placeUnits(Country country, int units){
//  }
//
//    
//
//}
//
//class Map{
//  ArrayList<Country> countries = new ArrayList<Country>;
//  
//  Map(){
//    for(int i=0;i<5;i++){
//      countries[i] = new Country();
//    }
//  }
//
//  public ArrayList<Country> getCountries(){
//    return countries;
//  }
//
//}
//
//class Country{
//  String name = "Country name";
//  Player owner;
//  int numUnits;
//
//  public String getName(){
//    return name;
//  }
//
//  public Player getOwner(){
//    return owner;
//  }
//
//  public void setOwner(Player player){
//    owner = player;
//  }
//
//}
// //END TEMP

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

    printAsciiArt();

    initializePlayers();

    initializeMap();

    initializeTurn();
    firstTurn = currentPlayer;

    distributeUnits();

    currentPlayer = firstTurn;
  }

  /**
   * Checks if player has won the game i.e. owns every territory on the map
   * @return gameOver true if player owns every territory otherwise false
   */
  public boolean isGameOver(){
    boolean gameOver = false;
    ArrayList<Country> allCountries = map.getCountries();

    for(int i=0; i < players.length ; i++){
      if(players[i].getCountriesOwned().size() == allCountries.size()){
        gameOver = true;
        break;
      }
    }

    return gameOver;

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

    while (!success){
      numPlayers = receiveInt("Please enter number of players (2-4): ");
      if(numPlayers > 1 || numPlayers <= 4) success = true;
      else numPlayers = 0;
    }
    
    players = new Player[numPlayers];
    for(int i=0; i < numPlayers; i++){
      players[i] = new Player();
    }
  }

  /**
   * initializes turn order randomly
   */
  private void initializeTurn(){
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
      case 2: numUnits = 40;
      case 3: numUnits = 35;
      case 4: numUnits = 30;
      default: numUnits = 30;
    }

    for(i=0; i < players.length; i++){
      players[i].setAvailableUnits(numUnits);
    }

    // This loop distributes the initial units allowing players to select their initial countries
    allCountries = map.getCountries();
    for(i=0; i < allCountries.size(); i++){
        printTurn();
        countryIndex = printCountriesAvailable();

        validChoice = false;
        while(!validChoice){
          userChoice = receiveInt("Enter Country # to place unit: ");
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

      printCountriesOwned(currentPlayer);
      allCountries = currentPlayer.getCountriesOwned();
      validChoice = false;
      while(!validChoice){
        userChoice = receiveInt("Select country # to place units: ");
        if(userChoice < allCountries.size() && userChoice > 0){
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

  private void playTurn(){
    boolean turnOver = false;
    int userChoice;

    while(!turnOver){

      placeNewTurnUnits();

      printTurnMenu();
      userChoice = receiveInt("Please select menu option: ");

      switch(userChoice){
        case 1:
          attack();
        case 2:
          fortify();
          turnOver = true;
        case 3:
          printPlayerInfo();
        case 4:
          printMap();
        case 5:
          turnOver = true;
        case 6:
          exitGame();

        default:
          System.out.println("invalid menu option");
    }

  }

  private void placeNewTurnUnits(){


  }

  /**
   * Prints the menu for available options during players turn
   */
  private void printTurnMenu(){
    System.out.println("----------------------------\n"
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
    System.out.println("[*] " + currentPlayer.getName() + "'s turn.");
  }

  /**
   * Sets up combat between two countries, current players selects attacking and defending country
   */
  private void attack(){
    int userChoice;
    ArrayList<Country> countries;
    Country attackingCountry, defendingCountry;

    System.out.println("Attack");
    System.out.println("----------------------------------------");

    printCountriesOwned(currentPlayer);
    userChoice = receiveInt("Select country to attack from: ");

    countries = currentPlayer.getCountriesOwned();
    attackingCountry = countries.get(userChoice-1);

    printAllCountries();
    userChoice = receiveInt("Select country to attack: ");
    countries = map.getCountries();

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

    System.out.println("Fortify");
    System.out.println("----------------------------------------");
    countries = currentPlayer.getCountriesOwned();

    printCountriesOwned(currentPlayer);
    userChoice = receiveInt("Select country to move units from: ");
    fromCountry = countries.get(userChoice-1);

    userChoice = receiveInt("Select country to move units to: ");
    toCountry = countries.get(userChoice-1);

    currentPlayer.moveUnits(fromCountry, toCountry);

  }

  private void printMap(){
    System.out.println("Map");
    System.out.println("----------------------------------------");
    map.printMap();
  }

  private void printPlayerInfo(){
    System.out.println(currentPlayer.getName() + "'s Player Information:");
    System.out.println("----------------------------------------");

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

  private void printCountriesOwned(Player player){
    ArrayList<Country> countriesOwned;
    countriesOwned = player.getCountriesOwned();

    System.out.println(player.getName() + "'s countries:");
    for(int i=0; i < countriesOwned.size(); i++){
        System.out.println("\t" + (i+1) + ". " + countriesOwned.get(i).getName());
    }

  }

  private void printAllCountries(){
    ArrayList<Country> allCountries;
    allCountries = map.getAllCountries();

    System.out.println("All Countries:");
    for(int i=0; i < allCountries.size(); i++){
        System.out.println("\t" + (i+1) + ". " + allCountries.get(i).getName());
    }

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
