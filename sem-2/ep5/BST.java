class NoRangeException extends Exception
{
    NoRangeException (String errorMessage)
    {
        super("No search range: " + errorMessage);
    }
}


public class BST<Key extends Comparable<Key>, Value>
{
    // raiz da BST
    private Node root;

    private class Node
    {
        private final Key key;
        private Value val;
        private Node left, right;

        public Node(Key key, Value val)
        {
            this.key = key;
            this.val = val;
        }
    }

    public boolean isEmpty()
    {
        return root == null;
    }

    // Retorna o valor correspondente a uma dada chave
    public Value get(Key key)
    {
        return get(root, key);
    }

    private Value get(Node x, Key key)
    {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return get(x.left, key);
        else if (cmp > 0)
            return get(x.right, key);
        else
            return x.val;
    }

    // verifica a existência do valor de key
    public boolean contains(Key key)
    {
        return get(key) != null;
    }

    // Imprime os elementos da tabela de símbolo por ordem de chaves
    public void inOrder()
    {
        inOrder(root);
    }

    private void inOrder(Node x)
    {
        if (x == null)
            return;
        inOrder(x.left);
        StdOut.println(x.key + " ");
        inOrder(x.right);
    }

    /*
    Abaixo temos os métodos que você deve implementar
    */

    /*
    Adiciona um par (key, value) na tabela de símbolos
    e retorna a RAIZ da árvore
    */
    public Node put(Key key, Value val)
    {
        this.root = put(this.root, key, val);
        return this.root;
    }

    private Node put(Node x, Key key, Value val)
    {
        // if the key doesn't exist, make it
        if (x == null)
        {
            Node node = new Node(key, val);
            x = node;
        }
        // otherwise we compare
        int cmp = key.compareTo(x.key);
        // if it already exists, replace it
        if (cmp == 0)
        {
            Node node = new Node(key, val);
            node.left = x.left;
            node.right = x.right;
            x = node;
        } // else we call the function recursively
        else if (cmp < 0)
            x.left = put(x.left, key, val);
        else
            x.right = put(x.right, key, val);

        // then return the changed node
        return x;
    }

    /*
    Imprime os elementos da tabela de símbolos por nível da árvore, começando pela raiz

    DICA: usar filas pode ser útil. Por exemplo, os seguintes comandos criam filas de chaves e de nós.

    Queue<Key> keys = new Queue<Key>();
    Queue<Node> queue = new Queue<Node>();

    Os comandos abaixo podem ser úteis:
    queue.enqueue(nó) - coloca "nó" na fila "queue"
    keys.enqueue(x.key) - coloca a chave do nó x na fila "keys"

    Da mesma forma você pode usar os comandos queue.dequeue e keys.dequeue()
    */
    public void levelOrder()
    {
        this.levelOrder(this.root);
    }

    private void levelOrder(Node x)
    {
        Queue<Key> keys = new Queue<Key>();
        Queue<Node> nodes = new Queue<Node>();

        // first key
        nodes.enqueue(x);

        do
        {
            // get the current key
            x = nodes.dequeue();

            // add it to the printing queue
            keys.enqueue(x.key);

            // verify the next keys
            if (x.left != null)
                nodes.enqueue(x.left);
            if (x.right != null)
                nodes.enqueue(x.right);

        } while (!nodes.isEmpty());

        // then print in order
        while(!keys.isEmpty())
            StdOut.println(keys.dequeue());
    }

    //Imprime os elementos da tabela de símbolos por nível da árvore, começando das folhas no último nível
    public void levelOrderFolhas()
    {
        this.levelOrderFolhas(this.root);
    }

    private void levelOrderFolhas(Node x)
    {
        Stack<Key> keys = new Stack<Key>();
        Queue<Node> nodes = new Queue<Node>();

        // first key
        nodes.enqueue(x);

        do
        {
            // get the current key
            x = nodes.dequeue();

            // add it to the printing stack
            keys.push(x.key);

            // verify the next keys
            if (x.left != null)
                nodes.enqueue(x.left);
            if (x.right != null)
                nodes.enqueue(x.right);

        } while (!nodes.isEmpty());

        // then print in order
        while(!keys.isEmpty())
            StdOut.println(keys.pop());
    }

    //Retorna o valor associado à menor chave da tabela de símbolos
    public Value min()
    {
        return min(root);
    }

    private Value min(Node x)
    {
        // we just need to get the leftmost value
        if (x.left == null)
            return x.val;
        return this.min(x.left);
    }

    //Retorna o valor associado à maior chave da tabela de símbolos
    public Value max() {
        return max(root);
    }

    private Value max(Node x)
    {
        // we just need to get the rightmost value
        if (x.right == null)
            return x.val;
        return this.max(x.right);
    }

    /*
    Retorna a quantidade de pares key-value salvos na tabela de símbolos
    Você pode criar outras variáveis de instância se achar necessário
    */
    public int size()
    {
        return this.size(this.root, 0);
    }

    private int size(Node x, int current_size)
    {
        // if there's nothing here
        if (x == null)
            return current_size;

        // if there's something, we count it and go to the next level
        return (current_size + 1) +
               this.size(x.left, 0) +
               this.size(x.right, 0);
    }

    //imprime todas as chaves menores que key
    public void menores(Key key)
    {
        menores(this.root, key);
    }

    private void menores(Node x, Key key)
    {
        Queue<Node> nodes = new Queue<Node>();

        while (x != null)
        {
            // move left until we're < key
            if (key.compareTo(x.key) < 0)
                x = x.left;
            // when we move right we save the other keys
            else if (key.compareTo(x.key) > 0)
            {
                Node rightless = new Node(x.key, x.val);
                rightless.left = x.left;
                nodes.enqueue(rightless);
                x = x.right;
            }
            // if they're the same, just enqueue the left node
            else
            {
                if (x.left != null)
                    nodes.enqueue(x.left);
                break;
            }
        }

        // then print in order
        while(!nodes.isEmpty())
            this.inOrder(nodes.dequeue());
    }

    //imprime todas as chaves maiores que key
    public void maiores(Key key)
    {
        this.maiores(root, key);
    }

    private void maiores(Node x, Key key)
    {
        Stack<Node> nodes = new Stack<Node>();

        while (x != null)
        {
            // move right until we're > key
            if (key.compareTo(x.key) > 0)
                x = x.right;
            // when we move left we save the other keys
            else if (key.compareTo(x.key) < 0)
            {
                Node leftless = new Node(x.key, x.val);
                leftless.right = x.right;
                nodes.push(leftless);
                x = x.left;
            }
            // if they're the same, just push the right node
            else
            {
                if (x.right != null)
                    nodes.push(x.right);
                break;
            }
        }
        // then print in order
        while(!nodes.isEmpty())
            this.inOrder(nodes.pop());
    }

    //imprime todas as chaves maiores que "menor" e menores que "maior"
    public void entre(Key menor, Key maior) throws NoRangeException
    {
        this.entre(root, menor, maior);
    }

    private void entre(Node x, Key menor, Key maior) throws NoRangeException
    {
        // first we check if there is a range to look into
        if (menor.compareTo(maior) > 0)
            throw new NoRangeException(menor + " > " + maior + "!\nDid you mean\nBST.entre(" + maior + ", " + menor + ");");
        else if (menor.compareTo(maior) == 0)
            throw new NoRangeException(menor + " == " + maior + "!");

        while (x != null)
        {
            // then we move until we get in the range
            if (menor.compareTo(x.key) > 0)
                x = x.right;
            else if (maior.compareTo(x.key) < 0)
                x = x.left;
            else
            {
                // then we print everything in the range
                this.maiores(x.left, menor);
                StdOut.println(x.key);
                this.menores(x.right, maior);
                break;
            }
        }
    }

    //Remove o nó com menor chave na tabela de símbolos e retorna o valor associado a ele
    public Value removeMin()
    {
        if (this.isEmpty())
            return null;

        return this.removeMin(this.root);
    }

    private Value removeMin(Node x)
    {
        Stack<Node> nodes = new Stack<Node>();

        // find the smallest element of the subtree
        while (x.left != null)
        {
            nodes.push(x);
            x = x.left;
        }

        // save its value
        Value val = x.val;

        // in case we're already in the root
        if (nodes.isEmpty())
            this.root = x.right;
        else
        {
            // then we get its right value (to insert to the left of the one above it)
            Node y = x.right;

            // then we update the nodes upstream (up to the first)
            while(!nodes.isEmpty())
            {
                x = nodes.pop();
                x.left = y;
                y = x;
            }

            this.root = x;
        }

        return val;
    }

    /*
    O método "remove" abaixo é opcional e vale 1 ponto extra no EP

    Remove o nó com chave "key" e retorna o valor correspondente a essa chave
    ******** OBS: O seu algoritmo --NÃO-- deve ser recursivo
    */
    public Value remove(Key key)
    {
        if (this.isEmpty() || !this.contains(key))
            return null;
        return this.remove(this.root, key);
    }

    private Value remove(Node x, Key key)
    {
        // to save the nodes we've ran over
        Stack<Node> nodes = new Stack<Node>();
        // to save the path we performed
        Stack<Integer> direction = new Stack<Integer>();
        Value val = null;

        while (x != null)
        {
            // we first find the key
            if (key.compareTo(x.key) < 0)
            {
                nodes.push(x);
                direction.push(-1);
                x = x.left;
            }
            else if (key.compareTo(x.key) > 0)
            {
                nodes.push(x);
                direction.push(1);
                x = x.right;
            }
            else
            {
                // saves the current node to update later
                Node y = null;
                // now we update the current node
                if (x.right == null)
                {
                    val = x.val;
                    y = x.left;
                }
                else if (x.left == null)
                {
                    val = x.val;
                    y = x.right;
                }
                // if we have both we must update with the smallest element to the left
                else
                {
                    // get the smallest (as we did in removeMin)
                    while (x.left != null)
                    {
                        nodes.push(x);
                        direction.push(-1);
                        x = x.left;
                    }

                    // we save the least value
                    val = x.val;

                    // in case we are in the root
                    if (nodes.isEmpty())
                        x = this.root;
                    else
                    {
                        y = x.right;
                        x = nodes.pop();
                        x.left = y;
                    }
                }

                // update nodes upstream
                while(!nodes.isEmpty())
                {
                    x = nodes.pop();
                    int d = direction.pop();
                    if (d < 0)
                        x.left = y;
                    else if (d > 0)
                        x.right = y;
                    y = x;
                }

                this.root = x;

                break;
            }
        }

        return val;
    }

    public static void main(String[] args) throws NoRangeException
    {
        BST<String, Integer> st = new BST<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }

        st.inOrder();

        /*
        Implemente aqui os testes para os seus métodos
        */

        StdOut.println();

        st.put("meow", 123);

        StdOut.println();

        st.levelOrder();

        StdOut.println();

        st.levelOrderFolhas();

        StdOut.println();

        StdOut.println(st.size());

        StdOut.println();
        StdOut.println(st.min());
        StdOut.println();
        StdOut.println(st.max());

        StdOut.println();

        st.menores("piwi");

        StdOut.println();

        st.maiores("wire");

        StdOut.println();

        st.entre("piwi", "wire");

        StdOut.println();
        StdOut.println(st.min());
        StdOut.println(st.removeMin());
        StdOut.println(st.min());

        StdOut.println();

        st.inOrder();

        StdOut.println();

        StdOut.println(st.remove("meow"));

        StdOut.println();

        st.inOrder();
    }
}
