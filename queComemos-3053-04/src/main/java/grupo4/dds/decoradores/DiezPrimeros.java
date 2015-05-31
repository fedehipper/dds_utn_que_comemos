package grupo4.dds.decoradores;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.Repositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DiezPrimeros implements ProcesamientoPosterior {
	
	protected List<Receta> recetas = new ArrayList<>();
	protected Repositorio repositorio;
	
	public List<Receta> procesar(List<Receta> receta) {		
	 	  return receta.stream().limit(10).collect(Collectors.toList());
	}
	
}
