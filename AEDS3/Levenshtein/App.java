public class App {
    public static void main(String[] args) {
        Levenshtein lv = new Levenshtein("back", "book");
        lv.initMatriz();
        lv.imprimeMatriz();
        System.out.println("Distancia: " + lv.getDistancia());
        lv.imprimeMatriz();
    }
}
