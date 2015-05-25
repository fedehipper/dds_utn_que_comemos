package grupo4.dds.receta;

import grupo4.dds.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class RepositorioDeRecetas {

	private List<Receta> recetas = new ArrayList<>();
	
	
	
	public void agregarReceta(Receta unaReceta) {
		this.recetas.add(unaReceta);
	}
	
	public void quitarReceta(Receta unaReceta) {
		this.recetas.remove(unaReceta);
	}
	
	public void listarRecetasParaUnUsuario(Usuario unUsuario) {
		
		// busco las recetas que tienen su nomnbre
		// busco las recetas que son publicas
		// busco las recetas que son creadas por miembros de su grupo
	
	}
	
	
	public List<Receta> listarRecetasPublicas() {
		return recetas.stream().filter(r -> r.getCreador() == null).collect(Collectors.toList());
	}
	
		
	
	public List<Receta> getRecetas() {
		return this.recetas;
	}
	
}
