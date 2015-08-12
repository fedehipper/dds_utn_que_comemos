package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RecetasMasConsultadas implements Monitor {

	private Map<Receta, Integer> contadorRecetas = new HashMap<>();

	public void notificarConsulta(List<Receta> recetas, Usuario u) {

		recetas.forEach((receta) -> contadorRecetas.merge(receta, 1, (cant1,
				cant2) -> cant1 + cant2));
	}

	public Map<Receta, Integer> recetasMasConsultadas(int cantidad) {

		return contadorRecetas.keySet().stream()
				.sorted((r1, r2) -> getCantidad(r2) - getCantidad(r1))
				.limit(cantidad)
				.collect(Collectors.toMap(r -> r, r -> getCantidad(r)));
	}

	private int getCantidad(Receta receta) {
		return contadorRecetas.get(receta);
	}

}