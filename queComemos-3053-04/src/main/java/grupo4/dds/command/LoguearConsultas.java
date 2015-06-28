package grupo4.dds.command;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

import java.net.URL;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.Loader;

public class LoguearConsultas implements Command{

	private Logger log = Logger.getLogger(LoguearConsultas.class);
	private List<Receta> consultas;

	public LoguearConsultas(List<Receta> consultas){
		this.consultas = consultas;
	}

	public void ejecutar(Usuario usuario) {
		URL url = Loader.getResource("log4j.properties");
		PropertyConfigurator.configure(url);
		if (consultas.size() > 100){
			if (log.isInfoEnabled())
				log.info("Consultas Con Mas De 100 Resultados");	

		}
	}
	
}
