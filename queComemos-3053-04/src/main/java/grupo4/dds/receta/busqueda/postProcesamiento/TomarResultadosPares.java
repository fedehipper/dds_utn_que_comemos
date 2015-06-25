package grupo4.dds.receta.busqueda.postProcesamiento;

import grupo4.dds.receta.Receta;

import java.util.List;
import java.util.stream.Collectors;

public class TomarResultadosPares implements PostProcesamiento {
	
	public List<Receta> procesar(List<Receta> recetas) {
		return recetas.stream().filter(r -> esPar(recetas.indexOf(r))).collect(Collectors.toList());
	}

	private boolean esPar(int n) {
		return n % 2 == 0;
	}
	
}
