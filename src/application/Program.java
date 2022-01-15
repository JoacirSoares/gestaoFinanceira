package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import entities.Conta;
import entities.Contas;
import entities.Usuario;
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
	// Crei uma variavel que receber� a variavel apcao tranformada em inteiro
	private static int opcaoEscolhida = -1;
	// Criei um objeto do tipo Usuario
	private static Usuario novoUsuario = new Usuario();
	private static Contas contas = new Contas();
	
	private static String nomeUsuario = "";
	private static String idadeUsuario = "";
	private static int idadeUsu = 0;
	private static String cpfUsuario = ""; 
	
	private static Conta novaConta = null; 
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
	
	/**
	 * P�gina de inicio
	 */
	private static void paginaInicial() {
		do {
			
			System.out.println();
			System.out.println("---------------------");
			System.out.println("   Se��o de contas.");
			System.out.println("---------------------");
			System.out.println();
	
			System.out.println("1. Criar uma nova conta.");
			System.out.println("2. Editar conta.");
			System.out.println("3. Listar contas.");
			System.out.println("4. Transferir saldo entre contas.");
			System.out.println("0. Sair do Programa.");
			
			do {
				System.out.println();
				System.out.print("Introduza o n�mero da opera��o que deseja realizar: ");
				opcao = sc.nextLine();
				
				try {
					// Passando o valor que o usuario introduzio para um inteiro
					opcaoEscolhida = Integer.parseInt(opcao);
				} catch(Exception e) {
					System.out.println("Voc� tem que introduzir um n�mero "
							+ "para que umas dessas op��es seja realizada!");
				}
				// Continua o programa caso ocorra algum erro
			} while(opcaoEscolhida < 0 || opcaoEscolhida > 4 || opcao.isEmpty());
			
			switch (opcaoEscolhida) {
				case 1: // Cria uma nova conta
					if(introduzirConta()) {
						
						// Criando uma conta;
						novaConta = new Conta(novoUsuario);
						novaConta.setTipoConta(tipoConta);
						novaConta.setInstituicaoFinanceira(instituicaoFinanceira);
						
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
						System.out.println("N�o existe conta para listar.");
					}
					for(Conta conta : contas.getContas()) {
						System.out.println(conta.toString());
					}
					break;
				case 4: // Transferi saldo entre contas
					if(contas.getContas().isEmpty() || contas.getContas().size() < 2) {
						System.out.println("N�o existe conta para transferir.");
					} else {
						do {
							System.out.print("Digite o cpf da conta que deseja fazer a transfer�ncia: ");
							cpf1 = sc.nextLine();
						} while(String.valueOf(cpf1).toString().length() != 11);
						do {
							System.out.print("Digite o cpf da conta que receber� a transfer�ncia: ");
							cpf2 = sc.nextLine();
						} while(String.valueOf(cpf2).toString().length() != 11);
						do {
							System.out.print("Digite o valor da transfer�ncia: ");
							try {
								
								valorTransferencia = sc.nextDouble();
								
							} catch(Exception e) {
								
								System.out.print("O valor da tranfer�ncia s� pode ser n�mero. ");
								
							}
						} while(valorTransferencia == null);
						if(cpf1.equals(cpf2)) {
							System.out.print("O cpf da transferencia n�o pode ser igual ao cpf que receber� a transferencia.");
							break;
						}
						// fazer tratamento de erros
						for(Conta conta : contas.getContas()) {
							if(conta.getUsuario().getCpf().equals(cpf1)) {
								conta.addDespesa("Transfer�ncia de" + cpf2, valorTransferencia, new Date(), TipoDespesa.OUTROS);
							}
							if(conta.getUsuario().getCpf().equals(cpf2)) {
								conta.addReceita("Transfer�ncia de" + cpf1, valorTransferencia, new Date(), TipoReceita.OUTROS);
							}
						}
					}
					break;
				case 0: // Sai do Programa
					System.out.println("Programa finalizado\nObrigado por utilizar a aplica��o!");
					break;	
			}
			
		} while(opcaoEscolhida != 0);
	}
	
	/**
	 * P�gina de edi��o de contas
	 */
	private static void paginaEditarConta() {
		
		if(contas.getContas().isEmpty()) {
			System.out.println("N�o existe conta para editar.");
			paginaInicial();
		}
		
		System.out.println();
		System.out.println("--------------------.");
		System.out.println("   Se��o de contas.");
		System.out.println("---------------------");
		System.out.println();

		System.out.print("Digite o rg da conta que deseja editar: ");
		String rg = sc.nextLine();
		// fazer tratamento de erros
		for(Conta conta : contas.getContas()) {
			if(conta.getUsuario().getCpf().equals(rg)) {
				System.out.println(conta.getUsuario().getCpf());
				do {
					
					System.out.println();
					System.out.println("----------------------------");
					System.out.println("   Se��o de editar conta.");
					System.out.println("----------------------------");
					System.out.println();
					System.out.println("1. Modificar Despesas e Receita da conta.");
					System.out.println("2. Modificar o nome da conta.");
					System.out.println("3. Modificar o tipo da conta.");
					System.out.println("4. Modificar a institui��o financeira da conta.");
					System.out.println("5. Modificar a idade do usu�rio.");
					System.out.println("6. Modificar o rg do usu�rio.");
					System.out.println("7. Voltar para a pagina inicial.");
					
					do {
						System.out.println();
						System.out.print("Introduza o n�mero da opera��o que deseja realizar: ");
						opcao = sc.nextLine();
						
						try {
							// Passando o valor que o usuario introduzio para um inteiro
							opcaoEscolhida = Integer.parseInt(opcao);
						} catch(Exception e) {
							System.out.println("Voc� tem que introduzir um n�mero "
									+ "para que umas dessas op��es seja realizada!");
						}
						// Continua o programa caso ocorra algum erro
					} while(opcaoEscolhida < 0 || opcaoEscolhida > 7 || opcao.isEmpty());
					
					switch (opcaoEscolhida) {
						case 1: // Modifica Despesas e Receita da conta
							menuUsuario();
							break;
						case 2: // Modifica o nome da conta
							System.out.print("Digite o novo nome da conta: ");
							String novoNome = sc.nextLine();
							conta.getUsuario().setNome(novoNome);
							break;
						case 3: // Modifica o tipo da conta
							System.out.print("Digite o novo tipo da conta: ");
							TipoConta novoTipo = TipoConta.valueOf(sc.next().toUpperCase());
							conta.setTipoConta(novoTipo);
							break;
						case 4: // Modifica a institui��o financeira da conta
							System.out.print("Digite a nova institui��o financeira da conta: ");
							String novaInstituicaoFinanceira = sc.nextLine();
							conta.setInstituicaoFinanceira(novaInstituicaoFinanceira);
							break;
						case 5: // Modifica a idade do usu�rio
							System.out.print("Digite a nova idade do usuario: ");
							int novaIdade = sc.nextInt();
							conta.getUsuario().setIdade(novaIdade);
							break;
						case 6: // Modifica o rg do usu�rio
							System.out.print("Digite o novo rg do usuario: ");
							String novoRg = sc.nextLine();
							conta.getUsuario().setCPF(novoRg);
							break;
						case 7: // Volta para a pagina inicial
							paginaInicial();
							break;
						case 0: // Sai do Programa
							System.out.println("Programa finalizado\nObrigado por utilizar a aplica��o!");
							break;	
					}
					
				} while(opcaoEscolhida != 0);
			}
		}
		
	}
	/**
	 * Cria uma Conta
	 */
	private static boolean introduzirConta() {
		
		// Pedido do nome do usu�rio
		do {
			System.out.print("Introduza o nome do usu�rio: ");
			nomeUsuario = sc.nextLine().toUpperCase();
		} while(nomeUsuario.isEmpty());
		
		// Pedido da idade do usu�rio
		do {
			System.out.print("Introduza a idade do usu�rio: ");
			idadeUsuario = sc.nextLine();
			
			try {
				
				idadeUsu = Integer.parseInt(idadeUsuario);
				
			} catch(Exception e) {
				
				System.out.println("O n�mero precisa ser maior que zero para a idade");
				
			}
			
		} while(idadeUsuario.isEmpty() || idadeUsu <= 0);
		
		// Pedido do cpf do usu�rio
		do {
			System.out.print("Introduza o cpf do usu�rio: ");
			cpfUsuario = sc.nextLine();
			if(cpfUsuario.length() != 11) {
				System.out.println("O cpf deve conter 11 d�gitos.");
			}

			try {
	
				eNumero = Long.parseLong(cpfUsuario);
				
			} catch(Exception e) {
				
				System.out.println("O cpf s� pode conter n�meros");
				
			}
		} while(novoUsuario.setCPF(cpfUsuario) != true);
		
		// Pedido do tipo de conta do usu�rio
		do {
			System.out.print("Introduza o tipo de conta do usuario(carteira, corrente, poupanca): ");
			
			try {
				
				tipoConta = TipoConta.valueOf(sc.next().toUpperCase());
				sc.nextLine();
				
			} catch(Exception e) {
				
				System.out.println("O tipo de conta tem que ser um dos listados a cima.");

			}
		} while(tipoConta == null);
		
		// Pedido da institui��o financeira do usu�rio
		do {
			System.out.print("Introduza  do institui��o financeira usuario: ");
			instituicaoFinanceira = sc.nextLine();
		} while(instituicaoFinanceira == "");
	
		novoUsuario.setNome(nomeUsuario);
		novoUsuario.setIdade(idadeUsu);
		return true;
	}
	
	/**
	 * Metodo que visualiza o menu de op��es do usuario
	 */
	private static void menuUsuario() {
		do {
		
			System.out.println();
			System.out.println("-----------------------------------");
			System.out.println("   Se��o de despesas e receitas.");
			System.out.println("-----------------------------------");
			System.out.println();
			System.out.println("1. Introduzir uma nova despesa.");
			System.out.println("2. Introduzir uma nova receita.");
			System.out.println("3. Mostrar despesas.");
			System.out.println("4. Mostrar receitas.");
			System.out.println("5. Mostrar saldo da conta.");
			System.out.println("6. Voltar para a se��o de contas.");
			System.out.println("0. Sair.");
			
			do {
				System.out.println();
				System.out.print("Introduza o n�mero da opera��o que deseja realizar: ");
				opcao = sc.nextLine();
				
				try {
					// Passando o valor que o usuario introduzio para um inteiro
					opcaoEscolhida = Integer.parseInt(opcao);
				} catch(Exception e) {
					System.out.println("Voc� tem que introduzir um n�mero "
							+ "para que umas dessas op��es seja realizada!");
				}
				// Continua o programa caso ocorra algum erro
			} while(opcaoEscolhida < 0 || opcaoEscolhida > 6 || opcao.isEmpty());
			
			switch (opcaoEscolhida) {
				case 1: // Introduzi uma despesa
					introduzirDespesa();
					break;
				case 2: // Introduzi uma receita
					introduzirReceita();
					break;
				case 3: // Mostrar as Despesas
					mostrarListaDespesa();
					break;
				case 4: // Mostrar as receitas
					mostrarListaReceita();
					break;
				case 5: // Mostrar saldo da conta
					System.out.println(novaConta.toString());
					break;
				case 6: // Voltar para a pagina inicial
					paginaInicial();
					break;
				case 0: // Sair do programa
					System.out.println("Programa finalizado\nObrigado por utilizar a aplica��o!");
					break;	
			}
			
		} while(opcaoEscolhida != 0);
	}

	/**
	 * Metodo que introduz receita na conta
	 */
	private static void introduzirReceita() {
		do {
			System.out.println("Introduza a descri�ao da receita: ");
			descricaoTransferencia = sc.nextLine();
		} while(descricaoTransferencia.isEmpty());
		
		do {
			System.out.println("Introduza o custo da receita: ");
			custo = sc.nextLine();
			
			try {
				custoTransferencia = Double.parseDouble(custo);
			} catch(Exception e) {
				System.out.println("Voc� deve introduzir um valor decimal para o montante de despesa");
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
		} while(tipoDespesa == null);
		
		for(Conta conta : contas.getContas()) {
			if(conta.getUsuario().getCpf() == novaConta.getUsuario().getCpf()) {
				conta.addReceita(descricaoTransferencia, custoTransferencia, new Date(), tipoReceita);
				System.out.println("Receita registrada com sucesso.");
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
			System.out.println("Introduza a descri�ao da despesa: ");
			descricaoTransferencia = sc.nextLine();
		} while(descricaoTransferencia.isEmpty());
		
		do {
			System.out.println("Introduza o custo da despesa: ");
			custo = sc.nextLine();
			
			try {
				custoTransferencia = Double.parseDouble(custo);
			} catch(Exception e) {
				System.out.println("Voc� deve introduzir um valor decimal para o custo da despesa");
			}
			if(novaConta.getSaldo() < 0) {
				System.out.println("Voc� deve ingressar dinheiro para realizar o pagamento desta despesa");
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
		}
	}
	
	/*
	 * Metodo que mostrar� a lista de receitas
	 */
	
	private static void mostrarListaReceita() {
		for(Conta conta : contas.getContas()) {
			if(conta.getUsuario().getCpf() == novaConta.getUsuario().getCpf()) {
				for(int i = 0; i < conta.getListaReceita().size(); i++) {
					System.out.println(conta.getListaReceita().get(i));
				}
			}
		}
	}
	
	/*
	 * Metodo que mostrar� a lista de despesas
	 */
	
	private static void mostrarListaDespesa() {
		for(Conta conta : contas.getContas()) {
			if(conta.getUsuario().getCpf() == novaConta.getUsuario().getCpf()) {
				for(int i = 0; i < conta.getListaDespesa().size(); i++) {
					System.out.println(conta.getListaDespesa().get(i));
				}
			}
		}
	}
	
	public static void main(String[] args) throws SQLException {
		
		Locale.setDefault(Locale.US);
		
		Connection conexao = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexao = DriverManager.getConnection("jdbc:mysql://localhost/gestaoFinanceira", "root", "janetenuma2002");

            /* ResultSet rsCliente = conexao.createStatement().executeQuery("SELECT * FROM CLIENTE");
            while(rsCliente.next()) {
                System.out.println("Nome: " + rsCliente.getString("nome"));
            } */
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver do banco de dados n�o localizado.");
        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao conectar ao banco: " + ex.getMessage());
        } finally {
            if(conexao != null) {
                conexao.close();
            }
        }
		
		// Inicia o programa dando op��es do programa ao usuario 
		paginaInicial();
		
	}
}