package grupo4.dds.receta.busqueda.postProcesamiento;

import grupo4.dds.receta.Receta;

import java.util.Comparator;
import java.util.List;

public class Ordenar implements PostProcesamiento {
	
	private Comparator<Receta> criterio;

	public Ordenar() {}

	public Ordenar(Comparator<Receta> criterio) {
		this.criterio = criterio;
	}

	public List<Receta> procesar(List<Receta> recetas) {
		recetas.sort(criterio);
		return recetas;
	}

	public void setCriterio(Comparator<Receta> criterio) {
		this.criterio = criterio;
	}
		
}
