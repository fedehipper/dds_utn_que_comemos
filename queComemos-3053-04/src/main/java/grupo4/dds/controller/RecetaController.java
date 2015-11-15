package grupo4.dds.controller;

import java.util.HashMap;

import grupo4.dds.main.Routes;
import grupo4.dds.receta.Receta;
import grupo4.dds.repositorios.RepositorioDeRecetas;
import grupo4.dds.repositorios.RepositorioDeUsuarios;
import grupo4.dds.usuario.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class RecetaController {
	
	public ModelAndView mostrar(Request request, Response response){
		
		Receta receta = RepositorioDeRecetas.instance().buscar(Long.parseLong(request
		        .params("id")));
		Usuario usuario = Routes.usuarioActual;
		
        HashMap<String, Object> viewModel = new HashMap<>();
		
		viewModel.put("nombreDelPlato", receta.getNombreDelPlato());
		viewModel.put("creador", receta.getCreador());
		viewModel.put("cantidadCalorias", receta.getTotalCalorias());
		viewModel.put("dificultad", receta.getDificultad());
		viewModel.put("ingredientes", receta.getIngredientes());
		viewModel.put("temporada", receta.getTemporada());
		viewModel.put("condimentos", receta.getCondimentos());
		viewModel.put("preparacion", receta.getPreparacion());
		viewModel.put("marcaFavorita", usuario.getMarcaFavorita());
		viewModel.put("condiciones",usuario.getCondiciones());
		
		
		return new ModelAndView(viewModel, "receta.hbs");
	}
	
	public Receta recetaMostrada(long id){
		return RepositorioDeRecetas.instance().buscar(id);
	}

}
