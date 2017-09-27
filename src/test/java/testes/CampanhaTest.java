package testes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import bean.Campanha;
import business.CampanhaBusiness;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CampanhaTest {
	
	@Test
	public void testA_deveApagarTodasAsCampanhas() {
		
		//lista a ser populada com todas as campanhas cadastradas
		List<Campanha> campanhas = new LinkedList<>();
		
		//apagar todas as campanhas cadastradas
		CampanhaBusiness campanhaBusiness = new CampanhaBusiness();
		campanhaBusiness.deletarCampanhas();
		
		//popular lista com todas as campanhas cadastradas
		campanhas = campanhaBusiness.getCampanhas();
		
		//se lista estiver vazia, todas as campanhas foram apagadas
		Assert.assertTrue(campanhas.isEmpty());
	}

	@Test
	public void testB_deveCadastrarCampanha() throws ParseException {
		// date format
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date dt;

		// criar nova campanha
		Campanha campanha = new Campanha();
		campanha.setNome("Campanha");
		campanha.setIdTime(19);
		String dataInicioVigencia = "01/09/2017";
		dt = sdf.parse(dataInicioVigencia);
		campanha.setDataInicioVigencia(new java.sql.Date(dt.getTime()));
		String dataFimVigencia = "30/09/2017";
		dt = sdf.parse(dataFimVigencia);
		campanha.setDataFimVigencia(new java.sql.Date(dt.getTime()));

		// cadastrar campanha
		CampanhaBusiness campanhaBusiness = new CampanhaBusiness();
		String msgRetornada = campanhaBusiness.cadastrarCampanha(campanha);

		// se for retornado um id diferente de -1, a mesma foi cadastrada com
		// sucesso
		String msgEsperada = "cadastrado";
		Assert.assertEquals(msgEsperada, msgRetornada);

	}

	@Test
	public void testC_simulaCadastroTresCampanhas() throws ParseException {
		// date format
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date dt;

		Random gerarIdTime = new Random();
		String dataInicioVigencia;
		String dataFimVigencia;

		// criar campanha 1
		Campanha campanha1 = new Campanha();
		campanha1.setNome("Campanha 1");
		campanha1.setIdTime(gerarIdTime.nextInt(20));
		dataInicioVigencia = "01/10/2017";
		dt = sdf.parse(dataInicioVigencia);
		campanha1.setDataInicioVigencia(new java.sql.Date(dt.getTime()));
		dataFimVigencia = "03/10/2017";
		dt = sdf.parse(dataFimVigencia);
		campanha1.setDataFimVigencia(new java.sql.Date(dt.getTime()));

		// criar campanha 2
		Campanha campanha2 = new Campanha();
		campanha2.setNome("Campanha 2");
		campanha2.setIdTime(gerarIdTime.nextInt(20));
		dataInicioVigencia = "01/10/2017";
		dt = sdf.parse(dataInicioVigencia);
		campanha2.setDataInicioVigencia(new java.sql.Date(dt.getTime()));
		dataFimVigencia = "02/10/2017";
		dt = sdf.parse(dataFimVigencia);
		campanha2.setDataFimVigencia(new java.sql.Date(dt.getTime()));

		// criar campanha 3
		Campanha campanha3 = new Campanha();
		campanha3.setNome("Campanha 3");
		campanha3.setIdTime(gerarIdTime.nextInt(20));
		dataInicioVigencia = "01/10/2017";
		dt = sdf.parse(dataInicioVigencia);
		campanha3.setDataInicioVigencia(new java.sql.Date(dt.getTime()));
		dataFimVigencia = "03/10/2017";
		dt = sdf.parse(dataFimVigencia);
		campanha3.setDataFimVigencia(new java.sql.Date(dt.getTime()));

		// resultado esperado x obtido
		CampanhaBusiness campanhaBusiness = new CampanhaBusiness();
		String msgRetornada = null;
		String msgEsperada = "cadastrado";

		// cadastrar campanha 1
		msgRetornada = campanhaBusiness.cadastrarCampanha(campanha1);

		// se for retornada a msg "cadastrado", a campanha foi cadastrada com sucesso
		Assert.assertEquals(msgEsperada, msgRetornada);

		// cadastrar campanha 2
		msgRetornada = campanhaBusiness.cadastrarCampanha(campanha2);

		// se for retornada a msg "cadastrado", a campanha foi cadastrada com sucesso
		Assert.assertEquals(msgEsperada, msgRetornada);

		// cadastrar campanha 3
		msgRetornada = campanhaBusiness.cadastrarCampanha(campanha3);

		// se for retornada a msg "cadastrado", a campanha foi cadastrada com sucesso
		Assert.assertEquals(msgEsperada, msgRetornada);

	}
	
	@Test
	public void testD_deveApagarUmaCampanhaPeloId() {
		
		CampanhaBusiness campanhaBusiness = new CampanhaBusiness();
		
		//pegar todas as campanhas cadastradas
		List<Campanha> campanhas = new LinkedList<>();
		campanhas = campanhaBusiness.getCampanhas();
		
		//apagar uma campanha pelo id
		campanhaBusiness.deletarCampanhaById(campanhas.get(0).getId());
		
		//verificar se campanha foi deletada
		Campanha campanha = campanhaBusiness.getCampanhaById(campanhas.get(0).getId());		
		
		//se lista estiver vazia, todas as campanhas foram apagadas
		Assert.assertNull(campanha);
	}

	@Test
	public void testE_naoPodemHaverCampanhasComMesmaDataFimVigencia() {

		// lista a ser populada com as campanahs com mesma tada fim de vigencia
		List<Campanha> campanhas = new LinkedList<>();

		// verificar se existem campanhas com mesma data fim de vigencia
		CampanhaBusiness campanhaBusiness = new CampanhaBusiness();
		campanhas = campanhaBusiness.getCampanhasComMesmaDataFim();

		// se lista retornada estiver vazia, não existem campanhas com mesma data fim
		Assert.assertTrue(campanhas.isEmpty());

	}

}
