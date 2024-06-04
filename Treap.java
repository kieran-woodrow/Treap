public class Treap<T extends Comparable<? super T>>{
    public Node<T> root;
    private int[] priorityArray;

    public Treap(){
        root = null;
    }

    public boolean isEmpty(){
        return (root == null);
    }

    public T getRightValue(T value){
        Node<T> node = find(value);

        if(node != null && node.right != null){
            return node.right.data;
        }

        return null;
    }

    public T getLeftValue(T value){
        Node<T> node = find(value);

        if(node != null && node.left != null){
            return node.left.data;
        }

        return null;
    }

    private Node<T> parentNode(Node<T> n, T value){
        if(n == null)
            return null;

        if(n.left != null && value.equals(n.left.data)){
            //System.out.println("n.data = " + n.data);
            return n;
        }

       if(n.right != null && value.equals(n.right.data)){
          //System.out.println("n.data = " + n.data);
           return n;
       }
            

       if(value.compareTo(n.data) < 0)
            return parentNode(n.left, value);
        else if(value.compareTo(n.data) > 0)
            return parentNode(n.right, value);
        else
            return null;
    }

    public void insert(T value, int p){
        //System.out.println();
       // int priority = (int)(Math.random());//change for array implementation
        
        //insert newNode
        //Node<T> newNode = new Node<T>(value, priority);
        Node<T> newNode = new Node<T>(value, p);

        if(this.isEmpty()){
            root = newNode;
            return;
        }
        else{
            Node<T> current = root;

            while(true){
                if(value.compareTo(current.data) < 0){
                    if(current.left != null)
                        current = current.left;
                    else{
                        current.left = newNode;
                        //System.out.println("current.data = " + current.data);
                        break;
                    }
                }
                else{
                    if(current.right != null)
                        current = current.right;
                    else{
                        current.right = newNode;
                        break;
                    }
                }
            }//while not inserted

            //maintain heap property 1

            //newNode will not be at the root ???

            //System.out.println("current.data = " + current.data);

            Node<T> parent = parentNode(root, value);

            /*
            System.out.println("root = " + root);
            System.out.println("parent = " + parent);
            
            System.out.println("parent.data = " + parent.data);
            System.out.println("newNode.data = " + newNode.data);
            */
            

            while((newNode != root && parent != null) && newNode.priority > parent.priority){
                if(value.compareTo(parent.data) < 0){
                    //System.out.println("rightRotation(" + parent.data + ")");
                    rightRotation(parent);
                }
                else{
                    //System.out.println("leftRotation(" + parent.data + ")");
                    leftRotation(parent);
                }

                /*
                System.out.println("parent.data = " + parent.data);
                System.out.println("preOrder in the class");
                this.preOrder(root);
                System.out.println();
                */

                parent = parentNode(root, value);
            }
        }
    }

    public Node<T> find(T value){
        Node<T> current = root;

        while(true){
            if(value.equals(current.data)){
                return current;
            }

            if(value.compareTo(current.data) < 0){
                if(current.left != null)
                    current = current.left;
                else
                    return null;
            }
            else{
                if(current.right != null)
                    current = current.right;
                else
                    return null;
            }
        }
    }

    public void delete(T value){
        if(!this.isEmpty()){
            Node<T> node = find(value);

            System.out.println("node.data = " + node.data);

            if(node != null){
                while(node.left != null && node.right != null){
                    if(node.left.priority > node.right.priority)
                        rightRotation(node);
                    else
                        leftRotation(node);

                    
                }//while has 2 children

                //now has 1 or no children
                
                Node<T> parent = parentNode(root, value);

               

                Node<T> NODE_VALUE = (node.left != null) ? node.left : node.right;

                if(parent != null){
                    System.out.println("parent.data = " + parent.data);

                    if(node.data.compareTo(parent.data) < 0)
                        parent.left = NODE_VALUE;
                    else
                        parent.right = NODE_VALUE;

                    /*
                    parent.left = (node.data.compareTo(parent.data) < 0) ? NODE_VALUE : parent.left;
                    parent.right = (parent.left != NODE_VALUE) ? NODE_VALUE : parent.right;
                    
                    //difficult to read
                    */

                    /*
                    Node<T> PARENT_LINK = (node.data.compareTo(parent.data) < 0) ? parent.left : parent.right;
                    PARENT_LINK = NODE_VALUE;

                    //does not work this way
                    */
                }
                else
                    root = NODE_VALUE;
            }//node found
        }//not empty

        System.out.println("preOrder traversal of t");
        this.preOrder(this.root);
        System.out.println();
    }

    public void rightRotation(Node<T> p){
        Node<T> g = parentNode(root, p.data);
        Node<T> c = p.left;

        if(g != null){
            if(p.data.compareTo(g.data) > 0)
                g.right = c;
            else
                g.left = c;
        }
        else{
            root = c;
        }

        p.left = c.right;
        c.right = p;
    }

    public void leftRotation(Node<T> p){
        Node<T> g = parentNode(root, p.data);
        Node<T> c = p.right;

        if(g != null){
            if(p.data.compareTo(g.data) > 0)
                g.right = c;
            else
                g.left = c;
        }
        else
            root = c;

        p.right = c.left;
        c.left = p;
    }    

    public void preOrder(Node<T> node){
        if(node != null){
            System.out.print(node.data + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }
}