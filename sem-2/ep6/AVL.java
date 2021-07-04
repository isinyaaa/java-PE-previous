import java.time.Instant;

class NullNodeException extends Exception
{
    NullNodeException(String errorMessage)
    {
        super(errorMessage + ": Node is null!");
    }
}

public class AVL<Key extends Comparable<Key>, Value>
{
    // raiz da BST
    private Node root;

    private class Node
    {
        private final Key key;
        private Value val;
        private int height;
        private Node left, right;

        public Node(Key key, Value val)
        {
            this.key = key;
            this.val = val;
            this.height = 0;
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

    public void inOrderNicer()
    {
        inOrderNicer(this.root, 0);
        // StdOut.println();
    }

    private void inOrderNicer(Node x, int h)
    {
        if (x == null)
            return;

        StringBuffer s = new StringBuffer();
        for (int i = 0; i < h; i++)
            s.append("     |");

        inOrderNicer(x.right, h + 1);
        StdOut.println(s.toString() + x.key + ":" + x.height);
        inOrderNicer(x.left, h + 1);
    }

    public int getHeight()
    {
        return this.getHeight(this.root);
    }

    private int getHeight(Node x)
    {
        if (x == null)
            return -1;

        return x.height;
    }

    private int updateHeight(Node x)
    {
        if (x.right == null && x.left == null)
            return 0;
        if (x.right == null)
            return this.getHeight(x.left) + 1;
        if (x.left == null)
            return this.getHeight(x.right) + 1;

        return Math.max(x.right.height, x.left.height) + 1;
    }

    private int getBalance(Node x) throws NullNodeException
    {
        if (x == null)
            throw new NullNodeException("Cannot get height");

        int left_height = x.left == null ? 0 : this.getHeight(x.left) + 1;
        int right_height = x.right == null ? 0 : this.getHeight(x.right) + 1;
        return right_height - left_height;

    }

    public Node put(Key key, Value val) throws NullNodeException
    {
        this.root = put(this.root, key, val);
        return this.root;
    }

    private Node put(Node x, Key key, Value val) throws NullNodeException
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
            node.height = x.height;
            x = node;
        } // else we call the function recursively
        else if (cmp < 0)
        {
            x.left = put(x.left, key, val);
            x.height = this.updateHeight(x);
            // after we put a node, check if the tree is balanced
            if (this.getBalance(x) == -2) // this calls for a right rotation
            {
                if (this.getBalance(x.left) == 1) // but first rotate left
                    x.left = this.leftRotation(x.left);

                x = this.rightRotation(x);
            }
        } else
        {
            x.right = put(x.right, key, val);
            x.height = this.updateHeight(x);

            if (this.getBalance(x) == 2) // this calls for a left rotation
            {
                if (this.getBalance(x.right) == -1) // but first rotate right
                    x.right = this.rightRotation(x.right);

                x = this.leftRotation(x);
            }
        }

        // then return the changed node
        return x;
    }

    /*
     * Suppose:
     *    pivot
     *   x     a (new pivot)
     *        y b
     * where pivot and a are not null =========>
     *     new pivot
     *  pivot       b
     * x     y
     */
    private Node leftRotation(Node pivot)
    {
        // create the new pivot (a)
        Node new_pivot = new Node(pivot.right.key, pivot.right.val);
        // get its new right node (b)
        new_pivot.right = pivot.right.right;

        // copy the old pivot
        Node pivot_copy = new Node(pivot.key, pivot.val);
        pivot_copy.left = pivot.left;
        // update right (y)
        pivot_copy.right = pivot.right.left;
        pivot_copy.height = this.updateHeight(pivot_copy);
        // update the new pivot
        new_pivot.left = pivot_copy;
        new_pivot.height = this.updateHeight(new_pivot);

        return new_pivot;
    }

    // mirror of leftRotation
    private Node rightRotation(Node pivot)
    {
        Node new_pivot = new Node(pivot.left.key, pivot.left.val);
        new_pivot.left = pivot.left.left;

        Node pivot_copy = new Node(pivot.key, pivot.val);
        pivot_copy.right = pivot.right;
        pivot_copy.left = pivot.left.right;
        pivot_copy.height = this.getHeight(pivot_copy);

        new_pivot.right = pivot_copy;
        new_pivot.height = this.updateHeight(new_pivot);

        return new_pivot;
    }

    // Remove o nó com menor chave na tabela de símbolos e retorna o valor associado
    // a ele
    public Value removeMin() throws NullNodeException
    {
        if (this.isEmpty())
            return null;

        this.root = this.removeMin(this.root);

        return this.removedValue;
    }

    private Node removeMin(Node x) throws NullNodeException
    {
        // desired case
        if (x.left == null)
        {
            this.removedValue = x.val;
            this.removedKey = x.key; // save the key to use it in the remove method
            x = x.right;
        } else // recursive case
        {
            x.left = this.removeMin(x.left);
            x.height = this.updateHeight(x);

            if (this.getBalance(x) == 2)
            {
                if (this.getBalance(x.right) == -1)
                    x.right = this.rightRotation(x.right);

                x = this.leftRotation(x);
            }
        }

        return x;
    }

    private Value removedValue;
    private Key removedKey;

    public Value remove(Key key) throws NullNodeException
    {
        if (this.isEmpty() || !this.contains(key))
            return null;

        this.root = this.remove(this.root, key);

        return this.removedValue;
    }

    private Node remove(Node x, Key key) throws NullNodeException
    {
        int cmp = x.key.compareTo(key);

        // do while false to avoid weird returns
        do
        {
            // in case we didn't find the desired key
            if (cmp != 0)
            {
                if (cmp > 0)
                {
                    x.left = this.remove(x.left, key);
                    x.height = this.updateHeight(x);

                    if (this.getBalance(x) == 2)
                    {
                        if (this.getBalance(x.right) == -1)
                            x.right = this.rightRotation(x.right);

                        x = this.leftRotation(x);
                    }

                } else if (cmp < 0)
                {
                    x.right = this.remove(x.right, key);
                    x.height = this.updateHeight(x);

                    if (this.getBalance(x) == -2)
                    {
                        if (this.getBalance(x.left) == 1)
                            x.left = this.leftRotation(x.left);

                        x = this.rightRotation(x);
                    }
                }
                break;
            }

            // if we did find it
            do
            {
                // simplest case
                if (x.left == null || x.right == null)
                {
                    this.removedKey = x.key;
                    this.removedValue = x.val;

                    if (x.left == null)
                        x = x.right;
                    else if (x.right == null)
                        x = x.left;
                    break;
                }

                // hardcore case
                x.right = this.removeMin(x.right);
                Node new_x = new Node(this.removedKey, this.removedValue);
                new_x.left = x.left;
                new_x.right = x.right;

                this.removedKey = x.key;
                this.removedValue = x.val;

                x = new_x;
                x.height = this.updateHeight(x);

            } while (false);

        } while (false);

        return x;
    }

    /* Remove todos os nós da árvore */
    public void clean()
    {
        // garbage collection goes brr
        this.root = null;
    }

    public static void main(String[] args) throws NullNodeException
    {
        AVL<String, Integer> st = new AVL<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }

        st.inOrderNicer();

        st.put("meow", 123);

        StdOut.println();

        st.removeMin();

        st.inOrderNicer();

        st.removeMin();
        st.remove("epoch");
        st.remove("it");

        StdOut.println();

        st.inOrderNicer();
        int size_list[] = {100, 1000, 10000, 100000};

        //BenchmarkData bd_arr[] = benchmark(size_list);

        //for (int i = 0; i < size_list.length; i++)
        //{
        //    StdOut.println("Tamanho: " + size_list[i]);
        //    StdOut.println("------------------");

        //    for (int j = 0; j < 3; j++)
        //    {
        //        String order = "Order ";
        //        switch (j)
        //        {
        //            case 0:
        //                order += "crescente: ";
        //                break;
        //            case 1:
        //                order += "decrescente: ";
        //                break;
        //            case 2:
        //            default:
        //                order += "aleatória: ";
        //        }
        //        order = String.format(order + "%.2fms", bd_arr[i*3 + j].timing);
        //        StdOut.println(order);
        //        StdOut.println("Altura: " + bd_arr[i*3 + j].height);
        //    }
        //    StdOut.println();
        //}
    }

    static class BenchmarkData
    {
        int height;
        double timing;
    }

    public static BenchmarkData[] benchmark(int[] size_list) throws NullNodeException
    {
        BenchmarkData bd_arr[] = new BenchmarkData[3*size_list.length];

        double start, finish;

        int current_size, sample[];

        AVL<Integer, Integer> st = new AVL<Integer, Integer>();

        BenchmarkData bd;

        for (int i = 0; i < size_list.length; i++)
        {
            current_size = size_list[i];

            for (int j = 0; j < 3; j++)
            {
                bd = new BenchmarkData();
                switch (j)
                {
                    case 0:
                        sample = getIncreasingBenchmarkData(current_size);
                        break;
                    case 1:
                        sample = getDecreasingBenchmarkData(current_size);
                        break;
                    case 2:
                    default:
                        sample = getRandomBenchmarkData(current_size);
                }

                start = (double)Instant.now().getNano() / 1000000.0;

                for (int k : sample)
                    st.put(k, k);

                finish = (double)Instant.now().getNano() / 1000000.0;

                bd.timing = finish - start;
                bd.height = st.getHeight();
                st.clean();

                bd_arr[i*3 + j] = bd;
            }
        }

        return bd_arr;
    }

    private static int[] getRandomBenchmarkData(int size)
    {
        int sample[] = getIncreasingBenchmarkData(size);

        int random_index, temp;

        for (int i = 0; i < size; i++)
        {
            random_index = (int)(Math.random() * (double)(i + 1));

            temp = sample[i];
            sample[i] = sample[random_index];
            sample[random_index] = temp;
        }

        return sample;
    }

    private static int[] getIncreasingBenchmarkData(int size)
    {
        int sample[] = new int[size];

        for (int i = 0; i < size; i++)
            sample[i] = i + 1;

        return sample;
    }

    private static int[] getDecreasingBenchmarkData(int size)
    {
        int sample[] = new int[size];

        for (int i = size; i > 0; i--)
            sample[i - 1] = i;

        return sample;
    }
}
