/*************************************************************************
 * From S & W
 *************************************************************************/

public class Generator {
    
    public static void swap(int[] vec, int a, int b) {
        int temp = vec[a];
        vec[a] = vec[b];
        vec[b] = temp;
    }
    
    public static void scramble(int[] vec) { // metodo para embaralhar o vetor
        int n = vec.length;
        int r;
        for (int i = 0; i < n-1; i++) {
            r = StdRandom.uniform(i,n);
            swap(vec, i, r); // metodo que troca vec[i] com vec[r]
        }
    }

    
    public static void main(String[] args) {
        int M = Integer.parseInt(args[0]);
        int N = Integer.parseInt(args[1]);
        if (N > 2*M) N = 2*M;
        int[] vec = new int[2*M];
        for (int i = -M; i < M; i++) { // temos um vetor ordenado
            vec[i+M] = i;
        }
        scramble(vec);                // agora temos uma permutação aleatória
        for (int i = 0; i < N; i++) {
            StdOut.println(vec[i]);  // como é permutacao, garantimos que nao tem repetição
        }
    }
}
