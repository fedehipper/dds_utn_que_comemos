package grupo4.dds.usuario.condicion;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

public interface Condicion {

	public boolean esValido(Usuario usuario);

	public boolean cumpleNecesidades(Usuario usuario);

	public boolean esRecomendable(Receta receta);
}
