public class PlotFunctionGeneral {
  public static void main(String[] args) {
    // Pega as variáveis
    int N = Integer.parseInt(args[0]);
    double a = Double.parseDouble(args[1]);
    double b = Double.parseDouble(args[2]);
    double c = Double.parseDouble(args[3]);
    double e = Double.parseDouble(args[4]);
    double d = Double.parseDouble(args[5]);

    // Define o passo
    double step = (d - e)/(double)N;

    // Inicializa os arrays da função
    double[] x = new double[N + 1], y = new double[N + 1];

    // Variáveis para os mínimos e máximos
    double min = Double.MAX_VALUE, max = -Double.MAX_VALUE;
    // Calcula os pontos
    for (int i = 0; i < N + 1; i++) {
      x[i] = e + i * step;
      y[i] = a * x[i] * x[i] + b * x[i] + c;
      if (y[i] > max) max = y[i];
      if (y[i] < min) min = y[i];
    }

    // Define a escala utilizada
    // Incrementos na escala
    double hinc = (d - e)/10;
    double vinc = (max - min)/10;
    StdDraw.setXscale(e - hinc, d + hinc);
    StdDraw.setYscale(min - vinc, max + vinc);

    StdDraw.enableDoubleBuffering();

    // Desenhar eixos
    StdDraw.setPenColor(StdDraw.GRAY);
    if (min < 0 && max > 0) StdDraw.line(e - hinc, 0, d + hinc, 0);
    if (e < 0 && d > 0) StdDraw.line(0, min - vinc, 0, max + vinc);

    // Desenhar função
    StdDraw.setPenColor(StdDraw.RED);
    for (int i = 0; i < N; i++)
      StdDraw.line(x[i], y[i], x[i + 1], y[i + 1]);

    // Botar bolinhas nos extremos do gráfico
    StdDraw.setPenColor(StdDraw.BLACK);
    double circRad = ((max - min)*(d - e))/100;
    StdDraw.filledEllipse(x[0], y[0], circRad/(max - min), circRad/(d - e));
    StdDraw.filledEllipse(x[N], y[N], circRad/(max - min), circRad/(d - e));

    StdDraw.show();
  }
}
