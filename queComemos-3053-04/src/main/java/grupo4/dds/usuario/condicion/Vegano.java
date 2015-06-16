package grupo4.dds.usuario.condicion;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

public class Vegano extends Condicion {

	
	public boolean esValidaCon(Usuario usuario) {
		return !usuario.leGustaLaCarne();
	}

	public boolean subsanaCondicion(Usuario usuario) {
		return usuario.leGusta("fruta");
	}

	public boolean esRecomendable(Receta receta) {
		return !receta.tieneCarne();
	}
	
}
