package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.enums.TipoConta;
import entities.enums.TipoDespesa;
import entities.enums.TipoReceita;

public class Conta {

	private String nome;
	private String cpf;
	private int idade;
	private double saldo;
	private TipoConta tipoConta;
	private String instituicaoFinanceira;
	private List <Despesa> listaDespesa;
	private List <Receita> listaReceita;
	
	public Conta() {
		this.nome = "";
		this.cpf = "";
		this.idade = 0;
		this.saldo = 0;
		this.listaDespesa = new ArrayList<Despesa>();
		this.listaReceita = new ArrayList<Receita>();
		this.tipoConta = null;
		this.instituicaoFinanceira = "";
	}
	
	public Conta(String nome, String cpf, int idade) {
		this.nome = "";
		this.cpf = "";
		this.idade = 0;
		this.saldo = 0;
		this.listaDespesa = new ArrayList<Despesa>();
		this.listaReceita = new ArrayList<Receita>();
		this.tipoConta = null;
		this.instituicaoFinanceira = "";
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

	public double getSaldo() {
		return saldo;
	}
	
	public List<Despesa> getListaDespesa() {
		return listaDespesa;
	}

	public List<Receita> getListaReceita() {
		return listaReceita;
	}

	public TipoConta getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(TipoConta tipoConta) {
		this.tipoConta = tipoConta;
	}

	public String getInstituicaoFinanceira() {
		return instituicaoFinanceira;
	}

	public void setInstituicaoFinanceira(String instituicaoFinanceira) {
		this.instituicaoFinanceira = instituicaoFinanceira;
	}

	public double addDespesa(String descricao, double quantidade, Date dataAtual, TipoDespesa tipoDespesa) {
		this.listaDespesa.add(new Despesa(quantidade, descricao, dataAtual, tipoDespesa));
		this.saldo = this.saldo - quantidade;
		return this.saldo;
	}
	
	public double addReceita(String descricao, double quantidade, Date dataAtual, TipoReceita tipoReceita) {
		this.listaReceita.add(new Receita(quantidade, descricao, dataAtual, tipoReceita));
		this.saldo = this.saldo + quantidade;
		return this.saldo;
	}
	
	public String toString() {
		return "Olá " + this.nome + ", o saldo da sua conta é de " + String.format("%.2f", this.saldo);
	}
	
	public String toStringAll() {
		return  "\nNome: " + this.nome + 
				"\nSaldo: " + String.format("%.2f", this.saldo) + 
				"\nCpf: " + this.cpf +
				"\nIdade: " + this.idade +
				"\nInstituição Financeira: " + this.instituicaoFinanceira +
				"\nTipo da conta: " + this.tipoConta;
	}
	
}
