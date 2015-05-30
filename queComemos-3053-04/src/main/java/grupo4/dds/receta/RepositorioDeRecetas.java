package grupo4.dds.receta;

import grupo4.dds.excepciones.NoSePuedeAgregarFiltro;
import grupo4.dds.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioDeRecetas implements Repositorio {

	private List<Receta> recetas = new ArrayList<>();
	private boolean procesoFinalizado = false;
	
	/* Servicios */
	public List<Receta> listarRecetasParaUnUsuario(Usuario unUsuario) {
		if (!this.procesoFinalizado) {
			this.procesoFinalizado = true;
			return recetas.stream().filter(r -> unUsuario.puedeVer(r)).collect(Collectors.toList());
		}
		else 
			throw new NoSePuedeAgregarFiltro();
	}

	/* Accesors and Mutators */
	
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
