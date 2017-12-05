/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myavl;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author guest-caerzc
 */
public class AVL implements IAVL {

    private int size;
    private Node root;
    private Comparator comparator;

    public AVL(Object k, Object o, Comparator c) {
        size = 1;
        root = new Node(k, o);
        comparator = c;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int height() {
        return this.height(this.root);
    }

    private int height(Node n) {
        if (this.isExternal(n)) {
            return 0;
        }
        int h = 0;
        if (this.hasLeftChild(n)) {
            h = this.height(this.leftChild(n));
        }
        if (this.hasRightChild(n)) {
            h = Math.max(h, this.height(this.rightChild(n)));
        }
        return 1 + h;
    }

    @Override
    public Iterator elements() {
        Vector v = new Vector();
        for (Iterator<Node> it = this.nodes(); it.hasNext();) {
            v.add(it.next().getElement());
        }
        return v.iterator();
    }

    @Override
    public Iterator nodes() {
        return this.nodes(this.root).iterator();
    }

    private Vector nodes(Node n) {
        Vector v = new Vector();
        v.add(n);
        if (this.hasLeftChild(n)) {
            v.addAll(this.nodes(this.leftChild(n)));
        }
        if (this.hasRightChild(n)) {
            v.addAll(this.nodes(this.rightChild(n)));
        }
        return v;
    }

    protected Object key(Node n) {
        return n.getKey();
    }

    @Override
    public Node root() {
        return this.root;
    }

    @Override
    public Node parent(Node n) {
        return n.getParent();
    }

    @Override
    public boolean isInternal(Node n) {
        return (this.hasLeftChild(n) || this.hasRightChild(n));
    }

    @Override
    public boolean isExternal(Node n) {
        return (!this.hasLeftChild(n) && !this.hasRightChild(n));
    }

    @Override
    public boolean isRoot(Node n) {
        return (n == this.root);
    }

    @Override
    public int depth(Node n) {
        if (n == this.root) {
            return 0;
        }
        return 1 + this.depth(n.getParent());
    }

    @Override
    public Node leftChild(Node n) {
        return n.getLeftChild();
    }

    @Override
    public Node rightChild(Node n) {
        return n.getRightChild();
    }

    @Override
    public Node sibling(Node n) {
        if (this.rightChild(n.getParent()) == n) {
            return this.leftChild(n.getParent());
        }
        return this.rightChild(n.getParent());
    }

    @Override
    public boolean hasLeftChild(Node n) {
        return (this.leftChild(n) != null);
    }

    @Override
    public boolean hasRightChild(Node n) {
        return (this.rightChild(n) != null);
    }

    @Override
    public boolean hasSibling(Node n) {
        return (this.sibling(n) != null);
    }

    @Override
    public Node find(Object k) {
        return find(k, this.root());
    }

    private Node find(Object k, Node n) {
        if (this.isExternal(n)) {
            return n;
        }
        if (this.comparator.compare(this.key(n), k) > 0 && this.hasLeftChild(n)) {
            return find(k, this.leftChild(n));
        } else if (this.comparator.compare(this.key(n), k) < 0 && this.hasRightChild(n)) {
            return find(k, this.rightChild(n));
        }
        return n;
    }

    @Override
    public void insert(Object k, Object o) {
        Node p = this.find(k);

        if (this.comparator.compare(this.key(p), k) != 0) {
            Node n = new Node(k, o, p);
            if (this.comparator.compare(this.key(p), k) > 0) {
                p.setLeftChild(n);
            } else {
                p.setRightChild(n);
            }
            updateFactor(p, n.getKey(), true);
            this.size++;
        }

    }

    @Override
    public Object remove(Object k) throws InvalidKeyException {
        Node n = this.find(k);

        if (this.comparator.compare(this.key(n), k) != 0) {
            throw new InvalidKeyException();
        }

        return this.remove(n);
    }

    private Object remove(Node n) {
        Object o = n.getElement();

        if (this.isInternal(n)) {
            Node m;

            if (this.hasRightChild(n)) {
                m = (Node) traverse(this.rightChild(n)).next();

                n.setKey(m.getKey());
                n.setElement(m.getElement());

                this.remove(m);
            } else {
                m = this.leftChild(n);
                m.setParent(this.parent(n));
                if (this.leftChild(n.getParent()) == n) {
                    n.getParent().setLeftChild(m);
                } else {
                    n.getParent().setRightChild(m);
                }
                updateFactor(m, n.getKey(), false);
                n.clear();
                this.size--;
            }
        } else {
            if (this.leftChild(n.getParent()) == n) {
                n.getParent().setLeftChild(null);
            } else {
                n.getParent().setRightChild(null);
            }
            updateFactor(n.getParent(), n.getKey(), false);
            n.clear();
            this.size--;
        }

        return o;
    }

    private Node balance(Node n) {
        Node r = n;
        if (Math.abs(n.getFactor()) > 1) {
            r = rotate(n);
        }
        return r;
    }

    private void updateFactor(Node n, Object key, boolean inserting) {
        if (inserting) {
            if (this.comparator.compare(n.getKey(), key) < 0) {
                n.setFactor(n.getFactor() - 1);
            } else {
                n.setFactor(n.getFactor() + 1);
            }

            n = balance(n);

            if (n.getFactor() != 0 && !this.isRoot(n)) {
                updateFactor(n.getParent(), key, inserting);
            }
        } else {
            if (this.comparator.compare(n.getKey(), key) < 0) {
                n.setFactor(n.getFactor() + 1);
            } else {
                n.setFactor(n.getFactor() - 1);
            }
            
            n = balance(n);

            if (n.getFactor() == 0 && !this.isRoot(n)) {
                updateFactor(n.getParent(), key, inserting);
            }
        }
    }

    public Iterator traverse() {
        return this.traverse(this.root);
    }

    public Iterator traverse(Node n) {
        Vector v = new Vector();
        this.traverse(n, v);
        return v.iterator();
    }

    private void traverse(Node n, Vector v) {
        if (this.hasLeftChild(n)) {
            traverse(this.leftChild(n), v);
        }
        v.add(n);
        if (this.hasRightChild(n)) {
            traverse(this.rightChild(n), v);
        }
    }

    private void rotateToLeft(Node n) {
        Node rightTree = n.getRightChild();

        n.setRightChild(rightTree.getLeftChild());
        if (rightTree.getLeftChild() != null) {
            rightTree.getLeftChild().setParent(n);
        }

        rightTree.setLeftChild(n);
        rightTree.setParent(n.getParent());
        if (n.getParent() != null && n.getParent().getLeftChild() == n) {
            n.getParent().setLeftChild(rightTree);
        } else if (n.getParent() != null) {
            n.getParent().setRightChild(rightTree);
        }
        n.setParent(rightTree);

        if (rightTree.getParent() == null) {
            this.root = rightTree;
        }
    }

    private void rotateToRight(Node n) {
        Node leftTree = n.getLeftChild();

        n.setLeftChild(leftTree.getRightChild());
        if (leftTree.getRightChild() != null) {
            leftTree.getRightChild().setParent(n);
        }

        leftTree.setRightChild(n);
        leftTree.setParent(n.getParent());
        if (n.getParent() != null && n.getParent().getLeftChild() == n) {
            n.getParent().setLeftChild(leftTree);
        } else if (n.getParent() != null) {
            n.getParent().setRightChild(leftTree);
        }
        n.setParent(leftTree);

        if (leftTree.getParent() == null) {
            this.root = leftTree;
        }
    }

    private void rotateToLeftPlus(Node n) {
        rotateToRight(n.getRightChild());
        rotateToLeft(n);
    }

    private void rotateToRightPlus(Node n) {
        rotateToLeft(n.getLeftChild());
        rotateToRight(n);
    }

    private Node rotate(Node n) {
        Node r = n;
        if (n.getFactor() == 2) { // direita
            if (n.getLeftChild().getFactor() >= 0) { // simples
                r = n.getLeftChild();
                n.setFactor(0);
                n.getLeftChild().setFactor(0);
                this.rotateToRight(n);
            } else { // dupla
                r = n.getLeftChild().getRightChild();
                if (n.getLeftChild().getRightChild().getFactor() > 0) {
                    n.setFactor(-1);
                    n.getLeftChild().setFactor(0);
                } else {
                    n.setFactor(0);
                    n.getLeftChild().setFactor(1);
                }
                n.getLeftChild().getRightChild().setFactor(0);
                this.rotateToRightPlus(n);
            }
        } else if (n.getFactor() == -2) { // esquerda
            if (n.getRightChild().getFactor() <= 0) { // simples
                r = n.getRightChild();
                n.setFactor(0);
                n.getRightChild().setFactor(0);
                this.rotateToLeft(n);
            } else { // dupla
                r = n.getRightChild().getLeftChild();
                if (n.getRightChild().getLeftChild().getFactor() > 0) {
                    n.setFactor(0);
                    n.getRightChild().setFactor(-1);
                } else {
                    n.setFactor(1);
                    n.getRightChild().setFactor(0);
                }
                n.getRightChild().getLeftChild().setFactor(0);
                this.rotateToLeftPlus(n);
            }
        }
        return r;
    }
}
