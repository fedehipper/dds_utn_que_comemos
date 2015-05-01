package grupo4.dds;

import java.util.ArrayList;
import java.util.Collection;

public class Vegano implements Condicion {

	public boolean esValido(Usuario usuario) {
		Collection<String> palabrasClave = new ArrayList<>();
		palabrasClave.add("pollo");
		palabrasClave.add("carne");
		palabrasClave.add("chivito");
		palabrasClave.add("chori");
		// ver si se puede agregar todos las comidas de una a palabrasClave
		return usuario.getPreferenciasAlimenticias().containsAll(palabrasClave);

	}

	public boolean cumpleNecesidades(Usuario usuario) {
		return usuario.leGusta("frutas");
	}
	
	public boolean esRecomendable(Receta receta) {
		return !(receta.tenesIngrediente("carne") || receta.tenesIngrediente("chivito") ||
				 receta.tenesIngrediente("chori") || receta.tenesIngrediente("pollo") );
		// hay que ver si se puede reducir este metodo
	}

}
