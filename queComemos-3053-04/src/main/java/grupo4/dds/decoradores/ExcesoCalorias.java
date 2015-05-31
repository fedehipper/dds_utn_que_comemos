package grupo4.dds.decoradores;

import java.util.List;
import java.util.stream.Collectors;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.Repositorio;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.usuario.Usuario;

public class ExcesoCalorias extends Filtro {
		
	public ExcesoCalorias(RepositorioDeRecetas repo){
		repositorio = repo;
	}
	
	public ExcesoCalorias(Repositorio repo){
		repositorio = repo;
	}
	
	public List<Receta> listarRecetasParaUnUsuario(Usuario unUsuario) {		
	 	  return this.repositorio.listarRecetasParaUnUsuario(unUsuario).stream().filter(r -> r.getTotalCalorias() > 500 && unUsuario.indiceDeMasaCorporal() > 25).collect(Collectors.toList());
	}
	

	
}
