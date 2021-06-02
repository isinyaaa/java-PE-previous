public class BuscasArray {

  //Busca binária recursiva em arr
  //Chamada inicial: buscaBinaria(arr, 0, arr.length, x)
  public static int buscaBinaria (int[] arr, int l, int r, int x) {
    // guarda o valor intermediário
    int mid = (int)Math.floor((r - l)/2) + l;

    if (r - l <= 1 && // caso não haja mais elementos para buscar
        arr[r] != x || arr[l] != x) return -1; // caso não encontre

	// Se o valor médio é maior que o valor buscado, diminuímos o intervalo para sua metade inferior
	if (arr[mid] > x) return buscaBinaria(arr, l, mid, x);
    
    // idem para o intervalo superior
	if (arr[mid] < x) return buscaBinaria(arr, mid, r, x);
    
    // pelo terceiro excluído, só podem ser iguais
    return mid;
  }

  //Busca binária recursiva em arr
  //Chamada inicial: buscaBinariaPrint(arr, 0, 0, arr.length, x)
  //Imprime a quantidade de comparações realizadas pela busca
  public static int buscaBinariaPrint (int[] arr, int chamadas, int l, int r, int x) {    
    // guarda o valor intermediário
    int mid = (int)Math.floor((r - l)/2) + l;

    if (r - l <= 1 && // caso não haja mais elementos para buscar
        (arr[r] != x || arr[l] != x)) return -1; // caso não encontre
        
    chamadas++;

	// Se o valor médio é maior que o valor buscado, diminuímos o intervalo para sua metade inferior
	if (arr[mid] > x) return buscaBinariaPrint(arr, chamadas, l, mid, x);
    
    // idem para o intervalo superior
	if (arr[mid] < x) return buscaBinariaPrint(arr, chamadas, mid, r, x);

    // pelo terceiro excluído, só podem ser iguais
    StdOut.printf("Busca binária realizou um total de %d chamadas recursivas\n", chamadas - 1);
    
    return mid;
  }


  //Busca sequencial recursiva em arr
  //Chamada inicial: buscaSequencial(arr, 0, x)
  public static int buscaSequencial (int[] arr, int n, int x) {
    if (n == arr.length) return -1; // caso não encontre

    // se o valor não for igual à x continuamos a busca
    if (arr[n] != x) return buscaSequencial(arr, n + 1, x);
    
    return n;
  }

  //Busca sequencial recursiva em arr
  //Chamada inicial: buscaSequencialPrint(arr, 0, 0, x)
  //Imprime a quantidade de comparações realizadas pela busca
  public static int buscaSequencialPrint (int[] arr, int chamadas, int n, int x) {
	
    // basta chamar o outro método e conferir o valor de n, pois o método será chamado n + 1 vezes, e n vezes recursivamente
    int index = buscaSequencial(arr, n, x);
    StdOut.printf("Busca sequencial realizou um total de %d chamadas recursivas\n", index);

    return index;
  }

  public static int main(int[] arr, int x) {
    
    //int[] arr = {2, 5, 6, 7, 20, 35, 40, 43, 50, 98, 100};
    //int x = 50;
    //int n = arr.length;
    //int result;

    //result = buscaBinaria(arr, 0, arr.length, x);	
    //StdOut.println("BB: Encontrado no índice " + result);
    //StdOut.println();
    
    //result = buscaBinariaPrint(arr, 0, 0, arr.length, x);
    //StdOut.println("BBP: Encontrado no índice " + result);
    //StdOut.println();

    //result = buscaSequencial(arr, 0, x);
    //StdOut.println("BS: Encontrado no índice " + result);
    //StdOut.println();

    //result = buscaSequencialPrint(arr, 0, 0, x);
    //StdOut.println("BSP: Encontrado no índice " + result);
    //StdOut.println();
    
    // imprime formatado
    StdOut.println("##########################################################");
    // pega o índice ...
    int index = buscaBinariaPrint(arr, 0, 0, arr.length, x);
    buscaSequencialPrint(arr, 0, 0, x);
    StdOut.println("##########################################################");

    return index;
  }

}
