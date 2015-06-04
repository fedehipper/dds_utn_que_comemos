package grupo4.dds.usuario.condicion;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

public class Celiaco extends Condicion {

	public boolean esValidaCon(Usuario usuario) {
		return true;
	}

	public boolean subsanaCondicion(Usuario usuario) {
		return true;
	}

	public boolean esRecomendable(Receta receta) {
		return true;
	}

}
