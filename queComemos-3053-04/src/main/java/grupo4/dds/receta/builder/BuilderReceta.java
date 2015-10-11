package grupo4.dds.receta.builder;

import grupo4.dds.misc.CoberturaIgnore;
import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;

public class BuilderReceta extends Builder<Receta> {
	
	@CoberturaIgnore
	static public Receta buildRecetaValida() {	
		BuilderReceta builder = new BuilderReceta();
		return builder.calorias(10).ingrediente(Ingrediente.nuevoIngrediente("", 0)).build();
	}
	
	@Override
	@CoberturaIgnore
	protected Receta receta(){
		return new Receta();
	}
	
}
