package grupo4.dds.repositorios;

import grupo4.dds.monitores.Monitor;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.receta.busqueda.postProcesamiento.PostProcesamiento;
import grupo4.dds.usuario.Usuario;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RepositorioDeRecetas extends Repositorio<Receta> {

	private static final RepositorioDeRecetas self = new RepositorioDeRecetas();
	
	public static RepositorioDeRecetas instance() {
		return self;
	}

	public RepositorioDeRecetas() {
		elementType = Receta.class;
	}
	
	/* Servicios */

	public List<Receta> listarRecetasPara(Usuario usuario, List<Filtro> filtros, PostProcesamiento postProcesamiento) {
		
		Stream<Receta> stream = recetasQuePuedeVer(usuario);
		
		if(filtros != null)
			for (Filtro filtro : filtros)
				stream = stream.filter(r -> filtro.test(usuario, r));
		
		List<Receta> recetasFiltradas = stream.collect(Collectors.toList());
		List<Receta> consulta;
		
		if (postProcesamiento == null) 
			consulta = recetasFiltradas;
		else
			consulta = postProcesamiento.procesar(recetasFiltradas);

		notificarATodos(usuario, consulta, filtros);

		return consulta;
	}
	
	public void notificarATodos(Usuario usuario, List<Receta> consulta, List<Filtro> filtros) {
		monitores().forEach(monitor -> monitor.notificarConsulta(usuario, consulta, filtros));
	}
	
	public List<Monitor> monitores() {
		return entityManager().createQuery("from Monitor", Monitor.class).getResultList();
	}
	
	/* Servicios privados */
	
	private Stream<Receta> recetasQuePuedeVer(Usuario usuario) {
		
		HashSet<Receta> todasLasRecetas = new HashSet<>(this.list());
		todasLasRecetas.addAll(RepositorioRecetasExterno.get().getRecetas());
		
		return todasLasRecetas.stream().filter(r -> usuario.puedeVer(r));
	}
	
	/* Accesors and Mutators */
	
	public void agregarMonitor(Monitor monitor) {
		entityManager().persist(monitor);
	}
	
	public void removerMonitor(Monitor monitor) {
		entityManager().remove(monitor);
	}
}
