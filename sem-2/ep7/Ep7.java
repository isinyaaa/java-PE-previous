import java.util.List;
import java.util.ArrayList;

class Aresta {

	private int peso;
	private Vertice origem;
	private Vertice destino;

	public Aresta(Vertice v1, Vertice v2) {

		this.peso = 1;
		this.origem = v1;
		this.destino = v2;

	}

	public void setPeso(int novoPeso) {

		this.peso = novoPeso;
	}

	public int getPeso() {

		return peso;
	}

	public void setDestino(Vertice destino) {
		this.destino = destino;
	}

	public Vertice getDestino() {
		return destino;
	}

	public void setOrigem(Vertice origem) {
		this.origem = origem;
	}

	public Vertice getOrigem() {
		return origem;
	}
}

class Vertice implements Comparable<Vertice>
{
    private String descricao;
    private int distancia;
    private boolean visitado = false;
    private Vertice pai;
    private List<Aresta> arestas = new ArrayList<Aresta>();
    private List<Vertice> vizinhos = new ArrayList<Vertice>();

    public void setDescricao(String nome)
    {

        this.descricao = nome;
    }

    public String getDescricao()
    {

        return descricao;

    }

    public void visitar()
    {

        this.visitado = true;
    }

    public boolean verificarVisita()
    {

        return visitado;
    }

    public void setDistancia(int distancia)
    {

        this.distancia = distancia;
    }

    public int getDistancia()
    {

        return this.distancia;
    }

    public void setPai(Vertice pai)
    {

        this.pai = pai;
    }

    public Vertice getPai()
    {

        return this.pai;
    }

    public void setVizinhos(List<Vertice> vizinhos)
    {

        this.vizinhos.addAll(vizinhos);

    }

    public List<Vertice> getVizinhos()
    {

        return this.vizinhos;
    }

    public void setArestas(List<Aresta> arestas)
    {

        this.arestas.addAll(arestas);

    }

    public List<Aresta> getArestas()
    {

        return arestas;
    }

    public int compareTo(Vertice vertice)
    {
        if (this.getDistancia() < vertice.getDistancia())
            return -1;
        else if (this.getDistancia() == vertice.getDistancia())
            return 0;

        return 1;

    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Vertice)
        {
            Vertice vRef = (Vertice) obj;
            if (this.getDescricao().equals(vRef.getDescricao()))
                return true;
        }
        return false;
    }

    @Override
    public String toString()
    {
        String s = " ";
        s += this.getDescricao();
        return s;
    }

}

import java.util.NoSuchElementException;

public class BST<Key extends Comparable<Key>, Value>
{
    private Node root; // root of BST

    private class Node
    {
        private final Key key; // sorted by key
        private Value val; // associated data
        private Node left, right; // left and right subtrees
        private int size; // number of nodes in subtree

        public Node(Key key, Value val, int size)
        {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    public BST()
    {
    }

    /**
     * Returns true if this symbol table is empty.
     *
     * @return {@code true} if this symbol table is empty; {@code false} otherwise
     */
    public boolean isEmpty()
    {
        return size() == 0;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size()
    {
        return size(root);
    }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x)
    {
        if (x == null)
            return 0;
        else
            return x.size;
    }

    /**
     * Does this symbol table contain the given key?
     *
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key} and
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(Key key)
    {
        if (key == null)
            throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol
     *         table and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
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

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the
     * old value with the new value if the symbol table already contains the
     * specified key. Deletes the specified key (and its associated value) from this
     * symbol table if the specified value is {@code null}.
     *
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val)
    {
        if (key == null)
            throw new IllegalArgumentException("first argument to put() is null");
        if (val == null)
        {
            delete(key);
            return;
        }
        root = put(root, key, val);

    }

    private Node put(Node x, Key key, Value val)
    {
        if (x == null)
            return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = put(x.left, key, val);
        else if (cmp > 0)
            x.right = put(x.right, key, val);
        else
            x.val = val;
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    /**
     * Removes the smallest key and associated value from the symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin()
    {
        if (isEmpty())
            throw new NoSuchElementException("Symbol table underflow");
        root = deleteMin(root);

    }

    private Node deleteMin(Node x)
    {
        if (x.left == null)
            return x.right;
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * Removes the largest key and associated value from the symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMax()
    {
        if (isEmpty())
            throw new NoSuchElementException("Symbol table underflow");
        root = deleteMax(root);

    }

    private Node deleteMax(Node x)
    {
        if (x.right == null)
            return x.left;
        x.right = deleteMax(x.right);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * Removes the specified key and its associated value from this symbol table (if
     * the key is in this symbol table).
     *
     * @param key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(Key key)
    {
        if (key == null)
            throw new IllegalArgumentException("argument to delete() is null");
        root = delete(root, key);

    }

    private Node delete(Node x, Key key)
    {
        if (x == null)
            return null;

        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = delete(x.left, key);
        else if (cmp > 0)
            x.right = delete(x.right, key);
        else
        {
            if (x.right == null)
                return x.left;
            if (x.left == null)
                return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * Returns the smallest key in the symbol table.
     *
     * @return the smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key min()
    {
        if (isEmpty())
            throw new NoSuchElementException("called min() with empty symbol table");
        return min(root).key;
    }

    private Node min(Node x)
    {
        if (x.left == null)
            return x;
        else
            return min(x.left);
    }

    /**
     * Returns the largest key in the symbol table.
     *
     * @return the largest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key max()
    {
        if (isEmpty())
            throw new NoSuchElementException("called max() with empty symbol table");
        return max(root).key;
    }

    private Node max(Node x)
    {
        if (x.right == null)
            return x;
        else
            return max(x.right);
    }

    /**
     * Returns the largest key in the symbol table less than or equal to
     * {@code key}.
     *
     * @param key the key
     * @return the largest key in the symbol table less than or equal to {@code key}
     * @throws NoSuchElementException   if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Key floor(Key key)
    {
        if (key == null)
            throw new IllegalArgumentException("argument to floor() is null");
        if (isEmpty())
            throw new NoSuchElementException("called floor() with empty symbol table");
        Node x = floor(root, key);
        if (x == null)
            return null;
        else
            return x.key;
    }

    private Node floor(Node x, Key key)
    {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0)
            return x;
        if (cmp < 0)
            return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t != null)
            return t;
        else
            return x;
    }

    /**
     * Returns the smallest key in the symbol table greater than or equal to
     * {@code key}.
     *
     * @param key the key
     * @return the smallest key in the symbol table greater than or equal to
     *         {@code key}
     * @throws NoSuchElementException   if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Key ceiling(Key key)
    {
        if (key == null)
            throw new IllegalArgumentException("argument to ceiling() is null");
        if (isEmpty())
            throw new NoSuchElementException("called ceiling() with empty symbol table");
        Node x = this.ceiling(root, key);
        if (x == null)
            return null;
        else
            return x.key;
    }

    private Node ceiling(Node x, Key key)
    {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0)
            return x;
        if (cmp < 0)
        {
            Node t = this.ceiling(x.left, key);
            if (t != null)
                return t;
            else
                return x;
        }
        return ceiling(x.right, key);
    }

    /**
     * Return the kth smallest key in the symbol table.
     *
     * @param k the order statistic
     * @return the kth smallest key in the symbol table
     * @throws IllegalArgumentException unless {@code k} is between 0 and <em>N</em>
     *                                  &minus; 1
     */
    public Key select(int k)
    {
        if (k < 0 || k >= size())
            throw new IllegalArgumentException();
        Node x = select(root, k);
        return x.key;
    }

    // Return key of rank k.
    private Node select(Node x, int k)
    {
        if (x == null)
            return null;
        int t = size(x.left);
        if (t > k)
            return select(x.left, k);
        else if (t < k)
            return select(x.right, k - t - 1);
        else
            return x;
    }

    /**
     * Return the number of keys in the symbol table strictly less than {@code key}.
     *
     * @param key the key
     * @return the number of keys in the symbol table strictly less than {@code key}
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public int rank(Key key)
    {
        if (key == null)
            throw new IllegalArgumentException("argument to rank() is null");
        return rank(key, root);
    }

    // Number of keys in the subtree less than key.
    private int rank(Key key, Node x)
    {
        if (x == null)
            return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return rank(key, x.left);
        else if (cmp > 0)
            return 1 + size(x.left) + rank(key, x.right);
        else
            return size(x.left);
    }

    /**
     * Returns all keys in the symbol table as an {@code Iterable}. To iterate over
     * all of the keys in the symbol table named {@code st}, use the foreach
     * notation: {@code for (Key key : st.keys())}.
     *
     * @return all keys in the symbol table
     */
    public Iterable<Key> keys()
    {
        return rangeSearch(min(), max());
    }

    /**
     * Returns all keys in the symbol table in the given range, as an
     * {@code Iterable}.
     *
     * @return all keys in the symbol table between {@code lo} (inclusive) and
     *         {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi} is
     *                                  {@code null}
     */
    public Iterable<Key> rangeSearch(Key lo, Key hi)
    {
        if (lo == null)
            throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null)
            throw new IllegalArgumentException("second argument to keys() is null");

        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, Queue<Key> queue, Key lo, Key hi)
    {
        if (x == null)
            return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0)
            keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0)
            queue.enqueue(x.key);
        if (cmphi > 0)
            keys(x.right, queue, lo, hi);
    }

    /**
     * Returns the number of keys in the symbol table in the given range.
     *
     * @return the number of keys in the sybol table between {@code lo} (inclusive)
     *         and {@code hi} (exclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi} is
     *                                  {@code null}
     */
    public int rangeCount(Key lo, Key hi)
    {
        if (lo == null)
            throw new IllegalArgumentException("first argument to size() is null");
        if (hi == null)
            throw new IllegalArgumentException("second argument to size() is null");

        if (lo.compareTo(hi) > 0)
            return 0;
        if (contains(hi))
            return rank(hi) - rank(lo) + 1;
        else
            return rank(hi) - rank(lo);
    }

    /**
     * Returns the height of the BST (for debugging).
     *
     * @return the height of the BST (a 1-node tree has height 0)
     */
    public int height()
    {
        return height(root);
    }

    private int height(Node x)
    {
        if (x == null)
            return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    /**
     * Returns the keys in the BST in level order (for debugging).
     *
     * @return the keys in the BST in level order traversal
     */
    public Iterable<Key> levelOrderKeys()
    {
        Queue<Key> keys = new Queue<Key>();
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(root);
        while (!queue.isEmpty())
        {
            Node x = queue.dequeue();
            if (x == null)
                continue;
            keys.enqueue(x.key);
            queue.enqueue(x.left);
            queue.enqueue(x.right);
        }
        return keys;
    }

    /**
     * Returns the keys in the BST in rank order
     *
     * @return the keys in the BST in rank order
     */
    public Iterable<Key> rankOrderKeys()
    {
        if (this.root == null)
            return null;

        return this.rankOrderKeys(this.root);
    }

    private Iterable<Key> rankOrderKeys(Node x)
    {
        Queue<Key> keys = new Queue<Key>();

        if (x.left != null)
        {
            for (Key y : rankOrderKeys(x.left))
                keys.enqueue(y);
        }

        keys.enqueue(x.key);

        if (x.right != null)
        {
            for (Key y : rankOrderKeys(x.right))
                keys.enqueue(y);
        }

        return keys;
    }

    /**
     * Returns the values in the BST in level order (for debugging).
     *
     * @return the values in the BST in level order traversal
     */
    public Iterable<Value> levelOrderValues()
    {
        Queue<Value> values = new Queue<Value>();
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(root);
        while (!queue.isEmpty())
        {
            Node x = queue.dequeue();
            if (x == null)
                continue;
            values.enqueue(x.val);
            queue.enqueue(x.left);
            queue.enqueue(x.right);
        }
        return values;
    }

    /**
     * Returns the values in the BST in rank order
     *
     * @return the values in the BST in rank order
     */
    public Iterable<Value> rankOrderValues()
    {
        if (this.root == null)
            return null;

        return this.rankOrderValues(this.root);
    }

    private Iterable<Value> rankOrderValues(Node x)
    {
        Queue<Value> values = new Queue<Value>();

        if (x.left != null)
        {
            for (Value y : rankOrderValues(x.left))
                values.enqueue(y);
        }

        values.enqueue(x.val);

        if (x.right != null)
        {
            for (Value y : rankOrderValues(x.right))
                values.enqueue(y);
        }

        return values;
    }

    /**
     * Unit tests the {@code BST} data type.
     */
    public static void main(String[] args)
    {
    }
}

public class Ep7
{

    // Atributos usados na funcao encontrarMenorCaminho

    // Lista que guarda os vertices pertencentes ao menor caminho encontrado
    List<Vertice> menorCaminho = new ArrayList<Vertice>();

    // Variavel que recebe os vertices pertencentes ao menor caminho
    Vertice verticeCaminho = new Vertice();
    Vertice v2 = new Vertice();

    // Variavel que guarda o vertice que esta sendo visitado
    Vertice atual = new Vertice();

    // Variavel que marca o vizinho do vertice atualmente visitado
    Vertice vizinho = new Vertice();

    // Lista dos vertices que ainda nao foram visitados
    List<Vertice> naoVisitados = new ArrayList<Vertice>();

    public static Queue<Integer> cidadesegura = new Queue<Integer>();

    //Classe para os membros
	private static class Vertice {
    	private final String inicio;
    	private String safe;

    	public Membro(String inicio){
    		this.inicio = inicio;
    	}

    	public String getInicio() {
    		return this.inicio;
    	}

    	public String getSafe() {
    		return this.safe;
    	}

    	public void setSafe(String a) {
    		this.safe = a;
    	}
    }

    public static class Grafo
    {
        private final int V; // número de vértices do grafo
        private int E; // número de arestas do grafo
        public BST<Integer, Integer>[] adj; // adj[v] = lista de adjacência para o vértice v
        private int K;
        private int biroliro;
        public int[] membros; // vetor para guardar todos os membros
        private int pesoMax = 0;
        private int M; // = pesoMax*E; //número que é garantido ser maior do que qualquer caminho
                       // possível (mesmo se usasse todas as arestas, usaria pesos menores que o peso
                       // máximo)
        private int VMax = 0;

        public Grafo(In in)
        {
            String line1 = in.readLine();

            String[] nmk = line1.split(" ");

            V = Integer.parseInt(nmk[0]);
            E = Integer.parseInt(nmk[1]);
            K = Integer.parseInt(nmk[2]);

            membros = new int[K];

            int i = 0;
            while (i < E)
            {
                String line = in.readLine();
                String[] abt = line.split(" ");
                int a = Integer.parseInt(abt[0]);
                int b = Integer.parseInt(abt[1]);
                int t = Integer.parseInt(abt[2]);
                if (t > pesoMax)
                    pesoMax = t;
                if (a > VMax)
                    VMax = a;
                novaAresta(a, b, t);
                i++;
            }

            while (i < K + E)
            {
                String line = in.readLine();
                int pessoa = line;
                membros[i - E] = pessoa;
                i++;
            }

            String line = in.readLine();
            biroliro = line;
            M = pesoMax * E;
        }

        public int v()
        {
            return this.v_count;
        }

        public int e()
        {
            return this.e_count;
        }

        public BST<Integer, Integer> conectado(int vertex)
        {
            Queue<DirectedEdge> queue = new Queue<DirectedEdge>();
            for (DirectedEdge j : this.edges[vertex].rankOrderValues())
                queue.enqueue(j);

            return queue;
        }

        public BST<Integer, Integer> arestas()
        {
            Queue<DirectedEdge> collected_edges = new Queue<DirectedEdge>();
            BST<Integer, DirectedEdge> current_vertex;

            for (int i = 0; i < this.v_count; i++)
            {
                current_vertex = this.edges[i];
                for (DirectedEdge j : current_vertex.rankOrderValues())
                    collected_edges.enqueue(j);
            }

            return collected_edges;
        }

        public void novaAresta(DirectedEdge edge)
        {
            MyBST<Integer, DirectedEdge> vertex_BST = edges[edge.from()];
            int current_size = vertex_BST.size();
            vertex_BST.put(current_size, edge);
            edges[edge.from()] = vertex_BST;
            this.e_count++;
        }

    }

    // Algoritmo de Dijkstra
    public List<Vertice> encontrarMenorCaminhoDijkstra(Digraph grafo, Vertice v1)
    {

        // Adiciona a origem na lista do menor caminho
        menorCaminho.add(v1);
        // Colocando a distancias iniciais
        for (int i = 0; i < grafo.getVertices().size(); i++)
        {

            // Vertice atual tem distancia zero, e todos os outros,
            // 9999("infinita")
            if (grafo.getVertices().get(i).getDescricao().equals(v1.getDescricao()))
            {

                grafo.getVertices().get(i).setDistancia(0);

            } else
            {

                grafo.getVertices().get(i).setDistancia(9999);

            }
            // Insere o vertice na lista de vertices nao visitados
            this.naoVisitados.add(grafo.getVertices().get(i));
        }

        Collections.sort(naoVisitados);

        // O algoritmo continua ate que todos os vertices sejam visitados
        while (!this.naoVisitados.isEmpty())
        {

            // Toma-se sempre o vertice com menor distancia, que eh o primeiro
            // da
            // lista

            atual = this.naoVisitados.get(0);

            for (int i = 0; i < atual.getArestas().size(); i++)
            {

                vizinho = atual.getArestas().get(i).getDestino();

                if (!vizinho.verificarVisita())
                {

                    // Comparando a distância do vizinho com a possível
                    // distância
                    if (vizinho.getDistancia() > (atual.getDistancia() + atual.getArestas().get(i).getPeso()))
                    {

                        vizinho.setDistancia(atual.getDistancia() + atual.getArestas().get(i).getPeso());
                        vizinho.setPai(atual);

                        if (vizinho == v2)
                        {
                            menorCaminho.clear();
                            verticeCaminho = vizinho;
                            menorCaminho.add(vizinho);
                            while (verticeCaminho.getPai() != null)
                            {

                                menorCaminho.add(verticeCaminho.getPai());
                                verticeCaminho = verticeCaminho.getPai();

                            }
                            // Ordena a lista do menor caminho, para que ele
                            // seja exibido da origem ao destino.
                            Collections.sort(menorCaminho);

                        }
                    }
                }

            }
            // Marca o vertice atual como visitado e o retira da lista de nao
            // visitados
            atual.visitar();
            this.naoVisitados.remove(atual);
            /*
             * Ordena a lista, para que o vertice com menor distancia fique na primeira
             * posicao
             */

            Collections.sort(naoVisitados);

        }

        return menorCaminho;
    }

    public static boolean cidadesegura(int a[][])
    {
        int c = 0;

        int bozo = a.length - 1;

        for (int z = 0; z < a[0].length; z++)
        {

            int menbrosdistancia = a[0][z];

            for (int j = 0; j < a.length - 1; j++)
            {

                if (a[j][z] > menbrosdistancia)
                    menbrosdistancia = a[j][z];
            }

            if (menbrosdistancia < a[bozo][z])
            {
                cidadesegura.enqueue(z);
                c++;
                return true;
            }
        }
    }

    public static void main(String[] args)
    {
        In input = new In(args[0]);
        Digraph reino = new Digraph(input);

        Dijkstra biroliro = new Dijkstra(reino, biroliro.getInicio());
        Dijkstra membros = new Dijkstra(reino, membro);

        int[][] dijkstra;

        StdOut.println("Dijkstra");

        while (!membros.isEmpty())
        {
            for (int i = 0; i < dijkstra.length; i++)
            {
                if (i == dijkstra.length - 1)
                {
                    for (int j = 0; j < dijkstra[0].length; j++)
                        dijkstra = biroliro.setDistancia(j);
                }

                else
                {
                    int membro = membro.dequeue();
                    for (int j = 0; j < dijkstra[0].length; j++)
                        dijkstra = membros.setDistancia(j);
                }
            }
        }

        if (cidadesegura(dijkstra) == true)
        {
            StdOut.println("O REINO ESTÁ SALVO!");
            StdOut.println(c);
            while (!fil.isEmpty())
            {
                StdOut.println(cidadesegura.dequeue() + " ");
            }

        } else
        {
            StdOut.println("INFELIZMENTE O PRECONCEITO VENCEU :(");

        }

        StdOut.println("---------------------------------------");
    }
}
