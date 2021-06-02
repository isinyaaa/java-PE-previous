/**
 * @author : Isabella B
 * @created : 2020-12-06
**/


public class Threesum {
  
  public static void main(String[] args) {
    // ler a lista gerada pelo Generator
    int[] valores = StdIn.readAllInts();

    // inicio do cronometro
    long inicio = System.currentTimeMillis();
    StdOut.println(countTrivial(valores) + " soluções (countTrivial).");
    // final do cronometro
    long fim = System.currentTimeMillis();
    // Delta t
    long tempoTrivial = fim - inicio;
    
    // aqui ordenamos por quicksort (eficiencia n log n e n*n no pior caso)
    ordenacao(valores, 0, valores.length - 1);

    inicio = System.currentTimeMillis();
    StdOut.println(countEficiente(valores) + " soluções (countEficiente).");
    fim = System.currentTimeMillis();
    long tempoEficiente = fim - inicio;
    
    long diferenca = tempoTrivial - tempoEficiente;
    StdOut.println("Diferença de " + diferenca + "ms.");
  }

  // metodo para ordenar o array (usei quicksort)
  static void ordenacao(int[] valores, int l, int u) {
    // caso os limites se encostem terminamos o sort
    if (l >= u) return;

    // particionamento do vetor
    int p = particionar(valores, l, u);

    // ordenação de cada lado a partir do pivo (ordenado)
    ordenacao(valores, l, p - 1);
    ordenacao(valores, p + 1, u);
  }

  // o método retorna o novo indice do pivo (que vai estar na posição correta)
  static int particionar(int[] valores, int l, int u) {
    // escolhemos o último elemento da ordenação como pivo
    int pivo = valores[u];

    // começamos no mesmo índice
    int i = l;
    for (int j = l; j < u; j++) {
      // e a cada valor fora de posição, trocamos com o "primeiro"
      if (valores[j] < pivo) { troca(valores, i, j); i++; }
    }
    // e finalmente trocamos o pivo
    troca(valores, i, u);
    
    // de tal forma que a sua esquerda tenhamos valores menores do que ele
    // e a sua direita, só os valores maiores

    // finalmente, retornamos o índice do pivo
    return i;
  }

  // método de troca
  static void troca(int[] valores, int i, int j) {
    int temp = valores[i];
    valores[i] = valores[j];
    valores[j] = temp;
  }

  // contagem trivial
  static int countTrivial(int[] valores) {
    int tamanho = valores.length;

    int solucoes = 0;

    // basta loopar três índices subsequentes
    for (int i = 0; i < tamanho; i++)
      for (int j = i + 1; j < tamanho; j++)
        for (int k = j + 1; k < tamanho; k++)
          // e testar cada soma
          if (valores[i] + valores[j] + valores[k] == 0) solucoes++;

    return solucoes;
  }

  static int countEficiente(int[] valores) {
    int tamanho = valores.length;
    int solucoes = 0;

    // então vamos loopar i crescente
    for (int i = 0; i < tamanho; i++) {
      // j decrescente (até encontrar i)
      for (int j = tamanho - 1; j > i + 1; j--) {
        // salvamos sua soma
        int soma = valores[i] + valores[j];
        // e queremos um elemento com o valor oposto à essa soma
        int k = buscaBinaria(valores, i + 1, j - 1, -soma);
        // caso encontremos, basta contar a solução
        if (k != -1) solucoes++;
      }
    }

    return solucoes;
  }

  //Busca binária recursiva em arr
  static int buscaBinaria (int[] arr, int l, int r, int x) {
    if (r - l <= 1) { // caso não haja mais elementos para buscar
      if (arr[r] == x) return r;
      if (arr[l] == x) return l; 
      return -1; // caso não encontre (corrigi do ep3 kk)
    }

    // guarda o valor intermediário
    int mid = (r - l)/2 + l;
	  
	  if (arr[mid] > x) return buscaBinaria(arr, l, mid, x);
	  if (arr[mid] < x) return buscaBinaria(arr, mid, r, x);
    return mid;
  }

}
