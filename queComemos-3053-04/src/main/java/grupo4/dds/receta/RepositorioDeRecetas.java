package grupo4.dds.receta;

import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.receta.busqueda.postProcesamiento.PostProcesamiento;
import grupo4.dds.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RepositorioDeRecetas {

	private List<Receta> recetas = new ArrayList<>();
	
	/* Servicios */
	
	public List<Receta> listarRecetasPara(Usuario usuario) {
		return recetasQuePuedeVer(usuario).collect(Collectors.toList());
	}
	
	public List<Receta> listarRecetasPara(Usuario usuario, List<Filtro> filtros, PostProcesamiento postProcesamiento) {
		
		Stream<Receta> stream = recetasQuePuedeVer(usuario);

		if(filtros != null)
			for (Filtro filtro : filtros)
				stream = stream.filter(r -> filtro.test(usuario, r));
		
		List<Receta> recetasFiltradas = stream.collect(Collectors.toList());
		
		return postProcesamiento == null ? recetasFiltradas : postProcesamiento.procesar(recetasFiltradas);
		
	}

	/* Servicios privados */
	
	protected Stream<Receta> recetasQuePuedeVer(Usuario usuario) {
		return recetas.stream().filter(r -> usuario.puedeVer(r));
	}
	
	/* Accesors and Mutators */
	
	public void agregarReceta(Receta unaReceta) {
		this.recetas.add(unaReceta);
	}
	
	public void quitarReceta(Receta unaReceta) {
		this.recetas.remove(unaReceta);
	}

}
