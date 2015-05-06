package grupo4.dds.usuario.condicion;

import static grupo4.dds.usuario.Alimento.*;
import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Alimento;
import grupo4.dds.usuario.Usuario;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Vegano implements Condicion {

	static Collection<Alimento> Carnes = Arrays.asList(POLLO, CARNE, CHIVITO, CHORI);
	static Collection<String> IngredientesConCarne = Alimento.mapIngrediente(Carnes);

	public boolean esValidaCon(Usuario usuario) {
		return Collections.disjoint(Carnes,
				usuario.getPreferenciasAlimenticias());
	}

	public boolean subsanaCondicion(Usuario usuario) {
		return usuario.leGusta(FRUTAS);
	}

	public boolean esRecomendable(Receta receta) {
		return Collections.disjoint(IngredientesConCarne, receta.getNombreIngredientes());
	}

}
