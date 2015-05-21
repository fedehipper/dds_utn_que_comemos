package grupo4.dds.usuario.condicion;

import static grupo4.dds.usuario.Rutina.ACTIVA_EJERCICIO_ADICIONAL;
import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

public class Hipertenso implements Condicion {

	public boolean esValidaCon(Usuario usuario) {
		return !usuario.getPreferenciasAlimenticias().isEmpty();
	}

	public boolean subsanaCondicion(Usuario usuario) {
		return usuario.tieneRutina(ACTIVA_EJERCICIO_ADICIONAL);
	}

	public boolean esRecomendable(Receta receta) {
		return !(receta.tieneCondimento("sal") || receta.tieneCondimento("caldo"));
	}
}
