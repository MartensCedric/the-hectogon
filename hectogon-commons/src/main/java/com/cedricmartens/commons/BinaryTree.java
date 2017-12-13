package com.cedricmartens.commons;

/**
 * Created by Cedric Martens on 2017-12-13.
 */
public class BinaryTree<T extends Comparable>
{
    private Node<T> root = null;

    public void add(T value)
    {
        if(root == null)
        {
            root = new Node<>(value);
        }else{

        }
    }

    private class Node<T extends Comparable>
    {
        private T value;
        public Node(T value)
        {
            this.value = value;
        }
        private Node<T> leftChild;
        private Node<T> rightChild;
    }
}
