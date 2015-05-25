package grupo4.dds.receta;

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
	
	public List<Receta> listarTodas() {
		return this.recetas;
	}
	
}
