package grupo4.dds;


import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import grupo4.dds.command.Mail;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.receta.busqueda.filtros.FiltroExcesoCalorias;
import grupo4.dds.receta.busqueda.filtros.FiltroNoEsAdecuada;
import grupo4.dds.receta.busqueda.filtros.FiltroNoLeGusta;
import grupo4.dds.usuario.Usuario;

import org.junit.Before;
import org.junit.Test;

import queComemos.entrega3.dominio.Dificultad;


public class TestCommand {
		
	private Usuario federicoHipper;
	private Usuario fecheSena;
	private RepositorioDeRecetas repositorio = RepositorioDeRecetas.get();
	
	private Receta receta2;
	private Receta receta3;
	private RecetaPublica receta6;
	private RecetaPublica receta7;
	private RecetaPublica receta8;
	
	private FiltroExcesoCalorias filtroExceso;
	private FiltroNoEsAdecuada filtroInadecuada;
	private FiltroNoLeGusta filtroNoLeGusta;
	
	private List<Filtro> filtros = new ArrayList<Filtro>();
	private List<Receta> consulta = new ArrayList<Receta>();
	
	
	@Before
	public void setUp() {
		
		
		federicoHipper = Usuario.crearPerfil("Federico Hipperdinger", null, null, 1.91f, 99.0f, null, true, null);
		fecheSena = Usuario.crearPerfil("FecheSena", null, null, 1.74f, 80.0f, null, true, "fesena92@gmail.com");

		receta2 = Receta.crearNueva(federicoHipper, new EncabezadoDeReceta("receta2", null, Dificultad.DIFICIL, 300), null);
		receta3 = Receta.crearNueva(federicoHipper, new EncabezadoDeReceta("receta3", null, null, 600), null);
		receta6 = RecetaPublica.crearNueva(new EncabezadoDeReceta("receta6", null, null, 200), null);
		receta7 = RecetaPublica.crearNueva(new EncabezadoDeReceta("receta7", null, null, 300), null);
		receta8 = RecetaPublica.crearNueva(new EncabezadoDeReceta("receta8", null, null, 100), null);
		
		filtroExceso = new FiltroExcesoCalorias();
		filtroInadecuada = new FiltroNoEsAdecuada();
		filtroNoLeGusta = new FiltroNoLeGusta();
			
		consulta = Arrays.asList(receta2, receta3, receta6, receta7, receta8);
		filtros = Arrays.asList(filtroExceso, filtroInadecuada, filtroNoLeGusta);
	}
	

	@Test
	public void testEjecutarCommandMarcarComoFavoritasATodasLasRecetas() {
		repositorio.ejecutarAcciones(federicoHipper, consulta, null);
		assertTrue(federicoHipper.getHistorial().containsAll(consulta));
	}
	
	@Test
	public void testNoHayEfectoEnMarcarUnaRecetaQueYaEstaComoFavorita() {
		federicoHipper.marcarFavorita(receta2);
		repositorio.ejecutarAcciones(federicoHipper, consulta, null);
		assertTrue(federicoHipper.getHistorial().containsAll(consulta));
	}
	
	
	/*public boolean mockEnviarMail(Usuario usuario, List<Receta> consulta, List<Filtro> filtros) throws MessagingException {
	   Mail mail = new Mail();
	  mail.enviarMail(usuario, consulta, filtros);
			return true;
	}*/
	@Test
	public void testEnviarMail() {
		Mail mail = new Mail();
		 mail.enviarMail(fecheSena, consulta, filtros);
	}
	
	


}
