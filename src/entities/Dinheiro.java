package entities;

public abstract class Dinheiro {

	protected double dinheiro;
	protected String descricao;
	
	public void setDinheiro(double dinheiro) {
		this.dinheiro = dinheiro;
	}
	
	public double getDinheiro() {
		return dinheiro;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
