package grupo4.dds.usuario.condicion;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

public abstract class Condicion {
	
	public abstract boolean esValidaCon(Usuario usuario);

	public abstract boolean subsanaCondicion(Usuario usuario);

	public abstract boolean esRecomendable(Receta receta);

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;
		else
			return true;
	}

}
