package grupo4.dds.usuario.condicion;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Alimento;
import grupo4.dds.usuario.Usuario;

import java.util.ArrayList;
import java.util.Collection;

import static grupo4.dds.usuario.Alimento.*;
public class Vegano implements Condicion {

	public boolean esValido(Usuario usuario) {
		Collection<Alimento> palabrasClave = new ArrayList<>();
		palabrasClave.add(POLLO);
		palabrasClave.add(CARNE);
		palabrasClave.add(CHIVITO);
		palabrasClave.add(CHORI);
		// ver si se puede agregar todos las comidas de una a palabrasClave
		return usuario.getPreferenciasAlimenticias().containsAll(palabrasClave);

	}

	public boolean cumpleNecesidades(Usuario usuario) {
		return usuario.leGusta(FRUTAS);
	}
	
	public boolean esRecomendable(Receta receta) {
		return !(receta.tenesIngrediente("carne") || receta.tenesIngrediente("chivito") ||
				 receta.tenesIngrediente("chori") || receta.tenesIngrediente("pollo") );
		// hay que ver si se puede reducir este metodo
	}

}
