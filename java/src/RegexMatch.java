import java.util.Stack;

/**
 * 
 * @author Ephraim
 * 
 * Question Link: http://www.careercup.com/question?id=5124942133723136
 * Question:
 * Write a function to check if a string matches a regex patter. 
 * Note that you only have to deal with patterns containing "*". 
 * Also, note that the pattern can't start with "*". 
 * Some examples: 
 * 		isMatch(“aa”,”a”) → false 
 * 		isMatch(“aa”,”aa”) → true 
 * 		isMatch(“aaa”,”aa”) → false 
 * 		isMatch(“aa”, “a*”) → true 
 * 		isMatch(“aa”, “*”) → true 
 * 		isMatch(“ab”, “*”) → true 
 * 		isMatch(“ab”, “*”) → true 
 * 		isMatch(“a”, “b*a”) → true 
 * 		isMatch(“a”, “a*a”) → true 
 * 		isMatch(“aab”, “c*a*b”) → true
 *
 */
public class RegexMatch {
	
	public static void main(String[] args) {
		System.out.println(isMatch("aa", "a") == false);
		System.out.println(isMatch("aa", "aa") == true);
		System.out.println(isMatch("aaa", "aa") == false);
		System.out.println(isMatch("aa", "a*") == true);
		System.out.println(isMatch("aa", "*") == true);
		System.out.println(isMatch("ab", "*") == true);
		System.out.println(isMatch("ba", "*") == true);
		System.out.println(isMatch("a", "b*a") == true);
		System.out.println(isMatch("a", "a*a") == true);
		System.out.println(isMatch("aab", "c*a*b") == true);
	}
	
	public static boolean isMatch(String str, String regex) {
		//If the regex is just a * it's always a match
		if (regex.equals("*")) return true;
		
		//Create 2 stacks. One for the characters of the
		//regular expression, and the other for the string
	    Stack<Character> strStack = new Stack<Character>();
	    Stack<Character> regStack = new Stack<Character>();
	    
	    //Push the characters from each onto their corresponding stacks
	    for (int i = 0; i < str.length(); i++) {
	    	strStack.push(str.charAt(i));
	    }
	    for (int i = 0; i < regex.length(); i++) {
	    	regStack.push(regex.charAt(i));
	    }
	    
	    //As long as at least one of the stack is not empty
	    while (!strStack.empty() || !regStack.empty()) {
	    	
	    	//Get the next letter from the string (or null)
	    	char strChar = (strStack.empty()) ? '\0' : strStack.peek();
	    	//Get the next letter from the regex (or null)
	    	char regChar = (regStack.empty()) ? '\0' : regStack.peek();
	    	
	    	//If the character is a star, get the letter in the regex
	    	//that is under (proceding in the string) the star (denoted L), 
	    	//and continue popping from strStack until the top letter no 
	    	//longer equals L.
	    	if (regChar == '*') {
	    		regStack.pop();
	    		regChar = regStack.pop();
	    		while (!strStack.empty() && strStack.peek() == regChar)
	    			strStack.pop();
	    	}
	    	
	    	//If the two characters match, pop them off the stack
	    	else if (strChar == regChar) {
	    		strStack.pop();
	    		regStack.pop();
	    	}
	    	
	    	//If they don't, and one isn't a star, return false.
	    	else return false;
		}
		return true;
	}

}
