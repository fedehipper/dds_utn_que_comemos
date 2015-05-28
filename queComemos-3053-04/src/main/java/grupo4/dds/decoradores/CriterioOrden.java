package grupo4.dds.decoradores;

import grupo4.dds.receta.Receta;

import java.util.List;

public interface CriterioOrden {

	public List<Receta> ordenar(List<Receta> recetas);

}
