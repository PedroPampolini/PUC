package app;

import dao.DAO;
import dao.AlunoDAO;
import model.Aluno;
import java.util.List;


public class Principal {
	
	public static void main(String[] args) {
		
		AlunoDAO alunoDao = new AlunoDAO();
		alunoDao.conectar();
		
		
		System.out.println("BD normal: ");
		List<Aluno> turma = alunoDao.get();
		for(int i = 0; i < turma.size(); i++) {
			System.out.println(turma.get(i).toString());
		}
		
		System.out.println("\n\nInsert aluno 3: ");
		alunoDao.insert(new Aluno(3,"Aluno Tres","aluno3@email.com","adminAluno3",25));
		
		turma = alunoDao.get();
		for(int i = 0; i < turma.size(); i++) {
			System.out.println(turma.get(i).toString());
		}
		
		System.out.println("\n\nUpdate aluno 3: ");
		alunoDao.update(new Aluno(3,"Pedro Pampolini","aluno3@email.com","adminAluno3",25));
		
		turma = alunoDao.get();
		for(int i = 0; i < turma.size(); i++) {
			System.out.println(turma.get(i).toString());
		}
		
		System.out.println("\n\nDelete aluno 3: ");
		alunoDao.delete(3);
		
		turma = alunoDao.get();
		for(int i = 0; i < turma.size(); i++) {
			System.out.println(turma.get(i).toString());
		}
		
	}
}
