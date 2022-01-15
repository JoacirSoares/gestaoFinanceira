package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.enums.TipoConta;
import entities.enums.TipoDespesa;
import entities.enums.TipoReceita;

public class Conta {

	private double saldo;
	private TipoConta tipoConta;
	private String instituicaoFinanceira;
	private Usuario usuario;
	private List <Despesa> listaDespesa;
	private List <Receita> listaReceita;
	
	public Conta(Usuario usuario) {
		this.usuario = usuario; 
		this.saldo = 0;
		this.listaDespesa = new ArrayList<Despesa>();
		this.listaReceita = new ArrayList<Receita>();
		this.tipoConta = null;
		this.instituicaoFinanceira = "";
	}

	public double getSaldo() {
		return saldo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
		return "Olá " + usuario.getNome() + ", o saldo da sua conta é de " + String.format("%.2f", this.saldo);
	}
	
}
