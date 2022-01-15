package entities;

import java.text.SimpleDateFormat;
import java.util.Date;

import entities.enums.TipoReceita;

public class Receita extends Dinheiro{
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private Date dataRecebimento;
	private TipoReceita tipoReceita;
	
	public Receita() {
	}
	
	public Receita(double receita, String descricao, Date dataRecebimento, TipoReceita tipoReceita) {
		this.dinheiro = receita;
		this.descricao = descricao;
		this.dataRecebimento = dataRecebimento;
		this.tipoReceita = tipoReceita;
	}
	
	public Date getDataRecebimento() {
		return dataRecebimento;
	}

	public void setDataRecebimento(Date dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}

	public TipoReceita getTipoReceita() {
		return tipoReceita;
	}

	public void setTipoReceita(TipoReceita tipoReceita) {
		this.tipoReceita = tipoReceita;
	}

	public String toString() {
		return "Receita de: " + this.descricao + " no valor de " + 
				String.format("%.2f", this.dinheiro) + ", tipo: " + tipoReceita +
				", Data e Horário da operação: " + sdf.format(dataRecebimento);
	}
}
