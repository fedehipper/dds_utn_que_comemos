package grupo4.dds.receta;

import grupo4.dds.monitores.Monitor;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.receta.busqueda.postProcesamiento.PostProcesamiento;
import grupo4.dds.usuario.Usuario;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RepositorioDeRecetas {

	private static final RepositorioDeRecetas self = new RepositorioDeRecetas();
	private Set<Receta> recetas = new HashSet<Receta>();
	private List<Receta> consulta = new ArrayList<>();
	private Usuario usuarioConsultor;
	private Set<Monitor> monitores = new HashSet<>();
	
	public static RepositorioDeRecetas get() {
		return self;
	}

	protected RepositorioDeRecetas() {}
	
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
		
		usuarioConsultor = usuario;
		
		if (postProcesamiento == null) 
			consulta = recetasFiltradas;
		else
			consulta = postProcesamiento.procesar(recetasFiltradas);

		notificarATodos();
		return consulta;
	}
	
	// punto 3) observer
	public void notificar(Monitor monitor) {
		monitor.notificarConsulta(consulta, usuarioConsultor);
	}
	
	// punto 3) observer
	public void notificarATodos() {
		this.monitores.forEach(monitor -> this.notificar(monitor));
	}
	
	/* Seters and Getters */
	
	public void setMonitor(Monitor monitor) {
		this.monitores.add(monitor);
	}
	
	public void removeMonitor(Monitor monitor) {
		this.monitores.remove(monitor);
	}
	
	
	/* Servicios privados */
	
	private Stream<Receta> recetasQuePuedeVer(Usuario usuario) {
		return recetas.stream().filter(r -> usuario.puedeVer(r));
	}
	
	public void vaciar() {
		recetas.clear();
	}
	
	/* Accesors and Mutators */
	
	public void agregarReceta(Receta unaReceta) {
		this.recetas.add(unaReceta);
	}
	
	public void quitarReceta(Receta unaReceta) {
		this.recetas.remove(unaReceta);
	}

}
