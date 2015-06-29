package grupo4.dds.receta;


import static org.junit.Assert.assertTrue;
import grupo4.dds.command.Command;
import grupo4.dds.command.CommandMailSender;
import grupo4.dds.command.LoguearConsultas;
import grupo4.dds.command.MailSenderPosta;
import grupo4.dds.command.MarcarRecetasFavoritas;
import grupo4.dds.monitores.Monitor;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.receta.busqueda.postProcesamiento.PostProcesamiento;
import grupo4.dds.usuario.Usuario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.mockito.Mockito;

import queComemos.entrega3.dominio.Dificultad;



public class RepositorioDeRecetas {

	private static final RepositorioDeRecetas self = new RepositorioDeRecetas();
	private Set<Receta> recetas = new HashSet<Receta>();
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
	
	
	
	public static void main(String[] args){
		 Usuario federicoHipper;
		Usuario cristian;
		 Usuario fecheSena;
		 RepositorioDeRecetas repositorio = RepositorioDeRecetas.get();
		
		 Receta receta2;
		 Receta receta3;
		 RecetaPublica receta6;
		 RecetaPublica receta7;
		 RecetaPublica receta8;
		
		 List<Receta> mockConsultas = new ArrayList<>();
		Receta receta;
		
		 List<Receta> consulta= new ArrayList<Receta>();
		
		MailSenderPosta mailSender = Mockito.mock(MailSenderPosta.class);
	
			repositorio.vaciar();
			fecheSena = Usuario.crearPerfil("Feche Sena", null, null, 1.91f, 99.0f, null, false, "fesena92@gmail.com");
			federicoHipper = Usuario.crearPerfil("Federico Hipperdinger", null, null, 1.91f, 99.0f, null, true, null);
			receta2 = Receta.crearNueva(federicoHipper, new EncabezadoDeReceta("receta2", null, Dificultad.DIFICIL, 300), null);
			receta3 = Receta.crearNueva(federicoHipper, new EncabezadoDeReceta("receta3", null, null, 600), null);
			receta6 = RecetaPublica.crearNueva(new EncabezadoDeReceta("receta6", null, null, 200), null);
			receta7 = RecetaPublica.crearNueva(new EncabezadoDeReceta("receta7", null, null, 300), null);
			receta8 = RecetaPublica.crearNueva(new EncabezadoDeReceta("receta8", null, null, 100), null);
			
			consulta = Arrays.asList(receta2, receta3, receta6, receta7, receta8);
			
			
			CommandMailSender accionMail = new CommandMailSender(fecheSena, consulta, null);
			repositorio.getSuscriptores().add(fecheSena);
			repositorio.agregarAcciones(fecheSena, consulta, null);
			System.out.println(repositorio.getMailPendientes().stream().findFirst().getClass());
			System.out.println((accionMail).getClass());
			System.out.println(repositorio.getSuscriptores());
			
	}
}
