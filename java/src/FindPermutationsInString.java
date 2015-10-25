/**
 * 
 * @author Ephraim Rothschild
 * 
 * The Question:
 *		Given two strings A and B, print out all of the 
 *		permutations of A that are contained in B. You
 *		may assume the string will contain only ASCII 
 *		characters.
 *
 *	Example:
 *		Input: 
 *			A: "abcdabcd"
 *			B: "abcd"
 *		Output:
 *			abcd
 *			bcda
 *			cdab
 *			dabc
 *			abcd
 *
 * The Solution:
 *		This is a solution that I came up with, it is
 *		somewhat similar to the idea behind the Rabin
 *		Karp Algorithm. Before I understanding the
 *		algorithm, I will explain the math behind it.
 *		The math needed is as follows:
 *			Let S = {A_1, ..., A_n} be an unordered 
 *			list of size N that contains all prime
 *			numbers. Let the sum of the numbers in S
 *			equal some integer Q. Then S is the only
 *			possible list of size N, containing only
 *			primes, that can sum to Q.
 *		Because of this, we know we can map every
 *		character to a prime number. I propose a map
 *		as follows:
 *			1 -> 1st prime
 *			2 -> 2nd prime
 *			3 -> 3rd prime
 *			...
 *			n -> nth prime
 *		If we do this (which we can because ASCII
 *		only has 256 possible characters), then it
 *		becomes very easy for us to find each
 *		permutation in the larger string B.
 *		We will do the following:
 *		1: calculate the sum of the primes mapped
 *			to by each of the characters in A,
 *			let's call it smallHash.
 *		2: create 2 indices (righti and lefti).
 *			righti is initialized to zero, and
 *			lefti is initialzed to the size of A.
 *			ex:     |  |
 *				   "abcdabcd"
 *		3: create a variable currHash, and
 *			initialize it to the sum of the
 *			corresponding prime numbers mapped
 *			to by each of the characters in B,
 *			between (inclusive) righti, and
 *			lefti - 1.
 *		4: Iterate both righti and lefti by 1,
 *			each time updating currHash by
 *			subtracting the prime mapped from
 *			the character that is no longer in
 *			the range (lefti - 1) and adding
 *			the prime corresponding to the 
 *			character just added to the range
 *			(righti)
 *		5: Each time currHash is equal to
 *			smallHash, the characters in the
 *			range must be a permutation. So we
 *			print them out.
 *		6: Continue until we have reached the
 *			end of B. (When righti is equal
 *			to the length of B)
 *
 *		This solution runs in O(n) time complexity
 *		and O(1) space.
 */

public class FindPermutationsInString {
	//This is an array containing the first 256 prime numbers
	static int primes[] = 
		  {
		    2,     3,     5,     7,    11,    13,    17,    19,    23,    29,
		    31,    37,    41,    43,    47,    53,    59,    61,    67,    71,
		    73,    79,    83,    89,    97,   101,   103,   107,   109,   113,
		    127,   131,   137,   139,   149,   151,   157,   163,   167,   173,
		    179,   181,   191,   193,   197,   199,   211,   223,   227,   229,
		    233,   239,   241,   251,   257,   263,   269,   271,   277,   281,
		    283,   293,   307,   311,   313,   317,   331,   337,   347,   349,
		    353,   359,   367,   373,   379,   383,   389,   397,   401,   409,
		    419,   421,   431,   433,   439,   443,   449,   457,   461,   463,
		    467,   479,   487,   491,   499,   503,   509,   521,   523,   541,
		    547,   557,   563,   569,   571,   577,   587,   593,   599,   601,
		    607,   613,   617,   619,   631,   641,   643,   647,   653,   659,
		    661,   673,   677,   683,   691,   701,   709,   719,   727,   733,
		    739,   743,   751,   757,   761,   769,   773,   787,   797,   809,
		    811,   821,   823,   827,   829,   839,   853,   857,   859,   863,
		    877,   881,   883,   887,   907,   911,   919,   929,   937,   941,
		    947,   953,   967,   971,   977,   983,   991,   997,  1009,  1013,
		   1019,  1021,  1031,  1033,  1039,  1049,  1051,  1061,  1063,  1069,
		   1087,  1091,  1093,  1097,  1103,  1109,  1117,  1123,  1129,  1151,
		   1153,  1163,  1171,  1181,  1187,  1193,  1201,  1213,  1217,  1223,
		   1229,  1231,  1237,  1249,  1259,  1277,  1279,  1283,  1289,  1291,
		   1297,  1301,  1303,  1307,  1319,  1321,  1327,  1361,  1367,  1373,
		   1381,  1399,  1409,  1423,  1427,  1429,  1433,  1439,  1447,  1451,
		   1453,  1459,  1471,  1481,  1483,  1487,  1489,  1493,  1499,  1511,
		   1523,  1531,  1543,  1549,  1553,  1559,  1567,  1571,  1579,  1583,
		   1597,  1601,  1607,  1609,  1613,  1619
		  };
	
	public static void main(String[] args) {
		String big = "abcdabcd";
		String small = "abcd";
		printAllPermutations(big, small);
	}
	
	static void printAllPermutations(String big, String small) {
		
		// If the big one is smaller than the small one,
		// there can't be any permutations, so return
		if (big.length() < small.length()) return;
		
		// Initialize smallHash to be the sum of the primes
		// corresponding to each of the characters in small.
		int smallHash = primeHash(small, 0, small.length());
		
		// Initialize righti and lefti.
		int lefti = 0, righti = small.length();
		
		// Initialize smallHash to be the sum of the primes
		// corresponding to each of the characters in big.
		int currentHash = primeHash(small, 0, righti);
		
		while (righti <= big.length()) {
			// If the current section of big is a permutation
			// of small, print it out.
			if (currentHash == smallHash)
				System.out.println(big.substring(lefti, righti));
			
			// Subtract the corresponding prime value in position
			// lefti. Then increment lefti
			currentHash -= primeHash(big.charAt(lefti++));
			
			if (righti < big.length()) // To prevent index out of bounds
				// Add the corresponding prime value in position righti.
				currentHash += primeHash(big.charAt(righti));
			
			//Increment righti.
			righti++;
		}
		
	}
	
	// Gets the sum of all the nth primes corresponding
	// to n being each of the characters in str, starting
	// from position start, and ending at position end - 1.
	static int primeHash(String str, int start, int end) {
		int value = 0;
		for (int i = start; i < end; i++) {
			value += primeHash(str.charAt(i));
		}
		return value;
	}
	
	// Get's the n-th prime, where n is the ASCII value of
	// chr
	static int primeHash(Character chr) {
		return primes[chr];
	}
	
	
}
