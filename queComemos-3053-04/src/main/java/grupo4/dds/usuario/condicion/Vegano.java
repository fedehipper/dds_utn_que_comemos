package grupo4.dds.usuario.condicion;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

public class Vegano implements Condicion {

	
	public boolean esValidaCon(Usuario usuario) {
		return (usuario.getPreferenciasAlimenticias().stream().allMatch(a -> !a.getEsCarne()));
	}

	public boolean subsanaCondicion(Usuario usuario) {
		return usuario.leGusta("fruta");
	}

	
	public boolean esRecomendable(Receta receta) {
		
		return receta.getIngredientes().stream().allMatch(i -> !i.getEsCarne());

	}
	
}
