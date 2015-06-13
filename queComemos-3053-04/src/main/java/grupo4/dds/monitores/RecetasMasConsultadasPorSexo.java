package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Sexo;
import grupo4.dds.usuario.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;


public class RecetasMasConsultadasPorSexo implements Monitor {
	
	private HashMap<Receta, Integer> contadorDeRecetasSexoMasculino = new HashMap<Receta, Integer>();
	private HashMap<Receta, Integer> contadorDeRecetasSexoFemenino = new HashMap<Receta, Integer>();
	
	
	public void notificarConsulta(List<Receta> consulta, Usuario usuario) {

		ListIterator<Receta> punteroReceta = consulta.listIterator(); 
		
		HashMap<Receta, Integer> contador = contadorPorSexo(usuario.getSexo()); 
		
		while(punteroReceta.hasNext()) {
			Receta aux = punteroReceta.next();
			
			if (contador.containsKey(aux))
				contador.put(aux, contador.get(aux) + 1);
			else
				contador.put(aux, 1);
		}
	}
	
	public HashMap<Receta, Integer> recetasMasConsultadasPor(Sexo sexo, int cantidad) {
		
		HashMap<Receta, Integer> contador = contadorPorSexo(sexo); 
		List<Receta> recetas = contador.keySet().stream().collect(Collectors.toList());
		
		List<Receta> vistaRecetasPorCantidad = ordenMasConsultadas(recetas, sexo).subList(0, cantidad);
		HashMap<Receta, Integer> recetasYCantidad = new HashMap<>();
		
		ListIterator<Receta> punteroReceta = vistaRecetasPorCantidad.listIterator();
		
		while(punteroReceta.hasNext()) {
			Receta unaReceta = punteroReceta.next();
			recetasYCantidad.put(unaReceta, contador.get(unaReceta));
		}
		return recetasYCantidad;
	}
	
	public HashMap<Receta, Integer> contadorPorSexo(Sexo sexo) {

		if (sexo == Sexo.MASCULINO) 
			return contadorDeRecetasSexoMasculino; 
		else
			return contadorDeRecetasSexoFemenino;
	}
	
	public List<Receta> ordenMasConsultadas(List<Receta> recetas, Sexo sexo) {
		recetas.sort((r1,r2) -> getValor(r2, sexo) - (getValor(r1, sexo)));
		return recetas;
	}
	
	public int getValor(Receta receta, Sexo sexo) {
		return contadorPorSexo(sexo).get(receta);
	}

}
