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

		return usuario.getPreferenciasAlimenticias().containsAll(palabrasClave);

	}

	public boolean cumpleNecesidades(Usuario usuario) {
		return usuario.leGusta("frutas");
	}

}
