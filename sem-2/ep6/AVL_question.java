
public class AVL<Key extends Comparable<Key>, Value>
{
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
        }
    }

    public AVL()
    {
    }

    public boolean isEmpty()
    {
    }

    /* Retorna o valor correspondente a uma dada chave */
    public Value get(Key key)
    {
        return get(root, key);
    }

    private Value get(Node x, Key key)
    {
    }

    /* retorna TRUE se a chave key contém um valor na tabela de símbolos */
    public boolean contains(Key key)
    {
    }

    /* Imprime os elementos da tabela de símbolo por ordem de chaves */
    public void inOrder()
    {
    }

    private void inOrder(Node x)
    {
    }

    /* Insercao */
    public Node put(Key key, Value val)
    {
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val)
    {
    }

    /* Remove elemento com menor chave */
    public Value removeMin()
    {
        return removeMin(root);
    }

    private Value removeMin(Node x)
    {
    }

    /*
     * Remove o nó com chave "key" e retorna o valor correspondente a essa chave
     ********
     * OBS: O seu algoritmo DEVE ser recursivo ********
     */
    public Value remove(Key key)
    {
        return remove(root, key);
    }

    private Value remove(Node x, Key key)
    {
    }

    /* Remove todos os nós da árvore */
    public Value clean()
    {
        return clean(root);
    }

    private Value clean(Node x)
    {
    }

    public static void main(String[] args)
    {
        /* faça as simulações */
    }
}
