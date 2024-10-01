/**
 * @author Tony Russo
 * Date last modified: November 19, 2023.
 * Email: adrusso@mtu.edu
 */

import javax.swing.text.StyleContext;
import java.util.LinkedList;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

public class BinarySearchTree<K extends Comparable<K>, V> extends AbstractBinarySearchTree<K,V> {
   /**
    * Removes the mapping for a key from this map if it is present
    * (optional operation).   More formally, if this map contains a mapping
    * from key <tt>k</tt> to value <tt>v</tt> such that
    * <code>(key==null ?  k==null : key.equals(k))</code>, that mapping
    * is removed.  (The map can contain at most one such mapping.)
    *
    * <p>Returns the value to which this map previously associated the key,
    * or <tt>null</tt> if the map contained no mapping for the key.
    *
    * <p>If this map permits null values, then a return value of
    * <tt>null</tt> does not <i>necessarily</i> indicate that the map
    * contained no mapping for the key; it's also possible that the map
    * explicitly mapped the key to <tt>null</tt>.
    *
    * <p>The map will not contain a mapping for the specified key once the
    * call returns.
    *
    * @param key key whose mapping is to be removed from the map
    * @return the previous value associated with <tt>key</tt>, or
    *      <tt>null</tt> if there was no mapping for <tt>key</tt>.
    * @throws UnsupportedOperationException if the <tt>remove</tt> operation
    *                                       is not supported by this map
    * @throws ClassCastException            if the key is of an inappropriate type for
    *                                       this map
    *                                       (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
    * @throws NullPointerException          if the specified key is null and this
    *                                       map does not permit null keys
    *                                       (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
    */
   @Override
   public V remove ( Object key ) {
      Node currentNode = getRoot();
      V value = null;
      boolean isLeft = false;
      while( currentNode != null && ((K) key).compareTo( currentNode.getKey() ) != 0 ) {
         if ( ((K) key).compareTo( currentNode.getKey() ) < 0 ) {
            currentNode = currentNode.getLeftChild();
            isLeft = true;
         } else {
            currentNode = currentNode.getRightChild();
            isLeft = false;
         }
      }
      if (currentNode != null){
         setSize(size() - 1);
         value = currentNode.getValue();
         int leftChild = 0;
         int rightChild = 0;
         if (currentNode.hasRightChild()){
            rightChild = 1;
         }
         if (currentNode.hasLeftChild()){
            leftChild = 1;
         }
         Node parent = currentNode.getParent();

         if(leftChild + rightChild == 2) {
            Node smallest = getSmallest(currentNode.getRightChild());
            currentNode.setEntry(smallest.getEntry());
            currentNode.setRightChild(smallest.getRightChild());
         }
         else if (leftChild == 1) {
            if (currentNode.isRoot())
            {
               setRoot(currentNode.getLeftChild());
               getRoot().setParent(null);
            }
            else if (isLeft)
            {
               currentNode.getParent().setLeftChild(currentNode.getRightChild());
            }
            else
            {
               currentNode.getParent().setRightChild(currentNode.getRightChild());
            }
         }
         else if (rightChild == 1)
         {
            if (currentNode.isRoot())
            {
               setRoot(currentNode.getRightChild());
               getRoot().setParent(null);
            }
            else if (isLeft)
            {
               currentNode.getParent().setLeftChild(currentNode.getRightChild());
            }
            else
            {
               currentNode.getParent().setRightChild(currentNode.getRightChild());
            }
         }
         else
         {
            if (parent != null) {
               if (isLeft) {
                  parent.setLeftChild(null);
               } else {
                  parent.setRightChild(null);
               }
            }
            else
            {
               clear();
            }
         }
      }
      return value;
   }

   private Node getSmallest(Node node){
      Node smallest = node;
      while (node.hasLeftChild()){
         smallest = node.getLeftChild();
      }

      return smallest;
   }

   /**
    * Recursive Algorithm: Postorder Binary Tree Traversal
    *    1. Postorder traverse the left subtree.
    *    2. Postorder traverse the right subtree.
    *    3. Visit the root.
    *
    * Example:
    *    Tree
    *              4
    *             / \
    *            2   5
    *           / \
    *          1   3
    *
    *    Postorder traversal: 1, 3, 2, 5, 4
    *
    * @param visitor Lambda expression to process the key and value of each node.
    *                For example: (key, value) -> System.out.println( key )
    */
   @Override
   public void traversePostorder( Visitor visitor ) {
      traversePostorder( getRoot(), visitor );
   }
   private void traversePostorder( Node node, Visitor visitor ) {
      if ( node != null) {
         traversePostorder( node.getLeftChild(), visitor );
         traversePostorder( node.getRightChild(), visitor );
         visitor.visit( node.getKey( ), node.getValue( ) );
      }
   }

   /**
    * Iterative Algorithm: Level order Binary Tree Traversal
    *    Beginning at the root, visit each node in a level, from left to right
    *    then proceeding to the next level and repeat until all nodes are visited.
    *
    * Example:
    *    Tree
    *              4
    *             / \
    *            2   5
    *           / \
    *          1   3
    *
    *    Level order traversal: 4, 2, 5, 1, 3
    *
    * Hint: Every time you visit a node, add its children to a queue.
    *
    * @param visitor Lambda expression to process the key and value of each node.
    *                For example: (key, value) -> System.out.println( key )
    */
   @Override
   public void traverseLevelorder( Visitor visitor ) {
      traverseLevelorder(getRoot(), visitor);
   }

   private void traverseLevelorder(Node node, Visitor visitor)
   {
      if (node != null)
      {
         Queue<Node> queue = new LinkedList<>();
         queue.add(node);
         while(!queue.isEmpty())
         {
            Node currentNode = queue.remove();
            if (currentNode.hasLeftChild())
            {
               queue.add(currentNode.getLeftChild());
            }
            if (currentNode.hasRightChild())
            {
               queue.add(currentNode.getRightChild());
            }
            visitor.visit(currentNode.getKey(), currentNode.getValue());
         }
      }
   }

 }
