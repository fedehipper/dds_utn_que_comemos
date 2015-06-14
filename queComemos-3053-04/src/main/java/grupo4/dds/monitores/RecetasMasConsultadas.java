package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;

import grupo4.dds.usuario.Usuario;


import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class RecetasMasConsultadas implements Monitor {
	
	private HashMap<Receta, Integer> contadorRecetas = new HashMap<Receta, Integer>();
	

	public void notificarConsulta(List<Receta> consulta, Usuario usuarioConsultor) {

		ListIterator<Receta> receta = consulta.listIterator(); 
				
		while(receta.hasNext()) {
			Receta aux = receta.next();
			if (contadorRecetas.containsKey(aux))
				contadorRecetas.put(aux, contadorRecetas.get(aux) + 1);
			else
				contadorRecetas.put(aux, 1);
		}
	}
	
	public HashMap<Receta, Integer> recetasMasConsultadas(int cantidad) {
		
		ListIterator<Receta> punteroReceta = recetasOrdenadas(cantidad).listIterator();
		HashMap<Receta, Integer> recetasYCantidad = new HashMap<>();
		
		while(punteroReceta.hasNext()) {
			Receta unaReceta = punteroReceta.next();
			recetasYCantidad.put(unaReceta, contadorRecetas.get(unaReceta));
		}
		return recetasYCantidad;
	}
	
	public List<Receta> recetasOrdenadas(int cantidad) {
		List<Receta> recetas = contadorRecetas.keySet().stream().collect(Collectors.toList());
		return ordenMasConsultadas(recetas).subList(0, cantidad);
	}
	
	public List<Receta> ordenMasConsultadas(List<Receta> recetas) {
		recetas.sort((r1,r2) -> getValor(r2) - (getValor(r1)));
		return recetas;
	}
	
	public int getValor(Receta receta) {
		return contadorRecetas.get(receta);
	}
	
}