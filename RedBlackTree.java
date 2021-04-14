/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarytreeassignment;

import java.util.Random;

class RBTNode
{

    int key; // holds the key
    RBTNode parent; // pointer to the parent
    RBTNode left; // pointer to left child
    RBTNode right; // pointer to right child
    int color; // 1 . Red, 0 . Black

    //some data - in this case string
    String sdata;
}

public class RedBlackTree {
    public RBTNode root;
    private RBTNode TNULL;
    public int hops = 0;

    private void preOrderHelper(RBTNode node)
    {
        if (node != TNULL)
        {
            System.out.print(node.key + " ");
            preOrderHelper(node.left);
            preOrderHelper(node.right);
        }
    }

    private void inOrderHelper(RBTNode node)
    {
        if (node != TNULL)
        {
            inOrderHelper(node.left);
            System.out.print(node.key + " ");
            inOrderHelper(node.right);
        }
    }

    private void postOrderHelper(RBTNode node)
    {
        if (node != TNULL)
        {
            postOrderHelper(node.left);
            postOrderHelper(node.right);
            System.out.print(node.key + " ");
        }
    }

    private void preOrder()
    {
        System.out.println("\nPreOder");
        RBTNode node = root;
        if (node != TNULL)
        {
            System.out.print(" **"+ node.key + "** ");
            preOrderHelper(node.left);
            preOrderHelper(node.right);
        }
        System.out.println("\n------------------\n");
    }

    // In-Order traversal
    // Left Subtree . RBTNode . Right Subtree
    public void inOrder()
    {
         System.out.println("\ninOder");
        RBTNode node = root;
        if (node != TNULL)
        {
            inOrderHelper(node.left);
            System.out.print(" **"+ node.key + "** ");
            inOrderHelper(node.right);
        }
        System.out.println("\n------------------\n");
    }

    // Post-Order traversal
    // Left Subtree . Right Subtree . RBTNode
    public void postOrder()
    {
         System.out.println("\npostOder");
        RBTNode node = root;
        if (node != TNULL)
        {
            postOrderHelper(node.left);
            postOrderHelper(node.right);
            System.out.print(" **"+ node.key + "** ");
        }
        System.out.println("\n------------------\n");
    }

    // search the tree for the key k
    // and return the corresponding node
    public RBTNode searchTree(int k)
    {
        System.out.println("Number of hops = " + hops);
        return searchTreeHelper(this.root, k);
    }

    private RBTNode searchTreeHelper(RBTNode node, int key)
    {
        if (node == TNULL || key == node.key)
        {
            //if(node == null)
        
            return node;
        }

        if (key < node.key)
        {
            hops++;
            return searchTreeHelper(node.left, key);
        }
        return searchTreeHelper(node.right, key);
    }

    // fix the rb tree modified by the delete operation
    private void fixDelete(RBTNode x)
    {
        RBTNode s;
        while (x != root && x.color == 0)
        {
            if (x == x.parent.left)
            {
                s = x.parent.right;
                if (s.color == 1)
                {
                    // case 3.1
                    s.color = 0;
                    x.parent.color = 1;
                    leftRotate(x.parent);
                    s = x.parent.right;
                }

                if (s.left.color == 0 && s.right.color == 0)
                {
                    // case 3.2
                    s.color = 1;
                    x = x.parent;
                } else
                {
                    if (s.right.color == 0)
                    {
                        // case 3.3
                        s.left.color = 0;
                        s.color = 1;
                        rightRotate(s);
                        s = x.parent.right;
                    }

                    // case 3.4
                    s.color = x.parent.color;
                    x.parent.color = 0;
                    s.right.color = 0;
                    leftRotate(x.parent);
                    x = root;
                }
            } else
            {
                s = x.parent.left;
                if (s.color == 1)
                {
                    // case 3.1
                    s.color = 0;
                    x.parent.color = 1;
                    rightRotate(x.parent);
                    s = x.parent.left;
                }

                if (s.right.color == 0 && s.right.color == 0)
                {
                    // case 3.2
                    s.color = 1;
                    x = x.parent;
                } else
                {
                    if (s.left.color == 0)
                    {
                        // case 3.3
                        s.right.color = 0;
                        s.color = 1;
                        leftRotate(s);
                        s = x.parent.left;
                    }

                    // case 3.4
                    s.color = x.parent.color;
                    x.parent.color = 0;
                    s.left.color = 0;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = 0;
    }

    private void rbTransplant(RBTNode u, RBTNode v)
    {
        if (u.parent == null)
        {
            root = v;
        } else if (u == u.parent.left)
        {
            u.parent.left = v;
        } else
        {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    private void deleteRBTNodeHelper(RBTNode node, int key)
    {
        // find the node containing key
        RBTNode z = TNULL;
        RBTNode x, y;
        while (node != TNULL)
        {
            if (node.key == key)
            {
                z = node;
            }

            if (node.key <= key)
            {
                node = node.right;
            } else
            {
                node = node.left;
            }
        }

        if (z == TNULL)
        {
            System.out.println("Couldn't find key in the tree");
            return;
        }

        y = z;
        int yOriginalColor = y.color;
        if (z.left == TNULL)
        {
            x = z.right;
            rbTransplant(z, z.right);
        } else if (z.right == TNULL)
        {
            x = z.left;
            rbTransplant(z, z.left);
        } else
        {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z)
            {
                x.parent = y;
            } else
            {
                rbTransplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }

            rbTransplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (yOriginalColor == 0)
        {
            fixDelete(x);
        }
    }

    // fix the red-black tree
    private void fixInsert(RBTNode k)
    {
        RBTNode u;
        while (k.parent.color == 1)
        {
            if (k.parent == k.parent.parent.right)
            {
                u = k.parent.parent.left; // uncle
                if (u.color == 1)
                {
                    // case 3.1
                    u.color = 0;
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    k = k.parent.parent;
                } else
                {
                    if (k == k.parent.left)
                    {
                        // case 3.2.2
                        k = k.parent;
                        rightRotate(k);
                    }
                    // case 3.2.1
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    leftRotate(k.parent.parent);
                }
            } else
            {
                u = k.parent.parent.right; // uncle

                if (u.color == 1)
                {
                    // mirror case 3.1
                    u.color = 0;
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    k = k.parent.parent;
                } else
                {
                    if (k == k.parent.right)
                    {
                        // mirror case 3.2.2
                        k = k.parent;
                        leftRotate(k);
                    }
                    // mirror case 3.2.1
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    rightRotate(k.parent.parent);
                }
            }
            if (k == root)
            {
                break;
            }
        }
        root.color = 0;
    }

    private void printHelper(RBTNode root, String indent, boolean last)
    {
        // print the tree structure on the screen
        if (root != TNULL)
        {
            System.out.print(indent);
            if (last)
            {
                System.out.print("R----");
                indent += "     ";
            } else
            {
                System.out.print("L----");
                indent += "|    ";
            }

            String sColor = root.color == 1 ? "RED" : "BLACK";
            System.out.println(root.key + "-" + root.sdata
                    + "(" + sColor + ")");
            printHelper(root.left, indent, false);
            printHelper(root.right, indent, true);
        }
    }

    public RedBlackTree()
    {
        TNULL = new RBTNode();
        TNULL.color = 0;
        TNULL.left = null;
        TNULL.right = null;
        root = TNULL;
    }

    // find the node with the minimum key
    public RBTNode minimum(RBTNode node)
    {
        while (node.left != TNULL)
        {
            node = node.left;
            hops++;
        }
        System.out.println("\nNumber of hops = " + hops);
        return node;
    }

    // find the node with the maximum key
    public RBTNode maximum(RBTNode node)
    {
        while (node.right != TNULL)
        {
            node = node.right;
            hops++;
        }
        System.out.println("\nNumber of hops = " + hops);
        return node;
    }

    // find the successor of a given node
    public RBTNode successor(RBTNode x)
    {
        // if the right subtree is not null,
        // the successor is the leftmost node in the
        // right subtree
        if (x.right != TNULL)
        {
            return minimum(x.right);
        }

        // else it is the lowest ancestor of x whose
        // left child is also an ancestor of x.
        RBTNode y = x.parent;
        while (y != TNULL && x == y.right)
        {
            x = y;
            y = y.parent;
        }
        return y;
    }

    // find the predecessor of a given node
    public RBTNode predecessor(RBTNode x)
    {
        // if the left subtree is not null,
        // the predecessor is the rightmost node in the 
        // left subtree
        if (x.left != TNULL)
        {
            return maximum(x.left);
        }

        RBTNode y = x.parent;
        while (y != TNULL && x == y.left)
        {
            x = y;
            y = y.parent;
        }

        return y;
    }

    // rotate left at node x
    public void leftRotate(RBTNode x)
    {
        RBTNode y = x.right;
        x.right = y.left;
        if (y.left != TNULL)
        {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null)
        {
            this.root = y;
        } else if (x == x.parent.left)
        {
            x.parent.left = y;
        } else
        {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    // rotate right at node x
    public void rightRotate(RBTNode x)
    {
        RBTNode y = x.left;
        x.left = y.right;
        if (y.right != TNULL)
        {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null)
        {
            this.root = y;
        } else if (x == x.parent.right)
        {
            x.parent.right = y;
        } else
        {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    // insert the key to the tree in its appropriate position
    // and fix the tree
    public void insert(int key, String sdata)
    {
        // Ordinary Binary Search Insertion
        RBTNode node = new RBTNode();
        node.parent = null;
        node.key = key;

        node.sdata = sdata;

        node.left = TNULL;
        node.right = TNULL;
        node.color = 1; // new node must be red

        RBTNode y = null;
        RBTNode x = this.root;

        while (x != TNULL)
        {
            y = x;
            if (node.key < x.key)
            {
                x = x.left;
            } else
            {
                x = x.right;
            }
        }

        // y is parent of x
        node.parent = y;
        if (y == null)
        {
            root = node;
        } else if (node.key < y.key)
        {
            y.left = node;
        } else
        {
            y.right = node;
        }

        // if new node is a root node, simply return
        if (node.parent == null)
        {
            node.color = 0;
            return;
        }

        // if the grandparent is null, simply return
        if (node.parent.parent == null)
        {
            return;
        }

        // Fix the tree
        fixInsert(node);
    }

    public RBTNode getRoot()
    {
        return this.root;
    }

    // delete the node from the tree
    public void deleteRBTNode(int key)
    {
        deleteRBTNodeHelper(this.root, key);
    }

    // print the tree structure on the screen
    public void prettyPrint()
    {
        printHelper(this.root, "", true);
    }

    static RedBlackTree bst = new RedBlackTree();

    void insertRandom()
    {
        int amt = 25;
        int amts = 5;
        int amtRBTNodes, amtLetters;
        int[] keys;

        int selectKey = -1;
        int selectIndex = -1;
        boolean run = true;

        String numberOrder = "";
        int numberOrderCount = 0;

        Random RNG = new Random();
        amtRBTNodes = RNG.nextInt(amt);
        amtLetters = 3;

        System.out.println(amtRBTNodes);
        keys = new int[amtRBTNodes];

        //load keys
        for (int i = 0; i < keys.length; i++)
        {
            keys[i] = i;
        }

        for (int i = 0; i < amtRBTNodes; i++)
        {
            //create random letter patterns
            String ld = "";
            for (int j = 0; j < amtLetters; j++)
            {
                ld = ld + (char) (RNG.nextInt(25) + 65);
            }

            //pick random key
            int cntr = 0;
            selectIndex = RNG.nextInt(amtRBTNodes);

            selectKey = keys[selectIndex];
            run = true;
            while (run)
            {

                if (selectKey > -1)
                {
                    run = false;
                } else
                {
                    selectIndex = selectIndex + 1;
                    if (selectIndex >= amtRBTNodes)
                    {
                        selectIndex = 0;
                    }

                }
                selectKey = keys[selectIndex];

            }
            //System.out.print(selectKey + " ");

            numberOrder = numberOrder + selectKey + " ";
            numberOrderCount++;
            if (numberOrderCount > 20)
            {
                numberOrder = numberOrder + "\n";
                numberOrderCount = 0;

            }

            bst.insert(selectKey, ld);
            keys[selectKey] = -1;
        }
        System.out.println("\n" + numberOrder + "\n");
        System.out.println("");

}
}
