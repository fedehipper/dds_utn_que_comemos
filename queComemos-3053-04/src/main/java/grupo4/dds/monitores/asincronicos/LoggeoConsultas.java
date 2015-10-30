package grupo4.dds.monitores.asincronicos;

import grupo4.dds.monitores.asincronicos.tareas.TareaLoggeo;
import grupo4.dds.monitores.asincronicos.tareas.TareaPendiente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.usuario.Usuario;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

@Entity
@DiscriminatorValue("loggeo")
public class LoggeoConsultas extends MonitorAsincronico {

	private static LoggeoConsultas self = new LoggeoConsultas();

	@Transient
	public Logger logger;
	
	public static LoggeoConsultas instance() {
		return self;
	}

	
	private LoggeoConsultas() {
		logger = Logger.getLogger(LoggeoConsultas.class);
		PropertyConfigurator.configure("log4j.properties");
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	@Override
	public TareaPendiente nuevaTarea(Usuario usuario, List<Receta> resultadoConsulta, List<Filtro> parametros) {
		return new TareaLoggeo(usuario, resultadoConsulta, parametros);
	}

}
