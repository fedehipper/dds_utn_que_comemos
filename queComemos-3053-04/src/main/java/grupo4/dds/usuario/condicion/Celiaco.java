package grupo4.dds.usuario.condicion;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

public class Celiaco implements Condicion {

	public boolean esValido(Usuario usuario) {
		return true;
	}

	public boolean cumpleNecesidades(Usuario usuario) {
		return true;
	}
	
	public boolean esRecomendable(Receta receta) {
		return true;
	}

}
