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
public class CadastrarCemCampanhasTest {
	
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
	public void testB_deveCadastrarCemCampanhas() throws ParseException {
		// date format
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date dt;
		
		Random gerarIdTime = new Random();
		Integer dia;
		
		CampanhaBusiness campanhaBusiness = new CampanhaBusiness();
		String msgEsperada = "cadastrado";

		// criar nova campanha
		for (int i = 1; i <= 100; i++) {
			Campanha campanha = new Campanha();
			campanha.setNome("Campanha "+i);
			campanha.setIdTime(gerarIdTime.nextInt(20)+1);
			String dataInicioVigencia = "01/10/2017";
			dt = sdf.parse(dataInicioVigencia);
			campanha.setDataInicioVigencia(new java.sql.Date(dt.getTime()));
			dia = gerarIdTime.nextInt(31)+1;
			String dataFimVigencia = String.format("%02d", dia)+"/10/2017";
			dt = sdf.parse(dataFimVigencia);
			campanha.setDataFimVigencia(new java.sql.Date(dt.getTime()));	
			String msgRetornada = campanhaBusiness.cadastrarCampanha(campanha);
			Assert.assertEquals(msgEsperada, msgRetornada);
		}
	}
	
	@Test
	public void testC_naoPodemHaverCampanhasComMesmaDataFimVigencia() {

		// lista a ser populada com as campanahs com mesma tada fim de vigencia
		List<Campanha> campanhas = new LinkedList<>();

		// verificar se existem campanhas com mesma data fim de vigencia
		CampanhaBusiness campanhaBusiness = new CampanhaBusiness();
		campanhas = campanhaBusiness.getCampanhasComMesmaDataFim();

		// se lista retornada estiver vazia, não existem campanhas com mesma data fim
		Assert.assertTrue(campanhas.isEmpty());

	}

}
