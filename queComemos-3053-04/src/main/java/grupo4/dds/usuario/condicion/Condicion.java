package grupo4.dds.usuario.condicion;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

public interface Condicion {

	public boolean esValidaCon(Usuario usuario);

	public boolean subsanaCondicion(Usuario usuario);

	// TODO ver si es necesario para el punto 4
	public boolean esRecomendable(Receta receta);

}
