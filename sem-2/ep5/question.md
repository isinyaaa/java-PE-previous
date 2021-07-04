# EP 5

Neste EP você tem três tarefas a fazer:

- Estudar sobre algoritmos básicos em árvores binárias de busca (Por exemplo, você pode ler o Capítulo 11 do arquivo [Algoritmos.pdf](./Livro-Analise.de.Algoritmos.pdf));
- Estudar o código no arquivo [BST.java](./BST.java), que contém parte da implementação de uma tabela de símbolos com árvores binárias de busca;
- Completar a implementação da API da classe BST, descrita em detalhes abaixo, escrevendo os métodos
que estão faltando no arquivo [BST.java](./BST.java). No `main()` você precisa implementar testes para todos os seus métodos.

Segue em anexo arquivo [tale.txt](./tale.txt) para ajudar no teste de alguns dos métodos.

O arquivo BST implementa uma tabela de símbolos utilizando uma árvore binária de busca (binary search tree - BST) para armazenar pares (key, value).

Uma árvore binária de busca é definida recursivamente como:

- Uma BST é `null` ou uma referência a um Node (a raiz da árvore);
- Um Node x é um tipo que contém as variáveis: Key key, Value val, Node left, Node right,
onde Node left tem uma referência para o filho esquerdo a de x na árvore (a é a raiz da subárvore esquerda de x)
e Node right tem uma referência para o filho direito b de x na árvore (b é raiz da subárvore direita de x).

Propriedade importante: Em uma árvore binária de busca vale que, para todo nó x, as chaves na
subárvore esquerda de x são todas menores que x.key, e as chaves na subárvore direita de x são todas maiores que x.key.

Preste bem atenção na descrição de cada um dos métodos,
que está escrita como comentário logo acima de cada método no
arquivo [BST.java](./BST.java). Pode ser útil estudar os códigos em [LinkedStackString.java](./LinkedStackString.java).
Abaixo temos uma descrição da API:

```java
public class BST<Key extends Comparable<Key>, Value>

    private Node root;

    private class Node
        private final Key key;
        private Value val;
        private Node left, right;

        public Node(Key key, Value val)


    public BST()
    public boolean isEmpty()

    public Value get(Key key)

    private Value get(Node x, Key key)

    public boolean contains(Key key)

    private void inOrder()
    private void inOrder(Node x)
    /* Os métodos acima já estão implementados. Você precisa se preocupar
    somente em implementar os métodos descritos abaixo */

    public Node put(Key key, Value val)
    private Node put(Node x, Key key, Value val)
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
    private void levelOrder(Node x)

    /* Imprime os elementos da tabela de símbolos por nível da árvore, começando das folhas no último nível */
    public void levelOrderFolhas()
    private void levelOrderFolhas(Node x)

    /* Retorna o valor associado à menor chave da tabela de símbolos */
    public Value min()
    private Value min(Node x)

    /* Retorna o valor associado à maior chave da tabela de símbolos */
    public Value max()
    private Value max(Node x)

    /* Retorna a quantidade de pares key-value salvos na tabela de símbolos */
    /* Você pode criar outras variáveis de instância se achar necessário */
    public int size()

    /* imprime todas as chaves menores que key */
    public void menores(Key key)
    private void menores(Node x, Key key)

    /* imprime todas as chaves maiores que key */
    public void maiores(Key key)
    private void maiores(Node x, Key key)

    /* imprime todas as chaves maiores que "menor" e menores que "maior" */
    public void entre(Key menor, Key maior)
    private void entre(Node x, Key menor, Key maior)

    /* Remove o nó com menor chave na tabela de símbolos e retorna o valor associado a ele */
    public Value removeMin()
    private Value removeMin(Node x)


    /* extra */

    /* Remove o nó com chave "key" e retorna o valor correspondente a essa chave
    ******** OBS: O seu algoritmo --NÃO-- deve ser recursivo
    */
    public Value remove(Key key)
    private Value remove(Node x, Key key)
```

---

Veja o arquivo BST.java  para dicas de como implementar alguns dos métodos. Você deve entregar somente o arquivo BST.java, devidamente preenchido. Seu código deve estar bem comentado e fácil de entender.
