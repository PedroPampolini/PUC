// using System;

// public class Program2
// {
//     public static void Main(string[] args)
//     {
//         // 0 0 0 => 0
//         // 0 0 1 => 1
//         // 0 1 0 => 1
//         // 0 1 1 => 0
//         // 1 0 0 => 1
//         // 1 0 1 => 0
//         // 1 1 0 => 0
//         // 1 1 1 => 1
//         NeuralNetwork nn = new NeuralNetwork(new int[] {2,10, 1}, learningRate: 0.001, epochs: 100000, tolerate: 0.001);
//         nn.SetFunctions(NeuralNetwork.Functions.ReLu, NeuralNetwork.Functions.DReLu);
//         double[][] inputs = new double[][] {
//             new double[] {3,10},
//             new double[] {6,7},
//             new double[] {3,5},
//             new double[] {1,6},
//             new double[] {8,8},
//             new double[] {4,7},
//             new double[] {4,3},
//             new double[] {3,3},
//             new double[] {10,2},
//             new double[] {9,2},
//             new double[] {8,10},
//             new double[] {6,2},
//             new double[] {6,7},
//             new double[] {5,3},
//             new double[] {9,0},
//             new double[] {2,10},
//             new double[] {5,2},
//             new double[] {3,7},
//             new double[] {10,4},
//             new double[] {3,4},
//         };
//         double[][] outputs = new double[][] {
//             new double[] { 0 },
//             new double[] { 1 },
//             new double[] { 1 },
//             new double[] { 0 },
//             new double[] { 1 },
//             new double[] { 0 },
//             new double[] { 0 },
//             new double[] { 1 },
//         };

//         /*---------------------------------*/
//             outputs = new double[inputs.Length][];
//             for (int i = 0; i < inputs.Length; i++){
//                 outputs[i] = new double[1];
//                 outputs[i][0] = inputs[i][0] + inputs[i][1];
//             }
//         /*---------------------------------*/

//         long initTime = DateTime.Now.Ticks;
//         nn.train(inputs, outputs, log: false, reiniciate:0.05);
//         long finalTime = DateTime.Now.Ticks;
//         System.Console.WriteLine("/*-----------------------------Teste CONJUNTO 1 || Treino conjunto 1-----------------------------*/");
//         for (int i = 0; i < inputs.Length; i++){
//             System.Console.Write("Input: ");
//             for (int j = 0; j < inputs[i].Length; j++){
//                 System.Console.Write(inputs[i][j] + " ");
//             }
//             System.Console.Write("Output esperado: " + outputs[i][0] + " || Output obtido:");
//             double[] outTmp = nn.FeedFoward(inputs[i]);
//             for (int j = 0; j < outTmp.Length; j++){
//                 System.Console.Write(outTmp[j] + " ");
//             }
//             System.Console.WriteLine();
//         }

//         System.Console.WriteLine("Tempo de execução: " + (finalTime - initTime) / 10000 + "ms");
        
//         double[] userInput = new double[2];
//         while (true){
//             System.Console.WriteLine("Digite dois numeros para somar:");
//             userInput[0] = double.Parse(Console.ReadLine());
//             userInput[1] = double.Parse(Console.ReadLine());
//             double[] outTmp = nn.FeedFoward(userInput);
//             System.Console.WriteLine(userInput[0] + " + " + userInput[1] + " = " + (userInput[0] + userInput[1]) + " || " + outTmp[0]);
//             if(userInput[0] == -1 && userInput[1] == -1){
//                 break;
//             }
//         }
//     }
// }