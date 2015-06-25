package grupo4.dds.receta;

import grupo4.dds.command.Command;
import grupo4.dds.command.LoguearConsultas;
import grupo4.dds.command.MarcarRecetasFavoritas;
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
	private Set<Monitor> monitores = new HashSet<>();
	private List<Command> acciones = new ArrayList<>();
	
	public static RepositorioDeRecetas get() {
		return self;
	}

	protected RepositorioDeRecetas() {
	}
	
	/* Servicios */

	public List<Receta> listarRecetasPara(Usuario usuario) {
		List<Receta> consulta = recetasQuePuedeVer(usuario).collect(Collectors.toList());
		notificarATodos(usuario, consulta);
		ejecutarAcciones(usuario,consulta);
		return consulta;
	}
	
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

		notificarATodos(usuario, consulta);
		ejecutarAcciones(usuario, consulta);
		return consulta;
	}
	
	public void ejecutarAcciones(Usuario usuario, List<Receta> consulta) {
	
		// aca cada command recibe los parametros que necesitan, no es que todos reciben los mismo y 
		// usan los que requieren, lo que si es necesario es agregarle ese parametro en la firma de este metodo
		// (ejecutarAcciones) por ahora solo reciben al usuario y la consulta
		
		agregarAccion(new MarcarRecetasFavoritas(usuario, consulta));
		agregarAccion(new LoguearConsultas(usuario));
		//agregarAccion(new EnviarMail(usuario, .......,......,...));
		
		ejecutar();
	}
	
	public void ejecutar() {
		acciones.forEach(a -> a.ejecutar());
	}
	
	public void notificar(Monitor monitor, Usuario usuario, List<Receta> consulta) {
		monitor.notificarConsulta(consulta, usuario);
	}
	
	public void notificarATodos(Usuario usuario, List<Receta> consulta) {
		this.monitores.forEach(monitor -> notificar(monitor, usuario, consulta));
	}
	
	/* Servicios privados */
	
	private Stream<Receta> recetasQuePuedeVer(Usuario usuario) {
		HashSet<Receta> todasLasRecetas = new HashSet<>(recetas);
		todasLasRecetas.addAll(RepositorioRecetasExterno.get().getRecetas());
		return todasLasRecetas.stream().filter(r -> usuario.puedeVer(r));
	}
	
	/* Accesors and Mutators */
	
	public void agregarReceta(Receta unaReceta) {
		recetas.add(unaReceta);
	}
	
	public void agregarListaDeRecetas(List<Receta> recetas) {
		this.recetas.addAll(recetas);
	}
	
	public void quitarReceta(Receta unaReceta) {
		this.recetas.remove(unaReceta);
	}

	public void vaciar() {
		this.recetas.clear();
	}
	
	public void setMonitor(Monitor monitor) {
		this.monitores.add(monitor);
	}
	
	public void removeMonitor(Monitor monitor) {
		this.monitores.remove(monitor);
	}
	
	public void agregarAccion(Command unaAccion) {
		this.acciones.add(unaAccion);
	}
	
}
