package grupo4.dds.usuario.condicion;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

public class Diabetico implements Condicion {

	public boolean esValido(Usuario usuario) {

		return (usuario.getSexo() != null)
				&& (usuario.getPreferenciasAlimenticias().size() > 0);
	}

	public boolean cumpleNecesidades(Usuario usuario) {
		return (usuario.tieneRutinaActivaConEjercicioAdicional()
				|| (usuario.tieneRutinaActivaSinEjercicioAdicional()))
				&& (!(usuario.pesaMasDe(70.0)));

	}

	public boolean esRecomendable(Receta receta) {
		return (receta.cantidadCondimento("azucar") <= 100);
	}
	
}
