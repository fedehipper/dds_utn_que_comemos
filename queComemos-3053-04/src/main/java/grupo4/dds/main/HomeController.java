package grupo4.dds.main;

import java.util.HashMap;
import java.util.List;

import grupo4.dds.receta.Receta;
import grupo4.dds.repositorios.RepositorioDeRecetas;
import spark.ModelAndView;
import spark.Request;
import spark.Response;


public class HomeController {
	
	 public ModelAndView mostrar(Request request, Response response) {
		 Receta receta = RepositorioDeRecetas.instance().buscar(Long.parseLong(request.params("id")));
		 return new ModelAndView(receta, "bienvenidos.hbs");
	 }
	 
	 public ModelAndView listar(Request request, Response response) {
		    List<Receta> recetas;
		    
		    String idReceta = request.params("idReceta");
		
	    	recetas = RepositorioDeRecetas.instance().listar();
		   	
		    HashMap<String, Object> viewModel = new HashMap<>();
		    viewModel.put("idReceta", idReceta);
		    viewModel.put("recetas", recetas); 
		    
		    return new ModelAndView(viewModel, "bienvenidos.hbs");
	 }
	
}
