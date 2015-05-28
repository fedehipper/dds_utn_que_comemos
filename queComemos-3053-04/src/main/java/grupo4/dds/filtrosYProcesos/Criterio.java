package grupo4.dds.filtrosYProcesos;

import grupo4.dds.receta.Receta;

import java.util.List;

public interface Criterio {
	
	public List<Receta> ordenar(List<Receta> recetas);

}
