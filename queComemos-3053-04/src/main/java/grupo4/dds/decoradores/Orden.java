package grupo4.dds.decoradores;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.Repositorio;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.usuario.Usuario;

import java.util.List;

public class Orden extends ProcesamientoPosterior {
	
	private CriterioOrden criterio;
	
	public Orden(RepositorioDeRecetas repo) {
		repositorio = repo;
	}
	
	public Orden(Repositorio repo) {
		repositorio = repo;
	}
	
	public List<Receta> listarRecetasParaUnUsuario(Usuario unUsuario) {		
		return  this.criterio.ordenar(this.repositorio.listarRecetasParaUnUsuario(unUsuario));
	}
	
	
}
