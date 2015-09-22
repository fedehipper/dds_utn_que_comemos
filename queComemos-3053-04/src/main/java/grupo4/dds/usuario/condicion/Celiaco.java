package grupo4.dds.usuario.condicion;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

@Entity
@DiscriminatorValue(value = "C")
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
