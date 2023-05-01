using System;

class Program{

    public static int menu(){
        string[] opcoes = new string[]{
            "1) Treinar porta AND",
            "2) Treinar porta OR",
            "3) Treinar porta XOR",
            "4) Testar",
            "-1) Sair"
        };

        int opcao = 0;
        System.Console.WriteLine("Escolha uma opção:");
        foreach(string s in opcoes){
            System.Console.WriteLine(s);
        }
        System.Console.Write("Opção: ");

        opcao = int.Parse(System.Console.ReadLine()!);
        return opcao;
    }

    public static double[][] geraTabelaVerdade(int nEntradas){
        double[][] tabela = new double[(int)Math.Pow(2,nEntradas)][];
        for(int i = 0; i < tabela.Length; i++){
            tabela[i] = new double[nEntradas];
        }
        for(int i = 0; i < tabela.Length; i++){
            for(int j = 0; j < tabela[i].Length; j++){
                tabela[i][j] = (i / (int)Math.Pow(2,j)) % 2;
            }
        }
        return tabela;
    }
    
    public static double[] geraSaidaEsperada(int nEntradas, char operation){
        double[] saida = new double[(int)Math.Pow(2,nEntradas)];
        if(operation == '&'){
            for(int i = 0; i < saida.Length - 1; i++){
                saida[i] = 0;
            }
            saida[saida.Length - 1] = 1;
        }
        else if(operation == '|'){
            saida[0] = 0;
            for(int i = 1; i < saida.Length; i++){
                saida[i] = 1;
            }
        }
        //Tabela xor
        else if(operation == '+' && nEntradas == 2){
            for(int i = 0; i < saida.Length; i++){
                saida[i] = 0;
            }
            saida[0] = 1;
            saida[saida.Length - 1] = 1;
        }
        else{
            System.Console.WriteLine("Operação inválida!");
        }
        return saida;
    }

    public static void printaTabela(double[][] tabela, double[] saida){

        for(int i = 0; i < tabela[0].Length; i++){
            System.Console.Write("IN" + i + " | ");
        }
        System.Console.WriteLine("OUT");

        for(int i = 0; i < tabela.Length; i++){
            
            for(int j = 0; j < tabela[i].Length; j++){
                System.Console.Write(" " + tabela[i][j] + "  | ");
            }
            System.Console.WriteLine(saida[i]);
        }
    }

    static void Main(string[] args){
        
        int opcao = menu();
        int nEntradas = 0;
        double[][] tabela = null;
        double[] saida = null;

        double taxaAprendizagem = 0.0001;
        double tolerancia = 0.00001;
        int epochs = 10000;
        int randomSeed = -1;
        bool treinado = false;
        string porta = "";
        Perceptron p = new Perceptron(nEntradas, taxaAprendizagem, tolerancia, randomSeed, funcao:Perceptron.Functions.BinaryStep);

        while (opcao != -1){
            switch (opcao)
            {
                case -1:
                    System.Console.WriteLine("Saindo...");
                    break;

                //Porta AND
                case 1:
                    System.Console.Write("Digite o número de entradas da AND: ");
                    nEntradas = int.Parse(System.Console.ReadLine()!);
                    tabela = geraTabelaVerdade(nEntradas);
                    saida = geraSaidaEsperada(nEntradas, '&');
                    printaTabela(tabela, saida);
                    System.Console.WriteLine("Treinando o perceptron...");
                    p = new Perceptron(nEntradas, taxaAprendizagem, tolerancia, randomSeed, funcao:Perceptron.Functions.BinaryStep);
                    p.TrainEpoch(tabela, saida, epochs);
                    treinado = true;
                    porta = "AND";
                    break;

                //Porta OR
                case 2:
                    System.Console.Write("Digite o número de entradas da OR: ");
                    nEntradas = int.Parse(System.Console.ReadLine()!);
                    tabela = geraTabelaVerdade(nEntradas);
                    saida = geraSaidaEsperada(nEntradas, '|');
                    printaTabela(tabela, saida);
                    System.Console.WriteLine("Treinando o perceptron...");
                    p = new Perceptron(nEntradas, taxaAprendizagem, tolerancia, randomSeed, funcao:Perceptron.Functions.BinaryStep);
                    p.TrainEpoch(tabela, saida, epochs);
                    treinado = true;
                    porta = "OR";
                    break;
                
                //Porta XOR
                case 3:
                    nEntradas = 2;
                    tabela = geraTabelaVerdade(nEntradas);
                    saida = geraSaidaEsperada(nEntradas, '&');
                    printaTabela(tabela, saida);
                    System.Console.WriteLine("Treinando o perceptron...");
                    p = new Perceptron(nEntradas, taxaAprendizagem, tolerancia, randomSeed, funcao:Perceptron.Functions.BinaryStep);
                    p.TrainEpoch(tabela, saida, epochs);
                    treinado = true;
                    porta = "XOR";
                    break;

                //Testar
                case 4:
                    if(!treinado){
                        System.Console.WriteLine("Perceptron não treinado!");
                        break;
                    }
                    System.Console.WriteLine("Digite as entradas:");
                    double[] entradasTeste = new double[nEntradas];
                    for(int i = 0; i < entradasTeste.Length; i++){
                        Console.Write(i + ": ");                        
                        entradasTeste[i] = double.Parse(System.Console.ReadLine()!);
                    }
                    double saidaTeste = p.Predict(entradasTeste);
                    System.Console.WriteLine("\n/*****************/");
                    System.Console.WriteLine("Saída: " + saidaTeste);
                    System.Console.WriteLine("/*****************/\n");
                    break;
                
                default:
                    System.Console.WriteLine("Opção inválida!");
                    break;
            }
            if(treinado){
                System.Console.WriteLine("Treinado com " + porta);
            }
            opcao = menu();
        }
        
    }
}