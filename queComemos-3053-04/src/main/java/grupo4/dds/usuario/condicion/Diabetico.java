package grupo4.dds.usuario.condicion;

import static grupo4.dds.usuario.Rutina.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

@Entity
@DiscriminatorValue(value = "D")
public class Diabetico extends Condicion {

	private static Condicion self = new Diabetico();
	
	public static Condicion instance() {
		return self;
	}

	private Diabetico() { id = 4; nombre="diabetico";}
	
	public boolean esValidaCon(Usuario usuario) {
		return usuario.getSexo() != null && usuario.tienePreferenciasAlimenticias();
	}

	public boolean subsanaCondicion(Usuario usuario) {
		return usuario.tieneRutina(ACTIVA_SIN_EJERCICIO_ADICIONAL) || usuario.tieneRutina(ACTIVA_EJERCICIO_ADICIONAL)
															   || usuario.getPeso() <= 70;
	}

	public boolean esRecomendable(Receta receta) {
		return receta.cantidadCondimento("azucar") <= 100;
	}
	
}
