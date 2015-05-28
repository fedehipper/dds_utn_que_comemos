package grupo4.dds.filtrosYProcesos;

import java.util.List;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RepositorioDeRecetas;

public interface Procesamiento {
	
	
	public void procesar(List<Receta> recetaConFiltros, RepositorioDeRecetas repoRecetas);
	


}
