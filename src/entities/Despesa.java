package entities;

import java.text.SimpleDateFormat;
import java.util.Date;

import entities.enums.TipoDespesa;

public class Despesa extends Dinheiro{
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private Date dataPagamento;
	private TipoDespesa tipoDespesa;

	public Despesa() {
	}
	
	public Despesa(double despesa, String descricao, Date dataPagamento, TipoDespesa tipoDespesa) {
		this.dinheiro = despesa;
		this.descricao = descricao;
		this.dataPagamento = dataPagamento;
		this.tipoDespesa = tipoDespesa; 
	}
	
	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public TipoDespesa getTipoDespesa() {
		return tipoDespesa;
	}

	public void setTipoDespesa(TipoDespesa tipoDespesa) {
		this.tipoDespesa = tipoDespesa;
	}

	public String toString() {
		return "Despesa de: " + this.descricao + " no valor de " + 
				String.format("%.2f", this.dinheiro) + ", tipo: " + tipoDespesa + 
				", Data e Horário da operação: " + sdf.format(dataPagamento);
	}
}
