package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class RecetaMasConsultada implements Monitor {

	private HashMap<String, Integer> contadorDeRecetas = new HashMap<String, Integer>();
	private String recetaMasConsultada;
	private Integer cantidadDeConsultas = 0;
	
	
	public void notificarConsulta(List<Receta> consulta, Usuario usuarioConsultor) {

		ListIterator<Receta> r =  consulta.listIterator(); 
				
		while(r.hasNext()) {
			String nom = r.next().getNombreDelPlato();
			
			if (contadorDeRecetas.containsKey(nom)) 
				contadorDeRecetas.put(nom, contadorDeRecetas.get(nom) + 1);
			else
				contadorDeRecetas.put(nom, 1);
		}
	}
	
	public HashMap<String, Integer> recetaMasConsultada() {
		
		contadorDeRecetas.forEach((r, v) -> guardarMaximo(v, r));
		HashMap<String, Integer> receta = new HashMap<String, Integer>();
		receta.put(recetaMasConsultada, cantidadDeConsultas);
		return receta;
	}

	public void guardarMaximo(Integer unValor, String unaReceta) {
		
		if (unValor >= cantidadDeConsultas) {
			cantidadDeConsultas = unValor;
			recetaMasConsultada = unaReceta;
		}
	}
	
	public String getRecetaMasConsultada() {
		return recetaMasConsultada;
	}
	
}
