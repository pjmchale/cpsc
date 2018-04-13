import static org.junit.Assert.*;
import java.util.*;


import GameEngine.*;
import PlayerPackage.*;
import CombatEngine.*;
import MapStage.*;

import org.junit.Test;

public class GameManagerTest{


	// test constructors
	@Test
  public void test_creation(){
    GameManager gm = new GameManager(false);
    gm.getMap();
    assertNotNull("Map should be initialized", gm.getMap());
  }
	
  
  /**
   * Set all countries to owned by player and test function
   * which checks if function returns true
   */
	@Test
	public void test_checkIfAllCountriesOwnedTrue(){

    GameManager gm = new GameManager(false);

    /* create player and set all countries owned by player */
    Player pl = new Player("test");
    ArrayList<Country> countries = gm.getMap().getCountries();
    for(int i=0;i < countries.size(); i++){
      countries.get(i).setOwner(pl);
    }

		assertTrue("all countries owned wrong return", gm.allCountriesOwned());
	}
  
  /**
   * Set all countries to owned by player and test function
   * which checks if function returns true
   */
  @Test
	public void test_checkIfAllCountriesOwnedFalse(){
    GameManager gm = new GameManager(false);
		assertFalse("all countries owned wrong return", gm.allCountriesOwned());
	}


  /**
   * Tests player initialization (only human players)
   * should create two players i.e. numPLayers is 2
   */
  @Test
	public void test_initializePlayersHumanOnlySize(){
    int numHuman = 2;
    int numAI = 0;
    GameManager gm = new GameManager(false);
    String[] names = new String[2];
    names[0] = "test 1";
    names[1] = "test 2";

    gm.initializePlayers(numHuman, numAI, names);
    assertEquals("Number of players should be 2", gm.getNumPlayers(), 2);
  } 

  /**
   * Tests player initialization (only human players)
   * tests if input names are correctly set
   */
  @Test
	public void test_initializePlayersHumanOnlyNames(){
    int numHuman = 1;
    int numAI = 0;
    GameManager gm = new GameManager(false);
    String[] names = new String[1];
    names[0] = "test_1";

    gm.initializePlayers(numHuman, numAI, names);
    assertEquals("Player name should be test_1", gm.getAllPlayers()[0].getName(), "test_1");
  } 

  /**
   * Tests calculations for number of units player receieves at begginging of turn
   */
  @Test
  public void test_distributeUnitsCalculation(){
    GameManager gm = new GameManager(false);

    /* create player and set all countries owned by player */
    Player pl = new Player("test");
    ArrayList<Country> countries = gm.getMap().getCountries();
    for(int i=0;i < countries.size(); i++){
      countries.get(i).setOwner(pl);
    }
    gm.setCurrentPlayer(pl);
    gm.calcDistributeUnits();
    assertEquals("Incorrect number of available units at beginning of turn", pl.getAvailableUnits(), 37);
  }

	
}
