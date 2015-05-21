package grupo4.dds.usuario.condicion;

import static grupo4.dds.usuario.Rutina.*;
import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

public class Diabetico implements Condicion {

	public boolean esValidaCon(Usuario usuario) {
		return usuario.getSexo() != null && !usuario.getPreferenciasAlimenticias().isEmpty();
	}

	public boolean subsanaCondicion(Usuario usuario) {
		return usuario.tieneRutina(ACTIVA_EJERCICIO_ADICIONAL) || usuario.tieneRutina(ACTIVA_EJERCICIO_ADICIONAL)
															   || usuario.getPeso() <= 70;
	}

	public boolean esRecomendable(Receta receta) {
		Float cantAzucar = receta.cantidadCondimento("azucar");
		return cantAzucar != null && cantAzucar <= 100;
	}
	
}
