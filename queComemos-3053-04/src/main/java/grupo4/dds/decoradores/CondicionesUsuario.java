package grupo4.dds.decoradores;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.Repositorio;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CondicionesUsuario implements Repositorio {
	
	private List<Receta> recetas = new ArrayList<>();
	private Repositorio repositorio;
	
	public CondicionesUsuario(RepositorioDeRecetas repo){
		repositorio = repo;
	}
	
	public CondicionesUsuario(Repositorio repo){
		repositorio = repo;
	}
	
	public void agregarReceta(Receta unaReceta) {
		this.recetas.add(unaReceta);
	}
	
	public void quitarReceta(Receta unaReceta) {
		this.recetas.remove(unaReceta);
	}
	
	public List<Receta> getRecetas() {
		return this.recetas;
	}
	
	public List<Receta> listarRecetasParaUnUsuario(Usuario unUsuario) {		
	 	  return this.repositorio.listarRecetasParaUnUsuario(unUsuario).stream().filter(r -> unUsuario.esAdecuada(r)).collect(Collectors.toList());
	}
	
	
}
