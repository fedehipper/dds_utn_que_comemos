package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.usuario.Usuario;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractRecetasMasConsultadas extends Monitor {

	public abstract void notificarConsulta(Usuario usuario, List<Receta> resultadoConsulta, List<Filtro> parametros);

	protected void seRealizoUnaConsulta(Map<Receta, Integer> consultasAnteriores, List<Receta> resultadoNuevaConsulta) {

		resultadoNuevaConsulta.forEach(receta -> consultasAnteriores.merge(receta, 1, (cant1, cant2) -> cant1 + cant2));
	}

	protected Map<Receta, Integer> recetasMasConsultadas(Map<Receta, Integer> recetasConsultadas, int cantidad) {

		return recetasConsultadas.keySet().stream()
				.sorted((r1, r2) -> recetasConsultadas.get(r2) - recetasConsultadas.get(r1))
				.limit(cantidad)
				.collect(Collectors.toMap(r -> r, r -> recetasConsultadas.get(r)));
	}

}