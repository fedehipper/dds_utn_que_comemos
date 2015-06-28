package grupo4.dds;

import static org.junit.Assert.assertEquals;
import grupo4.dds.command.LoguearConsultas;
import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Before;
import org.junit.Test;

public class TestLogs {
	
	private List<Receta> mockConsultas = new ArrayList<>();
	private Receta receta;
	private Usuario cristian;
	
	@Before
	public void setUp() {
		
		cristian = Usuario.crearPerfil("Cristian Maldonado", null, null, 1.81f, 87.0f, null, true, null);
		for(int i = 0; i<110;i++){
			mockConsultas.add(receta);
		}
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
        Logger.getLogger(TestLogs.class).info("Test");
        final List<LoggingEvent> log = appender.getLog();
        final LoggingEvent firstLogEntry = log.get(0);
        assertEquals(firstLogEntry.getLevel(),Level.INFO);
        assertEquals((String) firstLogEntry.getMessage(),"Test");
        assertEquals(firstLogEntry.getLoggerName(),"grupo4.dds.TestLogs");

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
