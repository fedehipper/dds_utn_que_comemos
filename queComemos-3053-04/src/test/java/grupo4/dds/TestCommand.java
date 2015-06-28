package grupo4.dds;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import grupo4.dds.command.LoguearConsultas;
import grupo4.dds.command.MarcarRecetasFavoritas;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.usuario.Usuario;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Before;
import org.junit.Test;

import queComemos.entrega3.dominio.Dificultad;


public class TestCommand {
		
	private Usuario federicoHipper;
	private Usuario cristian;
	private RepositorioDeRecetas repositorio = RepositorioDeRecetas.get();
	
	private Receta receta2;
	private Receta receta3;
	private RecetaPublica receta6;
	private RecetaPublica receta7;
	private RecetaPublica receta8;
	
	private List<Receta> mockConsultas = new ArrayList<>();
	private Receta receta;
	
	private List<Receta> consulta = new ArrayList<Receta>();
	
	
	@Before
	public void setUp() {
		repositorio.vaciar();
		
		federicoHipper = Usuario.crearPerfil("Federico Hipperdinger", null, null, 1.91f, 99.0f, null, true, null);
		receta2 = Receta.crearNueva(federicoHipper, new EncabezadoDeReceta("receta2", null, Dificultad.DIFICIL, 300), null);
		receta3 = Receta.crearNueva(federicoHipper, new EncabezadoDeReceta("receta3", null, null, 600), null);
		receta6 = RecetaPublica.crearNueva(new EncabezadoDeReceta("receta6", null, null, 200), null);
		receta7 = RecetaPublica.crearNueva(new EncabezadoDeReceta("receta7", null, null, 300), null);
		receta8 = RecetaPublica.crearNueva(new EncabezadoDeReceta("receta8", null, null, 100), null);
		
		cristian = Usuario.crearPerfil("Cristian Maldonado", null, null, 1.81f, 87.0f, null, true, null);
		for(int i = 0; i<110;i++){
			mockConsultas.add(receta);
		}
		
		consulta = Arrays.asList(receta2, receta3, receta6, receta7, receta8);
	}
	

	@Test
	public void testUnitarioMarcarComoFavoritaATodasLasRecetas() {
		MarcarRecetasFavoritas marcarFavoritas = new MarcarRecetasFavoritas(consulta);
		marcarFavoritas.ejecutar(federicoHipper);
		assertTrue(federicoHipper.getHistorial().containsAll(consulta));
	}
	
	@Test
	public void testUnitarioNoHayEfectoEnMarcarUnaRecetaQueYaEstaComoFavorita() {
		federicoHipper.marcarFavorita(receta2);
		MarcarRecetasFavoritas marcarFavoritas = new MarcarRecetasFavoritas(consulta);
		marcarFavoritas.ejecutar(federicoHipper);
		assertTrue(federicoHipper.getHistorial().containsAll(consulta));
	}
	
	@Test
	public void testEjecutarCommandMarcarComoFavoritasATodasLasRecetas() {
		repositorio.agregarAcciones(federicoHipper, consulta, null);
		federicoHipper.ejecutarMarcadoPendiente();
		assertTrue(federicoHipper.getHistorial().containsAll(consulta));
	}
	
	@Test
	public void testNoHayEfectoEnMarcarUnaRecetaQueYaEstaComoFavorita() {
		federicoHipper.marcarFavorita(receta2);
		repositorio.agregarAcciones(federicoHipper, consulta, null);
		federicoHipper.ejecutarMarcadoPendiente();
		assertTrue(federicoHipper.getHistorial().containsAll(consulta));
	}
	
	@Test
	public void testConsultas(){
		LoguearConsultas logss = new LoguearConsultas(mockConsultas);		
		logss.ejecutar(cristian);
	}

    @Test
    public void testLogs() {
       final TestAppender appender = new TestAppender();
       final Logger logger = Logger.getRootLogger();
        logger.addAppender(appender);
        Logger.getLogger(TestCommand.class).info("Esto es un test");
        final List<LoggingEvent> log = appender.getLog();
        final LoggingEvent firstLogEvent = log.get(0);
        assertEquals(firstLogEvent.getLevel(),Level.INFO);
        assertEquals((String) firstLogEvent.getMessage(),"Esto es un test");
        assertEquals(firstLogEvent.getLoggerName(),"grupo4.dds.TestCommand");

    }
    
    class TestAppender extends AppenderSkeleton {
        private final List<LoggingEvent> log = new ArrayList<LoggingEvent>();

        @Override
        public boolean requiresLayout() {
            return false;
        }

        @Override
        protected void append(final LoggingEvent loggingEvent) {
            log.add(loggingEvent);
        }

        @Override
        public void close() {
        }

        public List<LoggingEvent> getLog() {
            return new ArrayList<LoggingEvent>(log);
        }
    }
    
}
