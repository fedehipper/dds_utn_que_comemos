package grupo4.dds.monitores.asincronicos.tareas;

import grupo4.dds.monitores.asincronicos.LoggeoConsultas;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.usuario.Usuario;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("loggeo")
public class TareaLoggeo extends TareaPendiente {
	
	public TareaLoggeo() {
	}

	public TareaLoggeo(Usuario usuario, List<Receta> resultadoConsulta, List<Filtro> parametros) {
		super(usuario, resultadoConsulta, parametros);
	}
	
	@Override
	public void ejecutar() {
		if (resultadoConsulta.size() > 100 && LoggeoConsultas.instance().logger.isInfoEnabled())
			LoggeoConsultas.instance().logger.info("Consultas Con Mas De 100 Resultados");	
	}

}
