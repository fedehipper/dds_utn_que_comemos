package grupo4.dds.decoradores;

import java.util.Collections;
import java.util.List;

import grupo4.dds.comparadores.ComparaCalorias;
import grupo4.dds.receta.Receta;

public class OrdenCalorias implements CriterioOrden {

	public List<Receta> ordenar(List<Receta> recetas) {
		Collections.sort(recetas, new ComparaCalorias());
		return recetas;
	}	

}
