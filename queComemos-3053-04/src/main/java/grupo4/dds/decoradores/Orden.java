package grupo4.dds.decoradores;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.Repositorio;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.usuario.Usuario;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Orden extends ProcesamientoPosterior {
	
	private Comparator<Receta> criterio;
	
	public Orden(RepositorioDeRecetas repo) {
		repositorio = repo;
	}
	
	public Orden(Repositorio repo) {
		repositorio = repo;
	}
	
	public void setCriterio(Comparator<Receta> unCriterio) {
		criterio = unCriterio;
	}
	
	public List<Receta> listarRecetasParaUnUsuario(Usuario unUsuario) {		
		return  this.ordenarPor(criterio, this.repositorio.listarRecetasParaUnUsuario(unUsuario));
	}
	
	public List<Receta> ordenarPor(Comparator<Receta> unCriterio, List<Receta> unaReceta){
		Collections.sort(unaReceta, criterio);
		return unaReceta;
	}
	
}
