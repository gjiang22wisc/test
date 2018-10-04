/**
 * Filename:   AVLTree.java
 * Project:    p2
 * Authors:    Debra Deppeler, Gordon Jiang
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * Lecture:    LEC - 46373
 * 
 * Due Date:   specified in Canvas
 * Version:    1.0
 * 
 * Credits:    TODO: name individuals and sources outside of course staff
 * 
 * Bugs:       no known bugs
 */

import java.lang.IllegalArgumentException;
import java.lang.Math.*;

/** TODO: add class header comments here
 * @param <K>
 */
public class AVLTree<K extends Comparable<K>> implements AVLTreeADT<K> {
	
	//Constructor for the start of my tree
	BSTNode<K> root;
	public AVLTree() { root = null; }
	
	/** TODO: add class header comments here
	 * Represents a tree node.
	 * @param <K>
	 */
	class BSTNode<K> {
		/* fields */
		private K key;	// TODO: variable declaration comment
		private int height;	// TODO: variable declaration comment
		private BSTNode<K> left, right;	// TODO: variable declaration comment
		//private BSTNode<K> root;
	    
		/**
		 * Constructor for a BST node.
		 * @param key, left, right
		 */
		BSTNode(K key, BSTNode<K> left, BSTNode<K> right) {
			// TODO: implement constructor
			this.key=key;
			this.left=left;
			this.right=right;
		}

		/* accessors */

		public K getKey() {
			return this.key;
		}
		
		public int getHeight() {
			return this.height;
		}

		public BSTNode<K> getLeft() {
			return this.left;
		}

		public BSTNode<K> getRight() {
			return this.right;
		}

		/* mutators */

		private void setKey(K key) {
			this.key=key;
		}
		
		private void setHeight(int height) {
			this.height=height;
		}

		private void setLeft (BSTNode<K> left) {
			this.left=left;
		}

		private void setRight(BSTNode<K> right) {
			this.right=right;
		}

	}

	/**
	 * Checks if the Binary Tree is empty
	 */
	@Override
	public boolean isEmpty() {
		if(root == null) {return true;}
		else {return false;}
	}

	/**
	 * Adds key to the AVL tree
	 * @param key
	 * @throws DuplicateKeyException if key is already in the AVL tree
	 * @throws IllegalArgumentException if null value inserted
	 */
	@Override
	public void insert(K key) throws DuplicateKeyException, IllegalArgumentException {
		 root = insert(root,key);
	}
	
	/**
	 * Private method to add a node to the tree.
	 * Will try to do rotations to make the tree balanced
	 * @param n
	 * @param key
	 * @return n
	 * @throws DuplicateKeyException
	 */
	
	private BSTNode<K> insert(BSTNode<K> n, K key) throws DuplicateKeyException {
	    if (n == null) {
	        return new BSTNode<K>(key, null, null);
	    }
	     
	    if (n.getKey().equals(key)) {
	        throw new DuplicateKeyException();
	    }
	    
	    if (key.compareTo(n.getKey()) < 0) {
	        // add key to the left subtree
	        n.setLeft(insert(n.getLeft(), key) );
	    }
	    
	    if ((key.compareTo(n.getKey()) > 0)) {
	        // add key to the right subtree
	        n.setRight( insert(n.getRight(), key) );
	    }
	    
	    
	    // Update height of this parent
        n.setHeight(1 + max(getHeight(n.getLeft()),getHeight(n.getRight())));
        
        //checks to make sure the node is balanced
        int balance = getBalance(n);
        
        // If this node becomes unbalanced then start rebalancing
        //Rotate one direction only
        if (balance < -1 && key.compareTo(n.getLeft().getKey()) < 0) {
            return rotateRight(n); 
        }
  
        if (balance > 1 && key.compareTo(n.getRight().getKey()) > 0) {
            return rotateLeft(n); 
        }
  
        // Handle double rotations 
        if (balance < -1 && key.compareTo(n.getLeft().getKey()) > 0) { 
            n.setLeft(rotateLeft(n.getLeft())); 
            return rotateRight(n); 
        } 
  
        if (balance > 1 && key.compareTo(n.getRight().getKey()) < 0) { 
            n.setRight(rotateRight(n.getRight())); 
            return rotateLeft(n); 
        }
       return n;
	}
	
	
	/**
	 * Deletes a node from the tree
	 * @throws IllegalArgumentException if the key doesn't exist in the tree
	 */
	@Override
	public void delete(K key) throws IllegalArgumentException {
		root = delete(root, key);
	}
	 
	/**
	 * Private method to delete a node from the tree.
	 * Will try to do rotations after deleting to make the tree balanced
	 * @param n
	 * @param key
	 * @return n
	 */
	
	private BSTNode<K> delete(BSTNode<K> n, K key) {
	    if (n == null) {
	        return null;
	    }
	    
	    if (key.equals(n.getKey())) {
	        // n is the node to be removed
	        if (n.getLeft() == null && n.getRight() == null) {
	            return null;
	        }
	        if (n.getLeft() == null) {
	            return n.getRight();
	        }
	        if (n.getRight() == null) {
	            return n.getLeft();
	        }
	       
	        // if we get here, then n has 2 children
	        K smallVal = smallest(n.getRight());
	        n.setKey(smallVal);
	        n.setRight( delete(n.getRight(), smallVal) ); 
	    }
	    
	    if (key.compareTo(n.getKey()) < 0) {
	        n.setLeft( delete(n.getLeft(), key) );
	    }
	    
	    if (key.compareTo(n.getKey()) > 0){
	        n.setRight( delete(n.getRight(), key) );
	    }
	    // If this node becomes unbalanced
	    n.setHeight(1 + max(getHeight(n.getLeft()),getHeight(n.getRight())));
	    
	    int balance = getBalance(n);
	    
	    // Right rotate 
	    if (balance < -1 && key.compareTo(n.getLeft().getKey()) <= 0) {
            return rotateRight(n); 
        }
	  
	    // Left Right rotate 
	    if (balance < -1 && key.compareTo(n.getLeft().getKey()) > 0) { 
            n.setLeft(rotateLeft(n.getLeft())); 
            return rotateRight(n); 
        }
	  
	    // Left rotate
	    if (balance > 1 && key.compareTo(n.getRight().getKey()) >= 0) {
            return rotateLeft(n); 
        }
	  
	    // rotate right then left 
	    if (balance > 1 && key.compareTo(n.getRight().getKey()) < 0) { 
            n.setRight(rotateRight(n.getRight())); 
            return rotateLeft(n); 
        }
	    return n;    
	}
	
	/**
	 * Helper function to figure out how to reorder the node after deleting
	 * @param n
	 * @return
	 */
	private K smallest(BSTNode<K> n)
	// precondition: n is not null
	// postcondition: return the smallest value in the subtree rooted at n

	{
	    if (n.getLeft() == null) {
	        return n.getKey();
	    } else {
	        return smallest(n.getLeft());
	    }
	}

	/**
	 * Search for a key in AVL tree
	 * @param key
	 * @return true if AVL tree contains that key
	 * @throws IllegalArgumentException if searching for a null value
	 */
	@Override
	public boolean search(K key) throws IllegalArgumentException {
		return lookup(root, key);
	}
	
	private boolean lookup(BSTNode<K> n, K key) {
	    if (n == null) {
	        return false;
	    }
	    
	    if (n.getKey().equals(key)) {
	        return true;
	    }
	    
	    if (key.compareTo(n.getKey()) < 0) {
	        // key < this node's key; look in left subtree
	        return lookup(n.getLeft(), key);
	    }
	    
	    else {
	        // key > this node's key; look in right subtree
	        return lookup(n.getRight(), key);
	    }
	}
	

	/**
	 * Performs in-order traversal of AVL Tree
	 * @return a String with all the keys, in order, with exactly one space between keys
	 */
	@Override
	public String print() {
		
		return printInOrder(root);
	}
	
	private String printInOrder(BSTNode<K> n) { 
	   String retVal = "";
		if (n == null) {return "";}
	   else {
	   /* first recur on left child */
		   retVal = retVal + (String)printInOrder(n.getLeft()); 
  
        /* then print the data of node */
        retVal=retVal+(n.getKey().toString() + " ");
		   
		
        /* now recur on right child */
        retVal=retVal+(String)printInOrder(n.getRight());
		}
	   return retVal;
    }

		
		
	/**
	 * Checks for the Balanced Search Tree.
	 * @return true if AVL tree is balanced tree
	 */
	@Override
	public boolean checkForBalancedTree() {
		// TODO: implement checkForBalancedTree()
		//add 1 to the max height of the left or right node
		BSTNode<K> a=root.getLeft();
		BSTNode<K> b=root.getRight();
		int heightCompare=b.getHeight()-a.getHeight();

		if (java.lang.Math.abs(heightCompare)>1) {
			return false;
		}
		else {return true;}
	}

	/**
	 * Checks for Binary Search Tree.
	 * @return true if AVL tree is binary search tree.
	 */
	@Override
	public boolean checkForBinarySearchTree() {
		return checkForBinarySearchTree(root);
	}
	
	private boolean checkForBinarySearchTree(BSTNode<K> n) {
		BSTNode<K> leftNode=n.getLeft();
		BSTNode<K> rightNode=n.getRight();
		
		//check if left child node is smaller than parent node
		if(leftNode != null && rightNode == null && leftNode.getKey().compareTo(n.getKey()) < 0){
			return checkForBinarySearchTree(leftNode);
		}
		//fail is left child node is bigger than right node
		else if(leftNode != null && rightNode == null && leftNode.getKey().compareTo(n.getKey()) > 0){
			return false;
		}
		
		//check if right child node is larger than parent node 
		else if(rightNode != null && leftNode == null && rightNode.getKey().compareTo(n.getKey()) > 0){
			return checkForBinarySearchTree(rightNode);
		} 
		//fail if right child node is smaller than parent node
		else if(rightNode != null && leftNode == null && rightNode.getKey().compareTo(n.getKey()) < 0){
			return false;
		} 
		//if both nodes populated check that child left is smaller than parent and child right is greater than parent
		else if(rightNode != null && leftNode != null && leftNode.getKey().compareTo(n.getKey()) < 0 && rightNode.getKey().compareTo(n.getKey()) > 0){
			return checkForBinarySearchTree(leftNode) && checkForBinarySearchTree(rightNode);
		}
		//if both node populated, fail if child left is greater than parent and child right is smaller than parent
		else if (rightNode != null && leftNode != null && leftNode.getKey().compareTo(n.getKey()) > 0 && rightNode.getKey().compareTo(n.getKey()) < 0){
			return false;
		}
		return true;
	}
	
	
	
	
	
	/**
	 * Calculates the height of the current node
	 * by taking the greater of the left or right
	 * nodes beneath it
	 * @param n
	 * @return the height of the current node
	 */
	public int getHeight(BSTNode<K> n) {
		if (n==null){
			return -1;
		}
		else {
			int leftHeight=getHeight(n.getLeft());
			int rightHeight=getHeight(n.getRight());
			
			if (leftHeight>rightHeight) {
				return leftHeight+1;
			}
			else {
				return rightHeight+1;
			}
		}	
	}
	
	/**
	 * Checks to see if the tree is balanced
	 * by comparing the right and left heights
	 * @param n
	 * @return difference between the height of the right
	 * and left subtree
	 */
	public int getBalance (BSTNode<K> n) {
		if (n==null) {
			return 0;
		}
		return getHeight(n.getRight())-getHeight(n.getLeft());
	}
	
	/** Rotates node a to the left, making its right child into its parent.
	 * @param a the former parent
	 * @return b (the new parent, formerly a's right child)
	 */
	public BSTNode<K> rotateLeft(BSTNode<K> a) {
        BSTNode<K> b = a.getRight();
        a.setRight(b.getLeft());
        b.setLeft(a);
	    
	    a.height = max(getHeight(a.left), getHeight(a.right)) + 1; 
        b.height = max(getHeight(b.left),getHeight(b.right)) + 1;
        return b;
		}
	
	/**
	 * Rotates node a to the right, making its left child into its parent.
	 * @param a the former parent
	 * @return b (the new parent, formerly a's left child)
	 */
	public BSTNode<K> rotateRight(BSTNode<K> a) {
	    BSTNode<K> b = a.getLeft();
	    a.setLeft(b.getRight());
	    b.setRight(a);
	    
	    a.height = max(getHeight(a.left), getHeight(a.right)) + 1; 
        b.height = max(getHeight(b.left),getHeight(b.right)) + 1; 
        return b;
	}
	
	/**
	 * Helper function to determine which integer has the greatest value
	 * @param a
	 * @param b
	 * @return the greater of two integers
	 */
	int max(int a, int b) { 
        return (a > b) ? a : b; 
    }

	/*
	// prints a tree diagram sideways on your screen
	// source:  Building Java Programs, 4th Ed., by Reges and Stepp, Ch 17
	public void printSideways() {
		System.out.println("------------------------------------------");
		recursivePrintSideways(root, "");
		System.out.println("------------------------------------------");

	}

	private void recursivePrintSideways(BSTNode<K> current, String indent) {
		if (current != null) {
			recursivePrintSideways(current.getRight(), indent + "    ");
			System.out.println(indent + current.getKey());
			recursivePrintSideways(current.getLeft(), indent + "    ");
		}
	}
	*/
	

	public static void main(String[] args) throws IllegalArgumentException, DuplicateKeyException {
		
		AVLTree<Integer> tree = new AVLTree<Integer>();
		tree.insert(10); 
		tree.insert(20); 
		tree.insert(30);
		tree.insert(40); 
		tree.insert(50); 
		tree.insert(25);
		tree.insert(5);
		tree.insert(15);
		tree.insert(21);
		tree.insert(28);
		tree.delete(40);
		//tree.printSideways();
		
		//check if tree is empty
		if (tree.isEmpty()==true){
			System.out.println("Your tree is empty");
		}
		if (tree.isEmpty()==false){
			System.out.println("Your tree is not empty");
		}
		
		//print out the tree using an in order traversal
		System.out.println("Your nodes from smallest to largest are: " + tree.print());
		
		//check for balance
		if (tree.checkForBalancedTree()==true) {
			System.out.println("Your tree is balanced");
		}
		if (tree.checkForBalancedTree()==false) {
			System.out.println("You tree is not balanced");
		}
		
		//check if it is binary
		if (tree.checkForBinarySearchTree()==true){
			System.out.println("Your tree is a binary search tree");
		}
		if (tree.checkForBinarySearchTree()==false){
			System.out.println("Your tree is not binary search tree");
		}
		
		//Trying to insert a duplicate value
		try {
			tree.insert(28);
		}
		catch (DuplicateKeyException e) {
			System.out.println("You are not allowed to insert an already existing value into the tree");
		}
		
		//testing to see if value exists in the tree
		if (tree.search(5)==true) {System.out.println("Your test value is inside the tree");}
		if (tree.search(5)==false) {System.out.println("Your test value is not inside the tree");}
		
	}	
}