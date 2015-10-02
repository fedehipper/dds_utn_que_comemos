package grupo4.dds.receta.builder;

import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;

public class BuilderReceta extends Builder {
	
	static public Receta buildRecetaValida() {	
		
		Builder builder = new BuilderReceta();
		return builder.calorias(10).ingrediente(Ingrediente.nuevoIngrediente("", 0)).build();
	}
	
	@Override
	protected Receta receta(){
		return new Receta();
	}
	
}
