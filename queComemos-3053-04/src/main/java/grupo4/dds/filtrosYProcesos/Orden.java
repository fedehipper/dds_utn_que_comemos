package grupo4.dds.filtrosYProcesos;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RepositorioDeRecetas;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Orden implements Procesamiento {
	
	private Comparator<Receta> criterio;
	
	public void procesar(List<Receta> recetaConFiltros, RepositorioDeRecetas repoRecetas) {
		repoRecetas.procesoFinalTerminado(this.ordenarPor(criterio, recetaConFiltros));
		
	}
	
	public void setCriterio(Comparator<Receta> unCriterio) {
		this.criterio = unCriterio;
	}
	
	public List<Receta> ordenarPor(Comparator<Receta> unCriterio, List<Receta> recetas){
		Collections.sort(recetas, criterio);
		return recetas;
	}
}
