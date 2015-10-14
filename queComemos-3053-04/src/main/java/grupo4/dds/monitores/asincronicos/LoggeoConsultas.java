package grupo4.dds.monitores.asincronicos;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.usuario.Usuario;

import java.util.List;
import java.util.function.Consumer;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

@Entity
@DiscriminatorValue("loggeo")
public class LoggeoConsultas extends MonitorAsincronico {

	@Transient
	private Logger logger;
	
	public LoggeoConsultas() {
		logger = Logger.getLogger(LoggeoConsultas.class);
		PropertyConfigurator.configure("log4j.properties");
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Override
	public Consumer<Usuario> operacion(List<Receta> resultadoConsulta, List<Filtro> parametros) {
		return (Usuario u) -> {			
			if (resultadoConsulta.size() > 100 && logger.isInfoEnabled()) {
				logger.info("Consultas Con Mas De 100 Resultados");	
			}
		};
	}
}
