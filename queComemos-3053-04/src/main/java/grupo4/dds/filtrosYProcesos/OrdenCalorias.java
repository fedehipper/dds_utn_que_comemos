package grupo4.dds.filtrosYProcesos;

import grupo4.dds.comparadores.ComparaCalorias;
import grupo4.dds.receta.Receta;

import java.util.Collections;
import java.util.List;

public class OrdenCalorias implements Criterio {
	
	public List<Receta> ordenar(List<Receta> recetas) {
		Collections.sort(recetas, new ComparaCalorias());
		return recetas;
	}	

}
