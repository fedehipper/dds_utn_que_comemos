package grupo4.dds.decoradores;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.Repositorio;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.usuario.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public class DiezPrimeros extends ProcesamientoPosterior {
	
	public DiezPrimeros(Repositorio repo) {
		repositorio = repo;
	}
	
	public DiezPrimeros(RepositorioDeRecetas repo) {
		repositorio = repo;
	}
	

	public List<Receta> listarRecetasParaUnUsuario(Usuario unUsuario) {		
	 	  return this.repositorio.listarRecetasParaUnUsuario(unUsuario).stream().limit(10).collect(Collectors.toList());
	}
	
}
