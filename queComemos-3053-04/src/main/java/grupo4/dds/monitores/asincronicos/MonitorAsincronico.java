package grupo4.dds.monitores.asincronicos;

import grupo4.dds.monitores.Monitor;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.repositorios.RepositorioDeTareas;
import grupo4.dds.usuario.Usuario;

import java.util.List;
import java.util.function.Consumer;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class MonitorAsincronico extends Monitor {

	@Override
	public void notificarConsulta(Usuario usuario, List<Receta> resultadoConsulta, List<Filtro> parametros) {
		RepositorioDeTareas.instance().agregarTarea(new TareaPendiente(usuario, operacion(resultadoConsulta, parametros)));
	}
	
	public abstract Consumer<Usuario> operacion(List<Receta> resultadoConsulta, List<Filtro> parametros);

}
