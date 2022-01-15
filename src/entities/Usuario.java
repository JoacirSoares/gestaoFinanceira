package entities;

public class Usuario {

	private String nome;
	private String cpf;
	private int idade;
	
	public Usuario() {
		this.nome = "";
		this.cpf = "";
		this.idade = 0;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public boolean setCPF(String cpf) {
		
		if(String.valueOf(cpf).toString().length() == 11) {
			this.cpf = cpf;
			return true;
		} else {
			return false;
		}
		
	}
	
	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}
	
	public String toString() {
		return "Nome: " 
				+ this.nome
				+ "\nIdade:"
				+ this.idade 
				+ "Cpf: "
				+ this.cpf;
				
	}
}
