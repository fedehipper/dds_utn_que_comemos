package grupo4.dds.monitores.asincronicos.tareas;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.usuario.Usuario;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("favoritas")
public class TareaMarcadoFavoritas extends TareaPendiente {

	public TareaMarcadoFavoritas() {
	}

	public TareaMarcadoFavoritas(Usuario usuario, List<Receta> resultadoConsulta, List<Filtro> parametros) {
		super(usuario, resultadoConsulta, parametros);
	}
	
	@Override
	public void ejecutar() {
		if(usuario.getMarcaFavorita())
			usuario.marcarFavoritas(resultadoConsulta);
	}

}
