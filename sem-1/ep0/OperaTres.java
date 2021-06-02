public class OperaTres {
    public static void main(String[] args) {
        // Converter elementos do array
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        int c = Integer.parseInt(args[2]);

        // Conta
        int result = a * (b+c);
        // String formatada
        String ans = String.format("Resultado de %d x (%d + %d): %d",
                                   a,b,c,result);

        // Imprimir resposta
        System.out.println(ans);
    }
}
