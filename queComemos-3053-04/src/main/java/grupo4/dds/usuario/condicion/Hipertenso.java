package grupo4.dds.usuario.condicion;

import static grupo4.dds.usuario.Rutina.ACTIVA_EJERCICIO_ADICIONAL;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

@Entity
@DiscriminatorValue(value = "H")
public class Hipertenso extends Condicion {

	private static Condicion self = new Hipertenso();
	
	public static Condicion instance() {
		return self;
	}

	private Hipertenso() { id = 2; nombre="hipertenso"; }
	
	public boolean esValidaCon(Usuario usuario) {
		return usuario.tienePreferenciasAlimenticias();
	}

	public boolean subsanaCondicion(Usuario usuario) {
		return usuario.tieneRutina(ACTIVA_EJERCICIO_ADICIONAL);
	}

	public boolean esRecomendable(Receta receta) {
		return !(receta.tieneCondimento("sal") || receta.tieneCondimento("caldo"));
	}
}
