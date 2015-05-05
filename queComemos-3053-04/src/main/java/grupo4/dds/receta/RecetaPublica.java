package grupo4.dds.receta;

import grupo4.dds.usuario.Usuario;

import java.util.HashMap;
import java.util.Collection;

public class RecetaPublica extends Receta {

	/* Constructores */

	public RecetaPublica() {
		super(null);
	};// Creado para testear por ahora

	public RecetaPublica(Usuario creador, String nombreDelPlato,
			HashMap<String, Float> ingredientes,
			HashMap<String, Float> condimentos, String preparacion,
			int totalCalorias, String dificultad, Temporada temporada,
			Collection<Receta> subReceta) {
		super(null, nombreDelPlato, ingredientes, condimentos, preparacion,
				totalCalorias, dificultad, temporada, subReceta);
	}

	/* Servicios */

	public boolean puedeSerVistaPor(Usuario unUsuario) {
		return true;
	}

	public boolean puedeSerModificadaPor(Usuario unUsuario) {
		return true;
	}

	public void modificarEncabezado(Usuario usuario, String nombreDelPlato,
			String dificultad, Temporada temporada)
			throws NoTienePermisoParaModificar {

		Receta receta = convertirEnPrivada(usuario);
		receta.modificarEncabezado(usuario, nombreDelPlato, dificultad,
				temporada);
	}

	public void modificarDetalle(Usuario usuario,
			HashMap<String, Float> ingredientes,
			HashMap<String, Float> condimentos, String preparacion,
			Collection<Receta> subRecetas) throws NoTienePermisoParaModificar {

		Receta receta = convertirEnPrivada(usuario);
		receta.modificarDetalle(usuario, ingredientes, condimentos,
				preparacion, subRecetas);
	}

	public Receta convertirEnPrivada(Usuario usuario) {

		return Receta.crearNueva(usuario, nombreDelPlato, ingredientes,
				condimentos, preparacion, totalCalorias, dificultad, temporada,
				subRecetas);

	}

}
