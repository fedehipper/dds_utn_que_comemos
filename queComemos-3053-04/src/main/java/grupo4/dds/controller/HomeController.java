package grupo4.dds.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import grupo4.dds.main.Routes;
import grupo4.dds.monitores.RecetasMasConsultadas;
import grupo4.dds.receta.Receta;
import grupo4.dds.repositorios.RepositorioDeUsuarios;
import grupo4.dds.usuario.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class HomeController {

	public ModelAndView listarRecetas(Request request, Response response) {

		Map<String, List<Receta>> modelAndView = new HashMap<>();
		modelAndView.put("recetas", recetasAMostrar());

		return new ModelAndView(modelAndView, "home.hbs");
	}
	
	public List<Receta> recetasAMostrar() {

		long usuarioId = Routes.usuarioActual.getId();
		Usuario usuario = RepositorioDeUsuarios.instance().buscar(usuarioId);
		List<Receta> recetasAMosrar = new ArrayList<Receta>(usuario.getFavoritas());
		
		if(Objects.isNull(recetasAMosrar) || recetasAMosrar.isEmpty())
			recetasAMosrar = usuario.getHistorial();
		if(Objects.isNull(recetasAMosrar) || recetasAMosrar.isEmpty())
			recetasAMosrar = new RecetasMasConsultadas().recetasMasConsultadas(10);
		
		return recetasAMosrar.subList(0, Math.min(10, recetasAMosrar.size()));
	}

}
