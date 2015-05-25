package grupo4.dds.receta;

import grupo4.dds.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;


public class RepositorioDeRecetas {

	private List<Receta> recetas = new ArrayList<>();
	
	
	
	public void agregarReceta(Receta unaReceta) {
		this.recetas.add(unaReceta);
	}
	
	public void quitarReceta(Receta unaReceta) {
		this.recetas.remove(unaReceta);
	}
	
	public void listarRecetasParaUnUsuario(Usuario unUsuario) {
		
	}
	
	
	public List<Receta> getRecetas() {
		return this.recetas;
	}
	
}
