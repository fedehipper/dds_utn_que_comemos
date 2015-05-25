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
	
	// punto 2
	public List<Receta> listarRecetasParaUnUsuario(Usuario unUsuario) {
		return recetas.stream().filter(r -> unUsuario.puedeVer(r)).collect(Collectors.toList());
	}
	
	
	public List<Receta> getRecetas() {
		return this.recetas;
	}
	
}
