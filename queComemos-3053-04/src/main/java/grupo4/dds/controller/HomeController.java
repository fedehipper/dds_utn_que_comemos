package grupo4.dds.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import grupo4.dds.receta.Receta;
import grupo4.dds.repositorios.RepositorioDeRecetas;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class HomeController {

	public ModelAndView listarRecetas(Request request, Response response) {

		List<Receta> recetas = RepositorioDeRecetas.instance().listar();

		Map<String, List<Receta>> modelAndView = new HashMap<>();
		modelAndView.put("recetasFavoritas", recetas);

		return new ModelAndView(modelAndView, "home.hbs");
	}

}
