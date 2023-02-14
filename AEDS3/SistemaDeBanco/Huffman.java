import java.util.PriorityQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Huffman {

    //Função de compressão, cria a árvore Huffman e um hash com os códigos, 
    //e depois cria a string com os dados comprimidos e escreve no arquivo, o hash também é escrito no arquivo
    public static void CompressFile(String inFile, String outFile) throws Exception {
        String fileString = ReadFileRaf(inFile);
        Map<Character, Integer> charFrequencyMap = GetCharFrequency(fileString);
        HuffmanNode root = Huffman.BuildTree(
            charFrequencyMap.size(), charFrequencyMap.keySet().toArray(new Character[0]), 
            charFrequencyMap.values().toArray(new Integer[0]));
        Map<Character, String> codeTable = new HashMap<Character, String>();
        Huffman.CreateCodeTable(root, "", codeTable);
        String encodedText = Huffman.Encode(fileString, codeTable);
        WriteBitString(encodedText, outFile);
        SaveHash(codeTable, "code" + outFile);
    }

    //Descompressão. Lê o arquivo compactado e o hash com os códigos, descrompime e escreve o arquivo final em outro arquivo
    public static void DecompressFile(String inFile, String outFile) throws Exception {
        Map<Character, String> codeTableFromFile = GetHash("code" + inFile);
        String encodedTextFromFile = BitToString(inFile);
        String decodedTextFromFile = Huffman.Decode(encodedTextFromFile, codeTableFromFile);
        WriteToFile(outFile, decodedTextFromFile);
    }

    // Função para criação da árvore de Huffman usando o arquivo a ser comprimido
    public static HuffmanNode BuildTree(int n, Character[] charArray, Integer[] charfreq) {
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<HuffmanNode>(n, new ImplementComparator());

        for (int i = 0; i < n; i++) {
            HuffmanNode node = new HuffmanNode();
            node.key = charArray[i];
            node.frequencySum = charfreq[i];
            node.left = null;
            node.right = null;
            queue.add(node);
        }
        HuffmanNode root = null;

        while (queue.size() > 1) {
            HuffmanNode x = queue.peek();
            queue.poll();
            HuffmanNode y = queue.peek();
            queue.poll();

            HuffmanNode f = new HuffmanNode(x.frequencySum + y.frequencySum, '-');
            f.left = x;
            f.right = y;
            root = f;

            queue.add(f);
        }
        return root;
    }

    //Cria uma tabela hash com os códigos da árvore de Huffman
    public static void CreateCodeTable(HuffmanNode root, String result, Map<Character, String> codeTable) {
        if (root.left == null && root.right == null && root.key != '-') {
            codeTable.put(root.key, result);
            return;
        }
        CreateCodeTable(root.left, result + "0", codeTable);
        CreateCodeTable(root.right, result + "1", codeTable);
    }

    //Comprime a string usando a tabela com os códigos huffman
    public static String Encode(String text, Map<Character, String> codeTable) {
        String result = "";
        for (int i = 0; i < text.length(); i++) {
            result += codeTable.get(text.charAt(i));
        }
        int extra =(byte)(result.length() % 8);
        while (result.length() % 8 != 0) {
            result += 0;
        }
        String tmp = Integer.toBinaryString(extra);
        while (tmp.length() < 8) {
            tmp = '0' + tmp;
        }
        return tmp + result;
    }

    //Descomprime o arquivo comprimido usando a tabela Huffman
    public static String Decode(String encodedText, Map<Character, String> codeTable) {
        byte extra = Byte.parseByte(encodedText.substring(0, 8), 2);
        System.out.println(extra);
        encodedText = encodedText.substring(8);
        Map<String, Character> newCodeTable = new HashMap<>();
        for(Map.Entry<Character, String> entry : codeTable.entrySet()){
            newCodeTable.put(entry.getValue(), entry.getKey());
        }

        String result = "";
        String code = "";
        for (int i = 0; i < encodedText.length() - extra; i++) {
            code += encodedText.charAt(i);
            if (newCodeTable.containsKey(code)) {
                result += newCodeTable.get(code);
                code = "";
            }
        }
        return result;
    }

    //Salva um Hash em arquivo
    public static void SaveHash(Map<Character, String> codeTable, String file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(codeTable);
        oos.close();
    }

    //Recupera um hash de um arquivo
    @SuppressWarnings("unchecked")
    public static Map<Character, String> GetHash(String file) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Map<Character, String> codeTable = (Map<Character, String>) ois.readObject();
        ois.close();
        return codeTable;
    }

    //Lê os bits de um arquivo e os converte para uma string de bits
    public static String BitToString(String file) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        byte[] byteArr = new byte[(int)raf.length()];
        for (int i = 0; i < raf.length(); i++) {
            byteArr[i] = raf.readByte();
        }
        raf.close();
        String result = "";
        for (int i = 0; i < byteArr.length; i++) {
            result += String.format("%8s", Integer.toBinaryString(byteArr[i] & 0xFF)).replace(' ', '0');
        }
        return result;
    }

    //Usa uma string de bits para escrever os mesmos num arquivo bit a bit
    public static void WriteBitString(String code, String file) throws IOException {
        int counter = 0;
        int[] intArray = new int[code.length()/8];
        int toByte;
        for (int i = 0; i < code.length(); i += 8) {
            toByte = Integer.parseInt(code.substring(i, i + 8), 2);
            intArray[counter++] = toByte;
        }
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        for (int i = 0; i < intArray.length; i++) {
            raf.writeByte(intArray[i]);
        }
        raf.close();
    }

    //Calcula a frequência de cada char numa dada string
    public static Map<Character, Integer> GetCharFrequency(String string) {
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            Integer val = map.get(c);
            if (val != null) {
                map.put(c, val + 1);
            }
            else {
                map.put(c, 1);
            }
        }
        return map;
    }

    //Lê um arquivo como string
    public static String ReadFile(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String fileString = "";
        String line = "";
        while ((line = br.readLine()) != null) {
            fileString += line + '\n';
        }
        br.close();
        return fileString;
    }

    //Escreve uma string num arquivo, byte a byte
    public static void WriteToFile(String file, String text) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        for (int i = 0; i < text.length(); i++) {
            raf.writeByte((byte)text.charAt(i));
        }
        raf.close();
    }

    //Lê um arquivo como string
    public static String ReadFileRaf(String fileName) throws IOException {
        File file = new File(fileName);
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        raf.seek(0);
        String fileString = "";
        while (raf.getFilePointer() < raf.length()) {
            fileString += raf.readLine() + '\n';
        }
        raf.close();
        return fileString;
    }
}

//Impleta um comparador para a classe do nó de Huffman
class ImplementComparator implements Comparator<HuffmanNode> {
    public int compare(HuffmanNode x, HuffmanNode y) {
        return x.frequencySum - y.frequencySum;
    }
}

//Classe que representa um nó de Huffman
class HuffmanNode {
    int frequencySum;
    char key;
    HuffmanNode left;
    HuffmanNode right;

    HuffmanNode() {
        frequencySum = 0;
        key = '-';
        left = right = null;
    }

    HuffmanNode(int frequencySum, char key) {
        this.frequencySum = frequencySum;
        this.key = key;
        left = right = null;
    }
}
