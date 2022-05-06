package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Professor;

public class ProfessorDao extends DAO {

	public ProfessorDao() {
		super();
		conectar();
	}
	
	public void finalize() {
		close();
	}
	
	public boolean insert(Professor p) {
		boolean status = false;
		try {
			String query = "INSERT INTO Professor(Id,Username,Nome,Email,Telefone,Senha,Genero,DataNasc,Cep,"
					+ "Cidade,Estado,Papel,Experiencia,Descricao) VALUES ('"			//Inseriri a SQL String
					+ p.getId() + "', '" + p.getUsername()  + "', '" + p.getNome() + "', '" + p.getEmail()
					+ "', '" + p.getTelefone() + "', '" + p.getPassword() + "', '" +p.getGenero() + "', '"
					+ p.getDataNasc() + "', '" + p.getCep() + "', '" + p.getCidade() + "', '" + p.getEstado()
					+ "', '" + p.getPapel() + "', " + p.getExperiencia() + ", '" + p.getDescricao() + "');";
			System.out.println(query);
			PreparedStatement st = conexao.prepareStatement(query);
			st.executeUpdate();
			st.close();
			status = true;
		} catch(SQLException u) {
			throw new RuntimeException(u);
		}
		
		return status;
	}
	
	public boolean update(Professor p) {
		boolean status = false;
		if(delete(p.getId()) == true) {
			status = insert(p);
			
		}
		return status;
	}
	
	public boolean delete(String id) {
		boolean status = false;
		
		try {
			String query = "DELETE FROM professor WHERE id='" + id + "';";
			System.out.println(query);
			Statement st = conexao.createStatement();
			st.executeUpdate(query);
			st.close();
			status = true;
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return status;
	}

	public List<Professor> get(){
		List<Professor> professores = new ArrayList<Professor>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String query = "SELECT * FROM Professor";
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				Professor professor = new Professor(rs.getString("id"),rs.getString("username"),
						rs.getString("nome"),rs.getString("email"),rs.getString("telefone"),rs.getString("senha"),
						rs.getString("genero"),rs.getString("datanasc"),rs.getString("cep"),rs.getString("cidade"),
						rs.getString("estado"),rs.getString("papel"),rs.getInt("experiencia"),
						rs.getString("descricao"));		//Inserir parametros
				professores.add(professor);
			}
			
			st.close();
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
		
		return professores;
	}

	public Professor getProfessor(String id) {
		Professor professor = new Professor();
		
		try {
			Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM professor WHERE id='" + id + "';");
			if(rs.next()) {
				professor = new Professor(rs.getString("id"),rs.getString("username"),
						rs.getString("nome"),rs.getString("email"),rs.getString("telefone"),rs.getString("senha"),
						rs.getString("genero"),rs.getString("datanasc"),rs.getString("cep"),rs.getString("cidade"),
						rs.getString("estado"),rs.getString("papel"),rs.getInt("experiencia"),
						rs.getString("descricao"));		//Inserir parametros

			}
			
			st.close();
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return professor;
	}
	
	public List<Professor> search(String busca){
		List<Professor> professores = new ArrayList<Professor>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			//String query = "SELECT * FROM Professor WHERE nome LIKE '" + busca + "%' OR username LIKE '" + busca
					//+ "%' OR cidade LIKE '" + busca + "%' OR estado LIKE '" + "%' OR cidade LIKE '" + busca + "';" ;
			String query = "SELECT * FROM Professor WHERE nome LIKE '" + busca + "%' OR username LIKE '" + busca
					+ "%' OR cidade LIKE '" + busca + "%' OR estado LIKE '" + busca + "%';";
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				Professor professor = new Professor(rs.getString("id"),rs.getString("username"),
						rs.getString("nome"),rs.getString("email"),rs.getString("telefone"),rs.getString("senha"),
						rs.getString("genero"),rs.getString("datanasc"),rs.getString("cep"),rs.getString("cidade"),
						rs.getString("estado"),rs.getString("papel"),rs.getInt("experiencia"),
						rs.getString("descricao"));		//Inserir parametros
				professores.add(professor);
			}
			
			st.close();
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
		
		return professores;
	}
	
	public Professor login(String email,String senha) throws Exception {
		Professor professor = new Professor();
		
		try {
			Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM professor WHERE email='" + email + "' AND senha='" + senha + "';");
			if(rs.next()) {
				professor = new Professor(rs.getString("id"),rs.getString("username"),
						rs.getString("nome"),rs.getString("email"),rs.getString("telefone"),rs.getString("senha"),
						rs.getString("genero"),rs.getString("datanasc"),rs.getString("cep"),rs.getString("cidade"),
						rs.getString("estado"),rs.getString("papel"),rs.getInt("experiencia"),
						rs.getString("descricao"));		//Inserir parametros

			}
			
			st.close();
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		if(professor.getUsername().equals("username")) {
			throw new Exception("Email ou senha incorretos");
		}
		return professor;
	}

	public List<String> getProfessorMateria(String idProfessor){
		List<String> resultado = new ArrayList<String>();
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String query = "SELECT professor.username, leciona.professor_id, "
					+ "leciona.materia_sigla, materia.nome "
					+ "FROM professor "
					+ "INNER JOIN leciona ON professor.id = leciona.professor_id "
					+ "INNER JOIN materia ON leciona.materia_sigla = materia.sigla "
					+ "WHERE professor.id = '" + idProfessor + "';";
			System.out.println(query);
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				resultado.add(rs.getString("nome"));
			}
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
		return resultado;
	}
}
