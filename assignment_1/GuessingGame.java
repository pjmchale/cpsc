/**
 * A random number is selected between a range and the user have to guess what the number is
 */

import java.util.Random;
import java.util.Scanner;

public class GuessingGame {
    Scanner kb = new Scanner(System.in);
    Random rand = new Random();
    int randomInt;
    int low;
    int high;
    int guess;

    /**
     * Main method , Receives guess, checks guess
     * @param low the lowest value for guessing
     * @param high the highest value for guessing
     */
    GuessingGame(int low, int high) {
        this.low = low;
        this.high = high;
        randomInt = rand.nextInt(this.high) + this.low;
        do {
            System.out.print("What is your guess: ");
            guess = selectInt();
        } while (!checkGuess(guess));
        System.out.println("That's the number I was thinking of! Well done.");
    }

    /**
     * Receives guess and checks if input guess is correct or not
     * @param guess user input
     * @return True if guess is correct
     */
    public boolean checkGuess(int guess){
        boolean correct = false;
        if(!inRange(guess)){
            System.out.println("Out of bounds");
        }else if (guess > randomInt) {
            System.out.println("Too high");
        } else if (guess < randomInt) {
            System.out.println("Too low");
        }else{
            correct = true;
        }
        return correct;
    }

    /**
     * Receives guess and checks if it is in range
     * @param guess user input
     * @return True if input is in range
     */
    public boolean inRange(int guess){
        boolean isRange = false;
        if(guess <= high && guess >= low){
            isRange = true;
        }
        return isRange;
    }

    /**
     * Ask the user to input an integer, keeps asking till receive a valid integer
     * @return an integer
     */
    public int selectInt() {
        while(!kb.hasNextInt()){
            System.out.println("Please enter a valid integer...");
            System.out.print("What is your guess: ");
            kb.next();
        }
        return kb.nextInt();
    }

    /**
     * calls the GuessingGame constructor
     * @param args
     */
    public static void main(String[] args) {
        new GuessingGame(1,20);
    }

}
