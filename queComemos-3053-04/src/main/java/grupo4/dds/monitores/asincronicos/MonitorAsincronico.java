package grupo4.dds.monitores.asincronicos;

import java.util.List;
import java.util.function.Consumer;

import grupo4.dds.monitores.Monitor;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.repositorios.RepositorioDeTareas;
import grupo4.dds.usuario.Usuario;

public abstract class MonitorAsincronico implements Monitor {

	@Override
	public void notificarConsulta(Usuario usuario, List<Receta> resultadoConsulta, List<Filtro> parametros) {
		RepositorioDeTareas.instance().agregarTarea(new TareaPendiente(usuario, operacion(resultadoConsulta, parametros)));
	}
	
	public abstract Consumer<Usuario> operacion(List<Receta> resultadoConsulta, List<Filtro> parametros);

}
