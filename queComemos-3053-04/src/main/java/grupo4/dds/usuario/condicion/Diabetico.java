package grupo4.dds.usuario.condicion;

import static grupo4.dds.usuario.Rutina.*;
import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;
public class Diabetico implements Condicion {

	public boolean esValidoCon(Usuario usuario) {

		return (usuario.getSexo() != null)
				&& (usuario.getPreferenciasAlimenticias().size() > 0);
	}

	public boolean subsanaCondicion(Usuario usuario) {
		return (usuario.tieneRutina(ACTIVA_EJERCICIO_ADICIONAL)
				|| (usuario.tieneRutina(ACTIVA_SIN_EJERCICIO_ADICIONAL)))
				&& (!(usuario.getPeso() < 70.0));

	}

	public boolean esRecomendable(Receta receta) {
		return (receta.cantidadCondimento("azucar") <= 100);
	}
	
}
