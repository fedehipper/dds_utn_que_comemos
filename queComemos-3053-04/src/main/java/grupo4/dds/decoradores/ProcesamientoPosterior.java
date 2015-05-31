package grupo4.dds.decoradores;

import grupo4.dds.receta.Receta;

import java.util.List;

public interface ProcesamientoPosterior {

	public List<Receta> procesar(List<Receta> receta);
	
}
