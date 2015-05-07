package grupo4.dds.receta;

import grupo4.dds.usuario.Usuario;

import java.util.HashMap;
import java.util.Collection;

public class RecetaPublica extends Receta {

	/* Constructores */

	public RecetaPublica() {
		super(null);
	};// Creado para testear por ahora

	public RecetaPublica(EncabezadoDeReceta encabezado, String preparacion) {
		super(null, encabezado, preparacion);
	}

	/* Servicios */

	public boolean puedeSerVistaPor(Usuario unUsuario) {
		return true;
	}

	public boolean puedeSerModificadaPor(Usuario unUsuario) {
		return true;
	}

	public void modificarReceta(Usuario usuario, EncabezadoDeReceta encabezado,
			HashMap<String, Float> ingredientes,
			HashMap<String, Float> condimentos, String preparacion,
			Collection<Receta> subRecetas) throws NoTienePermisoParaModificar {

		Receta receta = convertirEnPrivada(usuario);
		receta.modificarReceta(usuario, encabezado, ingredientes, condimentos,
				preparacion, subRecetas);
	}

	private Receta convertirEnPrivada(Usuario usuario) {

		return Receta.crearNueva(usuario, encabezado, preparacion);

	}

}
