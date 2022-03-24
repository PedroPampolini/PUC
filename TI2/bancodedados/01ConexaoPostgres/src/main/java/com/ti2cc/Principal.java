package com.ti2cc;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        DAO dao = new DAO();
        dao.conectar();
        Scanner teclado = new Scanner(System.in);
        boolean sair = false;
        
        while(sair == false) {
	        switch(solicitaOpcao(teclado)) {
	        	case 1: 
	        		try {
		        		Casa[] casas = dao.getCasas();
		        		for(int i = 0; i < casas.length; i++) {
		        			System.out.println(casas[i].toString());
		        		}
	        		} catch(Exception e) {
	        			System.out.println("==========Atencao==========\nBanco vazio, insira novos itens\n===========================");
	        		}
	        		break;
	        	
	        	case 2:
	        		Casa casaInserida = getCasaInfo(teclado);
	        		if(dao.inserirCasa(casaInserida) == true)
	        			System.out.println("Casa inserida com sucesso: " + casaInserida.toString());
	        		else
	        			System.out.println("Erro ao inserir casa");
	        		break;
	        	
	        	case 3:
	        		System.out.print("ID para excluir: ");
	        		int id = teclado.nextInt();
	        		if(dao.excluirCasa(id) == true)
	        			System.out.println("Casa excluida com sucesso");
	        		else
	        			System.out.println("Erro ao excluir casa de id " + id);
	        		break;
	        	
	        	case 4:
	        		Casa casaAtualizada = getCasaInfo(teclado);
	        		if(dao.atualizaCasa(casaAtualizada) == true)
	        			System.out.println("Casa atualizada com sucesso: " + casaAtualizada.toString());
	        		else
	        			System.out.println("Erro ao atualizada casa");
	        		break;
	        	
	        	case 5: sair = true; break;
	        	
	        	default: System.out.println("Opcao invalida");
	        }
        }
        System.out.println("Fim do programa");
        teclado.close();
        dao.close();
    }
    
    public static int solicitaOpcao(Scanner teclado) {
    	int opcao;
    	System.out.print("Digite o que ira fazer:\n" + 
    	"1) Listar\n2) Inserir\n3) Excluir\n4) Atualizar\n5) Sair\nDigite: ");
    	opcao = teclado.nextInt();
    	return opcao;
    }
    
    public static Casa getCasaInfo(Scanner scan) {
    	int id;
    	int cpfProprietario;
    	String rua = new String();
    	String bairro = new String();
    	int numero;
    	int quantQuartos;
    	int quantVagas;
    	int quantbanheiros;
    	double metragem;
    	
    	System.out.print("Digite o id: ");
    	id = scan.nextInt();

    	System.out.print("Digite o CPF do proprietario: ");
    	cpfProprietario = scan.nextInt();
    	scan.nextLine();
    	System.out.print("Digite a rua: ");
    	rua = scan.nextLine();
    	
    	System.out.print("Digite o bairro: ");
    	bairro = scan.nextLine();

    	System.out.print("Digite o numero: ");
    	numero = scan.nextInt();

    	System.out.print("Digite a quantidade de quartos: ");
    	quantQuartos = scan.nextInt();

    	System.out.print("Digite a quantidade de vagas: ");
    	quantVagas = scan.nextInt();

    	System.out.print("Digite a quantidade de banheiros: ");
    	quantbanheiros = scan.nextInt();

    	System.out.print("Digite a metragem: ");
    	metragem = scan.nextDouble();

    	Casa casa = new Casa(id, cpfProprietario, rua, bairro, numero, quantQuartos, quantVagas, quantbanheiros, metragem);
    	
    	return casa;
    }
}
