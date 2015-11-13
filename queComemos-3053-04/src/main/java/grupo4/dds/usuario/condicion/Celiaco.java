package grupo4.dds.usuario.condicion;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

@Entity
@DiscriminatorValue(value = "C")
public class Celiaco extends Condicion {

	private static Condicion self = new Celiaco();
	
	public static Condicion instance() {
		return self;
	}
	
	private Celiaco() { id = 1;nombre="celiaco"; }
	
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
