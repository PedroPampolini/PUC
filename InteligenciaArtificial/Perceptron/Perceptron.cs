
class Perceptron{
    public double[] pesos;
    double taxaAprendizagem;    //Taxa de aprendizagem junto com os gettes e setters
    
    int randomSeed;

    double bias;

    double pesoBias;

    Func<double, double>? funcaoAtivacao = null;
    Func<double, double> FuncaoAtivacao{
        set{
            funcaoAtivacao = value;
        }
    }
    
    public Perceptron(int nEntradas, double taxaAprendizagem, double bias = 1, int randomSeed = -1, Func<double, double> funcao = null){
        if(randomSeed == -1){
           this.randomSeed = (int)DateTimeOffset.Now.ToUnixTimeMilliseconds();
        }
        //Caso a função de ativação não seja passada, a função padrão é a função relu
        if(funcao == null){
            funcao = Perceptron.Functions.DSigmoid;
        }
        if(taxaAprendizagem > 0.031){
            taxaAprendizagem = 0.031;
        }
        if(taxaAprendizagem < 0.00000001){
            taxaAprendizagem = 0.00000001;
        }
        this.funcaoAtivacao = funcao;
        this.pesos = new double[nEntradas];
        this.taxaAprendizagem = taxaAprendizagem;
        this.bias = 1;
        Randomize();
    }

    //Método para gerar pesos iniciais aleatórios
    public void Randomize(){
        Random gen = new Random(Seed:this.randomSeed);
        for(int i = 0; i < pesos.Length; i++){
            pesos[i] = (double)gen.NextDouble() * 2 - 1;
        }
        this.pesoBias = (double)gen.NextDouble() * 2 - 1;
    }

    //Método para calcula o somatório ponderado
    public double Soma(double[] entradas){
        double soma = this.bias * this.pesoBias; //Inicializa com o bias já ponderado
        for(int i = 0; i < entradas.Length; i++){
            soma += entradas[i] * pesos[i];
        }
        return soma;
    }

    //Método para calcular a função de ativação (Passar a função por referência no futuro)
    public double Ativacao(double soma){
        return this.funcaoAtivacao(soma);
    }

    //Método para comparar se o valor de saída é igual ao valor esperado dado uma tolerância
    public bool ComparaIgual(double saida, double esperado, double tolerancia = 0){
        return Math.Abs(saida - esperado) <= tolerancia;
    }

    //Método que treina o Perceptron com base em uma tabela de dados e uma tabela de saídas
    public double Train(double[][] tabela, double[] saidas, double tolerancia = 0.05f){
        double erroTotal = 0;    //Variável para armazenar o erro total para parar o treinamento se for zero
        for(int i = 0; i < tabela.Length; i++){
            double soma = Soma(tabela[i]);   //Calcula o somatório ponderado da linha
            soma = Ativacao(soma);        //Calcula a função de ativação da linha somada
            
            //Se errou o resultado, recalcula os pesos
            if(ComparaIgual(soma, saidas[i],tolerancia:tolerancia) == false){
                double error = calculateError(saidas[i], soma);  //Calcula o erro da linha
                erroTotal += Math.Abs(error);    //Adiciona o erro absoluto ao erro total para verificar parada
                weightAdjust(error, tabela[i]); //Ajusta os pesos
            }
        }
        return erroTotal;
    }

    private double calculateError(double esperado, double calculado){
        return esperado - calculado;
    }

    public void TrainEpoch(double[][] tabela, double[] saidas, int epochs = 100, double tolerancia = 0.05f){
        //StreamWriter sw = new StreamWriter("log.txt");
        //sw.Write("{\n");
        int qtdEpochs = 0;
        //Realiza X passadas na base para treinar, caso o erro seja zero, não ná mais treinamento
        for(int i = 0; i < epochs; i++){
           //sw.Write("'" + i + "':'" + pesos[0] + "',\n");
           //Console.Write("'" + i + "':'" + pesos[0] + "',\n");
            //Treina e pega o erro total
            double erro = Train(tabela, saidas,tolerancia:tolerancia);
            qtdEpochs = i;
            suffle(tabela, saidas);
            if(erro == 0){
                i = epochs;
            }
        }
        //sw.Write("}\n");
        //Console.Write("}\n");
        Console.WriteLine("Treinamento concluído com " + qtdEpochs + " épocas");
    }

    //Método para embaralhar uma tabela de dados e uma tabela de saídas
    private double[][] suffle(double[][] tabela, double[] saidas){
        Random gen = new Random();
        for(int i = 0; i < tabela.Length; i++){
            int index = gen.Next(tabela.Length);
            double[] temp = tabela[i];
            tabela[i] = tabela[index];
            tabela[index] = temp;
            double temp2 = saidas[i];
            saidas[i] = saidas[index];
            saidas[index] = temp2;
        }
        return tabela;
    }

    //Reajusta os pesos do Perceptron
    public void weightAdjust(double error, double[] expected){
        //Atualiza o peso do bias
        this.pesoBias += this.bias * this.taxaAprendizagem * error;
        //Para cada peso, atualiza o peso com base no erro
        for (int j = 0; j < this.pesos.Length; j++)
        {
            double pesoAntigo = this.pesos[j];   //Armazena o peso antigo para imprimir
            //Console.WriteLine(pesoAntigo + " + " + this.taxaAprendizagem + " * " + erro + " * " + tabela[i][j] + " = " + this.pesos[j]);
            this.pesos[j] += this.taxaAprendizagem * error * expected[j];   //Atualiza o peso -> peso novo = peso antigo + taxa * erro * entrada
        }
    }

    //Método para prever o resultado de uma tabela de dados
    public double Predict(double[] entradas){
        double soma = Soma(entradas);
        return Ativacao(soma);
    }

    //Método para prever o resultado de uma tabela de dados
    public double[] Predict(double[][] testTable){
        double[] result = new double[testTable.Length];   //Cria um vetor para armazenar os resultados
        for(int i = 0; i < testTable.Length; i++){
            result[i] = Predict(testTable[i]);     //Calcula a função de ativação da linha somada
        }
        return result;
    }

    public String weightToString(){
        String result = "Peso Bias: " + Double.Round(this.pesoBias,3);
        result += "\tPesos: ";
        for(int i = 0; i < this.pesos.Length; i++){
            result += Double.Round(this.pesos[i],3) + " ";
        }
        return result;
    }

    public static class Functions{
        public static double Sigmoid(double x){
            return 1 / (1 + Math.Exp(-x));
        }
        public static double TanH(double x){
            return Math.Tanh(x);
        }
        public static double ReLu(double x){
            return Math.Max(0, x);
        }
        public static double LeakyReLu(double x){
            return Math.Max(0.01 * x, x);
        }
        public static double BinaryStep(double x){
            if(x < 0)
                return 0;
            else
                return 1;
        }
        public static double SoftPlus(double x){
            return Math.Log(1 + Math.Exp(x));
        }
        //Derivatives of the Activation Functions
        public static double DSigmoid(double x){
            return x * (1 - x);
        }
        public static double DTanH(double x){
            return 1 - (x * x);
        }
        public static double DReLu(double x){
            if(x > 0)
                return 1;
            else
                return 0;
        }
        public static double DLeakyReLu(double x){
            if(x > 0)
                return 1;
            else
                return 0.01;
        }
        public static double DBinaryStep(double x){
            return 0;
        }
        public static double DSoftPlus(double x){
            return 1 / (1 + Math.Exp(-x));
        }

    }

}