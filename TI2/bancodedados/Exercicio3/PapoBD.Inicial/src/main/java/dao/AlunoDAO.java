package dao;

import model.Aluno;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO extends DAO {
	public AlunoDAO() {
		super();
		conectar();
	}
	
	public void finalize() {
		close();
	}
	
	public boolean insert(Aluno a) {
		boolean status = false;
		try {
			String query = "INSERT INTO aluno (id, nome, email, senha, idade) " +
							"VALUES (" + a.getId() + ", '" + a.getNome() + "' , '" + a.getEmail() + "', '" +
							a.getSenha() + "', " + a.getIdade() + ");";
			PreparedStatement st = conexao.prepareStatement(query);
			st.executeUpdate();
			st.close();
			status = true;			
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		
		return status;
	}

	public Aluno get(int id) {
		Aluno aluno = null;
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String query = "SELECT * FROM aluno WHERE id=" + id;
			ResultSet rs = st.executeQuery(query);
			if(rs.next()) {
				aluno = new Aluno(rs.getInt("id"),rs.getString("nome"),rs.getString("email"),
						rs.getString("senha"),rs.getInt("idade"));
			}
			st.close();
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		
		return aluno;
	}
	
	//Method thats returns an ordered list of Alunos
	private List<Aluno> get(String orderBy){
		List<Aluno> alunos = new ArrayList<Aluno>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String query = "SELECT * FROM aluno" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				Aluno aluno = new Aluno(rs.getInt("id"),rs.getString("nome"),rs.getString("email"),
								rs.getString("senha"),rs.getInt("idade"));
				alunos.add(aluno);
			}
			
			st.close();
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
		
		return alunos;
	}
	
	public List<Aluno> get(){
		return get("");
	}
	
	public List<Aluno> getOrderById(){
		return get("id");
	}
	
	public List<Aluno> getOrderByNome(){
		return get("nome");
	}
	
	public List<Aluno> getOrderByEmail(){
		return get("email");
	}
	
	public List<Aluno> getOrderByIdade(){
		return get("idade");
	}
	
	public boolean update(Aluno a) {
		boolean status = false;
		
		try {
			String query = "UPDATE aluno SET nome = '" + a.getNome() + "' , email = '" + a.getEmail() + "' , " +
							"senha = '" + a.getSenha() + "' , idade = " + a.getIdade() + " WHERE id= " + a.getId();
			PreparedStatement st = conexao.prepareStatement(query);
			st.executeUpdate();
			st.close();
			status = true;
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return status;
	}
	
	public boolean delete(int id) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM aluno WHERE id=" + id);
			st.close();
			status = true;
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return status;
	}
}
