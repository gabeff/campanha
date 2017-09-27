package business;

import java.util.LinkedList;
import java.util.List;

import bean.Campanha;
import dao.CampanhaDAO;

public class CampanhaBusiness {

	public String cadastrarCampanha(Campanha campanha) {
		
		CampanhaDAO campanhaDAO = new CampanhaDAO();
		
		//variavel de retorno
		String retorno;		
		
		//data fim da vigencia nao pode ser anterior a data de inicio da mesma
		if (campanha.getDataFimVigencia().before(campanha.getDataInicioVigencia())) {
			retorno = "Fim da vigencia anterior ao inicio da vigencia!";
		} else {
			//caso não haja exceções cadastra a campanha
			retorno = campanhaDAO.cadastrarCampanha(campanha);
		}
		
		//retorno do metodo
		return retorno;
	}

	public List<Campanha> getCampanhasComMesmaDataFim() {
		
		CampanhaDAO campanhaDAO = new CampanhaDAO();
		
		//lista campanhas que recebera as campanha com a mesma data e sera retornada pelo metodo
		List<Campanha> campanhas = new LinkedList<>();
		
		//popula lista campanhas com a mesma data fim vigencia
		campanhas = campanhaDAO.getCampanhasComMesmaDataFim();
		
		//retorna lista de campanhas com a mesma data fim vigencia
		return campanhas;
	}

	public void deletarCampanhas() {
		CampanhaDAO campanhaDAO = new CampanhaDAO();
		
		//deletar todas as campanhas cadastradas
		campanhaDAO.deletarCampanhas();		
	}

	public List<Campanha> getCampanhas() {
		CampanhaDAO campanhaDAO = new CampanhaDAO();
		
		//lista campanhas que recebera todas as campanhas cadastradas
		List<Campanha> campanhas = new LinkedList<>();
		
		//popula lista campanhas
		campanhas = campanhaDAO.getCampanhas();
		
		//retorna lista de campanhas cadastradas
		return campanhas;
	}
	
	public List<Campanha> getCampanhasAtivas() {
		CampanhaDAO campanhaDAO = new CampanhaDAO();
		
		//lista campanhas que recebera todas as campanhas ativas
		List<Campanha> campanhas = new LinkedList<>();
		
		//popula lista campanhas
		campanhas = campanhaDAO.getCampanhasAtivas();
		
		//retorna lista de campanhas ativas
		return campanhas;
	}
	
	public List<Campanha> getCampanhasAtivasTimeCoracao(Integer idCliente) {
		CampanhaDAO campanhaDAO = new CampanhaDAO();
		
		//lista campanhas que recebera todas as campanhas ativas do time do coracao
		List<Campanha> campanhas = new LinkedList<>();
		
		//popula lista campanhas
		campanhas = campanhaDAO.getCampanhasAtivasTimeCoracao(idCliente);
		
		//retorna lista de campanhas ativas do time do coracao
		return campanhas;
	}

	public void deletarCampanhaById(Integer id) {		
		CampanhaDAO campanhaDAO = new CampanhaDAO();
		
		//deletar uma campanha pelo id
		campanhaDAO.deletarCampanhaById(id);		
	}

	public Campanha getCampanhaById(Integer id) {
		CampanhaDAO campanhaDAO = new CampanhaDAO();
		Campanha campanha = new Campanha();
		
		//selecionar campanha pelo id
		campanha = campanhaDAO.getCampanhaById(id);
		
		//retorna campanha do id desejado
		return campanha;
	}
	
	public void alterarCampanhaById(Campanha campanha) {		
		CampanhaDAO campanhaDAO = new CampanhaDAO();
		
		//deletar uma campanha pelo id
		campanhaDAO.alterarCampanhaById(campanha);		
	}

}
