package dao;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import bean.Campanha;

public class CampanhaDAO extends Utils {

	public String cadastrarCampanha(Campanha campanha) {
		// variavel de retorno
		String retorno = null;

		// conexao com o banco
		Connection conn = null;
		
		// acrescentar um dia as campanhas dentro do periodo da nova campanha
		addDiaAsCampanhasDoPeriodo(campanha);

		// realizar cadastro no banco
		try {
			// pegar conexao
			conn = getConnection();

			// script insert sql
			String sql = "INSERT INTO teste.campanha(nome, id_time, data_ini_vigencia, data_fim_vigencia) "
					+ "VALUES (?, ?, ?, ?);";
			PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);

			// atribuir variaveis
			pstmt.setString(1, campanha.getNome());
			pstmt.setInt(2, campanha.getIdTime());
			pstmt.setDate(3, campanha.getDataInicioVigencia());
			pstmt.setDate(4, campanha.getDataFimVigencia());

			// executa insert sql
			pstmt.execute();
			pstmt.close();

			// atribui cadastrado a variavel de retorno em caso de sucesso
			retorno = "cadastrado";

		} catch (IOException e) {
			retorno = "Desculpe, houve algum problema no cadastro, favor tentar novamente.";
			e.printStackTrace();
		} catch (SQLException e) {
			retorno = "Desculpe, houve algum problema no cadastro, favor tentar novamente.";
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			retorno = "Desculpe, houve algum problema no cadastro, favor tentar novamente.";
			e.printStackTrace();
		} finally {
			try {
				// fechar conexao
				conn.close();
			} catch (SQLException e) {
				retorno = "Desculpe, houve algum problema no cadastro, favor tentar novamente.";
				e.printStackTrace();
			}
		}
		
		// altera as campanhas que possuam a mesma data fim vigencia
		if (!getCampanhasComMesmaDataFim().isEmpty()) {
			alteraCampanhasComMesmaDataFimVigencia(campanha);
		}

		// retorno da funcao
		return retorno;
	}

	public List<Campanha> getCampanhasComMesmaDataFim() {

		// lista de campanhas a ser populada com as campanhas de mesma data fim
		// vigencia
		List<Campanha> campanhas = new LinkedList<>();
		Campanha campanha;

		// conexao com o banco
		Connection conn = null;

		try {
			// pegar conexao
			conn = getConnection();

			// script sql para selecionar campanhas com mesma data fim vigencia
			String sql = "select * from teste.campanha "
					+ "where data_fim_vigencia in (SELECT data_fim_vigencia "
					+ "FROM teste.campanha "
					+ "GROUP BY data_fim_vigencia "
					+ "having count(id) > 1) "
					+ "order by id;";
			
			// criar statement e resultset para manipulacao dos dados retornados pela query
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			// adicionar campanhas a lista
			while (rs.next()) {
				campanha = new Campanha();
				campanha.setId(rs.getInt("id"));
				campanha.setNome(rs.getString("nome"));
				campanha.setIdTime(rs.getInt("id_time"));
				campanha.setDataInicioVigencia(rs.getDate("data_ini_vigencia"));
				campanha.setDataFimVigencia(rs.getDate("data_fim_vigencia"));
				campanhas.add(campanha);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			try {
				// fechar conexao
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// retornar lista com as campanhas
		return campanhas;
	}
	
	private void addDiaAsCampanhasDoPeriodo(Campanha campanha) {
		// conexao com o banco
		Connection conn = null;

		// acrescentar um dia as campanhas dentro do periodo da nova campanha
		try {
			// pegar conexao
			conn = getConnection();

			// script update para add um dia a data fim vigencia
			String sql = "UPDATE teste.campanha SET data_fim_vigencia = data_fim_vigencia + 1 "
					+ "WHERE data_ini_vigencia >= ? "
					+ "AND data_fim_vigencia <= ? ;";
			PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);

			// atribuir variaveis
			pstmt.setDate(1, campanha.getDataInicioVigencia());
			pstmt.setDate(2, campanha.getDataFimVigencia());

			// executa update sql
			pstmt.execute();
			pstmt.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			try {
				// fechar conexao
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void alteraCampanhasComMesmaDataFimVigencia(Campanha campanha) {
		// conexao com o banco
		Connection conn = null;

		// altera as campanhas que possuam a mesma data fim vigencia
		try {
			// pegar conexao
			conn = getConnection();

			// script para realizar a alteracao
			String sql = "DO $$DECLARE "
							+ "data_aux      date; "
							+ "id_aux         int; "
							+ "qtde_camp      int; "
							+ "r_campanhas record; "
							+ "soma_dias      int; "
						+ "BEGIN "
						+ "FOR r_campanhas IN (select * "
						+ "                        from teste.campanha "
						+ "                       where data_fim_vigencia in "
						+ "                             (SELECT data_fim_vigencia "
						+ "                                FROM teste.campanha "
						+ "                               GROUP BY data_fim_vigencia "
						+ "                              having count(id) > 1) "
						+ "                       order by id) LOOP "
						+ "    qtde_camp := 1; "
						+ "    soma_dias  := 1; "
						+ "    loop "
						+ "      select count(id) "
						+ "        into qtde_camp "
						+ "        from teste.campanha "
						+ "       where data_fim_vigencia = r_campanhas.data_fim_vigencia + soma_dias; "
						+ "      if qtde_camp = 0 then "
						+ "        exit; "
						+ "      end if; "
						+ "      soma_dias := soma_dias + 1; "
						+ "    end loop;   "
						+ "    update teste.campanha "
						+ "       set data_fim_vigencia = data_fim_vigencia + soma_dias "
						+ "     where id = r_campanhas.id; "
						+ "     data_aux := r_campanhas.data_fim_vigencia; "
						+ "     id_aux := r_campanhas.id; "
						+ "  END LOOP; "
						+ "  update teste.campanha "
						+ "     set data_fim_vigencia = data_aux "
						+ "   where id = id_aux; "
						+ "END$$;";
			PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);

			// executa update sql
			pstmt.execute();
			pstmt.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			try {
				// fechar conexao
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void deletarCampanhas() {
		// conexao com o banco
		Connection conn = null;

		// deletar todas as campanhas cadastradas
		try {
			// pegar conexao
			conn = getConnection();

			// script delete para apagar as campanhas
			String sql = "DELETE FROM teste.campanha;";
			PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);

			// executa update sql
			pstmt.execute();
			pstmt.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			try {
				// fechar conexao
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Campanha> getCampanhas() {
		// lista de campanhas a ser populada com as campanhas cadastradas
		List<Campanha> campanhas = new LinkedList<>();
		Campanha campanha;

		// conexao com o banco
		Connection conn = null;

		try {
			// pegar conexao
			conn = getConnection();

			// script sql para selecionar todas as campanhas
			String sql = "select * from teste.campanha order by id;";
			
			// criar statement e resultset para manipulacao dos dados retornados pela query
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			// adicionar campanhas a lista
			while (rs.next()) {
				campanha = new Campanha();
				campanha.setId(rs.getInt("id"));
				campanha.setNome(rs.getString("nome"));
				campanha.setIdTime(rs.getInt("id_time"));
				campanha.setDataInicioVigencia(rs.getDate("data_ini_vigencia"));
				campanha.setDataFimVigencia(rs.getDate("data_fim_vigencia"));
				campanhas.add(campanha);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			try {
				// fechar conexao
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// retornar lista com as campanhas
		return campanhas;
	}
	
	public List<Campanha> getCampanhasAtivas() {
		// lista de campanhas a ser populada com as campanhas ativas
		List<Campanha> campanhas = new LinkedList<>();
		Campanha campanha;

		// conexao com o banco
		Connection conn = null;

		try {
			// pegar conexao
			conn = getConnection();

			// script sql para selecionar todas as campanhas
			String sql = "select * from teste.campanha "
					+ "where data_fim_vigencia >= current_date "
					+ "order by id;";
			
			// criar statement e resultset para manipulacao dos dados retornados pela query
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			// adicionar campanhas a lista
			while (rs.next()) {
				campanha = new Campanha();
				campanha.setId(rs.getInt("id"));
				campanha.setNome(rs.getString("nome"));
				campanha.setIdTime(rs.getInt("id_time"));
				campanha.setDataInicioVigencia(rs.getDate("data_ini_vigencia"));
				campanha.setDataFimVigencia(rs.getDate("data_fim_vigencia"));
				campanhas.add(campanha);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			try {
				// fechar conexao
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// retornar lista com as campanhas
		return campanhas;
	}
	
	public List<Campanha> getCampanhasAtivasTimeCoracao(Integer idCliente) {
		// lista de campanhas a ser populada com as campanhas ativas
		List<Campanha> campanhas = new LinkedList<>();
		Campanha campanha;

		// conexao com o banco
		Connection conn = null;

		try {
			// pegar conexao
			conn = getConnection();

			// script sql para selecionar todas as campanhas
			String sql = "select cam.* from teste.campanha cam, teste.cliente cli "
					+ "where cam.data_fim_vigencia >= current_date "
					+ "and cam.id_time = cli.id_time "
					+ "and cli.id = " + idCliente + " "
					+ "and not exists "
					+ "(select * from teste.cliente_campanhas "
					+ "where id_campanha = cam.id "
					+ "and id_cliente = cli.id) "
					+ "order by cam.id;";
			
			// criar statement e resultset para manipulacao dos dados retornados pela query
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			// adicionar campanhas a lista
			while (rs.next()) {
				campanha = new Campanha();
				campanha.setId(rs.getInt("id"));
				campanha.setNome(rs.getString("nome"));
				campanha.setIdTime(rs.getInt("id_time"));
				campanha.setDataInicioVigencia(rs.getDate("data_ini_vigencia"));
				campanha.setDataFimVigencia(rs.getDate("data_fim_vigencia"));
				campanhas.add(campanha);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			try {
				// fechar conexao
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// retornar lista com as campanhas
		return campanhas;
	}

	public void deletarCampanhaById(Integer id) {
		// conexao com o banco
		Connection conn = null;

		// deletar campanha pelo id
		try {
			// pegar conexao
			conn = getConnection();

			// script delete para apagar a campanha desejada
			String sql = "DELETE FROM teste.campanha where id = ?;";
			PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);
			
			// atribui parametro id a ser deletado
			pstmt.setInt(1, id);

			// executa update sql
			pstmt.execute();
			pstmt.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			try {
				// fechar conexao
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Campanha getCampanhaById(Integer id) {
		// campanha a ser retornada
		Campanha campanha = null;

		// conexao com o banco
		Connection conn = null;

		try {
			// pegar conexao
			conn = getConnection();

			// script sql para selecionar a campanha do id desejado
			String sql = "select * from teste.campanha where id = "+ id +";";
			
			// criar statement e resultset para manipulacao dos dados retornados pela query
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			// atribuir dados a campanha
			while (rs.next()) {
				campanha = new Campanha();
				campanha.setId(rs.getInt("id"));
				campanha.setNome(rs.getString("nome"));
				campanha.setIdTime(rs.getInt("id_time"));
				campanha.setDataInicioVigencia(rs.getDate("data_ini_vigencia"));
				campanha.setDataFimVigencia(rs.getDate("data_fim_vigencia"));
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			try {
				// fechar conexao
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// retornar lista com as campanhas
		return campanha;
	}

	public void alterarCampanhaById(Campanha campanha) {
		// conexao com o banco
		Connection conn = null;
		
		try {
			// pegar conexao
			conn = getConnection();

			// script update campanha
			String sql = "UPDATE teste.campanha "
					+ "SET data_fim_vigencia = coalesce(?,data_fim_vigencia), "
					+ "data_inicio_vigencia = coalesce(?,data_inicio_vigencia), "
					+ "nome = coalesce(?,nome), id_time = coalesce(?,id_time) "
					+ "WHERE id = ? ;";
			PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);

			// atribuir variaveis
			pstmt.setDate(1, campanha.getDataFimVigencia());
			pstmt.setDate(2, campanha.getDataInicioVigencia());
			pstmt.setString(3, campanha.getNome());
			pstmt.setInt(4, campanha.getIdTime());
			pstmt.setInt(5, campanha.getId());

			// executa update sql
			pstmt.execute();
			pstmt.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			try {
				// fechar conexao
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
