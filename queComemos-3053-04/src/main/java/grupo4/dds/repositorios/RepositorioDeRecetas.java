package grupo4.dds.repositorios;

import grupo4.dds.command.Command;
import grupo4.dds.command.CommandMailSender;
import grupo4.dds.command.LoguearConsultas;
import grupo4.dds.command.MarcarRecetasFavoritas;
import grupo4.dds.monitores.Monitor;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.receta.busqueda.postProcesamiento.PostProcesamiento;
import grupo4.dds.usuario.Usuario;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class RepositorioDeRecetas implements WithGlobalEntityManager {

	private static final RepositorioDeRecetas self = new RepositorioDeRecetas();
	private Set<Monitor> monitores = new HashSet<>();
	private List<CommandMailSender> mailPendientes = new ArrayList<>();
	private List<Usuario> suscriptores = new ArrayList<>();
	private List<Command> logs = new ArrayList<>();
	
	public static RepositorioDeRecetas get() {
		return self;
	}

	protected RepositorioDeRecetas() {
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

		notificarATodos(usuario, consulta);
		agregarAcciones(usuario, consulta, filtros);
		return consulta;
	}
	
	public void agregarAcciones(Usuario usuario, List<Receta> consulta, List<Filtro> filtros) {
			
		usuario.agregarAccionDeMarcarFavorita(new MarcarRecetasFavoritas(consulta));
		this.agregarLogsDeConsulta(new LoguearConsultas(consulta));
		
		if (suscriptores.stream().anyMatch(u -> u.equals(usuario)) )
		   agregarEnvioMail(new CommandMailSender (usuario, consulta, filtros));
		
	}

	public void notificarATodos(Usuario usuario, List<Receta> consulta) {
		this.monitores.forEach(monitor -> monitor.notificarConsulta(consulta, usuario));
	}
	
	/* Servicios privados */
	
	private Stream<Receta> recetasQuePuedeVer(Usuario usuario) {
		List<Receta> recetas = entityManager().createQuery("from Receta", Receta.class).getResultList();
		
		HashSet<Receta> todasLasRecetas = new HashSet<>(recetas);
		todasLasRecetas.addAll(RepositorioRecetasExterno.get().getRecetas());
		
		return todasLasRecetas.stream().filter(r -> usuario.puedeVer(r));
	}
	
	/* Accesors and Mutators */
	
	public void agregarReceta(Receta unaReceta) {
		entityManager().persist(unaReceta);
	}
	
	public void agregarListaDeRecetas(List<Receta> recetas) {
		recetas.forEach(r -> agregarReceta(r));
	}
	
	public void quitarReceta(Receta unaReceta) {
		entityManager().remove(unaReceta);
	}

	public void setMonitor(Monitor monitor) {
		this.monitores.add(monitor);
	}
	
	public void removeMonitor(Monitor monitor) {
		this.monitores.remove(monitor);
	}
	//entrega 4 punto 3
	public void agregarEnvioMail(CommandMailSender commandMailSender){
		this.mailPendientes.add(commandMailSender);
	}
		
	//entrega4 punto 4
	public void agregarLogsDeConsulta(Command accion){
		this.logs.add(accion);
	}
	
	public List<CommandMailSender> getMailPendientes(){
		return mailPendientes;
	}
	
	public List<Usuario> getSuscriptores(){
		return suscriptores;
	}
}
