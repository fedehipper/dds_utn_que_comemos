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
			String dificultad, Temporada temporada,
			Collection<RecetaPublica> subReceta) {
		super(null, nombreDelPlato, ingredientes, condimentos, preparacion, dificultad, temporada, subReceta);
	}

	/* Servicios */

	public boolean puedeSerVistaOModificadaPor(Usuario unUsuario) {
		return true;
	}
	
	public boolean esElCreador(Usuario unUsuario) {
		return false;
	}
	

	// TODO arreglar este metodo
	public void serModificadaPor(Usuario unUsuario, String nombre,
			HashMap<String, Float> ingredientes,
			HashMap<String, Float> condimentos, String preparacion,
			String dificultad, Temporada temporada,
			Collection<RecetaPublica> subReceta) {
		Receta unaReceta = new RecetaPublica(unUsuario, nombre,
				ingredientes, condimentos, preparacion, dificultad,
				temporada, subReceta);
		unUsuario.agregarReceta(unaReceta);
	}

}
