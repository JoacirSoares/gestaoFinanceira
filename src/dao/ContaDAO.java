package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import conexao.Conexao;
import entities.Conta;
import entities.Despesa;
import entities.Receita;

public class ContaDAO {
	private Conexao conexao;
	private Connection conn;
	
	public ContaDAO() {
		this.conexao = new Conexao();
		this.conn = this.conexao.getConexao();
	}
	
	public void inserir(Conta conta) {
		String sql = "INSERT INTO conta(cpf, nome, idade, instituicaoFinanceira, saldo, tipoConta) VALUES " 
				+ "(?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement stmt = this.conn.prepareStatement(sql);
			stmt.setString(1, conta.getCpf());
			stmt.setString(2, conta.getNome());
			stmt.setInt(3, conta.getIdade());
			stmt.setString(4, conta.getInstituicaoFinanceira());
			stmt.setDouble(5, conta.getSaldo());
			stmt.setString(6, conta.getTipoConta().name());
			stmt.execute();
		} catch(Exception e) {
			System.out.println("Erro ao iserir conta: " + e.getMessage());
		}
	}
	
	public void atualizarNome(Conta conta) {
		String sqlUpdate = "UPDATE conta "
                + "SET nome = ? "
                + "WHERE cpf = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
        	
            // prepare data for update
            String nome = conta.getNome();
            String cpf = conta.getCpf();
            pstmt.setString(1, nome);
            pstmt.setString(2, cpf);
            
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
		
	}
	
	public void atualizarIdade(Conta conta) {
		String sqlUpdate = "UPDATE conta "
                + "SET idade = ? "
                + "WHERE cpf = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {

            // prepare data for update
            pstmt.setInt(1, conta.getIdade());
            pstmt.setString(2, conta.getCpf());
            
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
		
	}
	
	public void atualizarInstituicaoFinanceira(Conta conta) {
		String sqlUpdate = "UPDATE conta "
                + "SET instituicaoFinanceira = ? "
                + "WHERE cpf = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {

            // prepare data for update
            pstmt.setString(1, conta.getInstituicaoFinanceira());
            pstmt.setString(2, conta.getCpf());
            
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
		
	}
	
	public void atualizarTipoConta(Conta conta) {
		String sqlUpdate = "UPDATE conta "
                + "SET tipoConta = ? "
                + "WHERE cpf = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {

            // prepare data for update
            pstmt.setString(1, conta.getTipoConta().name());
            pstmt.setString(2, conta.getCpf());
            
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
		
	}
	
	public void inserirDespesa(Despesa despesa) {
		String sqlUpdate = "INSERT INTO despesas(descricao, tipoDespesa, dinheiro, dataPagamento) VALUES" 
				+ "(?, ?, ?, ?)";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {

            // prepare data for update
            pstmt.setString(1, despesa.getDescricao());
            pstmt.setString(2, despesa.getTipoDespesa().name());
            pstmt.setDouble(3, despesa.getDinheiro());
            pstmt.setDate(4, new java.sql.Date(despesa.getDataPagamento().getTime()));
            
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
	}
	
	public void inserirReceita(Receita receita) {
		String sqlUpdate = "INSERT INTO receita(descricao, tipoReceita, dinheiro, dataRecebimento) VALUES" 
				+ "(?, ?, ?, ?)";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {

            // prepare data for update
            pstmt.setString(1, receita.getDescricao());
            pstmt.setString(2, receita.getTipoReceita().name());
            pstmt.setDouble(3, receita.getDinheiro());
            pstmt.setDate(4, new java.sql.Date(receita.getDataRecebimento().getTime()));
            
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
	}
}