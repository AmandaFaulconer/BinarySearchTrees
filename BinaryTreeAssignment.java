/*
Binary Tree Assignment
Amanda Faulconer

*/
package binarytreeassignment;

/////////////////////////////////////////////////////////////////////////////

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Stack;

//Classes for Binary Search Tree

class Node {

    //the key should always be unique
    public int key;             //int - whole numbers
    public Node leftChild;
    public Node rightChild;

    //store some number data - anything you need to track
    public String myData = null;

    public Node() {
        this.key = 0;
        this.leftChild = null;
        this.rightChild = null;
        this.myData = "";
    }
    
    

    public void DisplayNode() {
        System.out.println(" {" + key + ", " + myData + "} ");
    }

}

class BTree {

    private Node root;
    
    public int hops = 0;

    public BTree() {
        root = null;
    }

    //public void insert(int theKey) {
    public void insert(int theKey, String Data) {

        Node newNode = new Node();

        newNode.key = theKey;

        //root == null means if the tree is empty
        if (root == null) {
            root = newNode;
        } else {
            //start at the top of the tree
            Node current = root;
            //create a parent node
            Node parent;

            //while allows you sort & continue, the returns exit the while loop
            while (true) {
                //1. start at the top of the tree
                parent = current;

                //2. if the key is less than the current key:
                if (theKey < current.key) {
                    //a. we go left
                    current = current.leftChild;
                    //b. if the current = null, the parent of the left 
                    //child is = to the new node
                    if (current == null) {
                        parent.leftChild = newNode;
                        return;
                    }
                }//---------------------------------
                else {
                    //3. we go right
                    current = current.rightChild;
                    if (current == null) {
                        parent.rightChild = newNode;
                        return;
                    }
                }
            }
        }
    }//end of insert function
    
    //function to find a node based on a key
    public Node find(int key) {
        
        //int hops = 0;
        //define the node that is going to act like the pointer and 
        //start at the top with root
        Node current = root;
        //while loop to find the node with the searched key
        while (current.key != key) {

            //navigate downt the tree
            if (key < current.key) {
                current = current.leftChild;
                hops++;
            } else {
                current = current.rightChild;
                hops++;
            }

            if (current == null) {
                //goes down to the last leaf and it's null & not found
                //checks to see if we are at the very end
                return null;
            }
        }
        System.out.println("Number of hops = " + hops);
        return current;
    } //end of function find

    //deleting a node by key
    public boolean delete(int key) {

        //delete a node with a given key
        //assume a non-empty list
        Node current = root;
        Node parent = root;

        boolean isLeftChild = true;

        //find the node to delete
        while (current.key != key) {

            parent = current;

            if (key < current.key) {
                //going left
                isLeftChild = true;
                current = current.leftChild;
            } else {
                //going right
                isLeftChild = false;
                current = current.rightChild;
            }

            if (current == null) {
                return false;
            }
        }

        //if we make it here, we have found the node
        //Senario 1
        //if there are no children we can simply delete the node
        if (current.leftChild == null & current.rightChild == null) {
            if (current == root) {
                root = null;
            } else if (isLeftChild) {
                parent.leftChild = null;
            } else {
                parent.rightChild = null;
            }
        } //Senario 2
        //if there is no right child, replace with left subtree
        else if (current.rightChild == null) {

            //if we are deleting the root we have to 
            //make the next left node the new root 
            if (current == root) {
                root = current.leftChild;
            } //if it's the left child
            else if (isLeftChild) {
                parent.leftChild = current.leftChild;
            } else {
                parent.rightChild = current.leftChild;
            }
        } //Senario 3
        //if no left child, replace with the right subtree
        else if (current.leftChild == null) {
            if (current == root) {
                root = current.rightChild;
            } else if (isLeftChild) {
                parent.leftChild = current.rightChild;
            } else {
                parent.rightChild = current.rightChild;
            }
        } //Senario 4
        //when you have two children in the tree
        //must replace with the inorder successor 
        else {
            //get the successor of the node you wish to delete
            Node successor = getSuccessor(current);

            //then connect the parent of the current to 
            //the new successor
            if (current == root) {
                root = successor;
            } else if (isLeftChild) {
                parent.leftChild = successor;
            } else {
                parent.rightChild = successor;
            }

            //now connect the successor to the current's left child
            successor.leftChild = current.leftChild;
        }

        return true;

    }//end of boolean delete

    //helper function for boolean delete else statement
    private Node getSuccessor(Node deleteNode) {

        Node successorParent = deleteNode;
        Node successor = deleteNode;
        Node current = deleteNode.rightChild;

        while (current != null) {

            successorParent = successor;
            successor = current;
            current = current.leftChild;

        }

        if (successor != deleteNode.rightChild) {
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = deleteNode.rightChild;
        }

        return successor;

    }//end of getSuccessor
       
    public void traverse(int travseTye){
        
        switch(travseTye){
            
            case 1: 
                System.out.println("\nPreOrder Traversal");
                preOrder(root);
                break;
            case 2: 
                System.out.println("\nInOrder Traversal");
                inOrder(root);
                break;
            case 3: 
                System.out.println("\nPostOrder Traversal");
                postOrder(root);
                break;
        }
        System.out.println("\n\n");
    }
    
    //helper functions for traversing the tree
    private void preOrder(Node localRoot){
       
        if(localRoot != null){
            System.out.println(localRoot.key + " | " + localRoot.myData);
            preOrder(localRoot.leftChild);
            preOrder(localRoot.rightChild);
        }
  
    }
    
    private void inOrder(Node localRoot){
        
        if(localRoot != null){
            inOrder(localRoot.leftChild);
            System.out.println(localRoot.key + " | " + localRoot.myData);      
            inOrder(localRoot.rightChild);
        }
    }
    
    private void postOrder(Node localRoot){
        
        if(localRoot != null){
            postOrder(localRoot.leftChild);                  
            postOrder(localRoot.rightChild);
            System.out.println(localRoot.key + " | " + localRoot.myData);
        }
    }

    public void displayTree() {

        Stack globalStack = new Stack();
        globalStack.push(root);
        int nBlanks = 32;
        boolean isRowEmpty = false;

        System.out.println(".................................................");

        while (isRowEmpty == false) {

            Stack localStack = new Stack();
            isRowEmpty = true;

            for (int j = 0; j < nBlanks; j++) {

                System.out.print(' ');
            }

            while (globalStack.isEmpty() == false) {

                Node temp = (Node) globalStack.pop();

                if (temp != null) {
                    System.out.print(temp.key);
                    localStack.push(temp.leftChild);
                    localStack.push(temp.rightChild);

                    if (temp.leftChild != null || temp.rightChild != null) {
                        isRowEmpty = false;
                    }
                } else {
                    System.out.print("--");
                    localStack.push(null);
                    localStack.push(null);
                }

                for (int j = 0; j < nBlanks * 2 - 2; j++) {
                    System.out.print(' ');
                }
            } //inside while
            System.out.println();
            nBlanks /= 2;

            while (localStack.isEmpty() == false) {
                globalStack.push(localStack.pop());
            }
        }//outside while
        System.out.println(".................................................");
    }//end of function

    // print the tree structure on the screen
    private void printHelper(Node currPtr, String indent, boolean last) {
		// print the tree structure on the screen
	   	if (currPtr != null) {
		   System.out.print(indent);
		   if (last) {
		      System.out.print("R----");
		      indent += "     ";
		   } else {
		      System.out.print("L----");
		      indent += "|    ";
		   }

		   System.out.println(currPtr.key +  "");

		   printHelper(currPtr.leftChild, indent, false);
		   printHelper(currPtr.rightChild, indent, true);
		}
	}

    public void prettyPrint(){
        printHelper(this.root, "", true);
    }
    
    
    //------------------------------------------------------------
    // find the node with the minimum key
    public int MinKey(){ //do right for the MaxKey
        Node currentNode = new Node();
        currentNode = root; //where to start
        while(currentNode.leftChild != null){   //stops at last node in tree
            currentNode = currentNode.leftChild;
            hops++;
        }
        System.out.println("\nNumber of hops = " + hops);
        return currentNode.key;
      }

    // find the node with the maximum key
    public int MaxKey(){ 
        Node currentNode = new Node();
        currentNode = root; //where to start
        while(currentNode.rightChild != null){   //stops at last node in tree
            currentNode = currentNode.rightChild;
            hops++;
        }
        System.out.println("\nNumber of hops = " + hops);
        return currentNode.key;
      }
    
    //-------------------------------------------------------------
    
} //end of class BST

////////////////////////////////////////////////////////////////////////////////

//public static void BST_Ex(){

//}

public class BinaryTreeAssignment {
    
    
    //----------------------------------------------------------
    public static String getString() throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        s = s.toLowerCase();
        return s;
    }
    //----------------------------------------------------------
    public static char getChar() throws IOException {
        String s = getString();
        return s.charAt(0);
    }
    //----------------------------------------------------------
    public static int getInt() throws IOException {
	String s = getString();
	return Integer.parseInt(s);
    }

    //------------------------------------------------------
    //Running Binary Search Tree

    static Scanner Scan = new Scanner(System.in);
    
    static BTree theTree = new BTree();
    
    static void BSTreadFile(String filepath){

        String inputLine;

        String x, y;

        int cntr = 1;
        
        try{
        BufferedReader scanIn = new BufferedReader(new FileReader(filepath));

            while((inputLine = scanIn.readLine() ) != null){
                
                    String[] inputArray = inputLine.split(", ");

                    for(int c = 0; c < inputArray.length; c++){
                        theTree.insert(Integer.parseInt(inputArray[c]),"");
                        cntr++;
                        System.out.println(inputArray[c]);
                    }                             
                }
        }
        
        //You can stack different exceptions
        catch(IOException e){
            System.out.println("Error 1 " + e.getMessage());
        }
        catch(Exception e){
            System.out.println("Error 2 " + e.getMessage());
        }
    }
 
    public static void BSTRun() throws IOException{
        
        String thePath = "c:\\users\\amanda\\desktop\\Random9000integers.txt";
        BSTreadFile(thePath);
        
        System.out.println("------------------------");
        
        int dint, iint, sint;

        Node findNode = new Node();
        
        while(true){
            
            System.out.print("Enter first letter of ");
            System.out.print("view, search, delete, insert, highest, lowest, or quit: ");

            char choice = getChar();
            
            switch(choice){
                
                case 'v':
                    theTree.prettyPrint();
                    break;
                case 's':
                    System.out.print("Search key (integer): ");
                    sint = Integer.parseInt(Scan.nextLine());
                    findNode = theTree.find(sint);
                    if (findNode != null) {
                        System.out.println("Value is: " + findNode.key);
                    } else {
                        System.out.println("The node was not found");
                    }
                    break;
                case 'd':
                    System.out.print("Delete key (integer): ");
                    dint = Integer.parseInt(Scan.nextLine());
                    theTree.delete(dint);
                    break;
                case 'i':
                    System.out.print("Insert key (integer): ");
                    iint = Integer.parseInt(Scan.nextLine());
                    theTree.insert(iint, thePath);
                    break;
                case 'h':
                    System.out.print("View the highest key (maximum)");
                    System.out.println("The max: " + theTree.MaxKey());
                    break;
                case 'l':
                    System.out.print("View the lowest key (minumum)");
                    System.out.println("The min: " + theTree.MinKey());
                    break;
                case 'q':
                    return;
                default:
                    System.out.println("Invalid Key!");
                    break;
            }           
        }
    }
    
    //-------------------------------------------------------------------------
    
    //AVL tree
    
    static AVLTree theAVLTree = new AVLTree();
    
    static void AVLreadFile(String filepath){

        String inputLine;

        int cntr = 5;
        
        try{
        BufferedReader scanIn = new BufferedReader(new FileReader(filepath));

            while((inputLine = scanIn.readLine() ) != null){
                
                    String[] inputArray = inputLine.split(", ");

                    for(int c = 0; c < inputArray.length; c++){
                        int x = Integer.valueOf(inputArray[c]);
                        theAVLTree.insert(x);
                    }                             
                }                
        }

        catch(IOException e){
            System.out.println("Error 1 " + e.getMessage());
        }
        catch(Exception e){
            System.out.println("Error 2 " + e.getMessage());
        }
    }
    
    public static void AVLRun() throws IOException {
        
                String thePath = "c:\\users\\amanda\\desktop\\Random9000integers.txt";
        AVLreadFile(thePath);
        
        System.out.println("------------------------");
        
        int dint, iint, sint;

        AVLNode findNode = new AVLNode();
        
        while(true){
            
            System.out.print("Enter first letter of ");
            System.out.print("view, search, delete, insert, highest, lowest, or quit: ");

            char choice = getChar();
            
            switch(choice){
                
                case 'v':
                    theAVLTree.prettyPrint();
                    break;
                case 's':
                    System.out.print("Search key (integer): ");
                    sint = Integer.parseInt(Scan.nextLine());
                    findNode = theAVLTree.searchTree(sint);
                    if (findNode != null) {
                        System.out.println("Value is: " + findNode.key);
                    } else {
                        System.out.println("The node was not found");
                    }
                    break;
                case 'd':
                    System.out.print("Delete key (integer): ");
                    dint = Integer.parseInt(Scan.nextLine());
                    theAVLTree.deleteNode(dint);
                    break;
                case 'i':
                    System.out.print("Insert key (integer): ");
                    iint = Integer.parseInt(Scan.nextLine());
                    theAVLTree.insert(iint);
                    break;
                case 'h':
                    System.out.print("View the highest key (maximum)");                    
                    AVLNode mNode = new AVLNode();
                    mNode =  theAVLTree.maximum(theAVLTree.root);
                    System.out.println("The max: " + mNode.key);
                    break;
                case 'l':
                    System.out.print("View the lowest key (minumum)");
                    AVLNode lNode = new AVLNode();
                    mNode =  theAVLTree.maximum(theAVLTree.root);
                    System.out.println("The min: " + lNode.key);
                    break;
                case 'q':
                    return;
                default:
                    System.out.println("Invalid Key!");
                    break;
            }           
        }
    }
    //-----------------------------------------------------------

    //red black tree
    
    static RedBlackTree bst = new RedBlackTree();
    
    static void RBTreadFile(String filepath){

        String inputLine;

        String x, y;

        int cntr = 1;
        
        try{
        BufferedReader scanIn = new BufferedReader(new FileReader(filepath));

            while((inputLine = scanIn.readLine() ) != null){
                
                    String[] inputArray = inputLine.split(", ");

                    for(int c = 0; c < inputArray.length; c++){
                        bst.insert(cntr, inputArray[c]);
                        cntr++;
                        System.out.println(inputArray[c]);
                    }                             
                }                
        }
        
        //You can stack different exceptions
        catch(IOException e){
            System.out.println("Error 1 " + e.getMessage());
        }
        catch(Exception e){
            System.out.println("Error 2 " + e.getMessage());
        }
    }

    static void RBTRun() throws IOException{
        String thePath = "c:\\users\\amanda\\desktop\\Random9000integers.txt";
        RBTreadFile(thePath);
        
        System.out.println("------------------------");
        
        int dint, iint, sint;

        RBTNode findNode = new RBTNode();
        
        while(true){
            
            System.out.print("Enter first letter of ");
            System.out.print("view, search, delete, insert, highest, lowest, or quit: ");

            char choice = getChar();
            
            switch(choice){
                
                case 'v':
                    bst.prettyPrint();
                    break;
                case 's':
                    System.out.print("Search key (integer): ");
                    sint = Integer.parseInt(Scan.nextLine());
                    findNode = bst.searchTree(sint);
                    if (findNode != null) {
                        System.out.println("Value is: " + findNode.key);
                    } else {
                        System.out.println("The node was not found");
                    }
                    break;
                case 'd':
                    System.out.print("Delete key (integer): ");
                    dint = Integer.parseInt(Scan.nextLine());
                    bst.deleteRBTNode(dint);
                    break;
                case 'i':
                    System.out.print("Insert key (integer): ");
                    iint = Integer.parseInt(Scan.nextLine());
                    bst.insert(iint, thePath);
                    break;
                case 'h':
                    System.out.print("View the highest key (maximum)");
                    RBTNode hNode = new RBTNode();
                    hNode =  bst.maximum(bst.root);
                    System.out.println("The min: " + hNode.key);
                    break;
                case 'l':
                    System.out.print("View the lowest key (minumum)");
                    RBTNode lNode = new RBTNode();
                    lNode =  bst.maximum(bst.root);
                    System.out.println("The min: " + lNode.key);
                    break;
                case 'q':
                    return;
                default:
                    System.out.println("Invalid Key!");
                    break;
            }           
        }
    }
    
    //----------------------------------------------------------
    
    //menu for trees
    
    public static void Menu() throws IOException {
        Scanner menuScan = new Scanner(System.in);
        int choice;
        System.out.println("Menu\n");
        System.out.println("Please choose one of the following options:"
                + "\n1. Binary Search Tree"
                + "\n2. AVL Tree"
                + "\n3. Red Black Tree"
                + "\n4. End the entire Program"
                + "\n Please make a choice: ");
        choice = Integer.parseInt(menuScan.nextLine());
        switch(choice){
            case 1 -> BSTRun();
            case 2 -> AVLRun();
            case 3 -> RBTRun();
            case 4 -> {
                System.out.println("Thank you for stopping by!");
                System.exit(0);
            }
        }       
    }
    
    public static void main(String[] args) throws IOException {
        
        Menu();
        
    }
}
    

