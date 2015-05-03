package grupo4.dds.usuario.condicion;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;


public class Hipertenso implements Condicion {

	public boolean esValido(Usuario usuario) {

		return usuario.getPreferenciasAlimenticias().size() > 0;
	}

	public boolean cumpleNecesidades(Usuario usuario) {
		return usuario.tieneRutinaActivaConEjercicioAdicional();
	}
	
	public boolean esRecomendable(Receta receta) {
		return !(receta.tenesIngrediente("sal") || (receta.tenesIngrediente("caldo")));
	}

 
}
