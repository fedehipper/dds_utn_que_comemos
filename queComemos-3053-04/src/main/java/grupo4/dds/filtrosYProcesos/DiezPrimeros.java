package grupo4.dds.filtrosYProcesos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RepositorioDeRecetas;

public class DiezPrimeros implements Procesamiento {
	
	public void procesar(List<Receta> recetaConFiltros, RepositorioDeRecetas repoRecetas) {
		
		List<Receta> recetaProcesada = new ArrayList<>();
		
		recetaProcesada = recetaConFiltros.stream().limit(10).collect(Collectors.toList());
		
		repoRecetas.procesoFinalTerminado(recetaProcesada);
	}
	 	
}
