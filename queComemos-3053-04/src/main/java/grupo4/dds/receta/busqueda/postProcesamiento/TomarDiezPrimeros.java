package grupo4.dds.receta.busqueda.postProcesamiento;

import grupo4.dds.receta.Receta;

import java.util.List;

public class TomarDiezPrimeros implements PostProcesamiento {

	public List<Receta> procesar(List<Receta> recetas) {
		return recetas.subList(0, 9);
	}
	
}
