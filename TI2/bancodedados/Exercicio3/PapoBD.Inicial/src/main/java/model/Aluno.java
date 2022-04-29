package model;

public class Aluno {
	private int id;
	private String nome;
	private String email;
	private String senha;
	private int idade;
	
	public Aluno(){
		id = -1;
		nome = "admin";
		email = "admin@email.com";
		nome = "admin";
		idade = 99;
	}
	
	public Aluno(int id, String nome, String email, String senha, int idade) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.idade = idade;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
	
	
	public String toString() {
		return "Nome: " + nome + " ID: " + id + " E-mail: " + email +
				" Senha: " + senha + " idade: " + idade; 
	}
	
}
