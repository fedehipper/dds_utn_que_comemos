package grupo4.dds.decoradores;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.Repositorio;

import java.util.ArrayList;
import java.util.List;

public abstract class ProcesamientoPosterior implements Repositorio {

	protected List<Receta> recetas = new ArrayList<>();
	protected Repositorio repositorio;
	
	public void agregarReceta(Receta unaReceta) {
		this.recetas.add(unaReceta);
	}
	
	public void quitarReceta(Receta unaReceta) {
		this.recetas.remove(unaReceta);
	}
	
	public List<Receta> getRecetas() {
		return this.recetas;
	}
	
	
}
