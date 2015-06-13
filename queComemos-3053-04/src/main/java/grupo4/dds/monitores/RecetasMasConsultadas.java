package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;

import grupo4.dds.usuario.Usuario;


import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class RecetasMasConsultadas implements Monitor {
	

	private HashMap<Receta, Integer> contadorDeRecetas = new HashMap<Receta, Integer>();
	
	
	
	public void notificarConsulta(List<Receta> consulta, Usuario usuarioConsultor) {

		ListIterator<Receta> receta = consulta.listIterator(); 
				
		while(receta.hasNext()) {
			
			Receta aux = receta.next();
			
			if (contadorDeRecetas.containsKey(aux))
				contadorDeRecetas.put(aux, contadorDeRecetas.get(aux) + 1);
			else
				contadorDeRecetas.put(aux, 1);
		}
	}
	
	public HashMap<Receta, Integer> recetasMasConsultadas(int cantidad) {
		
		List<Receta> recetas = contadorDeRecetas.keySet().stream().collect(Collectors.toList());
		
		List<Receta> vistaRecetasPorCantidad = ordenMasConsultadas(recetas).subList(0, cantidad);
		
		HashMap<Receta, Integer> recetasYCantidad = new HashMap<Receta, Integer>();
		
		ListIterator<Receta> punteroReceta = vistaRecetasPorCantidad.listIterator();
		
		while(punteroReceta.hasNext()) {
			Receta unaReceta = punteroReceta.next();
			recetasYCantidad.put(unaReceta, contadorDeRecetas.get(unaReceta));
		}
		return recetasYCantidad;
	}
	
	public List<Receta> ordenMasConsultadas(List<Receta> recetas) {
		recetas.sort((r1,r2) -> getValor(r2) - (getValor(r1)));
		return recetas;
	}
	
	public int getValor(Receta receta) {
		return contadorDeRecetas.get(receta);
	}
	
}