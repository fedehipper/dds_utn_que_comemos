package grupo4.dds.filtrosYProcesos;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RepositorioDeRecetas;

import java.util.ArrayList;
import java.util.List;

public class ResultadosPares implements Procesamiento {
	
	public void procesar(List<Receta> recetaConFiltros, RepositorioDeRecetas repoRecetas) {
		
		repoRecetas.procesoFinalTerminado(pares(recetaConFiltros));
		
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
