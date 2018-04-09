package PlayerPackage;

import MapStage.*;

/**
 * Class representing a move made by an AI
 */
public class AiMove{
    private Country fromCountry;
    private Country toCountry;
    private int numUnits;

    /**
     * Only constructor used for building an AiMove by the Ai, can be either an attack or a fortification
     * @param fromCountry is the country owned by the Ai
     * @param toCountry is the target country
     * @param numUnits is the number of units
     */
    public AiMove(Country fromCountry, Country toCountry, int numUnits){
        this.fromCountry = fromCountry;
        this.toCountry = toCountry;
        this.numUnits = numUnits;
    }

    public AiMove(){
    }

    /**
     *Only has getters and setters
     */
    public Country getFromCountry() {
        return fromCountry;
    }

    public Country getToCountry() {
        return toCountry;
    }

    public int getNumUnits() {
        return numUnits;
    }

    public void setFromCountry(Country fromCountry) {
        this.fromCountry = fromCountry;
    }

    public void setNumUnits(int numUnits) {
        this.numUnits = numUnits;
    }

    public void setToCountry(Country toCountry) {
        this.toCountry = toCountry;
    }
}
