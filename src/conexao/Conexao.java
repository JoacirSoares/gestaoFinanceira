package conexao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

	public Connection getConexao() {
		try {
			// tenta estabelecer a conexão
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/gestaoFinanceira",
					"root",
					"" // Adicione a senha do seu MySQL
			);
			return conn;
		} catch(Exception e) {
			// Se deu erro na hora de conectar
			System.out.println("Erro ao conectar" + e.getMessage());
			return null;
		}
	}
	
}
