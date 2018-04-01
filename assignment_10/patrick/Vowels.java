
/**
 * Class containing method to calculate the number of vowels occuring in a string;
 */
public class Vowels{

  /**
   * Caluates the number of values in an input string
   * @param string inut string
   * @return the number of vowels occuring in the string
   */
  static public int numOfVowels(String string){
    char letter;

    /* This is the terminating condition
     * It will always get hit because the recursive call reduces the length of the string by 1
     * So eventually the length will be less than 1
     */
    if(string.length() < 1) return 0;

    /* Retrieves the first letter in the string and converts it to lowercase.
     * If the character is a vowel (a,e,i,o,u) then the return value will be incremented by 1.
     * The functions is recusrisvley called on the string minus the character (substring(1)).
     * This moves the string size closer and closer o the terminating condition.
     */
    letter = Character.toLowerCase(string.charAt(0));
    if(letter == 'a' || letter == 'e' || letter == 'i' || letter == 'o' || letter == 'u'){
      return 1 + numOfVowels(string.substring(1));
    }

    /* This return is when the first letter is not a vowel. 
     * This will increment the total by 0 and perform the recursive call on the 
     * string minus the first letter*/
    return 0 + numOfVowels(string.substring(1));
  }

}
