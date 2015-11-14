package grupo4.dds.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import grupo4.dds.receta.Receta;
import grupo4.dds.repositorios.RepositorioDeRecetas;
import grupo4.dds.repositorios.RepositorioDeUsuarios;
import grupo4.dds.usuario.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ConsultasController {

	public ModelAndView listar(Request request, Response response) {
		
		List<Receta> recetas;
		String filtroNombre = request.queryParams("filtroNombre");
		//Usuario usuario = RepositorioDeUsuarios.instance().buscar(Long.parseLong(request.params("id")));
		
		/*if (Objects.isNull(usuario))
			recetas = RepositorioDeRecetas.instance().listar();
		else
			recetas = RepositorioDeRecetas.instance().listarRecetasPara(usuario, null, null);*/
		
		if (Objects.isNull(filtroNombre) || filtroNombre.isEmpty())
			recetas = RepositorioDeRecetas.instance().listar();
		else 
			recetas = RepositorioDeRecetas.instance().buscar(filtroNombre);

		HashMap<String, Object> viewModel = new HashMap<>();
		
		viewModel.put("consultas", recetas);
		viewModel.put("filtroNombre", filtroNombre);

		return new ModelAndView(viewModel, "consultas.hbs");
	}
	
	public ModelAndView mostrar(Request request, Response response){
		return new ModelAndView(null, "consultas.hbs");
		
	}

}
