package grupo4.dds.decoradores;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.Repositorio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Orden implements ProcesamientoPosterior {
	
	private Comparator<Receta> criterio;
	protected List<Receta> recetas = new ArrayList<>();
	protected Repositorio repositorio;
	
	public void setCriterio(Comparator<Receta> unCriterio) {
		criterio = unCriterio;
	}
	
	public List<Receta> procesar(List<Receta> receta) {		
		return  this.ordenarPor(criterio, receta);
	}
	
	public List<Receta> ordenarPor(Comparator<Receta> unCriterio, List<Receta> unaReceta){
		Collections.sort(unaReceta, criterio);
		return unaReceta;
	}
	
}
