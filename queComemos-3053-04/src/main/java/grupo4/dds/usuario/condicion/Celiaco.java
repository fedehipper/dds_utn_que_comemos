package grupo4.dds.usuario.condicion;

import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.usuario.Usuario;

public class Celiaco implements Condicion {

	public boolean esValidaCon(Usuario usuario) {
		return true;
	}

	public boolean subsanaCondicion(Usuario usuario) {
		return true;
	}

	public boolean esRecomendable(RecetaPublica receta) {
		return true;
	}

}
