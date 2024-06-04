public class Node<T extends Comparable<? super T>>{
    protected T data;
    protected Node<T> left;
    protected Node<T> right;

    //double-threaded 
    /*
    protected boolean hasLeftThread = true;
    protected boolean hasRightThread = true;
    */

    protected int priority;

    public Node(T value, int p){
        data = value;
        left = null;
        right = null;

        priority = p;
    }
}