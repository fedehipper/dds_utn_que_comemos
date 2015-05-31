package grupo4.dds.decoradores;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.Repositorio;

import java.util.ArrayList;
import java.util.List;

public class ResultadosPares implements ProcesamientoPosterior {
	
	protected List<Receta> recetas = new ArrayList<>();
	protected Repositorio repositorio;
	
	public List<Receta> procesar(List<Receta> receta) {		
	 	  return pares(receta);
	}
	
	public List<Receta> pares(List<Receta> unaLista) {
		int cont = 1;
		List<Receta> resultado = new ArrayList<>();
		unaLista.get(cont);
		
		while(cont < unaLista.size() ){
			resultado.add(unaLista.get(cont));
			cont = cont + 2;
		}
		return resultado;	
	}
	
}


 