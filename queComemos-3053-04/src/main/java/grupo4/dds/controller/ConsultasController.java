package grupo4.dds.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import grupo4.dds.main.Routes;
import grupo4.dds.receta.Receta;
import grupo4.dds.repositorios.RepositorioDeRecetas;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ConsultasController {

	public ModelAndView listar(Request request, Response response) {
		
		
		String filtroNombre = request.queryParams("filtroNombre");
		String filtroDificultad = request.queryParams("filtroDificultad");
		String filtroTemporada = request.queryParams("filtroTemporada");
		String filtroCaloriasDesde = (request.queryParams("filtroCaloriasDesde"));
		String filtroCaloriasHasta = (request.queryParams("filtroCaloriasHasta"));

		HashMap<String, Object> viewModel = new HashMap<>();
		
		viewModel.put("consultas", recetasAMostrar(filtroNombre));
		viewModel.put("filtroNombre", filtroNombre);
		viewModel.put("filtroDificultad", filtroDificultad);
		viewModel.put("filtroTemporada", filtroTemporada);
		viewModel.put("filtroCaloriasDesde", filtroCaloriasDesde);
		viewModel.put("filtroCaloriasHasta", filtroCaloriasHasta);

		return new ModelAndView(viewModel, "consultas.hbs");
	}
	
	public ModelAndView mostrar(Request request, Response response){
		return new ModelAndView(null, "consultas.hbs");
		
	}
	
	public List<Receta> recetasAMostrar(String filtroNombre){
		
		List<Receta> recetas;
		
		if (Objects.isNull(filtroNombre) || filtroNombre.isEmpty())
			recetas = RepositorioDeRecetas.instance().listar();
		else 
			recetas = RepositorioDeRecetas.instance().buscar(filtroNombre);
	    
		return recetas.stream().filter(r-> r.puedeSerVistaPor(Routes.usuarioActual)).collect(Collectors.toList());
	}

}
