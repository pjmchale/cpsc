import static org.junit.Assert.*;
import java.util.*;

import org.junit.Test;

public class GameManagerTest{


	// test constructors
	@Test
  public void test_creation(){
    GameManager gm = new GameManager();
    gm.getMap();
    //assertNotNull("Map should be initialized", gm.getMap());
  }
	
  
	@Test
	public void test_checkIfAllCountriesOwnedTrue(){

    GameManager gm = new GameManager();

    /* create player and set all countries owned by player */
    Player pl = new Player("test");
    ArrayList<Country> countries = gm.getMap().getCountries();
    for(int i=0;i < countries.size(); i++){
      countries.get(i).setOwner(pl);
    }

		assertTrue("all countries owned wrong return", gm.allCountriesOwned());
	}
  
  @Test
	public void test_checkIfAllCountriesOwnedFalse(){
    GameManager gm = new GameManager();
		assertFalse("all countries owned wrong return", gm.allCountriesOwned());
	}


  @Test
	public void test_initializePlayersHumanOnlySize(){
    int numHuman = 2;
    int numAI = 0;
    GameManager gm = new GameManager();
    String[] names = new String[2];
    names[0] = "test 1";
    names[1] = "test 2";

    gm.initializePlayers(numHuman, numAI, names);
    assertEquals("Number of players should be 2", gm.getNumPlayers(), 2);
  } 

  @Test
	public void test_initializePlayersHumanOnlyNames(){
    int numHuman = 1;
    int numAI = 0;
    GameManager gm = new GameManager();
    String[] names = new String[1];
    names[0] = "test_1";

    gm.initializePlayers(numHuman, numAI, names);
    assertEquals("Player name should be test_1", gm.getAllPlayers()[0].getName(), "test_1");
  } 

  @Test
  public void test_distributeUnitsCalculation(){
    GameManager gm = new GameManager();

    /* create player and set all countries owned by player */
    Player pl = new Player("test");
    ArrayList<Country> countries = gm.getMap().getCountries();
    for(int i=0;i < countries.size(); i++){
      countries.get(i).setOwner(pl);
    }
    gm.setCurrentPlayer(pl);
    gm.calcDistributeUnits();
    assertEquals("Incorrect number of available units at beginning of turn", pl.getAvailableUnits(), countries.size());
  }

	
}
