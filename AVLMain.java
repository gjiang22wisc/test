
public class AVLMain {

	public static void main(String[] args) throws IllegalArgumentException, DuplicateKeyException {
		AVLTree<Integer> bst = new AVLTree<Integer>();
		
		/*
		bst.insert(10);
		bst.printSideways();
		bst.insert(20);
		bst.printSideways();
		bst.insert(5);
		bst.printSideways();
		bst.insert(25);
		bst.printSideways();
		bst.insert(22);
		bst.printSideways();
		bst.insert(50);
		bst.printSideways();
		bst.insert(70);
		bst.printSideways();
		//bst.delete(20);
		bst.insert(75);
		bst.printSideways();
		bst.insert(80);
		bst.printSideways();
		*/
		/*
		bst.insert(10); 
		//System.out.println(bst.getKey+"---------"+bst.getHeight);
		bst.insert(20); 
		//System.out.println("---------");
		bst.insert(30);
		//System.out.println("---------");
		bst.insert(40); 
		bst.insert(50); 
		bst.insert(25);
		bst.insert(5);
		bst.insert(15);
		bst.insert(21);
		bst.insert(28);
		bst.delete(40);
		//System.out.println(bst.print());
		System.out.println(bst.checkForBalancedTree());
		bst.printSideways();
		
		*/
	
	bst.insert(10); 
	bst.insert(20); 
	bst.insert(30);
	bst.printSideways();
	System.out.println(bst.checkForBinarySearchTree());
	}
}