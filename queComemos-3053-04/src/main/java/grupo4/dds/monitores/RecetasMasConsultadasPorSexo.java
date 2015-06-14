package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Sexo;
import grupo4.dds.usuario.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;


public class RecetasMasConsultadasPorSexo implements Monitor {
	
	private HashMap<Receta, Integer> contadorSexoMasculino = new HashMap<Receta, Integer>();
	private HashMap<Receta, Integer> contadorSexoFemenino = new HashMap<Receta, Integer>();
	
	
	public void notificarConsulta(List<Receta> consulta, Usuario usuario) {

		ListIterator<Receta> punteroReceta = consulta.listIterator(); 
		HashMap<Receta, Integer> contador = contadorPor(usuario.getSexo()); 
		
		while(punteroReceta.hasNext()) {
			Receta aux = punteroReceta.next();
			
			if (contador.containsKey(aux))
				contador.put(aux, contador.get(aux) + 1);
			else
				contador.put(aux, 1);
		}
	}
	
	public HashMap<Receta, Integer> recetasMasConsultadasPor(Sexo sexo, int cantidad) {
				
		ListIterator<Receta> punteroReceta = recetasOrdenadasPor(sexo, cantidad).listIterator();
		HashMap<Receta, Integer> recetasYCantidad = new HashMap<>();
		
		while(punteroReceta.hasNext()) {
			Receta unaReceta = punteroReceta.next();
			recetasYCantidad.put(unaReceta, contadorPor(sexo).get(unaReceta));
		}
		return recetasYCantidad;
	}
	
	public List<Receta> recetasOrdenadasPor(Sexo sexo, int cantidad) {
		List<Receta> recetas = contadorPor(sexo).keySet().stream().collect(Collectors.toList());
		return ordenMasConsultadas(recetas, sexo).subList(0, cantidad);
	}
	
	public HashMap<Receta, Integer> contadorPor(Sexo sexo) {
		if (sexo == Sexo.MASCULINO) 
			return contadorSexoMasculino; 
		else
			return contadorSexoFemenino;
	}
	
	public List<Receta> ordenMasConsultadas(List<Receta> recetas, Sexo sexo) {
		recetas.sort((r1,r2) -> getValor(r2, sexo) - (getValor(r1, sexo)));
		return recetas;
	}
	
	public int getValor(Receta receta, Sexo sexo) {
		return contadorPor(sexo).get(receta);
	}

	
}


