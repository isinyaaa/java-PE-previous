/*****************************************************************************
 * Este programa implementa a estrategia aleatoria, que do precisamente 50% de 
 * chance de vitoria para Bob.
 * 
 * Execucao: java BiggestNumber a b T
 * 
 * onde a e b sao os inteiros escolhidos por Alice (no intervalo 0..99)
 * e T contem o numero de simulacoes que serao feitas.
 *
 * Exemplos de execucao:
 * 
 * $ java BiggestNumberCompare 25 80 10000
 * No choice: 5071
 * % of wins - No choice: 0.5071
 ******************************************************************************/

public class BiggestNumberCompare {
  public static void main(String[] args) {
    int a = Integer.parseInt(args[0]);
    int b = Integer.parseInt(args[1]);
    int trials = Integer.parseInt(args[2]);

    int biggest;
    int choice;

    //Guarda o valor da maior carta em "biggest"
    biggest = a < b ? 1 : 0;

    //Estratégia quando Bob não vê nenhuma carta
    int numNoChoice = 0;

    for (int i = 0; i < trials; i++) {
	    //Bob escolhe uma das duas cartas uniformemente ao acaso
	    choice = (Math.random() < .5) ? 0 : 1;

	    // Escolha correta?
	    if (choice == biggest) numNoChoice++;
    }

    //Estrategia quando Bob ve uma carta
    int numWithChoice = 0;
    int c, d;

    for (int i = 0; i < trials; i++) {
      //Bob escolhe uma das duas cartas uniformemente ao acaso (carta inicial)
      if (Math.random() < .5) {
        c = 0;
        d = 1;
      } else {
        c = 1;
        d = 0;
      }

      //Sortear um número entre 0 e 100 e compará-lo com a carta escolhida (pois a chance da outra carta ser maior é inversamente proporcional ao número da carta selecionada). Essa distribuição probabilística determina a escolha final de Bob.
      choice = 100.0 * Math.random() > (1 - c) * a + c * b ? d : c;

      // Escolha correta?
	    if (choice == biggest) numWithChoice++;
    }
    // Estatísticas
    double freqNo = 100*(double)numNoChoice/trials;
    String outNo = String.format("Sem escolha: %d - %.2f%%",numNoChoice,freqNo);
    System.out.println(outNo);

    double freqWith = 100*(double)numWithChoice/trials;
    String outWith = String.format("Com escolha: %d - %.2f%%",numWithChoice,freqWith);
    System.out.println(outWith);
  }
}



