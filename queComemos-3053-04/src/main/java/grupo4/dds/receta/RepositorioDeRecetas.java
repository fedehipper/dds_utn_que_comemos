package grupo4.dds.receta;

import grupo4.dds.decoradores.ProcesamientoPosterior;
import grupo4.dds.excepciones.NoSePuedeAgregarFiltro;
import grupo4.dds.excepciones.NoSePuedeAgregarOtroProceso;
import grupo4.dds.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioDeRecetas implements Repositorio {

	private List<Receta> recetas = new ArrayList<>();
	private boolean filtroFinalizado = false;
	private boolean procesoFinalizado = false;
	private ProcesamientoPosterior proceso;
	
	
	/* Servicios */
	public List<Receta> listarRecetasParaUnUsuario(Usuario unUsuario) {
		if (!this.filtroFinalizado) {
			this.filtroFinalizado = true;
			return recetas.stream().filter(r -> unUsuario.puedeVer(r)).collect(Collectors.toList());
		}
		else 
			throw new NoSePuedeAgregarFiltro();
	}
	
	public List<Receta> aplicarProceso(List<Receta> recetasFiltradas) {
		return this.proceso.procesar(recetasFiltradas);
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
	
	public void setProceso(ProcesamientoPosterior proceso) {
		
		if (!this.procesoFinalizado) {
			this.proceso = proceso;
			this.procesoFinalizado = true;
		}
		else 
			throw new NoSePuedeAgregarOtroProceso();
	}
	
}
