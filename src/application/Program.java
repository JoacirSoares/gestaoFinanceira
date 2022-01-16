package application;

import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import dao.ContaDAO;
import entities.Conta;
import entities.Contas;
import entities.Despesa;
import entities.Receita;
import entities.enums.TipoConta;
import entities.enums.TipoDespesa;
import entities.enums.TipoReceita;

/**
 * @author Joacir Soares Arlego Reis
 *
 */
public class Program {

	// Criei um novo objeto do tipo Scanner para ler dados do teclado
	private static Scanner sc = new Scanner(System.in);
	// Criei uma variavel para alocar a resposta do usuario
	private static String opcao = "";
	// Crei uma variavel que receberá a variavel apcao tranformada em inteiro
	private static int opcaoEscolhida = -1;
	// Criei um objeto do tipo Usuario
	private static Contas contas = new Contas();
	
	private static String nomeUsuario = "";
	private static String idadeUsuario = "";
	private static int idadeUsu = 0;
	private static String cpfUsuario = ""; 
	
	private static Conta novaConta = new Conta(); 
	private static TipoConta tipoConta = null; 
	private static TipoReceita tipoReceita = null;
	private static TipoDespesa tipoDespesa = null;
	private static String instituicaoFinanceira = ""; 
	private static String descricaoTransferencia = "";
	private static String custo;
	private static double custoTransferencia = -1;
	private static Double valorTransferencia = null;
	private static boolean saldoNegativo = false; 
	private static String cpf1;
	private static String cpf2;
	private static long eNumero;
	private static ContaDAO contaDao = new ContaDAO();
	
	/**
	 * Página de inicio
	 */
	private static void paginaInicial() {
		do {
			
			System.out.println();
			System.out.println("---------------------");
			System.out.println("   Sessão de contas.");
			System.out.println("---------------------");
			System.out.println();
	
			System.out.println("1. Criar uma nova conta.");
			System.out.println("2. Editar conta.");
			System.out.println("3. Listar contas.");
			System.out.println("4. Transferir saldo entre contas.");
			System.out.println("0. Sair do Programa.");
			
			do {
				System.out.println();
				System.out.print("Introduza o número da operação que deseja realizar: ");
				opcao = sc.nextLine();
				
				try {
					// Passando o valor que o usuario introduzio para um inteiro
					opcaoEscolhida = Integer.parseInt(opcao);
				} catch(Exception e) {
					System.out.println("Você tem que introduzir um número "
							+ "para que umas dessas opções seja realizada!");
				}
				// Continua o programa caso ocorra algum erro
			} while(opcaoEscolhida < 0 || opcaoEscolhida > 4 || opcao.isEmpty());
			
			switch (opcaoEscolhida) {
				case 1: // Cria uma nova conta
					if(introduzirConta()) {
						
						// Criando uma conta;
						novaConta.setNome(nomeUsuario);
						novaConta.setIdade(idadeUsu);
						novaConta.setTipoConta(tipoConta);
						novaConta.setInstituicaoFinanceira(instituicaoFinanceira);
						contaDao.inserir(novaConta);
						
						// Adcionando a um array de contas
						contas.addConta(novaConta);
						
						menuUsuario();
						
					}	
					break;
				case 2: // Editar uma conta
					paginaEditarConta();
					break;
				case 3: // Lista as contas
					if(contas.getContas().isEmpty()) {
						System.out.println("Não existe conta para listar.");
					}
					for(Conta conta : contas.getContas()) {
						System.out.println(conta.toString());
					}
					break;
				case 4: // Transferi saldo entre contas
					if(contas.getContas().isEmpty() || contas.getContas().size() < 2) {
						System.out.println("Não existe conta para transferir.");
					} else {
						do {
							System.out.print("Digite o cpf da conta que deseja fazer a transferência: ");
							cpf1 = sc.nextLine();
						} while(String.valueOf(cpf1).length() != 11);
						do {
							System.out.print("Digite o cpf da conta que receberá a transferência: ");
							cpf2 = sc.nextLine();
						} while(String.valueOf(cpf2).length() != 11);
						do {
							System.out.print("Digite o valor da transferência: ");
							try {
								
								valorTransferencia = sc.nextDouble();
								
							} catch(Exception e) {
								
								System.out.print("O valor da tranferência só pode ser número. ");
								
							}
						} while(valorTransferencia == null);
						if(cpf1.equals(cpf2)) {
							System.out.print("O cpf da transferencia não pode ser igual ao cpf que receberá a transferencia.");
							break;
						}
						
						for(Conta conta : contas.getContas()) {
							if(conta.getCpf().equals(cpf1)) {
								conta.addDespesa("Transferência de" + cpf2, valorTransferencia, new Date(), TipoDespesa.OUTROS);
							}
							if(conta.getCpf().equals(cpf2)) {
								conta.addReceita("Transferência de" + cpf1, valorTransferencia, new Date(), TipoReceita.OUTROS);
							}
						}
					}
					break;
				case 0: // Sai do Programa
					System.out.println("Programa finalizado\nObrigado por utilizar a aplicação!");
					break;	
			}
			
		} while(opcaoEscolhida != 0);
	}
	
	/**
	 * Página de edição de contas
	 */
	private static void paginaEditarConta() {
		
		if(contas.getContas().isEmpty()) {
			System.out.println("Não existe conta para editar.");
			paginaInicial();
		}
		
		System.out.println();
		System.out.println("--------------------.");
		System.out.println("   Sessão de contas.");
		System.out.println("---------------------");
		System.out.println();

		System.out.print("Digite o cpf da conta que deseja editar: ");
		String cpf = sc.nextLine();
		// fazer tratamento de erros
		for(Conta conta : contas.getContas()) {
			if(conta.getCpf().equals(cpf)) {
				System.out.println(conta.getCpf());
				do {
					
					System.out.println();
					System.out.println("----------------------------");
					System.out.println("   Sessão de editar conta.");
					System.out.println("----------------------------");
					System.out.println();
					System.out.println("1. Modificar Despesas e Receitas da conta.");
					System.out.println("2. Modificar o nome da conta.");
					System.out.println("3. Modificar o tipo da conta(carteira, corrente, poupanca).");
					System.out.println("4. Modificar a instituição financeira da conta.");
					System.out.println("5. Modificar a idade do usuário.");
					System.out.println("6. Voltar para a pagina inicial.");
					
					do {
						System.out.println();
						System.out.print("Introduza o número da operação que deseja realizar: ");
						opcao = sc.nextLine();
						
						try {
							// Passando o valor que o usuario introduzio para um inteiro
							opcaoEscolhida = Integer.parseInt(opcao);
						} catch(Exception e) {
							System.out.println();
							System.out.println("Você tem que introduzir um número "
									+ "para que umas dessas opções seja realizada!");
						}
						// Continua o programa caso ocorra algum erro
					} while(opcaoEscolhida < 0 || opcaoEscolhida > 6 || opcao.isEmpty());
					
					switch (opcaoEscolhida) {
						case 1: // Modifica Despesas e Receita da conta
							menuUsuario();
							break;
						case 2: // Modifica o nome da conta
							System.out.print("Digite o novo nome da conta: ");
							String novoNome = sc.nextLine();
							conta.setNome(novoNome);
							contaDao.atualizarNome(conta); 
							break;
						case 3: // Modifica o tipo da conta
							System.out.print("Digite o novo tipo da conta: ");
							TipoConta novoTipo = TipoConta.valueOf(sc.next().toUpperCase());
							conta.setTipoConta(novoTipo);
							contaDao.atualizarTipoConta(conta); 
							break;
						case 4: // Modifica a instituição financeira da conta
							System.out.print("Digite a nova instituição financeira da conta: ");
							String novaInstituicaoFinanceira = sc.nextLine();
							conta.setInstituicaoFinanceira(novaInstituicaoFinanceira);
							contaDao.atualizarInstituicaoFinanceira(conta); 
							break;
						case 5: // Modifica a idade do usuário
							System.out.print("Digite a nova idade do usuario: ");
							int novaIdade = sc.nextInt();
							conta.setIdade(novaIdade);
							contaDao.atualizarIdade(conta); 
							break;
						case 6: // Volta para a pagina inicial
							paginaInicial();
							break;
						case 0: // Sai do Programa
							System.out.println("Programa finalizado\nObrigado por utilizar a aplicação!");
							break;	
					}
					
				} while(opcaoEscolhida != 0);
			}
		}
		
	}
	
	/**
	 * Metodo que visualiza o menu de opções do usuario
	 */
	private static void menuUsuario() {
		do {
		
			System.out.println();
			System.out.println("-----------------------------------");
			System.out.println("   Sessão de despesas e receitas.");
			System.out.println("-----------------------------------");
			System.out.println();
			System.out.println("1. Introduzir uma nova receita.");
			System.out.println("2. Introduzir uma nova despesa.");
			System.out.println("3. Mostrar receitas.");
			System.out.println("4. Mostrar despesas.");
			System.out.println("5. Mostrar saldo da conta.");
			System.out.println("6. Mostrar sua conta.");
			System.out.println("7. Voltar para a seção de contas.");
			System.out.println("0. Sair.");
			
			do {
				System.out.println();
				System.out.print("Introduza o número da operação que deseja realizar: ");
				opcao = sc.nextLine();
				
				try {
					// Passando o valor que o usuario introduzio para um inteiro
					opcaoEscolhida = Integer.parseInt(opcao);
				} catch(Exception e) {
					System.out.println("Você tem que introduzir um número "
							+ "para que umas dessas opções seja realizada!");
				}
				// Continua o programa caso ocorra algum erro
			} while(opcaoEscolhida < 0 || opcaoEscolhida > 7 || opcao.isEmpty());
			
			switch (opcaoEscolhida) {
				case 1: // Introduzi uma receita
					introduzirReceita();
					break;
				case 2: // Introduzi uma despesa
					introduzirDespesa();
					break;
				case 3: // Mostrar as receitas
					mostrarListaReceita();
					break;
				case 4: // Mostrar as Despesas
					mostrarListaDespesa();
					break;
				case 5: // Mostrar saldo da conta
					System.out.println(novaConta.toString());
					break;
				case 6: // Mostrar sua conta
					System.out.println(novaConta.toStringAll());
					for(int i = 0; i < novaConta.getListaReceita().size(); i++) {
						System.out.println(novaConta.getListaReceita().get(i));
					}
					for(int i = 0; i < novaConta.getListaDespesa().size(); i++) {
						System.out.println(novaConta.getListaDespesa().get(i));
					}
					break;
				case 7: // Voltar para a pagina inicial
					paginaInicial();
					break;
				case 0: // Sair do programa
					System.out.println("Programa finalizado\nObrigado por utilizar a aplicação!");
					break;	
			}
			
		} while(opcaoEscolhida != 0);
	}

	/**
	 * Cria uma Conta
	 */
	private static boolean introduzirConta() {
		
		// Pedido do nome do usuário
		do {
			System.out.print("Introduza o nome do usuário: ");
			nomeUsuario = sc.nextLine();
		} while(nomeUsuario.isEmpty());
		
		// Pedido da idade do usuário
		do {
			System.out.print("Introduza a idade do usuário: ");
			idadeUsuario = sc.nextLine();
			
			try {
				
				idadeUsu = Integer.parseInt(idadeUsuario);
				
			} catch(Exception e) {
				
				System.out.println("O número precisa ser maior que zero para a idade");
				
			}
			
		} while(idadeUsuario.isEmpty() || idadeUsu <= 0);
		
		// Pedido do cpf do usuário
		do {
			System.out.print("Introduza o cpf do usuário: ");
			cpfUsuario = sc.nextLine();
			if(cpfUsuario.length() != 11) {
				System.out.println("O cpf deve conter 11 dígitos.");
			}

			try {
	
				eNumero = Long.parseLong(cpfUsuario);
				
			} catch(Exception e) {
				
				System.out.println("O cpf só pode conter números");
				
			}
		} while(novaConta.setCPF(cpfUsuario) != true);
		
		// Pedido do tipo de conta do usuário
		do {
			System.out.print("Introduza o tipo de conta(carteira, corrente, poupanca): ");
			
			try {
				
				tipoConta = TipoConta.valueOf(sc.next().toUpperCase());
				sc.nextLine();
				
			} catch(Exception e) {
				
				System.out.println("O tipo de conta tem que ser um dos listados a cima.");

			}
		} while(tipoConta == null);
		
		// Pedido da instituição financeira do usuário
		do {
			System.out.print("Introduza  do instituição financeira usuario: ");
			instituicaoFinanceira = sc.nextLine();
		} while(instituicaoFinanceira == "");
	
		return true;
	}
	
	/**
	 * Metodo que introduz receita na conta
	 */
	private static void introduzirReceita() {
		do {
			System.out.println("Introduza a descriçao da receita: ");
			descricaoTransferencia = sc.nextLine();
		} while(descricaoTransferencia.isEmpty());
		
		do {
			System.out.println("Introduza o custo da receita: ");
			custo = sc.nextLine();
			
			try {
				custoTransferencia = Double.parseDouble(custo);
			} catch(Exception e) {
				System.out.println("Você deve introduzir um valor decimal para o montante de despesa");
			}
		} while(custo.isEmpty() || custoTransferencia < 0);
		
		do {
			System.out.println("Introduza o tipo da receita(salario, presente, premio, outros): ");
			
			try {
				
				tipoReceita = TipoReceita.valueOf(sc.next().toUpperCase());
				sc.nextLine();
				
			} catch(Exception e) {
				
				System.out.println("O tipo de receita tem que ser um dos listados a cima.");

			}
		} while(tipoReceita == null);
		
		for(Conta conta : contas.getContas()) {
			if(conta.getCpf() == novaConta.getCpf()) {
				String descricao = descricaoTransferencia;
				conta.addReceita(descricaoTransferencia, custoTransferencia, new Date(), tipoReceita);
				System.out.println("Receita registrada com sucesso.");
				for (Receita receita : conta.getListaReceita()) {
					if(descricao.equals(receita.getDescricao())){
						contaDao.inserirReceita(receita);
					}
				}
			} else {
				System.out.print("falha ao registrar receita");
			}
		}
	}
	
	/*
	 * Metodo que introduz despesa na conta
	 */
	private static void introduzirDespesa() {
		do {
			System.out.println("Introduza a descriçao da despesa: ");
			descricaoTransferencia = sc.nextLine();
		} while(descricaoTransferencia.isEmpty());
		
		do {
			System.out.println("Introduza o custo da despesa: ");
			custo = sc.nextLine();
			
			try {
				custoTransferencia = Double.parseDouble(custo);
			} catch(Exception e) {
				System.out.println("Você deve introduzir um valor decimal para o custo da despesa");
			}
			if(novaConta.getSaldo() < 0) {
				System.out.println("Você deve ingressar dinheiro para realizar o pagamento desta despesa");
				saldoNegativo = true;
				break;
			} else {
				saldoNegativo = false;
			}
		} while(custo.isEmpty() || custoTransferencia < 0 || saldoNegativo == true);
		
		do {
			System.out.println("Introduza o tipo da despesa(alimentacao, educacao, lazer, moradia, roupa, saude, transporte, outros): ");
			
			try {
				
				tipoDespesa = TipoDespesa.valueOf(sc.next().toUpperCase());
				sc.nextLine();
				
			} catch(Exception e) {
				
				System.out.println("O tipo de despesa tem que ser um dos listados a cima.");

			}
		} while(tipoDespesa == null);
		
		if(saldoNegativo == false) {
			// No caso do saldo ser negativo 
			novaConta.addDespesa(descricaoTransferencia, custoTransferencia, new Date(), tipoDespesa);
			for(Conta conta : contas.getContas()) {
				if(conta.getCpf() == novaConta.getCpf()) {
					String descricao = descricaoTransferencia;
					for (Despesa despesa : conta.getListaDespesa()) {
						if(descricao.equals(despesa.getDescricao())){
							contaDao.inserirDespesa(despesa);
							
						}
					}
				}
			}
		}
	}
	
	/*
	 * Metodo que mostrará a lista de receitas
	 */
	private static void mostrarListaReceita() {
		for(Conta conta : contas.getContas()) {
			if(conta.getCpf() == novaConta.getCpf()) {
				for(int i = 0; i < conta.getListaReceita().size(); i++) {
					System.out.println(conta.getListaReceita().get(i));
				}
			}
		}
	}
	
	/*
	 * Metodo que mostrará a lista de despesas
	 */	
	private static void mostrarListaDespesa() {
		for(Conta conta : contas.getContas()) {
			if(conta.getCpf() == novaConta.getCpf()) {
				for(int i = 0; i < conta.getListaDespesa().size(); i++) {
					System.out.println(conta.getListaDespesa().get(i));
				}
			}
		}
	}
	
	/*
	 * Metodo Principal
	 */
	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);

		// Inicia o programa dando opções do programa ao usuario 
		paginaInicial();
		
	}
}