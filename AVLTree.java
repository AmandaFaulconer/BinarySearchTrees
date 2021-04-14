/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarytreeassignment;

// key structure that represents a node in the tree
class AVLNode {
	int key; // holds the key
	AVLNode parent; // pointer to the parent
	AVLNode left; // pointer to left child
	AVLNode right; // pointer to right child
	int bf; // balance factor of the node

	public AVLNode(int key) {
		this.key = key;
		this.parent = null;
		this.left = null;
		this.right = null;
		this.bf = 0;
	}
        
        public AVLNode(){
            
        }
}

public class AVLTree {
	public AVLNode root;
        public int hops = 0;

	public AVLTree() {
		root = null;
	}

	private void printHelper(AVLNode currPtr, String indent, boolean last) {
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

		   System.out.println(currPtr.key + "(BF = " + currPtr.bf + ")");

		   printHelper(currPtr.left, indent, false);
		   printHelper(currPtr.right, indent, true);
		}
	}

	private AVLNode searchTreeHelper(AVLNode node, int key) {
		if (node == null || key == node.key) {
                    return node;     
		}

		if (key < node.key) {
                    hops++;
                    return searchTreeHelper(node.left, key);
		} 
		return searchTreeHelper(node.right, key);
	}

	private AVLNode deleteNodeHelper(AVLNode node, int key) {
		// search the key
		if (node == null) return node;
		else if (key < node.key) node.left = deleteNodeHelper(node.left, key);
		else if (key > node.key) node.right = deleteNodeHelper(node.right, key);
		else {
			// the key has been found, now delete it

			// case 1: node is a leaf node
			if (node.left == null && node.right == null) {
				node = null;
			}

			// case 2: node has only one child
			else if (node.left == null) {
				AVLNode temp = node;
				node = node.right;
			}

			else if (node.right == null) {
				AVLNode temp = node;
				node = node.left;
			}

			// case 3: has both children
			else {
				AVLNode temp = minimum(node.right);
				node.key = temp.key;
				node.right = deleteNodeHelper(node.right, temp.key);
			}

		} 

		// Write the update balance logic here 
		// YOUR CODE HERE

		return node;
	}

	// update the balance factor the node
	private void updateBalance(AVLNode node) {
		if (node.bf < -1 || node.bf > 1) {
			rebalance(node);
			return;
		}

		if (node.parent != null) {
			if (node == node.parent.left) {
				node.parent.bf -= 1;
			} 

			if (node == node.parent.right) {
				node.parent.bf += 1;
			}

			if (node.parent.bf != 0) {
				updateBalance(node.parent);
			}
		}
	}

	// rebalance the tree
	void rebalance(AVLNode node) {
		if (node.bf > 0) {
			if (node.right.bf < 0) {
				rightRotate(node.right);
				leftRotate(node);
			} else {
				leftRotate(node);
			}
		} else if (node.bf < 0) {
			if (node.left.bf > 0) {
				leftRotate(node.left);
				rightRotate(node);
			} else {
				rightRotate(node);
			}
		}
	}


	private void preOrderHelper(AVLNode node) {
		if (node != null) {
			System.out.print(node.key + " ");
			preOrderHelper(node.left);
			preOrderHelper(node.right);
		} 
	}

	private void inOrderHelper(AVLNode node) {
		if (node != null) {
			inOrderHelper(node.left);
			System.out.print(node.key + " ");
			inOrderHelper(node.right);
		} 
	}

	private void postOrderHelper(AVLNode node) {
		if (node != null) {
			postOrderHelper(node.left);
			postOrderHelper(node.right);
			System.out.print(node.key + " ");
		} 
	}

	// Pre-Order traversal
	// Node.Left Subtree.Right Subtree
	public void preorder() {
		preOrderHelper(this.root);
	}

	// In-Order traversal
	// Left Subtree . AVLNode . Right Subtree
	public void inorder() {
		inOrderHelper(this.root);
	}

	// Post-Order traversal
	// Left Subtree . Right Subtree . Node
	public void postorder() {
		postOrderHelper(this.root);
	}

	// search the tree for the key k
	// and return the corresponding node
	public AVLNode searchTree(int k) {
            System.out.println("Number of hops = " + hops);
		return searchTreeHelper(this.root, k);
	}

	// find the node with the minimum key
	public AVLNode minimum(AVLNode node) {
		while (node.left != null) {
			node = node.left;
                        hops++;
		}
                System.out.println("Number of hops = " + hops);
		return node;
	}

	// find the node with the maximum key
	public AVLNode maximum(AVLNode node) {
		while (node.right != null) {
			node = node.right;
                        hops++;
		}
                System.out.println("Number of hops = " + hops);
		return node;
	}

	// find the successor of a given node
	public AVLNode successor(AVLNode x) {
		// if the right subtree is not null,
		// the successor is the leftmost node in the
		// right subtree
		if (x.right != null) {
			return minimum(x.right);
		}

		// else it is the lowest ancestor of x whose
		// left child is also an ancestor of x.
		AVLNode y = x.parent;
		while (y != null && x == y.right) {
			x = y;
			y = y.parent;
		}
		return y;
	}

	// find the predecessor of a given node
	public AVLNode predecessor(AVLNode x) {
		// if the left subtree is not null,
		// the predecessor is the rightmost node in the 
		// left subtree
		if (x.left != null) {
			return maximum(x.left);
		}

		AVLNode y = x.parent;
		while (y != null && x == y.left) {
			x = y;
			y = y.parent;
		}

		return y;
	}

	// rotate left at node x
	void leftRotate(AVLNode x) {
		AVLNode y = x.right;
		x.right = y.left;
		if (y.left != null) {
			y.left.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == null) {
			this.root = y;
		} else if (x == x.parent.left) {
			x.parent.left = y;
		} else {
			x.parent.right = y;
		}
		y.left = x;
		x.parent = y;

		// update the balance factor
		x.bf = x.bf - 1 - Math.max(0, y.bf);
		y.bf = y.bf - 1 + Math.min(0, x.bf);
	}

	// rotate right at node x
	void rightRotate(AVLNode x) {
		AVLNode y = x.left;
		x.left = y.right;
		if (y.right != null) {
			y.right.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == null) {
			this.root = y;
		} else if (x == x.parent.right) {
			x.parent.right = y;
		} else {
			x.parent.left = y;
		}
		y.right = x;
		x.parent = y;

		// update the balance factor
		x.bf = x.bf + 1 - Math.min(0, y.bf);
		y.bf = y.bf + 1 + Math.max(0, x.bf);
	}


	// insert the key to the tree in its appropriate position
	public void insert(int key) {
		// PART 1: Ordinary BST insert
		AVLNode node = new AVLNode(key);
		AVLNode y = null;
		AVLNode x = this.root;

		while (x != null) {
			y = x;
			if (node.key < x.key) {
				x = x.left;
			} else {
				x = x.right;
			}
		}

		// y is parent of x
		node.parent = y;
		if (y == null) {
			root = node;
		} else if (node.key < y.key) {
			y.left = node;
		} else {
			y.right = node;
		}

		// PART 2: re-balance the node if necessary
		updateBalance(node);
	}

	// delete the node from the tree
	AVLNode deleteNode(int key) {
		return deleteNodeHelper(this.root, key);
	}

	// print the tree structure on the screen
	public void prettyPrint() {
		printHelper(this.root, "", true);
	}
}
