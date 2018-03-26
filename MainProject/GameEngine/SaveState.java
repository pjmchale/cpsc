
package GameEngine;
import PlayerPackage.*;
import MapStage.*;
import java.util.*;
import java.io.*;

public class SaveState implements Serializable{
  private ArrayList<PlayerSave> allPlayers;
  private int numPlayers;
  private int currentPlayerIndex;

  public ArrayList<PlayerSave> getAllSavedPlayers(){
    return allPlayers;
  }

  public int getNumSavedPlayers(){
    return numPlayers;
  }

  public int getCurrentPlayerIndex(){
    return currentPlayerIndex;
  }

  SaveState(GameManager gameManager){
    numPlayers = gameManager.getNumPlayers();
    Player[] players = gameManager.getAllPlayers();
    allPlayers = new ArrayList<PlayerSave>();

    for(int i=0;i<numPlayers;i++){
      allPlayers.add(new PlayerSave(players[i]));
      if(players[i] == gameManager.getCurrentPlayer()){
        currentPlayerIndex = i;
      }
    }
  }
}

class PlayerSave implements Serializable{
    private String name;
    private int[] countriesOwned;
    private int[] numUnitsPerCountry;
    boolean isAI;

    public String getName(){
      return name;
    }

    public int[] getCountriesOwned(){
      return countriesOwned;
    }

    public int[] getNumUnitsPerCountry(){
      return numUnitsPerCountry;
    }

    public boolean isAI(){
      return isAI;
    }

    PlayerSave(Player player){
      name = player.getName();
      if(player.getPlayerType().equals("AI")){
        isAI = true;
      }else{
        isAI = false;
      }

      ArrayList<Country> countries = player.getCountriesOwned();
      countriesOwned = new int[countries.size()];
      numUnitsPerCountry = new int[countries.size()];
      for(int i=0;i<countries.size();i++){
        countriesOwned[i] = countries.get(i).getCountryID();
        numUnitsPerCountry[i] = countries.get(i).getUnits();
      }
    }
  }
