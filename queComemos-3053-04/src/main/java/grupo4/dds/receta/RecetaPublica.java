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
			Collection<Receta> subRecetas) throws NoTienePermisoParaModificarReceta {

		Receta receta = convertirEnPrivada(usuario);
		
		try {
		usuario.agregarReceta(receta);
		}
		catch(NoTienePermisoParaAgregarReceta e){}
		
		receta.modificarReceta(usuario, encabezado, ingredientes, condimentos,
				preparacion, subRecetas);
	}

	private Receta convertirEnPrivada(Usuario usuario) {

		return new Receta(usuario, encabezado, preparacion);

	}

}
