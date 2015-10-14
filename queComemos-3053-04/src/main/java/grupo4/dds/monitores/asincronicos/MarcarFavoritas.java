package grupo4.dds.monitores.asincronicos;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.usuario.Usuario;

import java.util.List;
import java.util.function.Consumer;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("favoritas")
public class MarcarFavoritas extends MonitorAsincronico {

	@Override
	public Consumer<Usuario> operacion(List<Receta> resultadoConsulta, List<Filtro> parametros) {
		return (Usuario u) -> {
			if (u.getMarcaFavorita())
				u.marcarFavoritas(resultadoConsulta);
		};
	}
}
