package com.company.util;

public class Pair<T, K> {
    T left;
    K right;

    public Pair(T first, K second){
        left = first; right = second;
    }

    @Override
    public int hashCode(){
        return left.hashCode()*6+right.hashCode()*3;
    }

    @Override
    public boolean equals(Object o){
        if(o != null
                && o.hashCode() == this.hashCode()
                && o instanceof Pair<?, ?>
                && ((Pair) o).left.equals(left)
                && ((Pair) o).right.equals(right))
        {
            return true;
        }
        return false;
    }

    public T getLeft() {
        return left;
    }

    public void setLeft(T left) {
        this.left = left;
    }

    public K getRight() {
        return right;
    }

    public void setRight(K right) {
        this.right = right;
    }
}
