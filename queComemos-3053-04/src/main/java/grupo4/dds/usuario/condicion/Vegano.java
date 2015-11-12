package grupo4.dds.usuario.condicion;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

@Entity
@DiscriminatorValue(value = "V")
public class Vegano extends Condicion {
	
	private static Condicion self = new Vegano();
	
	public static Condicion instance() {
		return self;
	}

	private Vegano() { id = 3; nombre = "vegano";}
	
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
