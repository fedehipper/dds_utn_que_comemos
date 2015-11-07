package grupo4.dds.controller;


import grupo4.dds.receta.Receta;
import grupo4.dds.repositorios.RepositorioDeRecetas;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;






import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ConsultasController {
	
	 public ModelAndView listar(Request request, Response response) {
		    List<Receta> recetas;
		    
		    String filtroNombre = request.params("filtroNombre");
		    if (Objects.isNull(filtroNombre) || filtroNombre.isEmpty()) {
		      recetas = RepositorioDeRecetas.instance().listar();
		    } else {
		      recetas = RepositorioDeRecetas.instance().buscarPorNombre(filtroNombre);
		    }

		    HashMap<String, Object> viewModel = new HashMap<>();
		    viewModel.put("consultas", recetas);
		    viewModel.put("filtroNombre", filtroNombre);
		    
		    response.redirect("/consultas");
		    
		    
		    return new ModelAndView(viewModel, "consultas.hbs");
		  }
	 


}
