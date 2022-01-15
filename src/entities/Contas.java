package entities;

import java.util.ArrayList;
import java.util.List;

public class Contas {
	private List<Conta> contas = new ArrayList<>();
	
	public Contas() {
	}
	
	public Contas(List<Conta> contas) {
		this.contas = contas;
	}

	public List<Conta> getContas() {
		return contas;
	}

	public void addConta(Conta conta) {
		contas.add(conta);
	}
	
	public void removeConta(Conta conta) {
		contas.remove(conta);
	}
}
