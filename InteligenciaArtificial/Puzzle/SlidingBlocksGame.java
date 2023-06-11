/*
 * Autor: Pedro Pampolini Mendicino
 * Data de Criação: 09/06/2023      Última atualização: 11/06/2023
 * Objetivo: Implementar o jogo dos 8 blocos deslizantes e dar a opção de solucioná-lo usando busca em largura, A* ou busca gulosa.
 * 
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

public class SlidingBlocksGame extends JFrame {

    private JButton[][] buttons;
    private int[][] grid;
    private int emptyRow, emptyCol;

    // Construtor da classe construindo a interface gráfica do jogo
    public SlidingBlocksGame() {
        setTitle("Sliding Blocks Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 550);

        // Criação do painel de cabeçalho e dos botões de embaralhar e solucionar
        JPanel headerPanel = new JPanel();
        JButton shuffleButton = new JButton("Embaralhar");
        shuffleButton.addActionListener(new ShuffleButtonListener());

        JButton solveBFSButton = new JButton("Largura");
        solveBFSButton.addActionListener(new SolveBFSButtonListener());

        JButton solveAStarButton = new JButton("A Estrela");
        solveAStarButton.addActionListener(new SolveAStarButtonListener());
        
        JButton solveGreedyButton = new JButton("Guloso");
        solveGreedyButton.addActionListener(new SolveGreedyButtonListener());


        headerPanel.add(shuffleButton);
        headerPanel.add(solveBFSButton);
        headerPanel.add(solveAStarButton);
        headerPanel.add(solveGreedyButton);
        add(headerPanel, BorderLayout.NORTH);

        // Criação do painel do jogo e configuração do layout de grade 3x3
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(3, 3));

        buttons = new JButton[3][3];
        grid = new int[3][3];

        initializeGrid(); // Inicializa a matriz do jogo
        initializeButtons(gamePanel); // Cria os botões do jogo

        add(gamePanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Inicializa a matriz do jogo com os números de 1 a 8 e a posição do espaço vazio
    private void initializeGrid() {
        int count = 1;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (count <= 8) {
                    grid[row][col] = count;
                    count++;
                }
            }
        }
        emptyRow = 2;
        emptyCol = 2;
        grid[emptyRow][emptyCol] = 0;
    }

    // Cria os botões do jogo e os adiciona ao painel do jogo
    private void initializeButtons(JPanel gamePanel) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton(Integer.toString(grid[row][col]));
                buttons[row][col].addActionListener(new ButtonListener(row, col));
                gamePanel.add(buttons[row][col]);
            }
        }
    }

    // Move um bloco para a posição do espaço vazio
    private void moveBlock(int row, int col) {
        if (isValidMove(row, col)) {
            buttons[emptyRow][emptyCol].setText(buttons[row][col].getText());
            buttons[row][col].setText("");
            grid[emptyRow][emptyCol] = grid[row][col];
            grid[row][col] = 0;
            emptyRow = row;
            emptyCol = col;
            repaint(); // Atualiza a interface gráfica após cada movimento
        }
    }

    private void makeMove(int row, int col) {
        buttons[emptyRow][emptyCol].setText(buttons[row][col].getText());
        buttons[row][col].setText("");
        grid[emptyRow][emptyCol] = grid[row][col];
        grid[row][col] = 0;
        emptyRow = row;
        emptyCol = col;
        repaint();
        pause(500);
    }

    // Verifica se o movimento selecionado é válido
    private boolean isValidMove(int row, int col) {
        return (row == emptyRow && Math.abs(col - emptyCol) == 1) ||
                (col == emptyCol && Math.abs(row - emptyRow) == 1);
    }

    // Embaralha o jogo aleatoriamente
    private void shuffleGame() {
        int shuffleSteps = 100;
        Random random = new Random(42);
        while (shuffleSteps > 0) {
            //int randomDirection = (int) (Math.random() * 4); // 0: cima, 1: baixo, 2: esquerda, 3: direita
            
            int randomDirection = random.nextInt(4); // 0: cima, 1: baixo, 2: esquerda, 3: direita
            int newRow = emptyRow;
            int newCol = emptyCol;

            if (randomDirection == 0 && emptyRow > 0) {
                newRow = emptyRow - 1;
            } else if (randomDirection == 1 && emptyRow < 2) {
                newRow = emptyRow + 1;
            } else if (randomDirection == 2 && emptyCol > 0) {
                newCol = emptyCol - 1;
            } else if (randomDirection == 3 && emptyCol < 2) {
                newCol = emptyCol + 1;
            }

            moveBlock(newRow, newCol);
            shuffleSteps--;
        }
    }

    // Busca uma solução para o jogo usando o algoritmo de busca em profundidade
    private void solveGameDFS() {
        int[][] target = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Stack<State> stack = new Stack<>();
        Set<State> visited = new HashSet<>();

        State initialState = new State(grid, emptyRow, emptyCol, "");
        stack.push(initialState);
        visited.add(initialState);

        while (!stack.isEmpty()) {
            State currentState = stack.pop();
            int[][] currentGrid = currentState.getGrid();

            if (Arrays.deepEquals(currentGrid, target)) {
                showSolution(currentState.getMoves());
                return;
            }

            int row = currentState.getEmptyRow();
            int col = currentState.getEmptyCol();

            // Explora todos os movimentos possíveis a partir do estado atual
            for (int[] direction : new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}) {
                int newRow = row + direction[0];
                int newCol = col + direction[1];

                if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
                    int[][] newGrid = new int[3][3];
                    for (int i = 0; i < 3; i++) {
                        System.arraycopy(currentGrid[i], 0, newGrid[i], 0, 3);
                    }

                    newGrid[row][col] = newGrid[newRow][newCol];
                    newGrid[newRow][newCol] = 0;

                    State newState = new State(newGrid, newRow, newCol, currentState.getMoves() + "(" + newRow + "," + newCol + ")");
                    if (!visited.contains(newState)) {
                        stack.push(newState);
                        visited.add(newState);
                    }
                }
            }
        }

    JOptionPane.showMessageDialog(this, "Não foi encontrada uma solução válida. Por favor embaralhe novamente.", "Sem Solução", JOptionPane.INFORMATION_MESSAGE);
}

    // Soluciona o jogo usando busca em largura
    private void solveGameBFS() {
        int[][] target = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();

        State initialState = new State(grid, emptyRow, emptyCol, "");
        queue.add(initialState);
        visited.add(initialState);

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            int[][] currentGrid = currentState.getGrid();

            if (Arrays.deepEquals(currentGrid, target)) {
                showSolution(currentState.getMoves());
                return;
            }

            int row = currentState.getEmptyRow();
            int col = currentState.getEmptyCol();

            // Explora todos os movimentos possíveis a partir do estado atual
            for (int[] direction : new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}) {
                int newRow = row + direction[0];
                int newCol = col + direction[1];

                if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
                    int[][] newGrid = new int[3][3];
                    for (int i = 0; i < 3; i++) {
                        System.arraycopy(currentGrid[i], 0, newGrid[i], 0, 3);
                    }

                    newGrid[row][col] = newGrid[newRow][newCol];
                    newGrid[newRow][newCol] = 0;

                    State newState = new State(newGrid, newRow, newCol, currentState.getMoves() + "(" + newRow + "," + newCol + ")");
                    if (!visited.contains(newState)) {
                        queue.add(newState);
                        visited.add(newState);
                    }
                }
            }
        }

        JOptionPane.showMessageDialog(this, "Não foi encontrada uma solução válida. Por favor embaralhe novamente.", "Sem Solução", JOptionPane.INFORMATION_MESSAGE);
    }

    private void solveGameAStar() {
        int[][] target = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Queue<State> queue = new PriorityQueue<>(Comparator.comparingInt(State::getTotalCost));
        Set<State> visited = new HashSet<>();

        State initialState = new State(grid, emptyRow, emptyCol, "");
        queue.add(initialState);
        visited.add(initialState);

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            int[][] currentGrid = currentState.getGrid();

            if (Arrays.deepEquals(currentGrid, target)) {
                showSolution(currentState.getMoves());
                return;
            }

            int row = currentState.getEmptyRow();
            int col = currentState.getEmptyCol();

            // Explora todos os movimentos possíveis a partir do estado atual
            for (int[] direction : new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}) {
                int newRow = row + direction[0];
                int newCol = col + direction[1];

                if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
                    int[][] newGrid = new int[3][3];
                    for (int i = 0; i < 3; i++) {
                        System.arraycopy(currentGrid[i], 0, newGrid[i], 0, 3);
                    }

                    newGrid[row][col] = newGrid[newRow][newCol];
                    newGrid[newRow][newCol] = 0;

                    State newState = new State(newGrid, newRow, newCol, currentState.getMoves() + "(" + newRow + "," + newCol + ")");
                    if (!visited.contains(newState)) {
                        newState.setHeuristicCost(calculateHeuristicCost(newGrid, target));
                        newState.setTotalCost(newState.getMoves().length() + newState.getHeuristicCost());

                        queue.add(newState);
                        visited.add(newState);
                    }
                }
            }
        }

        JOptionPane.showMessageDialog(this, "Não foi encontrada uma solução válida. Por favor embaralhe novamente.", "Sem Solução", JOptionPane.INFORMATION_MESSAGE);
    }

    private void solveGameGreedy() {
        int[][] target = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Queue<State> queue = new PriorityQueue<>(Comparator.comparingInt(State::getHeuristicCost));
        Set<State> visited = new HashSet<>();

        State initialState = new State(grid, emptyRow, emptyCol, "");
        queue.add(initialState);
        visited.add(initialState);

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            int[][] currentGrid = currentState.getGrid();

            if (Arrays.deepEquals(currentGrid, target)) {
                showSolution(currentState.getMoves());
                return;
            }

            int row = currentState.getEmptyRow();
            int col = currentState.getEmptyCol();

            // Explora todos os movimentos possíveis a partir do estado atual
            for (int[] direction : new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}) {
                int newRow = row + direction[0];
                int newCol = col + direction[1];

                if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
                    int[][] newGrid = new int[3][3];
                    for (int i = 0; i < 3; i++) {
                        System.arraycopy(currentGrid[i], 0, newGrid[i], 0, 3);
                    }

                    newGrid[row][col] = newGrid[newRow][newCol];
                    newGrid[newRow][newCol] = 0;

                    State newState = new State(newGrid, newRow, newCol, currentState.getMoves() + "(" + newRow + "," + newCol + ")");
                    if (!visited.contains(newState)) {
                        newState.setHeuristicCost(calculateHeuristicCost(newGrid, target));

                        queue.add(newState);
                        visited.add(newState);
                    }
                }
            }
        }

        JOptionPane.showMessageDialog(this, "Não foi encontrada uma solução válida. Por favor, embaralhe novamente.", "Sem Solução", JOptionPane.INFORMATION_MESSAGE);
    }

    private int calculateHeuristicCost(int[][] grid, int[][] target) {
        int cost = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int value = grid[i][j];
                if (value != 0) {
                    int targetRow = (value - 1) / 3;
                    int targetCol = (value - 1) % 3;
                    cost += Math.abs(i - targetRow) + Math.abs(j - targetCol);
                }
            }
        }
        return cost;
    }

    // Exibe a solução passo a passo movendo os blocos na interface gráfica
    private void showSolution(String moves) {
        String[] moveList = moves.split("\\(");
        moveList = Arrays.copyOfRange(moveList, 1, moveList.length);

        System.out.println("Solução: " + moves + " (" + moveList.length + " movimentos)");

        int delay = 500;
        for (int i = 0; i < moveList.length; i++) {
            String move = moveList[i];
            move = move.replaceAll("\\)", "");
            String[] coordinates = move.split(",");
            int row = Integer.parseInt(coordinates[0]);
            int col = Integer.parseInt(coordinates[1]);

            final int currentRow = row;
            final int currentCol = col;

            Timer timer = new Timer(delay * i, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    makeMove(currentRow, currentCol);

                    // if (currentRow == moveList.length - 1) {
                    //     JOptionPane.showMessageDialog(SlidingBlocksGame.this, "Solução encontrada!", "Solução", JOptionPane.INFORMATION_MESSAGE);
                    // }
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    // Pausa a execução por um determinado número de milissegundos
    private void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Classe interna que implementa ActionListener para os botões de jogo
    private class ButtonListener implements ActionListener {
        private int row, col;

        public ButtonListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void actionPerformed(ActionEvent e) {
            //System.out.println("Botão (" + row + "," + col + ") clicado.");
            moveBlock(row, col);
        }
    }

    // Classe interna que implementa ActionListener para o botão "Embaralhar"
    private class ShuffleButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            shuffleGame();
        }
    }

    // Classe interna que implementa ActionListener para o botão "Solucionar"
    private class SolveBFSButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            solveGameBFS();
        }
    }
    private class SolveAStarButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            solveGameAStar();
        }
    }
    private class SolveGreedyButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            solveGameGreedy();
        }
    }

    // Classe interna que representa um estado do jogo
    private class State {
        private int[][] grid;
        private int emptyRow;
        private int emptyCol;
        private String moves;
        private int heuristicCost;
        private int totalCost;

        public State(int[][] grid, int emptyRow, int emptyCol, String moves) {
            this.grid = grid;
            this.emptyRow = emptyRow;
            this.emptyCol = emptyCol;
            this.moves = moves;
        }

        public int[][] getGrid() {
            return grid;
        }

        public int getEmptyRow() {
            return emptyRow;
        }

        public int getEmptyCol() {
            return emptyCol;
        }

        public String getMoves() {
            return moves;
        }

        public int getHeuristicCost() {
            return heuristicCost;
        }

        public void setHeuristicCost(int heuristicCost) {
            this.heuristicCost = heuristicCost;
        }

        public int getTotalCost() {
            return totalCost;
        }

        public void setTotalCost(int totalCost) {
            this.totalCost = totalCost;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return Arrays.deepEquals(grid, state.grid);
        }

        @Override
        public int hashCode() {
            return Arrays.deepHashCode(grid);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SlidingBlocksGame();
            }
        });
    }
}
