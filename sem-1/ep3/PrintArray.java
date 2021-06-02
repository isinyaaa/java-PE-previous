public class PrintArray{

  // Retorna o índice do menor elemento de arr que é maior ou igual a x
  public static int maiorProximo (int[] arr, int l, int r, int x) {
    // usando o mesmo padrão da busca binária
    int mid = (int)Math.floor((r - l)/2) + l;
    
    // porém com uma pequena alteração no caso final
    if (r - l <= 1) {
      if (arr[l] == x) return l; // l só pode ser menor ou igual

      if (arr[r] >= x) return r;
    }
    
    // o resto segue idem
    if (arr[mid] < x) return maiorProximo(arr, mid, r, x);

    if (arr[mid] > x) return maiorProximo(arr, l, mid, x);

    return mid;
  }


  // Lista todas as matrículas maiores que x
  public static void listaMatriculas(int[] arr, int x) {
    // loop para imprimir as matrículas
    for (int i = maiorProximo(arr, 0, arr.length, x); i < arr.length; i++) {
      StdOut.println("Matrícula " + arr[i]);
    }
  }


    
  public static void main(int[] arr, int x) {
    //int[] arr = {2, 5, 6, 7, 20, 35, 40, 43, 50, 98, 100};

	//listaMatriculas(arr, 34);
	//listaMatriculas(arr, 35);

    listaMatriculas(arr, x);
  }

    
}
