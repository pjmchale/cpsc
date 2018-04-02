/**
 * Class with a method that calculates the number of vowels in a string
 */
public class Vowels{

    /**
     * Calculates the number of vowels in a string
     * @param string is the string
     * @return the number of vowels in the string, zero if string is null
     */
    static int numOfVowels(String string){
        /*Terminating condition
        Will always happen since the string is shortened every call
         */
        if (string == null || string.length() <= 0) {return 0;}

        /*
        Converts the first character in the string to uppercase
         */
        char letter = Character.toUpperCase(string.charAt(0));

        /*
        Returns either 1 or 0 + the number of vowels in the substring without the first character depending on if the
        first character is a vowel
        1 if letter is a vowel
        0 if letter is not a vowel
        Does a recursive call to find the number of vowels in the substring
         */
        return  (("AEIOU".indexOf(letter) >= 0) ? 1 : 0) + numOfVowels(string.substring(1));
    }
}
