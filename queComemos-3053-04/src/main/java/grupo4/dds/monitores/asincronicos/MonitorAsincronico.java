package grupo4.dds.monitores.asincronicos;

import grupo4.dds.monitores.Monitor;
import grupo4.dds.monitores.asincronicos.tareas.TareaPendiente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.repositorios.RepositorioDeTareas;
import grupo4.dds.usuario.Usuario;

import java.util.List;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class MonitorAsincronico extends Monitor {

	@Override
	public void notificarConsulta(Usuario usuario, List<Receta> resultadoConsulta, List<Filtro> parametros) {
		RepositorioDeTareas.instance().add(nuevaTarea(usuario, resultadoConsulta, parametros));
	}
	
	public abstract TareaPendiente nuevaTarea(Usuario usuario, List<Receta> resultadoConsulta, List<Filtro> parametros);

}
