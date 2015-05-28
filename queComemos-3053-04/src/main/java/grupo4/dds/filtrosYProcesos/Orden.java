package grupo4.dds.filtrosYProcesos;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RepositorioDeRecetas;

import java.util.List;

public class Orden implements Procesamiento {
	
	private Criterio criterio;
	
	public void procesar(List<Receta> recetaConFiltros, RepositorioDeRecetas repoRecetas) {
		repoRecetas.procesoFinalTerminado(this.criterio.ordenar(recetaConFiltros));
		
	}
	
	public void setCriterio(Criterio unCriterio) {
		this.criterio = unCriterio;
	}
	
}
