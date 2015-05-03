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
			Collection<RecetaPublica> subReceta) {
		super(null, nombreDelPlato, ingredientes, condimentos, preparacion,
				totalCalorias, dificultad, temporada, subReceta);
	}

	/* Servicios */

	public boolean puedeSerVistaOModificadaPor(Usuario unUsuario) {
		return true;
	}

	// TODO arreglar este metodo
	public void serModificadaPor(Usuario unUsuario, String nombre,
			HashMap<String, Float> ingredientes,
			HashMap<String, Float> condimentos, String preparacion,
			int calorias, String dificultad, Temporada temporada,
			Collection<RecetaPublica> subReceta) {
		RecetaPublica unaReceta = new RecetaPublica(unUsuario, nombre,
				ingredientes, condimentos, preparacion, calorias, dificultad,
				temporada, subReceta);
		unUsuario.agregarReceta(unaReceta);
	}

}
