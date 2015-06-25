package grupo4.dds.command;

import grupo4.dds.receta.Receta;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class LoguearConsultas implements Command{

	private Logger log = Logger.getLogger("ConsultasConMasDe100Resultados");
	private List<Receta> consultas;

	public LoguearConsultas(List<Receta> consultas){
		this.consultas = consultas;
	}

	public void ejecutar() {
		BasicConfigurator.configure();
		if (consultas.size() > 100){
			if (log.isTraceEnabled())
				log.trace("MasDe100Resultados");				
		}
	}
	
}
