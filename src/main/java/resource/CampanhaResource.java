package resource;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import bean.Campanha;
import business.CampanhaBusiness;

@Path("/")
public class CampanhaResource {

	private CampanhaBusiness campanhaBusiness;

	@GET
	@Path("/getCampanhasDisponiveis/{idCliente}")
	@Produces("application/json")
	public Response getCampanhasDisponiveisCliente(@PathParam("idCliente") final Integer idCliente) {
		// Lista a ser populada com as campanhas disponiveis para o cliente
		List<Campanha> campanhas = new LinkedList<>();

		// Pegar campanhas disponiveis
		campanhaBusiness = new CampanhaBusiness();
		campanhas = campanhaBusiness.getCampanhasAtivasTimeCoracao(idCliente);

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

		try {
			if (!campanhas.isEmpty()) {
				return Response.ok(gson.toJson(campanhas)).build();
			} else {
				return Response.ok(gson.toJson("Não há campanhas disponíveis para o cliente :(")).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.NO_CONTENT).build();
		}

	}

}
