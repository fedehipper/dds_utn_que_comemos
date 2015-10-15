package grupo4.dds.monitores.asincronicos;

import grupo4.dds.monitores.asincronicos.tareas.TareaMarcadoFavoritas;
import grupo4.dds.monitores.asincronicos.tareas.TareaPendiente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.usuario.Usuario;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("favoritas")
public class MarcarFavoritas extends MonitorAsincronico {

	private static MarcarFavoritas self = new MarcarFavoritas();
	
	public static MarcarFavoritas instance() {
		return self;
	}

	private MarcarFavoritas() {}

	@Override
	public TareaPendiente nuevaTarea(Usuario usuario, List<Receta> resultadoConsulta, List<Filtro> parametros) {
		return new TareaMarcadoFavoritas(usuario, resultadoConsulta, parametros);
	}
	

}
