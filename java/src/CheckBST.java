/**
 * 
 * @author Ephraim Rothschild
 *
 * Question: Check if a Binary tree is a Binary Search Tree	
 * Question link: http://www.careercup.com/question?id=5126656387710976
 *
 */
	

public class CheckBST {
	static class Node {
		int data;
		Node left;
		Node right;
		public Node(int data) {
			this.data = data;
		}
	}

	public static void main(String[] args) {
		//Is a BST
		Node bst = new Node(50);
		Node currbst = bst;
		currbst.right = new Node(70);
		currbst.left = new Node(40);
		currbst = currbst.right;
		currbst.right = new Node(90);
		currbst.left = new Node(60);
		currbst = bst.left;
		currbst.right = new Node(45);
		currbst.left = new Node(30);

		
		//Not a BST
		Node notbst = new Node(50);
		currbst = notbst;
		currbst.right = new Node(70);
		currbst.left = new Node(40);
		currbst = currbst.right;
		currbst.right = new Node(90);
		currbst.left = new Node(60);
		currbst = notbst.left;
		currbst.right = new Node(55);
		currbst.left = new Node(30);
		
		System.out.println(isBST(bst));
		System.out.println(isBST(notbst));
	}
	
	public static boolean isBST(Node node) {
		return isBST(node, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	private static boolean isBST(Node node, int min, int max) {
		//If it's null, we can assume this node is just an empty BST
		if (node == null) return true;
		//We want to make sure that the current data is greater than the min
		return node.data > min 
				//And less than the max
				&& node.data < max 
				//And all nodes to the right of this are greater than the min
				//and less then the current node (recursively)
				&& isBST(node.left, min, node.data)
				//And all nodes to the left of this are greater than the current
				//node, and less than the max (recursively).
				&& isBST(node.right, node.data, max);
	}
}
