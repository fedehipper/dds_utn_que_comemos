package grupo4.dds.receta.busqueda.filtros;

import grupo4.dds.misc.CoberturaIgnore;
import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

public class FiltroRecetasCaras implements Filtro {

	private List<Ingrediente> ingredientesCaros = new ArrayList<>();
	
	public boolean test(Usuario u, Receta r) {
		return r.noContieneNinguna(ingredientesCaros);
	}
	
	/* Accesors and Mutators */

	public void agregarIngredienteCaro(Ingrediente unIngrediente) {
		ingredientesCaros.add(unIngrediente);
	}
	
	public void quitarIngredienteCaro(Ingrediente unIngrediente) {
		ingredientesCaros.remove(unIngrediente);
	}
	
	@Override
	@CoberturaIgnore
	public String toString() {
		return "RecetasCaras";
	}
	
}
