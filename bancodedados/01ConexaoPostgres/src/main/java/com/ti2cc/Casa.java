package com.ti2cc;

public class Casa {
	private int id;
	private int cpfProprietario;
	private String rua = new String();
	private String bairro = new String();
	private int numero;
	private int quantQuartos;
	private int quantVagas;
	private int quantbanheiros;
	private double metragem;
	
	Casa(){
		this.id = -1;
		this.cpfProprietario = 0;
		this.rua = "default";
		this.bairro = "default";
		this.numero = -1;
		this.quantQuartos = 0;
		this.quantVagas = 0;
		this.quantbanheiros = 0;
		this.metragem = 0;
	}
	
	Casa(int id, int cpfProprietario, String rua, String bairro, int numero, int quantQuartos, int quantVagas, int quantBanheiros, double metragem){
		this.id = id;
		this.cpfProprietario = cpfProprietario;
		this.rua = rua;
		this.bairro = bairro;
		this.numero = numero;
		this.quantQuartos = quantQuartos;
		this.quantVagas = quantVagas;
		this.quantbanheiros = quantBanheiros;
		this.metragem = metragem;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCpfProprietario() {
		return cpfProprietario;
	}
	public void setCpfProprietario(int cpfProprietario) {
		this.cpfProprietario = cpfProprietario;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public int getQuantQuartos() {
		return quantQuartos;
	}
	public void setQuantQuartos(int quantQuartos) {
		this.quantQuartos = quantQuartos;
	}
	public int getQuantVagas() {
		return quantVagas;
	}
	public void setQuantVagas(int quantVagas) {
		this.quantVagas = quantVagas;
	}
	public int getQuantbanheiros() {
		return quantbanheiros;
	}
	public void setQuantbanheiros(int quantbanheiros) {
		this.quantbanheiros = quantbanheiros;
	}
	public double getMetragem() {
		return metragem;
	}
	public void setMetragem(double metragem) {
		this.metragem = metragem;
	}
	
	@Override
	public String toString() {
		return "Casa: [id: " + this.id + " Cpf do proprietario: " + this.cpfProprietario + " Bairro: " + this.bairro + " Rua: " + this.rua + " Numero: " + this.numero + " Num. de Quartos: " + this.quantQuartos + " Num. de Banheiros: " + this.quantbanheiros + " Num. de Vagas: " + this.quantVagas + " Metragem: " + this.metragem + " ]";
		
	}
	
	
}
