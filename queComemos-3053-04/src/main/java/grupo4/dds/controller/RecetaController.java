package grupo4.dds.controller;

import java.util.HashMap;
import java.util.Objects;

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
		long usuarioId = Routes.usuarioActual.getId();
		Usuario usuario = RepositorioDeUsuarios.instance().buscar(usuarioId);
		
        HashMap<String, Object> viewModel = new HashMap<>();
		
		viewModel.put("nombreDelPlato", receta.getNombreDelPlato());
		if(!Objects.isNull(receta.getCreador()))
		viewModel.put("creador", receta.getCreador());
		else
			viewModel.put("creador", "Nadie");
		viewModel.put("cantidadCalorias", receta.getTotalCalorias());
		viewModel.put("dificultad", receta.getDificultad());
		viewModel.put("ingredientes", receta.getIngredientes());
		viewModel.put("temporada", receta.getTemporada());
		viewModel.put("condimentos", receta.getCondimentos());
		viewModel.put("preparacion", receta.getPreparacion());
		viewModel.put("marcaFavorita", usuario.getMarcaFavorita());
		viewModel.put("condiciones",usuario.getCondiciones());
		
		
		viewModel.put("favorita", usuario.getHistorial().contains(receta));

		return new ModelAndView(viewModel, "receta.hbs");
	}
	
	public Receta recetaMostrada(long id){
		return RepositorioDeRecetas.instance().buscar(id);
	}

}
