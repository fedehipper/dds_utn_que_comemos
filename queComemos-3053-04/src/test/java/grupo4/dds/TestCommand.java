package grupo4.dds;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.verify;
import grupo4.dds.command.CommandMailSender;
import grupo4.dds.command.LoguearConsultas;
import grupo4.dds.command.Mail;
import grupo4.dds.command.MailSenderPosta;
import grupo4.dds.command.MarcarRecetasFavoritas;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.receta.busqueda.filtros.FiltroNoLeGusta;
import grupo4.dds.repositorios.RepositorioDeRecetas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class TestCommand extends BaseTest {
		
	private RepositorioDeRecetas repositorio = RepositorioDeRecetas.get();
	
	private List<Receta> respuestaCon101Recetas = new ArrayList<>();
	private List<Receta> consulta= new ArrayList<>();
	private List<Filtro> filtros =  new ArrayList<>();
	
	private MailSenderPosta mailSender = Mockito.mock(MailSenderPosta.class);
	private MockLogger mockLogger;
	
	private class MockLogger extends Logger {
		
		protected MockLogger(String name) {
			super(name);
		}
		
		@Override
		public
		boolean isInfoEnabled() {
			return true;			
		}
		
		String logMessage = null;
		boolean seRegistroElLog = false;
		
		@Override
		public void info(Object logMessage) {
			this.logMessage = (String) logMessage;			
		}
	}
	
	@Before
	public void setUp() {
		
		for(int i = 0; i<110;i++) {
			respuestaCon101Recetas.add(milanesa);
		}
		
		federicoHipper.setMarcaFavorita(true);
		mockLogger = new MockLogger(null);		
		consulta = Arrays.asList(pollo, pure, salmon, lomito, coliflor);
	}

	@Test
	public void testMarcarComoFavoritaATodasLasRecetas() {
		MarcarRecetasFavoritas marcarFavoritas = new MarcarRecetasFavoritas(consulta);
		marcarFavoritas.ejecutar(federicoHipper);
		assertTrue(federicoHipper.getHistorial().containsAll(consulta));
	}
	
	@Test
	public void testNoSeMarcanComoFavoritasSiElUsuarioNoTieneLaOpcionActivada() {
		MarcarRecetasFavoritas marcarFavoritas = new MarcarRecetasFavoritas(consulta);
		marcarFavoritas.ejecutar(fecheSena);
		assertTrue(fecheSena.getHistorial().isEmpty());
	}
	
	@Test
	public void testEjecutarCommandMarcarComoFavoritasATodasLasRecetas() {
		repositorio.agregarAcciones(federicoHipper, consulta, null);
		federicoHipper.ejecutarMarcadoPendiente();
		assertTrue(federicoHipper.getHistorial().containsAll(consulta));
	}
	
	@Test
	public void testNoHayEfectoEnMarcarUnaRecetaQueYaEstaComoFavorita() {
		federicoHipper.marcarFavorita(pollo);
		repositorio.agregarAcciones(federicoHipper, consulta, null);
		federicoHipper.ejecutarMarcadoPendiente();
		assertTrue(federicoHipper.getHistorial().containsAll(consulta));
	}
	
	@Test
	public void testAgregarEnvioDeMailEnMailPendientesEnRepositorioDeReceta() {
		CommandMailSender accionMail = new CommandMailSender(fecheSena, consulta, null);
		repositorio.agregarEnvioMail(accionMail);
		assertTrue(repositorio.getMailPendientes().contains(accionMail));
	}	

	
	@Test (expected = RuntimeException.class)
	public void testNoCreaMensajeMailConFiltrosNulos() {
		Mail mail = new Mail(fecheSena, consulta, null);
		mail.crearMensaje();	
	}
	
	@Test (expected = RuntimeException.class)
	public void testNoCreaMensajeMailConConsultaNula() {
		Mail mail = new Mail(fecheSena, null, filtros);
		mail.crearMensaje();
		
	}
	
	@Test (expected = RuntimeException.class)
	public void testNoCreaMensajeMailConUsuarioNulo() {
		Mail mail = new Mail(null, consulta, filtros);
		mail.crearMensaje();
		
	}
	
	@Test
	public void testCrearMensajeEnMail(){
		filtros.add(new FiltroNoLeGusta());	
		Mail mail = new Mail(fecheSena, consulta, filtros );
		assertTrue(!(mail.crearMensaje().isEmpty()));
		
	}
	
	@Test
	public void testEnviarMailAMailSenderConMensaje(){
		Mail otroMail= Mockito.mock(Mail.class);
		otroMail.crearMensaje();
		//otroMail.enviarMail(mailSender);
		doNothing().when(otroMail).enviarMail(mailSender);
		
	}	
	
	/* Mockito que acciona envio de mail */
	@Test
	public void testEnviarMailEnMailSender(){
		Mail otroMail = new Mail();
		mailSender.enviarMail(otroMail);
		verify(mailSender, times(1)).enviarMail(any(Mail.class));
	}
	
	/* Mockito para preparar Mail en Mailsender*/
	@Test 
	public void testEnviarAMailSender(){
		Mail otroMail = Mockito.mock(Mail.class);
		otroMail.enviarMail(mailSender);
		validateMockitoUsage();
	}
	
	@Test
	public void testEjecutarCommandMailSender(){
		CommandMailSender commandMail = new CommandMailSender(fecheSena, consulta, null);
		commandMail.ejecutar(fecheSena);
		validateMockitoUsage();
		
	}	
	
	@Test
	public void testNoLoggeaConsultasConMenosDe100Resultados(){
				
		LoguearConsultas logs = new LoguearConsultas(respuestaCon101Recetas.subList(0, 98));		
		logs.setLogger(mockLogger);
		
		logs.ejecutar(null);
		assertFalse(mockLogger.seRegistroElLog);
	}
	
	@Test
	public void testLoggeaConsultasConMasDe100Resultados(){

		LoguearConsultas logs = new LoguearConsultas(respuestaCon101Recetas);	
		logs.setLogger(mockLogger);
		
		logs.ejecutar(null);
		assertEquals("Consultas Con Mas De 100 Resultados", mockLogger.logMessage);
	}
    
}
