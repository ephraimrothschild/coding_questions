package example_questions;

public class DiffByK {
//  Question:
//	Given an array of positive, unique, increasingly sorted numbers A, e.g. A = [1, 2, 3, 5, 6, 8, 9, 11, 12, 13]. Given a positive value K, e.g. K = 3. Output all pairs in A that differ exactly by K. 
//			e.g. 2, 5 
//			3, 6 
//			5, 8 
//			6, 9 
//			8, 11 
//			9, 12 	
	
	
	public static void main(String[] args) {
		int[] arr = {1, 2, 3, 5, 6, 8, 9, 11, 12, 13, 16};
		findPairs(arr, 3);
	}
	
	public static void findPairs(int[] arr, int k) {
		//This implementation will be similar to the Kadane algorithm
		//If there is only one element, return
		if (arr.length < 2) return;
		//Otherwise, initialize two indices to positions 0 and 1
		int lefti = 0;
		int righti = 1;
		
		//As long as we haven't reached the end yet
		while (lefti < arr.length && righti < arr.length) {
			
			//Find the difference (since it's sorted, this will
			//always be positive.
			int diff = arr[righti] - arr[lefti];
			
			//If the difference is k, this is a pair, so print it out.
			//Then increment both indices by one.
			//The reason for this is because if you only incremented the
			//left index, the next difference will ALWAYS be smaller
			//and if you only incremented the right index, the next
			//difference would ALWAYS be larger (because the array
			//is sorted) So we increment both.
			if (diff == k) {
				System.out.println(arr[lefti] + ", " + arr[righti]);
				lefti++;
				righti++;
			}
			//If the difference is smaller, increment the right
			//index (because it's sorted, we know doing that will
			//increase the difference)
			else if (diff < k) righti++;
			
			//Otherwise (meaning the difference is smaller) increment
			//the left index (because it's sorted, we know doing that
			//will decrease the difference)
			else {
				lefti++;
				//The next line is because we never want the two indices
				//pointing to the same spot
				if (lefti == righti) righti++;
			}
		}
	}
}
