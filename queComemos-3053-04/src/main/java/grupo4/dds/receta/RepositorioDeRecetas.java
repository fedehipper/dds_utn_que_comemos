package grupo4.dds.receta;


import grupo4.dds.command.CommandMailSender;
import grupo4.dds.command.Mail;
import grupo4.dds.command.MailSender;
import grupo4.dds.command.MailSenderPosta;
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
	private List<CommandMailSender> mailPendientes = new ArrayList<>();
	private List<Usuario> suscriptores = new ArrayList<>();
	
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
		//agregarAccion(new LoguearConsultas(consulta));
		if (suscriptores.stream().anyMatch(u -> u.equals(usuario)) )
		   agregarEnvioMail(new CommandMailSender (usuario, consulta, filtros));
		
		// ya no ejecuta aca
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
	
	public void agregarEnvioMail(CommandMailSender commandMailSender){
		this.mailPendientes.add(commandMailSender);
	}
		
}
