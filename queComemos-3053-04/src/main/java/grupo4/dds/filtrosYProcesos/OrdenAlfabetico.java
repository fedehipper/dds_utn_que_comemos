package grupo4.dds.filtrosYProcesos;

import grupo4.dds.comparadores.ComparaAlfabeticamente;
import grupo4.dds.receta.Receta;

import java.util.Collections;
import java.util.List;

public class OrdenAlfabetico implements Criterio {

	public List<Receta> ordenar(List<Receta> recetas) {
		Collections.sort(recetas, new ComparaAlfabeticamente());
		return recetas;
	}

}
