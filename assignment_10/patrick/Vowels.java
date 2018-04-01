
public class Vowels{

  static public int numOfVowels(String string){
    char letter;
    if(string.length() < 1) return 0;

    letter = Character.toLowerCase(string.charAt(0));
    if(letter == 'a' || letter == 'e' || letter == 'i' || letter == 'o' || letter == 'u'){
      return 1 + numOfVowels(string.substring(1));
    }
    return 0 + numOfVowels(string.substring(1));
  }

}
