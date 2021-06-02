public class Busca { 
       
  // Recebe um número de matrícula (args[0]) e um arquivo de texto
  // com números de matrícula e nomes de estudantes
  public static void main(String args[]) 
  {
	  int matDesejada = Integer.parseInt(args[0]); // matrícula desejada
    
    int totalAlunos = StdIn.readInt(); // primeira linha do .txt
    int maxStuNum = StdIn.readInt(); // segunda linha do .txt

    // criar os vetores
    String[] nomes = new String[totalAlunos];
    int[] matriculas = new int[totalAlunos];

    // montar os vetores
    for (int i = 0; i < totalAlunos; i++) {
      matriculas[i] = StdIn.readInt();
      nomes[i] = StdIn.readString();
    }

    // pega o índice e imprime as coisas
    int index = BuscasArray.main(matriculas, matDesejada);

    // imprime o resto
    StdOut.println("\n###########################################");
    if (index == -1) StdOut.printf("Matrícula %d não foi encontrada!\n", matDesejada); // caso dê erro
    else StdOut.printf("Matrícula %d encontrada na posição %d\nNome: %s\n", matDesejada, index, nomes[index]); // caso encontre o índice desejado
    StdOut.println("###########################################\n");

    // printa as matrículas maiores que "x"
    PrintArray.main(matriculas, maxStuNum);
  } 
} 
